package com.server.fmb.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import com.server.fmb.engine.Context;
import com.server.fmb.entity.Scenarios;
import com.server.fmb.service.IScenarioService;

public class ScenarioEngine {

	@Autowired
	private static IScenarioService scenarioService;

	
	private static Map<String, Map<String, ScenarioInstance>> scenarioInstances = new HashMap<String, Map<String, ScenarioInstance>>();
	private static Map<String, PendingQueue> pendingQueues = new HashMap<String, PendingQueue>();

	public static PendingQueue getPendingQueue(String domainId) {
	    if (ScenarioEngine.pendingQueues.get(domainId) != null) {
	    	ScenarioEngine.pendingQueues.put(domainId, new PendingQueue(domainId));
	    }

	    return ScenarioEngine.pendingQueues.get(domainId);
	}

	public static ScenarioInstance getScenarioInstance(String domainId, String instanceName) {
		if (ScenarioEngine.scenarioInstances.get(domainId) != null) {
			return ScenarioEngine.scenarioInstances.get(domainId).get(instanceName);
		}
		return null;
	}

	public static List<ScenarioInstance> getScenarioInstances(String domainId, String scenarioName) {
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
	public static ScenarioInstance load(String instanceName, Scenarios scenarioConfig, Context context) {
		Map<String, ScenarioInstance> scenarioInstances = ScenarioEngine.scenarioInstances.get(context.domainId);
		if (scenarioInstances == null) {
			scenarioInstances = new HashMap<String, ScenarioInstance>();
		}
		
		ScenarioInstance scenarioInstance = scenarioInstances.get(instanceName);
		if (scenarioInstance != null) {
			return scenarioInstance;
		}
		
		try {
	        Class[] parameterTypes = new Class[2];
	        parameterTypes[0] = String.class;
	        parameterTypes[1] = String.class;
			context.disposer = ScenarioEngine.class.getMethod("unload", parameterTypes);
		} catch (Exception e) {}
		ScenarioInstance instance = new ScenarioInstance(instanceName, scenarioConfig, context);
		instance.start();
		
		scenarioInstances.put(instanceName, instance);
		ScenarioEngine.scenarioInstances.put(context.domainId, scenarioInstances);
		
		return instance;
	}
	
	@Async
	public static void unload(String domainId, String instanceName) {
		Map<String, ScenarioInstance> scenarioInstances = ScenarioEngine.scenarioInstances.get(domainId);
		if (scenarioInstances == null) {
			return;
		}
		
		ScenarioInstance instance = scenarioInstances.get(instanceName);
		if (instance == null) {
			return;
		}
		
		scenarioInstances.remove(instanceName);
		ScenarioEngine.scenarioInstances.put(domainId, scenarioInstances);
		instance.unload();
	}
	
	@Async
	public static void loadAll() throws Exception {
		List<Scenarios> activeScenarios = ScenarioEngine.scenarioService.getScenarios(); // where active = true, join domain, steps
		if (activeScenarios == null) {
			return;
		}
		for (Scenarios scenario : activeScenarios) {
			ScenarioEngine.load(scenario.getName(), scenario, null);
		}

	}
}
