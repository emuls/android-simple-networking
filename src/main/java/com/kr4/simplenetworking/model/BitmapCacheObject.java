package com.kr4.simplenetworking.model;

import android.graphics.Bitmap;

public class BitmapCacheObject implements Comparable<BitmapCacheObject> {
	private Bitmap bitmap;
	private long cacheDate;
	private String key;

	public BitmapCacheObject(String key, Bitmap bitmap) {
		this.bitmap = bitmap;
		this.cacheDate = System.currentTimeMillis();
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	public long getCacheDate() {
		return cacheDate;
	}

	public void setCacheDate(long cacheDate) {
		this.cacheDate = cacheDate;
	}

	@Override
	public int compareTo(BitmapCacheObject arg0) {
		return (int) (arg0.cacheDate - this.cacheDate);

	}
}
