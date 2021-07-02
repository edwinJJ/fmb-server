package com.server.fmb.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import com.server.fmb.engine.ITaskHandler.HandlerResult;
import com.server.fmb.engine.Types.SCENARIO_STATE;
import com.server.fmb.entity.Scenarios;
import com.server.fmb.entity.Steps;

import net.bytebuddy.asm.Advice.This;

public class ScenarioInstance {
	
	@Autowired
	SchedulerService schedulerService;
	
	public final static String[] ScenarioInstanceStatus = new String[] {"READY", "STARTED", "STOPPED", "HALTED", "UNLOADED"};
	
	private List<ScenarioInstance> subScenarioInstances = new ArrayList<ScenarioInstance>();
	
	private String domainId;
	private String scenarioName;
	private String instanceName;
	private Context context;
	
	private Steps[] steps = null;
	private int rounds = 0;
	private String message;
	private int lastStep = -1;
	private int nextStep = -1;
	private String schedule;
	private String timezone;
	private ScheduledFuture<?> cronjob;
	private Object disposer;
	
	public ScenarioInstance(String instanceName, Scenarios scenario, Context context) {
		this.instanceName = instanceName;
		this.scenarioName = scenario.getName();
		this.schedule = scenario.getSchedule();
		this.timezone = scenario.getTimezone();
		
		// TODO
		this.steps = null; //scenario.getSteps(); order by sequence;
		this.domainId = scenario.getDomainId();
		this.disposer = null; // context.disposer;
		this.context = new Context();
		this.context.domainId = domainId;
		
		try {
			this.context.instance = context != null ? context.instance : this;
			this.context.data = context != null ? context.data : null;
			this.context.variables = context != null ? context.variables : null;
			this.context.client = context != null ? context.client : null;
			this.context.state = SCENARIO_STATE.STOPPED;
			this.context.root = context != null ? context.root : this;
			this.context.closures = new ArrayList();

		} catch (Exception e) {}
		this.setState(SCENARIO_STATE.READY);
	}
	
	public List<ScenarioInstance> addSubScenarioInstance(ScenarioInstance instance) {
		this.subScenarioInstances.add(instance);
		return this.subScenarioInstances;
	}
	
	public List<ScenarioInstance> getSubScenarioInstance() {
		return this.subScenarioInstances;
	}
	
	@Async
	public void stopSubScenarios() {
		this.stopSubScenariosAwait();
	}
	public void stopSubScenariosAwait() {
		ScenarioInstance subScenarioInstance = this.subScenarioInstances.remove(0);
		while(subScenarioInstance != null) {
			subScenarioInstance.disposeAwait();
			subScenarioInstance = this.subScenarioInstances.remove(0);
		}		
	}
	
	@Async
	public void run() {
		this.runAwait();
	}
	 void runAwait() {		
		SCENARIO_STATE state = this.getState();
		if (state == SCENARIO_STATE.STARTED || this.steps.length == 0) {
			return;
		}
		
		this.setState(SCENARIO_STATE.STARTED);
		Context context = this.context;
		
		try {
			while(this.getState() == SCENARIO_STATE.STARTED) {
				if (this.nextStep == -1) {
					this.setNextStep(0);
				}
				
				if (this.nextStep == 0) {
					this.rounds++;
				}
				
				Steps step = this.steps[this.nextStep];
				String next;
				SCENARIO_STATE stepState = null;
				Object data;
				
				
				if (step.getSkip() != 1) {
					HandlerResult result = this.processAwait(step, context);
					next = result.next;
					stepState = result.state;
					data = result.data;
					if (context.data instanceof Map) {
						((Map)context.data).put(step.getName(), data);
					}
				} else {
					next = null;
					stepState = null;
				}
				
				this.publishState(domainId);
				
				if (next != null) {
					int nextStepIndex = -1;
					for (int i = 0; i < this.steps.length; i++) {
						if (this.steps[i].getName().equals(next)) {
							nextStepIndex = i;
							break;
						}
					}
					this.setNextStep(nextStepIndex);
					if (nextStepIndex == -1) {
						throw new Exception("Not Found Next Step");
					}
				} else if (this.nextStep == this.steps.length -1) {
					this.setState(SCENARIO_STATE.STOPPED);
				} else {
					this.setNextStep(this.nextStep+1);
				}
				
				if (stepState!= null) {
					this.setState(state);
				}
				
				Thread.sleep(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.setState(SCENARIO_STATE.HALTED);
		}		
	}
	
	@Async
	public Context loadSubscenario(Steps step, Scenarios scenarioConfig, Context context) throws Exception {
		return this.loadSubscenarioAwait(step, scenarioConfig, context);
	}
	public Context loadSubscenarioAwait(Steps step, Scenarios scenarioConfig, Context context) throws Exception {
		
		if (context.data instanceof Map) {
			((Map)context.data).put(step.getName(), null);			
		}
		Context subContext = context.clone();
		if (context.data instanceof Map) {
			subContext.data = ((Map)context.data).get(step.getName());			
		}
		subContext.closures = new ArrayList();
		subContext.state = SCENARIO_STATE.READY;
		
		if (scenarioConfig.getDomainId() != null) {
			scenarioConfig.setDomainId(context.domainId);
		}
		
		ScenarioInstance subScenarioInstance = new ScenarioInstance(this.instanceName+step.getName(), scenarioConfig, subContext);
		this.addSubScenarioInstance(subScenarioInstance);
		subScenarioInstance.runAwait();
		
		// TODO
	    // if (!preventErrorPropagation && subScenarioInstance.getState() == SCENARIO_STATE.HALTED) {
		if (subScenarioInstance.getState() == SCENARIO_STATE.HALTED) {
			throw new Exception("Sub-scenario[" + this.instanceName+step.getName() + "] is halted.");
		}
			
		return subContext;
	}
	
	
	public void publishData(String tag, Object data) {
		// TODO
//	    pubsub.publish('data', {
//	        data: {
//	          domain: this.context.domain,
//	          tag,
//	          data
//	        }
//	      })		
	}
	
	public void publishState(String message) {
		// TODO
//		pubsub.publish('scenario-instance-state', {
//			scenarioInstanceState: this
//		})		
	}
	
	public Map getProgress() {
		int steps = this.steps.length;
		int step = Math.max(this.lastStep, 0);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("rounds", this.rounds);
		result.put("rate", steps > 0 ? Math.round(100 * (step / steps)) : 0);
		result.put("steps", steps);
		result.put("step", step);		
		return result;
	}
	
	public void setNextStep(int step) {
		this.lastStep = this.nextStep+1;
		this.nextStep = step;
	}
	
	public SCENARIO_STATE getState() {
		return this.context.state;
	} 
	
	public void setState(SCENARIO_STATE state) {
		if (this.context.state == state) {
			return;
		}
		
		String log = this.instanceName + ":[state changed] " + ScenarioInstanceStatus[this.getState().ordinal()] + " => " + ScenarioInstanceStatus[state.ordinal()]; 
		
		this.message = "";
		
		this.context.state = state;
		
		if (state == SCENARIO_STATE.STARTED || state == SCENARIO_STATE.HALTED) {
			this.setNextStep(-1);
			if (this.cronjob == null) {
				this.setState(SCENARIO_STATE.UNLOADED);
			}
		} else if (state == SCENARIO_STATE.UNLOADED) {
			this.setNextStep(-1);
			this.dispose();
		}
		
		this.publishState(log);
	}
	
	void start() {
		if (this.schedule != null) {
			if (this.cronjob == null) {
				this.cronjob = schedulerService.start(this, this.schedule);
			}
		} else {
			this.run();
		}
	}
	
	void stop() {
		if (this.getState() != SCENARIO_STATE.HALTED) {
			this.setState(SCENARIO_STATE.STOPPED);
		}
	}

	void unload() {
		this.setState(SCENARIO_STATE.UNLOADED);
	}
	
	@Async
	void dispose() {
		this.disposeAwait();
	}
	void disposeAwait() {
		if (this.cronjob != null) {
			this.cronjob.cancel(true);
			this.cronjob = null;
		}
		
		this.stopSubScenariosAwait();
		
		this.unload();
		
		while (this.context.closures != null && this.context.closures.size() > 0) {
			Object closure = this.context.closures.remove(0);
			// TODO
			// closure.call(this);
		}
		
		if (this.disposer != null) {
			// TODO
			// await this.disposer.call(this);
		}
	}
	
	@Async
	HandlerResult process(Steps step, Context context) throws Exception {
		return this.processAwait(step, context);
	}
	HandlerResult processAwait(Steps step, Context context) throws Exception {
		// TODO
		//step.setParams(JSON.parse(step.getParams()));
		ITaskHandler handler = TaskRegistry.getTaskHandler(step.getTask());
		if (handler == null) {
			throw new Exception("No task handler for step " + step.getName() + step.getId());
		}
		
		HandlerResult retval = handler.runAwait(step, context);
		
		return retval;
		
	}
	
	
	public String getDomainId() {
		return domainId;
	}

	public void setDomainId(String domainId) {
		this.domainId = domainId;
	}


	public String getScenarioName() {
		return scenarioName;
	}


	public void setScenarioName(String scenarioName) {
		this.scenarioName = scenarioName;
	}


	public String getInstanceName() {
		return instanceName;
	}


	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}


	public Context getContext() {
		return context;
	}


	public void setContext(Context context) {
		this.context = context;
	}
	
	
}
