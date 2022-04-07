package com.example.scopedstoragejavayt;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class SaveImageActivity extends AppCompatActivity {

    private static int REQUEST_CODE = 100;
    ImageView bird;
    Button SaveImg;
    OutputStream outputStream;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        SaveImg = findViewById(R.id.saveimgbtn);
        bird = findViewById(R.id.bird);
        SaveImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(SaveImageActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){

                    saveImage();

                }else {
                    askPermission();

                }

            }
        });
    }

    private void askPermission() {
//* Для запроса используется метод ActivityCompat.requestPermissions(Activity activity, String[] permissions, int requestCode).
// Массив permissions соответственно содержит названия разрешений, которые вы хотите запросить.
// Отсюда видно, что одновременно можно запрашивать несколько разрешений.
// requestCode — значение, по которому в дальнейшем можно будет определить, на какой запрос разрешения вам пришел ответ */
        ActivityCompat.requestPermissions(SaveImageActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_CODE);

    }
/* Результат запроса разрешения следует обрабатывать в onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
 @NonNull int[] grantResults). Параметры requestCode и permissions содержат данные, которые вы передавали при запросе разрешений.
 Основные данные здесь несет массив grantResults, в котором находится информация о том, получены разрешения или нет.*/
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {

        if (requestCode == REQUEST_CODE)
        {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){


                saveImage();

            }else {


                Toast.makeText(SaveImageActivity.this,"Please provide the required permissions",Toast.LENGTH_SHORT).show();

            }

        }


        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void saveImage() {

        File dir = new File(Environment.getExternalStorageDirectory(),"SaveImage");

        if (!dir.exists()){

            dir.mkdir();

        }

        BitmapDrawable drawable = (BitmapDrawable) bird.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        File file = new File(dir,System.currentTimeMillis()+".jpg");
        try {
            outputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        /* С этим методом вы можете добиться сжатия изображения.
         Чтобы использовать этот метод, вам нужно передать три параметра: CompressFormat, int quality, OutputStream
CompressFormat
определяет формат сжатия растрового изображения и может выбирать JPEG, PNG, WEBP
качество
Определяет качество сжатия растрового изображения в диапазоне от 0 до 100: чем выше значение,
 тем выше качество изображения. 0 означает худшее качество, а 100 означает высшее качество.

OutputStream
определяет поток вывода байтов битовой карты. Общее использование:*/
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
        Toast.makeText(SaveImageActivity.this,"Successfuly Saved",Toast.LENGTH_SHORT).show();

        try {
            //Метод flush() используется, чтобы принудительно записать в целевой поток данные, которые могут кэшироваться в текущем потоке.
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}