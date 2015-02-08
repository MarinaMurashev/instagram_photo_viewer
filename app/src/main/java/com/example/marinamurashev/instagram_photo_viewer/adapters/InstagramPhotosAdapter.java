package com.example.marinamurashev.instagram_photo_viewer.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.marinamurashev.instagram_photo_viewer.R;
import com.example.marinamurashev.instagram_photo_viewer.models.InstagramPhoto;
import com.squareup.picasso.Picasso;

import java.util.List;

public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {

    public InstagramPhotosAdapter(Context context, List<InstagramPhoto> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        InstagramPhoto instagramPhoto = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
        }

        TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
        TextView tvUsername = (TextView) convertView.findViewById(R.id.tvUsername);
        ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);

        String boldUsername = "<b>" + instagramPhoto.getUsername() + "</b>";
        tvUsername.setText(Html.fromHtml(boldUsername));
        tvCaption.setText(instagramPhoto.getCaption());
        ivPhoto.setImageResource(0);
        Picasso.with(getContext()).load(instagramPhoto.getImageUrl()).into(ivPhoto);

        return convertView;
    }
}
