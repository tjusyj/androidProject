package com.example.tjusyj.intentfilter;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button bt_view,bt_launcher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_view = (Button)findViewById(R.id.bt_view);
        bt_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.example.com"));
                startActivity(i);
            }
        });

        bt_launcher = (Button)findViewById(R.id.bt_launcher);
        bt_launcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent("com.example.tjusyj.intentfilter.LAUNCH",Uri.parse("http://www.example.com"));
                startActivity(i);
            }
        });

    }
}
