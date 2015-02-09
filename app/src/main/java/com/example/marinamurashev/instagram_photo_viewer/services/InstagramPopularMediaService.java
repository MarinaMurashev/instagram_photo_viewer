package com.example.marinamurashev.instagram_photo_viewer.services;

import android.util.Log;

import com.example.marinamurashev.instagram_photo_viewer.adapters.InstagramPhotosAdapter;
import com.example.marinamurashev.instagram_photo_viewer.models.InstagramPhoto;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class InstagramPopularMediaService {
    private ArrayList<InstagramPhoto> instagramPhotos;
    private InstagramPhotosAdapter instagramPhotosAdapter;

    public static final String CLIENT_ID = "6d191da54a784f669b0df97ec01c6eab";
    public static final String URL = "https://api.instagram.com/v1/media/popular";

    public InstagramPopularMediaService(InstagramPhotosAdapter instagramPhotosAdapter, ArrayList<InstagramPhoto> instagramPhotos){
        this.instagramPhotosAdapter = instagramPhotosAdapter;
        this.instagramPhotos = instagramPhotos;
    }

    public void fetchPopularPhotos(){
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

                        InstagramPhoto instagramPhoto = createPhotoFromJSON(photoJSON);
                        instagramPhotos.add(instagramPhoto);
                    }
                } catch(JSONException e){
                    e.printStackTrace();
                }

                instagramPhotosAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.i("DEBUG", "INSIDE OF FAILURE");
            }
        });
    }

    private InstagramPhoto createPhotoFromJSON(JSONObject photoJSON) throws JSONException {
        InstagramPhoto instagramPhoto = new InstagramPhoto();
        instagramPhoto.setCreatedAt(Long.parseLong(photoJSON.getString("created_time")));
        instagramPhoto.setUsername(photoJSON.getJSONObject("user").getString("username"));
        instagramPhoto.setLikesCount(photoJSON.getJSONObject("likes").getInt("count"));
        instagramPhoto.setUserProfileImageUrl(photoJSON.getJSONObject("user").getString("profile_picture"));

        if (photoJSON.optJSONObject("caption") != null) {
            instagramPhoto.setCaption(photoJSON.getJSONObject("caption").getString("text"));
        }

        JSONObject standard_resolution_image = photoJSON.getJSONObject("images").getJSONObject("standard_resolution");
        instagramPhoto.setImageUrl(standard_resolution_image.getString("url"));
        instagramPhoto.setImageHeight(standard_resolution_image.getInt("height"));

        return  instagramPhoto;
    }
}
