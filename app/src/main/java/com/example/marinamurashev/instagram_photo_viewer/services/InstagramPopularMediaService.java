package com.example.marinamurashev.instagram_photo_viewer.services;

import android.util.Log;

import com.example.marinamurashev.instagram_photo_viewer.models.InstagramPhoto;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class InstagramPopularMediaService {
    private ArrayList<InstagramPhoto> instagramPhotos = new ArrayList<>();

    public static final String CLIENT_ID = "6d191da54a784f669b0df97ec01c6eab";
    public static final String URL = "https://api.instagram.com/v1/media/popular";

    public ArrayList<InstagramPhoto> fetchPopularPhotos(){
        String url = URL + "?client_id=" + CLIENT_ID;
        AsyncHttpClient client = new AsyncHttpClient();

        client.get(url, null, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray photosJSON = null;
                try{
                    photosJSON = response.getJSONArray("data");

                    for(int i = 0; i < photosJSON.length(); i++){
                        JSONObject photoJSON = photosJSON.getJSONObject(i);

                        InstagramPhoto instagramPhoto = new InstagramPhoto();
                        instagramPhoto.setUsername(photoJSON.getJSONObject("user").getString("username"));
                        if (photoJSON.optJSONObject("caption") != null) {
                            instagramPhoto.setCaption(photoJSON.getJSONObject("caption").getString("text"));
                        }
                        JSONObject standard_resolution_image = photoJSON.getJSONObject("images").getJSONObject("standard_resolution");
                        instagramPhoto.setImageUrl(standard_resolution_image.getString("url"));
                        instagramPhoto.setImageHeight(standard_resolution_image.getInt("height"));
                        instagramPhoto.setLikesCount(photoJSON.getJSONObject("likes").getInt("count"));

                        instagramPhotos.add(instagramPhoto);
                    }
                } catch(JSONException e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.i("DEBUG", "INSIDE OF FAILURE");
            }
        });

        return instagramPhotos;
    }
}
