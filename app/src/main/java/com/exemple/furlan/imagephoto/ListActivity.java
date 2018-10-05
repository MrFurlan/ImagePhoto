package com.exemple.furlan.imagephoto;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends Activity {
    private ProgressBar progressLoad;
    private ListView listView;

    private DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        progressLoad = findViewById(R.id.progressLoad);
        listView = findViewById(R.id.list);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference();

        Query mQuery = mDatabaseRef.child("photos").orderByKey();

        mQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Photo> photos = new ArrayList<Photo>();

                for(DataSnapshot photosSnapshot : dataSnapshot.getChildren()){
                    photos.add(photosSnapshot.getValue(Photo.class));
                    Log.d("DEBUG", photosSnapshot.getValue(Photo.class).getPhotoComment());
                }

                ListAdapter lAdapter = new ListAdapter(getApplicationContext(), R.layout.single_list_item, photos);
                listView.setAdapter(lAdapter);

                progressLoad.setVisibility(ProgressBar.GONE);
                listView.setVisibility(ListView.VISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
