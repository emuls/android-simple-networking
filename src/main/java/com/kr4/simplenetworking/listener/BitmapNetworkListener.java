package com.kr4.simplenetworking.listener;

import android.graphics.Bitmap;

public interface BitmapNetworkListener extends NetworkListener {
	public void bitmapLoaded(String requestID, Bitmap bitmap);

	public void bitmapError(String requestID, String message);

}
