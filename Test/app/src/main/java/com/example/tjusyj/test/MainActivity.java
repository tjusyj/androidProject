package com.example.tjusyj.test;

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Point size = new Point();
        Display display = getWindowManager().getDefaultDisplay();
        display.getSize(size);
        if (size.x > size.y) {// width > height
            Frag1 frag1 = new Frag1();
            getFragmentManager().beginTransaction().replace(R.id.main_layout, frag1).commit();
        } else {
            Frag2 frag2 = new Frag2();
            getFragmentManager().beginTransaction().replace(R.id.main_layout, frag2).commit();
        }
    }
}