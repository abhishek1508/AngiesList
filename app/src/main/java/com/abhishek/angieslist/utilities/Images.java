package com.abhishek.angieslist.utilities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Abhishek on 10/23/2015.
 */
public class Images implements Parcelable{

    public boolean mIsNSFW;
    public int mViews;
    public String mImageUrl;

    public Images(Parcel in) {
        mImageUrl = in.readString();
        mViews = in.readInt();
        mIsNSFW = in.readByte()!=0;
    }

    public Images(String url, int views, boolean nsfw){
        this.mImageUrl = url;
        this.mViews = views;
        this.mIsNSFW = nsfw;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mImageUrl);
        dest.writeInt(mViews);
        dest.writeByte((byte)(mIsNSFW ? 1:0));
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
