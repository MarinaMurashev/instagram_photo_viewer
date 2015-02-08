package com.example.marinamurashev.instagram_photo_viewer.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.marinamurashev.instagram_photo_viewer.R;
import com.example.marinamurashev.instagram_photo_viewer.models.InstagramPhoto;
import com.example.marinamurashev.instagram_photo_viewer.services.InstagramPopularMedia;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class PhotosActivity extends ActionBarActivity {

    private ArrayList<InstagramPhoto> instagramPhotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);

        instagramPhotos = new ArrayList<>();
        fetchPopularPhotos();

    }

    public void fetchPopularPhotos(){
        String url = InstagramPopularMedia.URL + "?client_id=" + InstagramPopularMedia.CLIENT_ID;
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
