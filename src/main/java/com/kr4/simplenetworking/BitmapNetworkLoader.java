package com.kr4.simplenetworking;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.kr4.simplenetworking.listener.BitmapNetworkListener;
import com.kr4.simplenetworking.listener.BitmapNetworkListenerContainer;
import com.kr4.simplenetworking.utils.BitmapCache;

/**
 * Kind of a shitty helper method and the cache isn't all that greate either, just use Picasso because it's awesome.
 *
 * @deprecated use Picasso instead.  
 */
public class BitmapNetworkLoader extends NetworkLoader<BitmapNetworkListener, BitmapNetworkListenerContainer> {

	private static BitmapNetworkLoader instance;

	public static BitmapNetworkLoader getInstance() {
		if (instance == null) {
			instance = new BitmapNetworkLoader();
		}
		return instance;
	}

	public void retrieveImage(final String containerTag, final String requestID, final String url) {
		Bitmap cachedImage = BitmapCache.getInstance().retrieveImage(url);
		if (cachedImage != null) {
			replyToListeners(containerTag, requestID, cachedImage);
		} else {
			new AsyncTask<Void, Void, Bitmap>() {
				@Override
				protected void onPreExecute() {
				}

				@Override
				protected Bitmap doInBackground(Void... args) {
					Bitmap bitmap = null;
					try {
						bitmap = BitmapFactory.decodeStream((InputStream) new URL(url).getContent());
					} catch (MalformedURLException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					return bitmap;
				}

				@Override
				protected void onPostExecute(Bitmap result) {
					if(result!=null){
						BitmapCache.getInstance().addImage(url, result);
					}
					replyToListeners(containerTag, requestID, result);
				}
			}.execute((Void[]) null);
		}
	}

	protected void replyToListeners(String containerTag, String requestID, Bitmap bitmap) {
		clearInProgressReuqestIDForTag(containerTag, requestID);
		BitmapNetworkListenerContainer container = getListenerContainer(containerTag);
		if (container != null) {
			container.replyToListeners(requestID, bitmap);
		}
	}

	@Override
	protected BitmapNetworkListenerContainer newContainer(String tag) {
		return new BitmapNetworkListenerContainer(tag);
	}

}
