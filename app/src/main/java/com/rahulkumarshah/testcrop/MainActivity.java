package com.rahulkumarshah.testcrop;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.isseiaoki.simplecropview.CropImageView;
import com.isseiaoki.simplecropview.callback.CropCallback;
import com.isseiaoki.simplecropview.callback.SaveCallback;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";
    private CropImageView imageView;
    private ImageButton leftRotate,rightRotate,squareCrop,verticalCrop;
    private RelativeLayout setWall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);


        imageView = (CropImageView) findViewById(R.id.cropView);
        leftRotate = (ImageButton) findViewById(R.id.leftID);
        rightRotate = (ImageButton) findViewById(R.id.rightID);
        squareCrop = (ImageButton) findViewById(R.id.squareID);
        verticalCrop = (ImageButton) findViewById(R.id.rectID);
        setWall = (RelativeLayout) findViewById(R.id.setWallpaperID);

        Glide.with(this)
                .load("https://wallpapers.wallhaven.cc/wallpapers/full/wallhaven-669799.jpg")
                .apply(new RequestOptions()
                    .override(Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL))
                .into(imageView);
        imageView.setCropMode(CropImageView.CropMode.SQUARE);




        leftRotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.rotateImage(CropImageView.RotateDegrees.ROTATE_M90D);
                imageView.setAnimationEnabled(true);
            }
        });



        rightRotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.rotateImage(CropImageView.RotateDegrees.ROTATE_90D);
                imageView.setAnimationEnabled(true);
            }
        });



        squareCrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setCropMode(CropImageView.CropMode.SQUARE);
                imageView.setAnimationEnabled(true);
            }
        });


        verticalCrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setCropMode(CropImageView.CropMode.RATIO_9_16);
                imageView.setAnimationEnabled(true);
            }
        });


        setWall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.crop(imageView.getSourceUri()).execute(mCropCallback);
            }
        });



    }


    // SimpleCropView Image Crop Callback
    private final CropCallback mCropCallback = new CropCallback() {
        @Override public void onSuccess(Bitmap cropped) {
            //imageView.save(cropped).compressFormat(mCompressFormat).execute(mSaveCallback);

            int width = cropped.getWidth();
            int height = cropped.getHeight();

            if (width < height){
                setVerticalWallpaper(cropped);
            }else {
                setHorizontalWallpaper(cropped);
            }
        }

        @Override public void onError(Throwable e) {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    };







    // Set Wallpaper Algorithms
    public void setHorizontalWallpaper(Bitmap bitmap){
        final WallpaperManager manager = (WallpaperManager)getSystemService(Context.WALLPAPER_SERVICE);
        manager.setWallpaperOffsets( imageView.getApplicationWindowToken(), 0.5f, 0f);
        manager.suggestDesiredDimensions(DeviceMetrics.getDisplayWidth(this)*2, DeviceMetrics.getDisplayHeight(this));
        try {
            manager.setBitmap(bitmap);
            Toast.makeText(this,"Scrollable Wallpaper Set Successfully",Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setVerticalWallpaper(Bitmap bitmap){
        final WallpaperManager manager = (WallpaperManager)getSystemService(Context.WALLPAPER_SERVICE);
        manager.suggestDesiredDimensions(DeviceMetrics.getDisplayWidth(this), DeviceMetrics.getDisplayHeight(this));
        try {
            manager.setBitmap(bitmap);
            Toast.makeText(this,"Static Wallpaper Set Successfully",Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
