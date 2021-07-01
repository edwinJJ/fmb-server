package com.server.fmb.engine;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.quartz.SchedulerFactoryBeanCustomizer;
import org.springframework.scheduling.annotation.Async;

import com.server.fmb.engine.Types.Context;
import com.server.fmb.engine.Types.SCENARIO_STATE;
import com.server.fmb.entity.Domains;
import com.server.fmb.entity.Scenarios;
import com.server.fmb.entity.Steps;

public class ScenarioInstance {
	
	public final static String[] ScenarioInstanceStatus = new String[] {"READY", "STARTED", "STOPPED", "HALTED", "UNLOADED"};
	
	private List<ScenarioInstance> subScenarioInstances = new ArrayList<ScenarioInstance>();
	
	private Domains domain;
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
	private Object cronJob;
	private Object disposer;
	
	public ScenarioInstance(String instanceName, Scenarios scenario, Context context) {
		this.instanceName = instanceName;
		this.scenarioName = scenario.getName();
		this.schedule = scenario.getSchedule();
		this.timezone = scenario.getTimezone();
		
		// TODO
		this.steps = null; //scenario.getSteps(); order by sequence;
		this.domain = null; //scenario.getDomain();
		this.disposer = null; // context.disposer;
		this.context = null; // TODO
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
		ScenarioInstance subScenarioInstance = this.subScenarioInstances.remove(0);
		while(subScenarioInstance != null) {
			subScenarioInstance.disposeAwait();
			subScenarioInstance = this.subScenarioInstances.remove(0);
		}
	}
	
	
	@Async
	public void run() {
		
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
				int next = 0;
				
//				if (!step.getSkip()) {
//					
//				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Async
	public void loadSubSscenario(int step, String ScenarioConfig, Context context) {
		this.loadSubSscenarioAwait(step, ScenarioConfig, context);
	}
	public void loadSubSscenarioAwait(int step, String ScenarioConfig, Context context) {
		
	}
	
	
	public void publishData(String tag, Object data) {
		
	}
	
	public void publishState(String message) {
		
	}
	
	public Object getProgress() {
		return null;
	}
	
	public void setNextStep(int step) {
		this.lastStep = this.nextStep+1;
		this.nextStep = step;
	}
	
	public SCENARIO_STATE getState() {
		return this.context.state;
	} 
	
	public void setState(SCENARIO_STATE state) {
		
	}
	
	void start() {
		
	}
	
	void stop() {
		
	}

	void unload() {
		
	}
	
	@Async
	void dispose() {
		this.disposeAwait();
	}
	void disposeAwait() {
		
	}
	
	@Async
	void process(int step, Context context) {
		this.processAwait(step, context);
	}
	void processAwait(int step, Context context) {
		
	}
	
	
	public Domains getDomain() {
		return domain;
	}

	public void setDomain(Domains domain) {
		this.domain = domain;
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
