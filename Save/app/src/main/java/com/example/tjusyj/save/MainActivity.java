package com.example.tjusyj.save;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    // Storage Permissions
    int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 200;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //REQUEST
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //REQUEST WRITE_EXTERNAL_STORAGE权 PERMISSIONS
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
        }

        save=(Button)findViewById(R.id.bt_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //External
                try{
                    String filename="ext.txt";
                    File file1 = new File(Environment.getExternalStorageDirectory().getAbsoluteFile(),filename);
                    file1.createNewFile();
                    FileOutputStream out = new FileOutputStream(file1);
                    OutputStreamWriter outWriter = new OutputStreamWriter(out);
                    outWriter.append("external info.");
                    outWriter.close();
                    Toast.makeText(getApplicationContext(),filename+"saved",Toast.LENGTH_LONG).show();
                }catch (FileNotFoundException e){
                    e.printStackTrace();
                }catch (IOException e){
                    e.printStackTrace();
                }

            }
        });

        //Internal
        // internal -- /data/data/com.example.tjusyj.save/files
        String filename = "myfile";
        String string = "Hello world!";
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(string.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doNext(requestCode,grantResults);
    }

    private void doNext(int requestCode, int[] grantResults) {
        if (requestCode == WRITE_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                Toast.makeText(getApplicationContext(),"Write Granted~",Toast.LENGTH_LONG).show();
            } else {
                // Permission Denied
            }
        }
    }
}
