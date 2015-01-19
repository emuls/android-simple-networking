package com.kr4.simplenetworking.utils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

public class SNUtils {

	public static String translatePastTimeIntoHumanReadableFormat(long pastTime) {
		long time = System.currentTimeMillis() - pastTime;
		if (pastTime == 0 || time < 0) {
			return "Just Now";
		} else {
			long seconds = TimeUnit.MILLISECONDS.toSeconds(time);
			long minutes = TimeUnit.MILLISECONDS.toMinutes(time);
			long hours = TimeUnit.MILLISECONDS.toHours(time);
			long days = TimeUnit.MILLISECONDS.toDays(time);
			long years = (int) days / 365;

			if (years > 0) {
				if (years == 1) {
					return "1 year ago";
				} else {
					return String.format("%d years ago", years);
				}
			} else if (days > 0) {
				if (days == 1) {
					return "Yesterday";
				} else {
					return String.format("%d days ago", days);
				}
			} else if (hours > 0) {
				if (hours == 1) {
					return "About an hour ago";
				} else {
					return String.format("%d hours ago", hours);
				}
			} else if (minutes > 0) {
				if (minutes == 1) {
					return "About one minute ago";
				} else {
					return String.format("%d minutes ago", minutes);
				}
			} else {
				return "Just Now";
			}
		}
	}

	public static Double doubleFromStringOrDouble(Object stringOrDouble) {
		if (stringOrDouble == null) {
			return 0.0;
		} else if (stringOrDouble instanceof String) {
			try {
				return Double.parseDouble((String) stringOrDouble);
			} catch (Exception e) {
				return 0.0;
			}
		} else if (stringOrDouble instanceof Double) {
			return (Double) stringOrDouble;
		} else {
			return 0.0;
		}
	}

	public static boolean intentIsAvailable(Context ctx, Intent intent) {
		final PackageManager mgr = ctx.getPackageManager();
		List<ResolveInfo> list = mgr.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
		return list.size() > 0;
	}

}
