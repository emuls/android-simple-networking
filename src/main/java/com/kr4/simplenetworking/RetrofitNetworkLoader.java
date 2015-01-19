package com.kr4.simplenetworking;

import java.util.List;
import java.util.Map;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.mime.TypedByteArray;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kr4.simplenetworking.listener.RetrofitNetworkListenerContainer;

public class RetrofitNetworkLoader extends NetworkLoader<RetrofitNetworkListener, RetrofitNetworkListenerContainer> {

	private static RestAdapter defaultAPIAdapter;

	public void replyToListeners(String containerTag, String requestID, Object data) {
		clearInProgressReuqestIDForTag(containerTag, requestID);
		RetrofitNetworkListenerContainer container = getListenerContainer(containerTag);
		if (container != null) {
			container.replyToListeners(requestID, data);
		}
	}

	public void replyToListeners(String requestID, Object data) {
		List<RetrofitNetworkListenerContainer> containers = getListenerContainers();
		if (containers != null && containers.size() > 0) {
			for (RetrofitNetworkListenerContainer container : containers) {
				clearInProgressReuqestIDForTag(container.getTag(), requestID);
				container.replyToListeners(requestID, data);
			}
		}
	}

	public void replyToListenersWithError(String containerTag, String requestID, String error) {
		clearInProgressReuqestIDForTag(containerTag, requestID);
		RetrofitNetworkListenerContainer container = getListenerContainer(containerTag);
		if (container != null) {
			container.replyToListenersWithError(requestID, error);
		}
	}
	
	public void replyToListenersWithAuthenticationError(String containerTag, String requestID){
		clearInProgressReuqestIDForTag(containerTag, requestID);
		RetrofitNetworkListenerContainer container = getListenerContainer(containerTag);
		if (container != null) {
			container.replyToListenersWithAuthenticationError(requestID);
		}
	}

	@Override
	protected RetrofitNetworkListenerContainer newContainer(String tag) {
		return new RetrofitNetworkListenerContainer(tag);
	}
	
	public static Map<String, Object> getErrorMapFromRetrofitError(RetrofitError error) {
		String json = null;
		try {
			json = getStringFromRetrofitError(error);
			if(json!=null){
				TypeToken<Map<String, Object>> token = new TypeToken<Map<String, Object>>() {
				};
				Gson gson = new Gson();
				Map<String, Object> result = (Map<String, Object>) gson.fromJson(json, token.getType());
				return result;
			}else{
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	public static String getStringFromRetrofitError(RetrofitError error) {
		try {
			error.getBodyAs(Map.class);
			String result = new String(((TypedByteArray) error.getResponse().getBody()).getBytes());
			return result;
		} catch (Exception e) {
			return null;
		}
	}
}
