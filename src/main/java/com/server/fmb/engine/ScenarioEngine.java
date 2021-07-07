package com.server.fmb.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.server.fmb.constant.Constant;
import com.server.fmb.engine.task.BookUpScenario;
import com.server.fmb.engine.task.DatabaseQuery;
import com.server.fmb.engine.task.EmptyCheck;
import com.server.fmb.engine.task.End;
import com.server.fmb.engine.task.FloatingPoint;
import com.server.fmb.engine.task.Goto;
import com.server.fmb.engine.task.PickPendingScenario;
import com.server.fmb.engine.task.Publish;
import com.server.fmb.engine.task.ResetPendingQueue;
import com.server.fmb.engine.task.Script;
import com.server.fmb.engine.task.Sleep;
import com.server.fmb.engine.task.SubScenario;
import com.server.fmb.engine.task.SwitchGoto;
import com.server.fmb.engine.task.SwitchRangeGoto;
import com.server.fmb.engine.task.SwitchRangeScenario;
import com.server.fmb.engine.task.SwitchSet;
import com.server.fmb.engine.task.Throw;
import com.server.fmb.engine.task.Variables;
import com.server.fmb.entity.Scenarios;
import com.server.fmb.entity.Steps;
import com.server.fmb.service.IScenarioService;
import com.server.fmb.service.IStepService;
import com.server.fmb.util.ValueUtil;

@Order(2)
@Service
public class ScenarioEngine implements CommandLineRunner {

	@Autowired
	private IScenarioService scenarioService;
	
	@Autowired
	SchedulerService schedulerService;
	
	@Autowired
	IStepService stepService;
	
	@Autowired
	DatabaseQuery databaseQueryService;
	
	private String schedule = "*/2 * * * * *";

	private static Map<String, Map<String, ScenarioInstance>> scenarioInstances = new HashMap<String, Map<String, ScenarioInstance>>();
	private static Map<String, PendingQueue> pendingQueues = new HashMap<String, PendingQueue>();
	private Map<String, Map<String, ScheduledFuture<?>>> cronJobMap = new HashMap<String, Map<String, ScheduledFuture<?>>>();

	@Override
	public void run(String... args) throws Exception {
		TaskRegistry.registerTaskHandler("book-up-scenario", new BookUpScenario());
		TaskRegistry.registerTaskHandler("database-query", databaseQueryService);
		TaskRegistry.registerTaskHandler("empty-check", new EmptyCheck());
		TaskRegistry.registerTaskHandler("end", new End());
		TaskRegistry.registerTaskHandler("floating-point", new FloatingPoint());
		TaskRegistry.registerTaskHandler("goto", new Goto());
		TaskRegistry.registerTaskHandler("pick-pending-scenario", new PickPendingScenario());
		TaskRegistry.registerTaskHandler("publish", new Publish());
		TaskRegistry.registerTaskHandler("reset-pending-queue", new ResetPendingQueue());
		TaskRegistry.registerTaskHandler("script", new Script());
		TaskRegistry.registerTaskHandler("sleep", new Sleep());
		TaskRegistry.registerTaskHandler("sub-scenario", new SubScenario());
		TaskRegistry.registerTaskHandler("switch-goto", new SwitchGoto());
		TaskRegistry.registerTaskHandler("switch-range-goto", new SwitchRangeGoto());
		TaskRegistry.registerTaskHandler("switch-range-scenario", new SwitchRangeScenario());
		TaskRegistry.registerTaskHandler("switch-set", new SwitchSet());
		TaskRegistry.registerTaskHandler("throw", new Throw());
		TaskRegistry.registerTaskHandler("variables", new Variables());
		loadAll();
	}
	
	public PendingQueue getPendingQueue(String domainId) {
	    if (ScenarioEngine.pendingQueues.get(domainId) != null) {
	    	ScenarioEngine.pendingQueues.put(domainId, new PendingQueue(domainId));
	    }

	    return ScenarioEngine.pendingQueues.get(domainId);
	}

	public ScenarioInstance getScenarioInstance(String domainId, String instanceName) {
		if (ScenarioEngine.scenarioInstances.get(domainId) != null) {
			return ScenarioEngine.scenarioInstances.get(domainId).get(instanceName);
		}
		return null;
	}

	public List<ScenarioInstance> getScenarioInstances(String domainId, String scenarioName) {
		List<ScenarioInstance> instances = new ArrayList<ScenarioInstance>();
		Map<String, ScenarioInstance> domainInstanceMap = ScenarioEngine.scenarioInstances.get(domainId);
		if (domainInstanceMap != null) {
			for (String key : domainInstanceMap.keySet()) {
				ScenarioInstance instance = domainInstanceMap.get(key);
				if (scenarioName == null || scenarioName.equals(instance.getScenarioName())) {
					instances.add(instance);
				}
			}
		}
		return instances;
	}

	@Async
	public ScenarioInstance load(String instanceName, Scenarios scenarioConfig, Context context) throws Exception {
		Map<String, ScenarioInstance> scenarioInstancesMap = scenarioInstances.get(scenarioConfig.getDomainId());
		if (scenarioInstancesMap == null) {
			scenarioInstancesMap = new HashMap<String, ScenarioInstance>();
		}
		
		ScenarioInstance scenarioInstance = scenarioInstancesMap.get(instanceName);
		if (scenarioInstance != null) {
			return scenarioInstance;
		}
		
		try {
	        Class[] parameterTypes = new Class[2];
	        parameterTypes[0] = String.class;
	        parameterTypes[1] = String.class;
			context.disposer = ScenarioEngine.class.getMethod("unload", parameterTypes);
		} catch (Exception e) {
			new Exception();
		}
		
		List<Steps> stepList = stepService.getStepsByScenarioId(scenarioConfig.getId());
		Steps[] stepsArray = stepList.toArray(new Steps[stepList.size()]);
		
		ScenarioInstance instance = new ScenarioInstance(instanceName, scenarioConfig, context, stepsArray);
		
		Map<String, ScheduledFuture<?>> cronJob = cronJobMap.get(scenarioConfig.getDomainId());
		if (ValueUtil.isEmpty(cronJob)) {
			cronJob = new HashMap<String, ScheduledFuture<?>>();
		}
		ScheduledFuture<?> cronJobTemp = schedulerService.start(instance, this.schedule);
		instance.setCronjob(cronJobTemp);
		cronJob.put(instanceName, cronJobTemp);
		cronJobMap.put(scenarioConfig.getDomainId(), cronJob);
		
		scenarioInstancesMap.put(instanceName, instance);
		scenarioInstances.put(scenarioConfig.getDomainId(), scenarioInstancesMap);
		
		return instance;
	}
	
	@Async
	public void unload(String domainId, String instanceName) {
		cronJobMap.get(domainId).get(instanceName).cancel(true);
//		Map<String, ScenarioInstance> scenarioInstances = ScenarioEngine.scenarioInstances.get(domainId);
//		if (scenarioInstances == null) {
//			return;
//		}
//		
//		ScenarioInstance instance = scenarioInstances.get(instanceName);
//		if (instance == null) {
//			return;
//		}
//		
//		scenarioInstances.remove(instanceName);
//		ScenarioEngine.scenarioInstances.put(domainId, scenarioInstances);
//		instance.stop();
//		instance.unload();
	}
	
	@Async
	public void loadAll() throws Exception {
		List<Scenarios> activeScenarios = scenarioService.getScenariosByActive(Constant.ACTIVE_TRUE); // where active = true, join domain, steps
		if (activeScenarios == null) {
			return;
		}
		for (Scenarios scenario : activeScenarios) {
			this.load(scenario.getName(), scenario, null);
		}

	}
}
