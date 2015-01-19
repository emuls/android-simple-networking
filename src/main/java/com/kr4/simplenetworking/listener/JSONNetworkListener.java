package com.kr4.simplenetworking.listener;

public interface JSONNetworkListener extends NetworkListener {

	public void JSONLoaded(String requestIDR, Object jsonObject);

	public void JSONError(String requestID, String result);

}
