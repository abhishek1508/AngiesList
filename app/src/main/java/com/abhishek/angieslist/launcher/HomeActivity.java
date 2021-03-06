package com.abhishek.angieslist.launcher;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.abhishek.angieslist.R;
import com.abhishek.angieslist.controller.ImgurImagesAdapter;
import com.abhishek.angieslist.networkmanager.CustomJsonRequest;
import com.abhishek.angieslist.networkmanager.CustomRequestQueueVolley;
import com.abhishek.angieslist.pagination.EndlessPaginationOnScroll;
import com.abhishek.angieslist.parser.JSONParser;
import com.abhishek.angieslist.utilities.DividerItemDecoration;
import com.abhishek.angieslist.utilities.Images;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private RequestQueue mQueue = null;
    private RecyclerView mRecycler;
    private SwipeRefreshLayout mRefresh;
    private LinearLayoutManager mLayoutManager = null;
    private ImgurImagesAdapter mAdapter = null;
    private JSONParser mParser = null;
    private ArrayList<Images> mList = null;
    public static final String TAG = HomeActivity.class.getSimpleName();

    public static String url = "https://api.imgur.com/3/gallery/r/funny/%s";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_home);
        mQueue = CustomRequestQueueVolley.getInstance(this).getRequestQueue();
        mLayoutManager = new LinearLayoutManager(this);
        mRecycler = (RecyclerView) findViewById(R.id.list_funny_images);
        //If savedInstanceState is not null then json response is fetched from the list passed
        //in the bundle and the recycler view is loaded using the list
        if(savedInstanceState!=null) {
            mList = savedInstanceState.getParcelableArrayList("ImageList");
            loadListView();
        }
        //If savedInstanceState is null then json response is fetched from the api and
        //loaded in the recycler view
        else{
            getJSON_response(String.format(url, 0), true);
            mList = new ArrayList<>();
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initialize();
    }

    private void initialize(){
        mParser = new JSONParser();
        mRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_to_refresh);
        mRefresh.setOnRefreshListener(this);
        mRefresh.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryText);
    }

    /*
    The method retains the object passed in the bundle before onDestroy() is called
    and same object can be retrieved when the activity starts again due to screen orientation change.
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("ImageList", mList);
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
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                /*
                 If the getJSON_Response method is called for the first time then we need to load the
                 recycler view from scratch. If it is called as a result of pagination we already have the
                 view and only need to notify the adapter about the addition of new items in the list.
                 */
                if(!isFirstTime)
                    mAdapter.notifyDataSetChanged();
                else
                    loadListView();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HomeActivity.this,R.string.error_message,Toast.LENGTH_SHORT).show();
            }
        });
        mQueue.add(req);
    }

    private void load_more_images(int currentPage) {
        Log.d(TAG,"method invoked for next set of data");
        Log.d(TAG, "the next url is: "+String.format(url, currentPage));
        getJSON_response(String.format(url, currentPage), false);
        Toast.makeText(HomeActivity.this,R.string.next_page,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        refreshList();
    }

    private void refreshList(){
        //When swiped to refresh, the app should fetch all the data from starting. Hence the
        //list is cleared and the initial url is passed to getJSON_response.
        mList.clear();
        getJSON_response(String.format(url, 0), true);
        if(mRefresh.isRefreshing())
            mRefresh.setRefreshing(false);
    }
}
