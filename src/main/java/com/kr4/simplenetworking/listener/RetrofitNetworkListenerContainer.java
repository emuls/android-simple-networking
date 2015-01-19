package com.kr4.simplenetworking.listener;

import java.lang.ref.WeakReference;

import com.kr4.simplenetworking.RetrofitNetworkListener;

public class RetrofitNetworkListenerContainer extends NetworkListenerContainer<RetrofitNetworkListener> {

	public RetrofitNetworkListenerContainer(String tag) {
		super(tag);
	}

	public void replyToListeners(String requestID, Object data) {
		if (listeners != null) {
			for (WeakReference<RetrofitNetworkListener> reference : listeners) {
				RetrofitNetworkListener listener = reference.get();
				if (listener != null && data != null) {
					try {
						listener.onLoaderSuccess(requestID, data);
					} catch (Exception e) {
						e.printStackTrace();
						listener.onLoaderFailure(requestID, e.getMessage());
					}
				} else if (listener != null && data == null) {
					if (data == null) {
						listener.onLoaderFailure(requestID, "json is null");
					}
				}
			}
		}
	}

	public void replyToListenersWithError(String requestID, String error) {
		if (listeners != null) {
			for (WeakReference<RetrofitNetworkListener> reference : listeners) {
				RetrofitNetworkListener listener = reference.get();
				if (listener != null && error != null) {
					listener.onLoaderFailure(requestID, error);
				} else if (listener != null && error == null) {
					listener.onLoaderFailure(requestID, "error processing request: " + requestID);
				}
			}
		}
	}

	public void replyToListenersWithAuthenticationError(String requestID) {
		if (listeners != null) {
			for (WeakReference<RetrofitNetworkListener> reference : listeners) {
				RetrofitNetworkListener listener = reference.get();
				if (listener != null) {
					listener.onAuthenticationError(requestID);
				}
			}
		}
	}
}
