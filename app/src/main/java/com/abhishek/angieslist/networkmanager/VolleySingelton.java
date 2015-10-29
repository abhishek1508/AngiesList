package com.abhishek.angieslist.networkmanager;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Abhishek on 10/22/2015.
 */
public class VolleySingelton {

    private static VolleySingelton sInstance = null;
    private RequestQueue mRequestQueue = null;
    private VolleySingelton(){
        mRequestQueue = Volley.newRequestQueue(MyApplication.getAppContext());//Creates queue provided by the volley framework
    }

    public static VolleySingelton getsInstance(){
        if(sInstance == null)
            sInstance = new VolleySingelton();
        return sInstance;
    }

    public RequestQueue getRequestQueue(){
        return mRequestQueue;
    }
}
