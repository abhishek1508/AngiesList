package com.abhishek.angieslist.networkmanager;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;

/**
 * Created by Abhishek on 10/23/2015.
 */
public class CustomRequestQueueVolley {

    private static CustomRequestQueueVolley mInstance;
    private static Context mCtx;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private static final String TAG = CustomRequestQueueVolley.class.getSimpleName();

    private CustomRequestQueueVolley(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();

        mImageLoader = new ImageLoader(mRequestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap>
                            cache = new LruCache<String, Bitmap>(20);

                    /*
                    Get the url and its corresponding bitmap from the cache. Returns bitmap if the url is present
                    else returns null.
                     */
                    @Override
                    public Bitmap getBitmap(String url) {
                        Bitmap bitmap = cache.get(url);
                        Log.d(TAG,"The url and bitmap get from cache are: "+url+" "+bitmap);
                        return bitmap;
                    }
                    /*
                    Put the url and corresponding bitmap in the cache.
                     */
                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                        Log.d(TAG,"The url and bitmap put into cache are: "+url+" "+bitmap);
                    }
                });
    }

    public static synchronized CustomRequestQueueVolley getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new CustomRequestQueueVolley(context);
        }
        return mInstance;
    }

    /*
    Method creates a custom queue
     */
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            /*The maximum number of entries the cache can hold is 20. Once the maximum number is reached the
            earlier loaded entries in the cache is deleted. We can use cache.resize(int maxsize) to increase
            the maximum size of the cache, but this method has been introduced in API 21 and the current
            minimum SDKVersion required for the application is 16. Hence its a trade off between the cache size
            and the capability of older android sdk to use the app.*/

            Cache cache = new DiskBasedCache(mCtx.getCacheDir(), 10 * 1024 * 1024);//Create a cahe of 10MiB.
            Network network = new BasicNetwork(new HurlStack());
            mRequestQueue = new RequestQueue(cache, network);
            mRequestQueue.start();
        }
        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }
}
