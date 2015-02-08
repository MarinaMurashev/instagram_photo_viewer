package com.example.marinamurashev.instagram_photo_viewer.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;

import com.example.marinamurashev.instagram_photo_viewer.R;
import com.example.marinamurashev.instagram_photo_viewer.adapters.InstagramPhotosAdapter;
import com.example.marinamurashev.instagram_photo_viewer.models.InstagramPhoto;
import com.example.marinamurashev.instagram_photo_viewer.services.InstagramPopularMediaService;

import java.util.ArrayList;


public class PhotosActivity extends ActionBarActivity {

    private ArrayList<InstagramPhoto> instagramPhotos;
    private InstagramPhotosAdapter instagramPhotosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);

        instagramPhotos = new ArrayList<>();
        instagramPhotosAdapter = new InstagramPhotosAdapter(this, instagramPhotos);
        ListView lvPhotos = (ListView) findViewById(R.id.lvPhotos);
        lvPhotos.setAdapter(instagramPhotosAdapter);

        new InstagramPopularMediaService(instagramPhotosAdapter, instagramPhotos).fetchPopularPhotos();

    }
}
