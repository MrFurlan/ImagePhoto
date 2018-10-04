package com.exemple.furlan.imagephoto;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void uploadPhotoFunction(View view) {
        Intent i = new Intent(this, UploadInfoActivity.class);
        startActivity(i);
    }

    public void listPhotoFunction(View view) {
        Intent i = new Intent(this, ListActivity.class);
        startActivity(i);
    }
}
