package com.kr4.simplenetworking.listener;

import java.lang.ref.WeakReference;

public class JSONNetworkListenerContainer extends NetworkListenerContainer<JSONNetworkListener> {

	public JSONNetworkListenerContainer(String tag) {
		super(tag);
	}

	public void replyToListeners(String requestID, Object data) {
		if (listeners != null) {
			for (WeakReference<JSONNetworkListener> reference : listeners) {
				JSONNetworkListener listener = reference.get();
				if (listener != null && data != null) {
					try {
						listener.JSONLoaded(requestID, data);
					} catch (Exception e) {
						e.printStackTrace();
						listener.JSONError(requestID, e.getMessage());
					}
				} else if (listener != null && data == null) {
					if (data == null) {
						listener.JSONError(requestID, "json is null");
					}
				}
			}
		}
	}

	public void replyToListenersWithError(String requestID, String error) {
		if (listeners != null) {
			for (WeakReference<JSONNetworkListener> reference : listeners) {
				JSONNetworkListener listener = reference.get();
				if (listener != null && error != null) {
					listener.JSONError(requestID, error);
				} else if (listener != null && error == null) {
					listener.JSONError(requestID, "error processing request: " + requestID);
				}
			}
		}
	}
}
