package com.kr4.simplenetworking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kr4.simplenetworking.listener.NetworkListener;
import com.kr4.simplenetworking.listener.NetworkListenerContainer;

public abstract class NetworkLoader<E extends NetworkListener, T extends NetworkListenerContainer<E>> {

	private Map<String, T> listenerMap;

	protected NetworkLoader() {
		listenerMap = new HashMap<String, T>();
	}

	protected abstract T newContainer(String tag);

	public String registerListener(E d) {
		String containerTag = d.getContainerTag();
		if (containerTag == null) {
			containerTag = "";
		}
		T container = listenerMap.get(containerTag);
		if (container == null) {
			container = newContainer(d.getContainerTag());
			listenerMap.put(d.getContainerTag(), container);
		}

		return container.registerListener(d);
	}

	public void cleanupListeners() {
		for (String containerTag : listenerMap.keySet()) {
			T container = listenerMap.get(containerTag);
			container.cleanupListeners();
		}
	}

	public void unregisterListener(E d) {
		String containerTag = d.getContainerTag();
		if (containerTag == null) {
			containerTag = "";
		}
		T container = listenerMap.get(containerTag);
		if (container != null) {
			container.unregisterListener(d);
		}
	}

	protected void setInProgressRequestIDForTag(String tag, String requestID) {
		T container = listenerMap.get(tag);
		if (container != null) {
			container.setInProgressRequestID(requestID);
		}
	}

	protected void clearInProgressReuqestIDForTag(String tag, String requestID) {
		T container = listenerMap.get(tag);
		if (container != null) {
			String currentID = container.getInProgressRequestID();
			if (requestID.equals(currentID)) {
				container.setInProgressRequestID(null);
			}
		}
	}

	public T getListenerContainer(String tag) {
		return listenerMap.get(tag);
	}
	
	public List<T> getListenerContainers(){
		List<T> result = new ArrayList<T>();
		if(listenerMap!=null && listenerMap.size()>0){
			for(String key : listenerMap.keySet()){
				result.add(listenerMap.get(key));
			}
		}
		return result;
	}
}
