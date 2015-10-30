package com.abhishek.angieslist.controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.abhishek.angieslist.R;
import com.abhishek.angieslist.networkmanager.CustomRequestQueueVolley;
import com.abhishek.angieslist.utilities.Images;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abhishek on 10/23/2015.
 */
public class ImgurImagesAdapter extends RecyclerView.Adapter<ImgurImagesAdapter.ImagesViewHolder>{

    private Context mContext;
    private LayoutInflater mInflater;
    private ImageLoader mLoader = null;
    private ArrayList<Images> mList;

    public ImgurImagesAdapter(Context context,ArrayList<Images> list){
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mList = list;
    }
    @Override
    public ImagesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.layout_single_list_item,parent,false);
        ImagesViewHolder holder = new ImagesViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ImagesViewHolder holder, int position) {
        if(mLoader == null)
            mLoader = CustomRequestQueueVolley.getInstance(mContext).getImageLoader();
        holder.mImgurImage.setImageUrl(mList.get(position).mImageUrl, mLoader);
    }

    /*
    Creates the number of rows equal to the size of list containing the object of type Images.
     */
    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ImagesViewHolder extends RecyclerView.ViewHolder{

        private CircularNetworkImageView mImgurImage;
        private TextView mTitle;
        private TextView mViews;
        private TextView mUpvotes;
        public ImagesViewHolder(View itemView) {
            super(itemView);
            mImgurImage = (CircularNetworkImageView) itemView.findViewById(R.id.image_network_view);
            mTitle = (TextView) itemView.findViewById(R.id.text_list_title);
            mViews = (TextView) itemView.findViewById(R.id.text_views_count);
            mUpvotes = (TextView) itemView.findViewById(R.id.text_upvotes_count);
        }
    }
}
