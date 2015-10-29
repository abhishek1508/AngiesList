package com.abhishek.angieslist.launcher;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.abhishek.angieslist.R;
import com.abhishek.angieslist.controller.ImgurImagesAdapter;
import com.abhishek.angieslist.networkmanager.CustomJsonRequest;
import com.abhishek.angieslist.networkmanager.CustomRequestQueueVolley;
import com.abhishek.angieslist.networkmanager.VolleySingelton;
import com.abhishek.angieslist.pagination.EndlessPaginationOnScroll;
import com.abhishek.angieslist.parser.JSONParser;
import com.abhishek.angieslist.utilities.DividerItemDecoration;
import com.abhishek.angieslist.utilities.Images;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity{

    private RequestQueue mQueue = null;
    private RecyclerView mRecycler;
    private LinearLayoutManager mLayoutManager = null;
    private ImgurImagesAdapter mAdapter = null;
    private JSONParser mParser = null;
    private List<Images> mList = null;

    public static int mPageCount = 1;

    public static String url = "https://api.imgur.com/3/gallery/r/funny/%s";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_home);
        mQueue = CustomRequestQueueVolley.getInstance(this).getRequestQueue();
        getJSON_response(String.format(url, 0), true);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initialize();
    }

    private void initialize(){
        mList = new ArrayList<>();
        mParser = new JSONParser();
        mLayoutManager = new LinearLayoutManager(this);
        mRecycler = (RecyclerView) findViewById(R.id.list_funny_images);
    }

    private void loadListView(){
        mAdapter = new ImgurImagesAdapter(this,mList);
        if(mRecycler != null) {
            mRecycler.setHasFixedSize(true);
            mRecycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
            if (mLayoutManager != null)
                mRecycler.setLayoutManager(mLayoutManager);
            if (mAdapter != null)
                mRecycler.setAdapter(mAdapter);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mRecycler.addOnScrollListener(new EndlessPaginationOnScroll(mLayoutManager) {
            @Override
            public void loadMore(int mCurrentPage) {
                load_more_images(mCurrentPage);
            }
        });
    }

    private void getJSON_response(String url, final boolean isFirstTime){

        CustomJsonRequest req = new CustomJsonRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response){
                try {
                    if(response.getInt("status") == 200)
                        mList = mParser.getImageAttribute(response,mList);
                    Log.d("asdf","list"+mList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(!isFirstTime)
                    mAdapter.notifyDataSetChanged();
                else
                    loadListView();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HomeActivity.this,"error in response",Toast.LENGTH_SHORT).show();
            }
        });
        mQueue.add(req);
    }

    private void load_more_images(int currentPage) {
        getJSON_response(String.format(url,mPageCount),false);
        mPageCount++;
        Toast.makeText(HomeActivity.this,"Loaded next page",Toast.LENGTH_SHORT).show();
    }
}
