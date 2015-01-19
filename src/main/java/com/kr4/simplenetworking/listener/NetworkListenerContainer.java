package com.kr4.simplenetworking.listener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class NetworkListenerContainer<T> {
	protected List<WeakReference<T>> listeners;
	private String tag;

	protected String inProgressRequestID;

	public NetworkListenerContainer(String tag) {
		this.tag = tag;
		listeners = new ArrayList<WeakReference<T>>(2);
	}

	public void cleanupListeners() {
		ArrayList<WeakReference<T>> removeList = new ArrayList<WeakReference<T>>();
		for (WeakReference<T> reference : listeners) {
			if (reference.get() == null) {
				removeList.add(reference);
			}
		}
		for (WeakReference<T> reference : removeList) {
			listeners.remove(reference);
		}
	}

	public String registerListener(T d) {
		boolean found = false;

		for (WeakReference<T> reference : listeners) {
			T v = reference.get();
			if (d == v) {
				found = true;
			}
		}

		if (found == false) {
			WeakReference<T> reference = new WeakReference<T>(d);
			listeners.add(reference);
		}
		cleanupListeners();
		return getInProgressRequestID();

	}

	public void unregisterListener(T d) {
		ArrayList<WeakReference<T>> removeList = new ArrayList<WeakReference<T>>();

		for (WeakReference<T> reference : listeners) {
			if (reference.get() == null || reference.get() == d) {
				removeList.add(reference);
			}
		}
		for (WeakReference<T> reference : removeList) {
			listeners.remove(reference);
		}
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getInProgressRequestID() {
		return inProgressRequestID;
	}

	public void setInProgressRequestID(String inProgressRequestID) {
		this.inProgressRequestID = inProgressRequestID;
	}

}
