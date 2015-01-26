package com.kr4.simplenetworking;

import java.lang.reflect.Type;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kr4.simplenetworking.listener.JSONNetworkListener;
import com.kr4.simplenetworking.listener.JSONNetworkListenerContainer;
import com.kr4.simplenetworking.utils.NetworkUtils;

public class JSONNetworkLoader extends NetworkLoader<JSONNetworkListener, JSONNetworkListenerContainer> {

    private static JSONNetworkLoader instance;

    public static JSONNetworkLoader getInstance() {
        if (instance == null) {
            instance = new JSONNetworkLoader();
        }
        return instance;
    }

    public void executeGet(final String containerTag, final String requestID, final String url) {
        executeGet(containerTag, requestID, url, new TypeToken<Object>() {
        }.getType());
    }

    public void executeGet(final String containerTag, final String requestID, final String url, final Type responseType) {
        Log.i("SimpleNetworking", "Getting url: " + url);
        setInProgressRequestIDForTag(containerTag, requestID);
        AsyncTask<Type, Void, Object> task = new AsyncTask<Type, Void, Object>() {
            Type responseType;

            @Override
            protected void onPreExecute() {

            }

            @Override
            protected Object doInBackground(Type... parms) {
                responseType = parms[0];
                Object result = NetworkUtils.executeGet(url, responseType);
                return result;
            }

            @Override
            protected void onPostExecute(Object result) {
                replyToListeners(containerTag, requestID, result);
            }
        };
        task.execute(responseType);
    }

    public void executePost(final String containerTag, final String requestID, final String url, final Object postData) {
        executePost(containerTag, requestID, url, postData, new TypeToken<Object>() {
        }.getType());
    }

    public void executePost(final String containerTag, final String requestID, final String url, final Object postData, final Type responseType) {
        setInProgressRequestIDForTag(containerTag, requestID);
        AsyncTask<Type, Void, Object> task = new AsyncTask<Type, Void, Object>() {

            @Override
            protected void onPreExecute() {

            }

            @Override
            protected Object doInBackground(Type... parms) {
                Type responseType = parms[0];
                String jsonString = NetworkUtils.executePost(url, postData);
                Gson gson = new Gson();
                try {
                    Object jsonObject = gson.fromJson(jsonString, responseType);
                    return jsonObject;
                } catch (Exception e) {
                    Log.i("JSONNetworkLoader", "Error parsing json response: " + jsonString);
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Object result) {
                replyToListeners(containerTag, requestID, result);
            }
        };
        task.execute(responseType);
    }

    protected void replyToListeners(String containerTag, String requestID, Object data) {
        clearInProgressReuqestIDForTag(containerTag, requestID);
        JSONNetworkListenerContainer container = getListenerContainer(containerTag);
        if (container != null) {
            container.replyToListeners(requestID, data);
        }
    }

    protected void replyToListenersWithError(String containerTag, String requestID, String error) {
        clearInProgressReuqestIDForTag(containerTag, requestID);
        JSONNetworkListenerContainer container = getListenerContainer(containerTag);
        if (container != null) {
            container.replyToListenersWithError(requestID, error);
        }
    }

    @Override
    protected JSONNetworkListenerContainer newContainer(String tag) {
        return new JSONNetworkListenerContainer(tag);
    }
}
