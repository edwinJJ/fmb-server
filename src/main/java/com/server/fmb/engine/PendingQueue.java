package com.server.fmb.engine;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class PendingQueue {
	
	public class PendingObject {
		Object stuff;
		long due;
		int priority;
		String tag;
		int delay;
	}
	
	private void changedCallback(String domainId, List<PendingObject> queue) {
		// TODO
//	        pubsub.publish('scenario-queue-state', {
//		          scenarioQueueState: {
//		            domain,
//		            queue
//		          }
//		        })		  
	}
	  

	private List<PendingObject> _queue = new LinkedList<PendingObject>();
	private String _changedCallbackDomainId = null;
	
	public PendingQueue(String changedCallbackDomainId) {
		this._changedCallbackDomainId = changedCallbackDomainId;
	}
	
	public List<PendingObject> getQueue() {
		return _queue;
	}
	
	public Map getStatus() {
		Map statusMap = new HashMap<String, Object>();
		statusMap.put("total", this._queue.size());
		return statusMap;
	}
	
	void reset() {
		this._queue.clear();
		this.changedCallback(this._changedCallbackDomainId, this._queue);
	} 
	
	void put(PendingObject pended) {
		long due = new Date().getTime() + pended.delay*1000;
		int insertBefore = -1;
		for (int i = 0; i < this._queue.size(); i++) {
			PendingObject item = this._queue.get(i);
			if (item.due > pended.due) {
				insertBefore = i;
				break;
			}
		}
		if (insertBefore == -1) {
			this._queue.add(pended);
		} else {
			this._queue.add(insertBefore, pended);
		}
		
		this.changedCallback(this._changedCallbackDomainId, this._queue);
	}
	
	PendingObject pick(String tag) {
		long due = new Date().getTime();
		
		PendingObject toppick = null;
		int index = -1;
		
		for (int i = 0; i < this._queue.size(); i++) {
			PendingObject pended = this._queue.get(i);
			
			if (pended.due > due) {
				break;
			}
			
			if (tag == null) {
				tag = "";
			}
			if (!tag.equals(pended.tag)) {
				continue;
			}
			
			if (toppick == null || toppick.priority < pended.priority) {
				toppick = pended;
				index = i;
			}
		}
		
		if (index != -1) {
			this._queue.remove(index);
			this.changedCallback(this._changedCallbackDomainId, this._queue);
		}
		
		return toppick;
	}
	
	void cancel(Object stuff) {
		for (int i = 0; i < this._queue.size(); i++) {
			if (this._queue.get(i).stuff == stuff) {
				this._queue.remove(i);
				break;
			}
		}
		this.changedCallback(this._changedCallbackDomainId, this._queue);
	}

}
