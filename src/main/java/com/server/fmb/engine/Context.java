package com.server.fmb.engine;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import com.server.fmb.engine.Types.SCENARIO_STATE;

public class Context {
	public String domainId;
	public Object loger;
	public ScenarioInstance instance;
	public Types.SCENARIO_STATE state;
	public Object data;
	public Object variables;
	public Object client;
	public Object root;
	public List closures;
	public Method disposer;

	public boolean checkState() {
		return this.state == SCENARIO_STATE.STARTED;
	}
	public Context clone() {
		Context cloned = new Context();
		cloned.domainId = this.domainId;
		cloned.loger = this.loger;
		cloned.state = this.state;
		cloned.data = this.data;
		cloned.variables = this.variables;
		cloned.client = this.client;
		cloned.root = this.root;
		cloned.closures = this.closures;
		cloned.disposer = this.disposer;
		return cloned;
	}

}
