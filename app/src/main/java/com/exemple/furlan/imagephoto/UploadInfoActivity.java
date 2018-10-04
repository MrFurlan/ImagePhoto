package com.exemple.furlan.imagephoto;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;

import me.iwf.photopicker.PhotoPicker;

public class UploadInfoActivity extends Activity {
    private EditText txtTitle;
    private ImageView imgSelected;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private ArrayList<String> photos;
    private ProgressBar progressUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_info);

        txtTitle = findViewById(R.id.txt_title);
        imgSelected = findViewById(R.id.imgSelected);
        progressUpdate = findViewById(R.id.progressUpdate);
        photos = new ArrayList<>();

        mStorageRef = FirebaseStorage.getInstance().getReference();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
    }

    public void photoPickerFunction(View view) {
        PhotoPicker.builder()
                .setPhotoCount(1)
                .setShowCamera(true)
                .setShowGif(true)
                .setPreviewEnabled(false)
                .start(this, PhotoPicker.REQUEST_CODE);
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE) {
            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                imgSelected.setImageURI(Uri.parse(photos.get(0)));
            }
        }
    }

    private void resetForm(Boolean ok){
        photos.clear();
        imgSelected.setImageResource(0);

        if (ok) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }
    }

    public void sendPhotoFunction(View view) {
        if (txtTitle.getText().toString().equals("")) {
            Toast.makeText(this, "Título não foi informado.", Toast.LENGTH_SHORT).show();
        } else if (photos.size() == 0) {
            Toast.makeText(this, "Foto não foi selecionada.", Toast.LENGTH_SHORT).show();
        } else {
            progressUpdate.setVisibility(ProgressBar.VISIBLE);

            Uri file = Uri.fromFile(new File(photos.get(0)));

           StorageReference photoRef = mStorageRef.child("images/"+System.currentTimeMillis());

            photoRef.putFile(file)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Photo photo = new Photo(taskSnapshot.getDownloadUrl().toString(), txtTitle.getText().toString());

                    mDatabaseRef.child("photos").child(String.valueOf(System.currentTimeMillis())).setValue(photo).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            progressUpdate.setVisibility(ProgressBar.GONE);

                            Toast.makeText(UploadInfoActivity.this, "Foto enviada com sucesso!", Toast.LENGTH_SHORT).show();

                            resetForm(true);
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(UploadInfoActivity.this, "Falha ao enviar arquivo.", Toast.LENGTH_SHORT).show();

                    resetForm(false);
                }
            });
        }
    }
}
