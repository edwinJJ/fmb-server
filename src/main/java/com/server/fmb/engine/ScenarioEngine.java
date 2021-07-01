package com.server.fmb.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import com.server.fmb.engine.PendingQueue.PendingObject;
import com.server.fmb.engine.Types.Context;
import com.server.fmb.entity.Connections;
import com.server.fmb.entity.Domains;
import com.server.fmb.entity.Scenarios;
import com.server.fmb.service.IConnectionService;
import com.server.fmb.service.IScenarioService;

public class ScenarioEngine {

	@Autowired
	private static IScenarioService scenarioService;

	
	private static Map<String, Map<String, ScenarioInstance>> scenarioInstances = new HashMap<String, Map<String, ScenarioInstance>>();
	private static Map<String, PendingQueue> pendingQueues = new HashMap<String, PendingQueue>();

	public static PendingQueue getPendingQueue(Domains domain) {
	    if (ScenarioEngine.pendingQueues.get(domain.getId()) != null) {
	    	Class[] parameterTypes = new Class[1];
	    	parameterTypes[0] = List.class;
	    	ScenarioEngine.pendingQueues.put(domain.getId(), new PendingQueue(domain.getId()));
	    }

	    return ScenarioEngine.pendingQueues.get(domain.getId());
	}

	public static ScenarioInstance getScenarioInstance(Domains domain, String instanceName) {
		if (ScenarioEngine.scenarioInstances.get(domain.getId()) != null) {
			return ScenarioEngine.scenarioInstances.get(domain.getId()).get(instanceName);
		}
		return null;
	}

	public static List<ScenarioInstance> getScenarioInstances(Domains domain, String scenarioName) {
		List<ScenarioInstance> instances = new ArrayList<ScenarioInstance>();
		Map<String, ScenarioInstance> domainInstanceMap = ScenarioEngine.scenarioInstances.get(domain.getId());
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
	public static ScenarioInstance load(String instanceName, Scenarios scenario, Context context) {
		Map<String, ScenarioInstance> scenarioInstances = ScenarioEngine.scenarioInstances.get(context.domain.getId());
		if (scenarioInstances == null) {
			scenarioInstances = new HashMap<String, ScenarioInstance>();
		}
		
		ScenarioInstance scenarioInstance = scenarioInstances.get(instanceName);
		if (scenarioInstance != null) {
			return scenarioInstance;
		}
		
		ScenarioInstance instance = null;//new ScenarioInstance();
		instance.start();
		
		scenarioInstances.put(instanceName, instance);
		ScenarioEngine.scenarioInstances.put(context.domain.getId(), scenarioInstances);
		
		return instance;
	}
	
	@Async
	public static void unload(Domains domain, String instanceName) {
		Map<String, ScenarioInstance> scenarioInstances = ScenarioEngine.scenarioInstances.get(domain.getId());
		if (scenarioInstances == null) {
			return;
		}
		
		ScenarioInstance instance = scenarioInstances.get(instanceName);
		if (instance == null) {
			return;
		}
		
		scenarioInstances.remove(instanceName);
		ScenarioEngine.scenarioInstances.put(domain.getId(), scenarioInstances);
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
