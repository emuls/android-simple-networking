package com.kr4.simplenetworking.listener;

import java.lang.ref.WeakReference;

import android.graphics.Bitmap;


public class BitmapNetworkListenerContainer extends NetworkListenerContainer<BitmapNetworkListener> {

	public BitmapNetworkListenerContainer(String tag) {
		super(tag);
	}

	public void replyToListeners(String requestID, Bitmap bitmap) {
		if (listeners != null) {
			for (WeakReference<BitmapNetworkListener> reference : listeners) {
				BitmapNetworkListener listener = reference.get();
				if (listener != null) {
					if (bitmap != null) {
						listener.bitmapLoaded(requestID, bitmap);
					} else {
						listener.bitmapError(requestID, "Unable to load bitmap");
					}

				}
			}
		}
	}

}
