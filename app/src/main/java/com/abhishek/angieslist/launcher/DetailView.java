package com.abhishek.angieslist.launcher;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.abhishek.angieslist.R;
import com.abhishek.angieslist.networkmanager.CustomRequestQueueVolley;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;

public class DetailView extends AppCompatActivity {

    private String imageUrl;
    private String imageTitle;

    private ImageView mImage;
    private TextView mText;

    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_detail_view);
        Bundle bundle = getIntent().getExtras();
        imageUrl = bundle.getString("imageURL");
        imageTitle = bundle.getString("title");
        mImage = (ImageView) findViewById(R.id.detail_image);
        mText = (TextView) findViewById(R.id.detail_text);
        mQueue = CustomRequestQueueVolley.getInstance(this).getRequestQueue();
        setDetails();
    }

    private void setDetails(){
        ImageRequest request = new ImageRequest(imageUrl, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                mImage.setImageBitmap(response);
            }
        },0,0,null,null);
        mQueue.add(request);
    }
}
