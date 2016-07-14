package com.example.tjusyj.intentfilter;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class CustomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);

        TextView label = (TextView)findViewById(R.id.tv_content);
        Uri url = getIntent().getData();
        label.setText(url.toString());
    }
}
