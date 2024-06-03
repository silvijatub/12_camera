package com.example.a12_camera;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    //ImageView image;
    Button takePhotoBtn, deleteBtn;
    ImageView[] images = new ImageView[4];
    int photoCount = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        takePhotoBtn = (Button) findViewById(R.id.takePhotoBtn);
        //image = (ImageView) findViewById(R.id.imageView);
        deleteBtn = (Button) findViewById(R.id.takeNewPhotoBtn);
        images[0] = findViewById(R.id.imageView1);
        images[1] = findViewById(R.id.imageView2);
        images[2] = findViewById(R.id.imageView3);
        images[3] = findViewById(R.id.imageView4);

        //disable the button if user has no camera
        if (!hasCamera()){
            takePhotoBtn.setEnabled(false);
        }

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAllPhotos();
            }
        });
    }

    public boolean hasCamera(){
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    //launching the camera
    public void launchCamera(View view){
        if (photoCount < 4) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        } else {
            Toast.makeText(this, "All photos are taken", Toast.LENGTH_SHORT).show();
        }
    }

    //if you want to return the image taken


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            //get the photo
            Bundle extras = data.getExtras();
            Bitmap photo = (Bitmap) extras.get("data");
        //    image.setImageBitmap(photo);
            if (photoCount < 4) {
                images[photoCount].setImageBitmap(photo);
                photoCount++;
            }

            if (photoCount > 0){
                deleteBtn.setEnabled(true);
            }

        }
    }

    public void deleteAllPhotos(){
        for (ImageView image : images) {
            image.setImageBitmap(null);
        }
        photoCount = 0;
    }
}