package com.kr4.simplenetworking.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import android.graphics.Bitmap;

import com.kr4.simplenetworking.model.BitmapCacheObject;

public class BitmapCache {

	private Map<String, BitmapCacheObject> images;
	private static final long CACHE_LIMIT_IN_BYTES = 4194304;
	private static BitmapCache instance;

	public static BitmapCache getInstance() {
		if (instance == null) {
			instance = new BitmapCache();
		}
		return instance;
	}

	private BitmapCache() {
		images = new HashMap<String, BitmapCacheObject>();
	}

	public Bitmap retrieveImage(String key) {
		BitmapCacheObject object = images.get(key);
		if (object != null) {
			return object.getBitmap();
		} else {
			return null;
		}
	}

	public void addImage(String key, Bitmap image) {
		cleanupImages();
		images.put(key, new BitmapCacheObject(key, image));
	}

	public void cleanupImages() {
		LinkedList<BitmapCacheObject> imageList = new LinkedList<BitmapCacheObject>();
		if (images.keySet().size() > 50) {
			for (String key : images.keySet()) {
				BitmapCacheObject cacheObject = images.get(key);
				imageList.add(cacheObject);
			}

			Collections.sort(imageList);
			for (int i = 0; i < 20; i++) {
				BitmapCacheObject toRemove = imageList.pop();
				images.remove(toRemove.getKey());
			}
		}
	}
}
