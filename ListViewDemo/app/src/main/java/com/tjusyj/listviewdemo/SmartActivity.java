package com.tjusyj.listviewdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SmartActivity extends AppCompatActivity {

    private SmartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart);
        setTitle("Smart: ListView");
        createSmartViews();
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
                Toast.makeText(this, "You're here already", Toast.LENGTH_SHORT).show();
                break;

            case R.id.stupid:
                startActivity(new Intent(this, StupidActivity.class));
                break;

            case R.id.add_views:
                long timer = -System.currentTimeMillis();
                adapter.addRows(500);
                timer += System.currentTimeMillis();
                Toast.makeText(this, "Took " + timer + "ms", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    public void createSmartViews() {
        ListView listView = (ListView) findViewById(R.id.list_view);
        adapter = new SmartAdapter(getLayoutInflater());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //ListView listView= (ListView) parent;
                TextView textView = (TextView) view.findViewById(R.id.message);
                TextView textView2 = (TextView) view.findViewById(R.id.message2);
                //String text = listView.getItemAtPosition(position).toString();
                String info = textView.getText().toString();
                String info2 = textView2.getText().toString();
                Toast.makeText(SmartActivity.this,info+"   "+info2,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
