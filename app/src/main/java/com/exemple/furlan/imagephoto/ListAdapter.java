package com.exemple.furlan.imagephoto;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ListAdapter extends ArrayAdapter<Photo> {
    private int resource;
    private List<Photo> photos;

    public ListAdapter(Context context, int resource, List<Photo> objects) {
        super(context, resource, objects);
        this.resource = resource;
        photos = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View currentView, ViewGroup parent) {
        View mView = currentView;

        if(mView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mView = inflater.inflate(resource, null, false);
        }

        Photo photo = photos.get(position);

        TextView comment = mView.findViewById(R.id.photoTitle);
        ImageView image = mView.findViewById(R.id.photoImage);

        if(comment != null) {
            comment.setText(photo.getPhotoComment());
        }

        if(image != null) {
            Picasso.get()
                    .load(photo.getPhotoPath())
                    .into(image);
        }

        return mView;
    }
}
