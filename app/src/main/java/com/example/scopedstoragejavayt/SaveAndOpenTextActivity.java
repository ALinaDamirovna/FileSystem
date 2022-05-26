package com.example.scopedstoragejavayt;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class SaveAndOpenTextActivity extends AppCompatActivity {

    private final static String FILE_NAME = "document2.txt";
    private final static String FILE_NAME1 = "content2.txt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.save_open_text);
    }
    //получаем доступ к папке приложения во внешнем хранилище и устанавливаем объект файла:
    private File getExternalPath() {
        return new File(getExternalFilesDir(null), FILE_NAME1);
    }
    // сохранение файла на SD
    public void saveText(View view){
        if(!isExternalStorageWritable()) return;
        if (!checkSpace()){
            Toast.makeText(this, "Осталось менее 500 МБ", Toast.LENGTH_SHORT).show();
        } else{
            try(FileOutputStream fos = new FileOutputStream(getExternalPath())) {
                EditText textBox = findViewById(R.id.editor);
                String text = textBox.getText().toString();
                fos.write(text.getBytes());
                Toast.makeText(this, "Файл сохранен", Toast.LENGTH_SHORT).show();
            }
            catch(IOException ex) {

                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

    }


    // открытие файла SD
    public void openText(View view){

        TextView textView = findViewById(R.id.text);
        File file = getExternalPath();
        // если файл не существует, выход из метода
        if(!file.exists() || !isExternalStorageReadable()) return;
        try(FileInputStream fin =  new FileInputStream(file)) {
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            String text = new String (bytes);
            textView.setText(text);
        }
        catch(IOException ex) {

            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    //РАБОТА С SD
    //проверка на возможность чтения и записи с/на SD
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public void deleteFileSD(View view){
        File file = getExternalPath();
        file.delete();
    }

    public void deleteFile(View view){
        File dir = getFilesDir();
        File file = new File(dir,FILE_NAME1);
        file.delete();
    }

    public boolean checkSpace(){
        File dir = new File(String.valueOf(getExternalFilesDir(null)));
        System.out.println("МЕСТО total "+ dir.getTotalSpace()/(1024*1024));
        System.out.println("МЕСТО usable "+ dir.getUsableSpace()/(1024*1024));
        System.out.println(dir.getTotalSpace()/(1024*1024)-dir.getUsableSpace()/(1024*1024));
        if ((dir.getTotalSpace()/(1024*1024)-dir.getUsableSpace()/(1024*1024))>200)
            return true;
        else
            return false;
    }

    // сохранение файла
    public void saveText1(View view){

        try(FileOutputStream fos = new FileOutputStream(getExternalPath())) {
            EditText textBox = findViewById(R.id.editor);
            String text = textBox.getText().toString();
            fos.write(text.getBytes());
            Toast.makeText(this, "Файл сохранен", Toast.LENGTH_SHORT).show();
        }
        catch(IOException ex) {

            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    // открытие файла
    public void openText1(View view){

        TextView textView = findViewById(R.id.text);
        File file = getExternalPath();
        // если файл не существует, выход из метода
        if(!file.exists()) return;
        try(FileInputStream fin =  new FileInputStream(file)) {
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            String text = new String (bytes);
            textView.setText(text);
        }
        catch(IOException ex) {

            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}