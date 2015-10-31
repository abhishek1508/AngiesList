package com.abhishek.angieslist.parser;

import com.abhishek.angieslist.utilities.Constants;
import com.abhishek.angieslist.utilities.Images;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abhishek on 10/28/2015.
 */
public class JSONParser {

    public static Images mImageAttribute;
    public Images mAttributes;
    public int upvotes;
    //public List<Images> list;
    int mLength;

    public ArrayList<Images> getImageAttribute(JSONObject object,ArrayList<Images> list){
        try {
            JSONArray array = object.getJSONArray(Constants.mJSONObjectData);
            mLength = array.length();
            for(int i = 0; i < mLength; i++){
                JSONObject obj = array.getJSONObject(i);
                boolean nsfw = getNonNSFWImageInfo(obj,Constants.NSFW);
                 //If the image is not nsfw, then only it is added to the list otherwise not.
                if(!nsfw) {
                    if((getUpvotesCountInfo(obj, Constants.UPVOTES)).toString().equalsIgnoreCase("null"))
                        upvotes = 0;
                    else
                        upvotes = Integer.parseInt((getUpvotesCountInfo(obj, Constants.UPVOTES)).toString());
                    mAttributes = new Images(getImageLinkInfo(obj, Constants.IMAGE_URL), getViewsCountInfo(obj, Constants.VIEWS),upvotes);
                    mAttributes.mTitle = getTitleInfo(obj,Constants.TITLE);
                    list.add(mAttributes);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public String getImageLinkInfo(JSONObject obj,String str) throws JSONException {
        return obj.getString(str);
    }

    public boolean getNonNSFWImageInfo(JSONObject obj,String str) throws JSONException {
        return obj.getBoolean(str);
    }

    public int getViewsCountInfo(JSONObject obj,String str) throws JSONException {
        return obj.getInt(str);
    }

    public Object getUpvotesCountInfo(JSONObject obj,String str) throws JSONException {
        return obj.get(str);
    }

    public String getTitleInfo(JSONObject obj,String str) throws JSONException {
        return obj.getString(str);
    }
}
