package com.kr4.simplenetworking;

import com.kr4.simplenetworking.listener.NetworkListener;

public interface RetrofitNetworkListener extends NetworkListener {

	public void onLoaderSuccess(String requestID, Object jsonObject);

	public void onLoaderFailure(String requestID, String result);
	
	public void onAuthenticationError(String requestID);
	

}
