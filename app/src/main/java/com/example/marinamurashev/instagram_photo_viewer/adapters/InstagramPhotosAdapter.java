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

    private static class ViewHolder {
        TextView tvCaption;
        TextView tvUsername;
        ImageView ivPhoto;
        ImageView ivUserPhoto;
    }


    public InstagramPhotosAdapter(Context context, List<InstagramPhoto> objects) {
        super(context, R.layout.item_photo, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        InstagramPhoto instagramPhoto = getItem(position);
        ViewHolder viewHolder;

        if(convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_photo, parent, false);

            viewHolder.tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
            viewHolder.tvUsername = (TextView) convertView.findViewById(R.id.tvUsername);
            viewHolder.ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);
            viewHolder.ivUserPhoto = (ImageView) convertView.findViewById(R.id.ivUserPhoto);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String boldUsername = "<b>" + instagramPhoto.getUsername() + "</b>";
        viewHolder.tvUsername.setText(Html.fromHtml(boldUsername));
        viewHolder.tvCaption.setText(instagramPhoto.getCaption());
        viewHolder.ivPhoto.setImageResource(0);
        Picasso.with(getContext()).load(instagramPhoto.getImageUrl()).into(viewHolder.ivPhoto);
        Picasso.with(getContext()).load(instagramPhoto.getUserProfileImageUrl()).into(viewHolder.ivUserPhoto);

        return convertView;
    }
}
