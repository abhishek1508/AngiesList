package com.abhishek.angieslist.launcher;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.abhishek.angieslist.R;
import com.abhishek.angieslist.networkmanager.CustomRequestQueueVolley;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;

public class DetailView extends AppCompatActivity {

    private String imageTitle;
    private String imageUrl;

    private ImageView mImage;
    private TextView mText;
    private ImageView mLandscapeImage;
    private TextView mLandscapeText;

    private Bitmap bitmap;
    private RequestQueue mQueue;
    private boolean isLandscape;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_detail_view);
        Intent intent = getIntent();
        imageUrl = intent.getStringExtra("image");
        imageTitle = intent.getStringExtra("title");
        if(savedInstanceState != null)
            bitmap = savedInstanceState.getParcelable("imageBitmap");
        else
            mQueue = CustomRequestQueueVolley.getInstance(this).getRequestQueue();
        setViews();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("imageBitmap", bitmap);
    }

    private void setViews(){
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            if(bitmap == null) {
                isLandscape = false;
                showDetails();
            }
            else
                showPortraitDetailView(bitmap);
        }
        else if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            if(bitmap == null) {
                isLandscape = true;
                showDetails();
            }
            else
                showLandscapeDetailView(bitmap);
        }
    }

    private void showDetails(){
        ImageRequest request = new ImageRequest(imageUrl, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                bitmap = response;
                if(bitmap != null) {
                    if(!isLandscape)
                        showPortraitDetailView(bitmap);
                    else
                        showLandscapeDetailView(bitmap);
                }
            }
        },0,0,null, null);
        mQueue.add(request);
    }

    private void initializePortraitResources(){
        mImage = (ImageView) findViewById(R.id.detail_image);
        mText = (TextView) findViewById(R.id.detail_text);
    }

    private void initializeLandscapeResources(){
        mLandscapeImage = (ImageView) findViewById(R.id.landscape_image_detail);
        mLandscapeText = (TextView) findViewById(R.id.landscape_text_detail);
    }

    private void showLandscapeDetailView(Bitmap bitmap){
        initializeLandscapeResources();
        mLandscapeImage.setImageBitmap(bitmap);
        mLandscapeText.setText(imageTitle);
    }

    private void showPortraitDetailView(Bitmap bitmap){
        initializePortraitResources();
        mImage.setImageBitmap(bitmap);
        mText.setText(imageTitle);
    }

}
