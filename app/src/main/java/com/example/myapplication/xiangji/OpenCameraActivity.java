package com.example.myapplication.xiangji;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.Utils.GlideImageLoader;
import com.example.myapplication.base.MyBase64;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OpenCameraActivity extends AppCompatActivity  implements View.OnClickListener {

    private static int CAMERA_WITH_DATA = 10000;


    private static final int REQUEST_PERMISSIONS_CODE = 1;
    private static final String[] REQUIRED_PERMISSIONS =
            {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};


    private ImageView iv_tu,iv_tu_two;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_camera);

        iv_tu = findViewById(R.id.iv_tu);
        iv_tu_two = findViewById(R.id.iv_tu_two);

        initView();

        findViewById(R.id.bt_daikai).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCamera();
            }
        });

        findViewById(R.id.bt_xiangce).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(OpenCameraActivity.this, ImageGridActivity.class);
                startActivityForResult(intent1, 100);
            }
        });

        // ??????????????????
        if (!isRequiredPermissionsGranted() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(REQUIRED_PERMISSIONS, REQUEST_PERMISSIONS_CODE);
        }

        Bitmap bitma1  = BitmapFactory.decodeResource(getResources(),
                R.mipmap.ic_launcher, null);
        String s = bitmapToBase64(bitma1);

    }

    public void openCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //Uri uri=Uri.fromFile(new File(path)); // path???????????????????????????
       // intent.putExtra(MediaStore.EXTRA_OUTPUT,uri); //???????????????????????? ?????????????????????
        //intent.ac(MediaStore.EXTRA_OUTPUT,Uri.fromFile(mCurrentPhotoFile));
        startActivityForResult(intent, CAMERA_WITH_DATA);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            Log.e("TAG","=====111111111=ddd:");

            if (data != null && requestCode ==100) {
                Log.e("TAG","==22222222222222222====ddd:" );

                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(com.lzy.imagepicker.ImagePicker.EXTRA_RESULT_ITEMS);
              //  String one = MyBase64.imageToBase64(images.get(0).path);

                Glide.with(OpenCameraActivity.this)
                        .load(images.get(0).path)
                        .into(iv_tu_two);

                Uri uri = Uri.parse(images.get(0).path);
                photoClip(uri);

                Log.e("TAG","======ddd:" + images.get(0).path);
            } else {
                Toast.makeText(this, "????????????", Toast.LENGTH_SHORT).show();
            }
        }

        Log.i("TAG","-----------resultCode:" + resultCode + "=requestCode:" +requestCode);
        if (requestCode == CAMERA_WITH_DATA){
            if (data == null){
                Log.i("TAG","--------------------dddd");
            }
            Uri uri = data.getData();
            Bundle extras = data.getExtras();

            Bitmap data1 = (Bitmap) extras.get("data");

            String s = bitmapToBase64(data1);
            Log.e("TAG","ddd:" + s);

            if (data1 != null){
                Glide.with(OpenCameraActivity.this)
                        .load(data1)
                        .into(iv_tu);
            }
        }
    }

    public String saveImage(String name, Bitmap bmp) {
        File appDir = new File(Environment.getExternalStorageDirectory().getPath());
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = name + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            return file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * ????????????????????????????????????????????????????????????????????????????????????????????? false???
     *
     * @return true ??????????????????
     */
    private boolean isRequiredPermissionsGranted() {
        for (String permission : REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }


    /**
     * bitmap??????base64
     * @param bitmap
     * @return
     */
    public  String bitmapToBase64(Bitmap bitmap) {

        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.URL_SAFE);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * base64??????bitmap
     * @param base64Data
     * @return
     */
    public  Bitmap base64ToBitmap(String base64Data) {
        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

//-----------------------------------------------------------------------------------------------------------------------------------


    private void initView() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //?????????????????????
        imagePicker.setShowCamera(true);  //??????????????????
        imagePicker.setCrop(true);        //?????????????????????????????????
        imagePicker.setSaveRectangle(true); //???????????????????????????
        imagePicker.setSelectLimit(9);    //??????????????????
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //??????????????????
        imagePicker.setFocusWidth(800);   //?????????????????????????????????????????????????????????????????????
        imagePicker.setFocusHeight(800);  //?????????????????????????????????????????????????????????????????????
        imagePicker.setOutPutX(1000);//????????????????????????????????????
        imagePicker.setOutPutY(1000);//????????????????????????????????????
    }



    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {


        }
    }



    private void photoClip(Uri uri) {
        // ????????????????????????????????????
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // ????????????crop=true?????????????????????Intent??????????????????VIEW?????????
        intent.putExtra("crop", "true");
        // aspectX aspectY ??????????????????
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY ?????????????????????
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);


    }


    //??????????????????
    private void goPhotoAlbum() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 2);
    }


}