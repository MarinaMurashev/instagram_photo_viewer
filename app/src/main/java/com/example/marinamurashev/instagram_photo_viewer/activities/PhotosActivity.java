package com.example.marinamurashev.instagram_photo_viewer.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
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
