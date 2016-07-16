package com.tjusyj.listviewdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.TooManyListenersException;
import java.util.zip.Inflater;

public class StupidActivity extends AppCompatActivity {

    private int viewCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stupid);
        setTitle("Stupid: No ListView");
        createStupidViews(500);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.smart:
                startActivity(new Intent(this, SmartActivity.class));
                break;

            case R.id.stupid:
                Toast.makeText(this, "You're here already", Toast.LENGTH_SHORT).show();
                break;

            case R.id.add_views:
                long timer = -System.currentTimeMillis();
                createStupidViews(500);
                timer += System.currentTimeMillis();
                Toast.makeText(this, "Took " + timer + "ms", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    public void createStupidViews(int n) {
        LayoutInflater inflater = this.getLayoutInflater();
        ViewGroup root = (ViewGroup) findViewById(R.id.root_layout);
        for (int i = 0; i < n; ++i) {
            View row = inflater.inflate(R.layout.list_item, root, false);
            ((TextView) row.findViewById(R.id.message)).setText("Row " + (++viewCount));
            ((TextView) row.findViewById(R.id.message2)).setText("   ---tjusyj");
            root.addView(row);
        }
    }
}
