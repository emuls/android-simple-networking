package com.kr4.simplenetworking.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import android.util.Log;

import com.google.gson.Gson;

public class NetworkUtils {

	public static final int IO_BUFFER_SIZE = 1024;

	public static String executeGet(String getURL) {
		HttpURLConnection urlConnection = null;
		try {
			URL url = new URL(getURL);
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.addRequestProperty("Accept", "application/json");

			InputStream in = new BufferedInputStream(
					urlConnection.getInputStream());
			InputStreamReader isr = new InputStreamReader(in);
			BufferedReader br = new BufferedReader(isr);
			String line = "";
			String json = "";
			while ((line = br.readLine()) != null) {
				json += line;
			}
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			Log.i("NetworkUtils",
					"Exception executing request: " + e.getMessage());
		} finally {
			if (urlConnection != null) {
				urlConnection.disconnect();
			}
		}
		return null;
	}

	public static Object executeGet(String getURL, Type type) {
		HttpURLConnection urlConnection = null;
		try {
			URL url = new URL(getURL);
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.addRequestProperty("Accept", "application/json");

			InputStream in = new BufferedInputStream(
					urlConnection.getInputStream());
			InputStreamReader isr = new InputStreamReader(in);
			Gson gson = new Gson();
			Object result = gson.fromJson(isr, type);
			return result;
		} catch (Exception e) {
			Log.i("NetworkUtils", "Exception creating user");
		} finally {
			if (urlConnection != null) {
				urlConnection.disconnect();
			}
		}
		return null;
	}

	public static String executePost(String postURL, Object postData) {
		try {
			Gson gson = new Gson();

			String postJson = gson.toJson(postData);

			HttpClient httpclient = new DefaultHttpClient();
			httpclient.getParams().setParameter(
					CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
			httpclient.getParams().setParameter(
					CoreConnectionPNames.SO_TIMEOUT, 20000);
			HttpPost httppost = new HttpPost(postURL);

			StringEntity strEntity = new StringEntity(postJson);
			httppost.setEntity(strEntity);

			httppost.setHeader("Accept", "application/json");
			httppost.setHeader("Content-Type", "application/json");

			// Execute HTTP Post Request
			HttpResponse response = httpclient.execute(httppost);
			String result = EntityUtils.toString(response.getEntity());
			return result;
		} catch (Exception e) {
			return null;
		}
	}

	// public static String executePostWithStringData(String postURL,
	// String postData) {
	// HttpURLConnection urlConnection = null;
	// try {
	// URL url = new URL(postURL);
	// urlConnection = (HttpURLConnection) url.openConnection();
	// urlConnection.addRequestProperty("Accept", "application/json");
	//
	// if (postData != null) {
	// byte[] postBytes = postData.getBytes();
	// urlConnection.addRequestProperty("Content-Type",
	// "application/json");
	// urlConnection.addRequestProperty("Content-Length", ""
	// + postBytes.length);
	// urlConnection.setDoOutput(true);
	// urlConnection.setChunkedStreamingMode(0);
	//
	// OutputStream out = new BufferedOutputStream(
	// urlConnection.getOutputStream());
	//
	// out.write(postData.getBytes());
	// }
	//
	// InputStream in = new BufferedInputStream(
	// urlConnection.getInputStream());
	// InputStreamReader isr = new InputStreamReader(in);
	// BufferedReader br = new BufferedReader(isr);
	// String line = "";
	// String json = "";
	// while ((line = br.readLine()) != null) {
	// json += line;
	// }
	// return json;
	// } catch (Exception e) {
	// Log.i("NetworkUtils", "Exception creating user");
	// } finally {
	// if (urlConnection != null) {
	// urlConnection.disconnect();
	// }
	// }
	// return null;
	// }
}
