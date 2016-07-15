package com.example.tjusyj.sharedata;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.io.File;

public class MainActivity extends Activity {

    private ShareActionProvider shareActionProvider;
    // The path to the root of this app's internal storage
    private File mPrivateRootDir;
    // The path to the "images" subdirectory
    private File mImagesDir;
    // Array of files in the images subdirectory
    File[] mImageFiles;
    // Array of filenames corresponding to mImageFiles
    String[] mImageFilenames;
    // Initialize the Activity
    private Button bt_select;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_select=(Button)findViewById(R.id.bt_select);
        bt_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,FileSelectActivity.class);
                startActivity(intent);
                //如果不关闭当前的会出现好多个页面
                MainActivity.this.finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate menu resource file.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        // Locate MenuItem with ShareActionProvider
        MenuItem item = menu.findItem(R.id.menu_item_share);
        shareActionProvider= (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        shareActionProvider.setShareIntent(getDefaultIntent());
        // Return true to display menu
        return true;
    }

    private Intent getDefaultIntent() {
        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra("Text","test");
        return intent;
    }
    // Call to update the share intent
    private void setShareIntent(Intent shareIntent) {
        if (shareActionProvider != null) {
            shareActionProvider.setShareIntent(shareIntent);
        }
    }
}
