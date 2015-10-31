package com.abhishek.angieslist.utilities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Abhishek on 10/23/2015.
 */
public class Images implements Parcelable{

    public int mViews;
    public int mUpvotes;
    public String mTitle;
    public String mImageUrl;

    public Images(Parcel in) {
        mImageUrl = in.readString();
        mViews = in.readInt();
        mUpvotes = in.readInt();
    }

    public Images(String url, int views, int upvotes){
        this.mImageUrl = url;
        this.mViews = views;
        this.mUpvotes = upvotes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mImageUrl);
        dest.writeInt(mViews);
        dest.writeInt(mUpvotes);
    }

    public static final Parcelable.Creator<Images> CREATOR = new Parcelable.Creator<Images>(){

        @Override
        public Images createFromParcel(Parcel source) {
            return new Images(source);
        }

        @Override
        public Images[] newArray(int size) {
            return new Images[size];
        }
    };
}
