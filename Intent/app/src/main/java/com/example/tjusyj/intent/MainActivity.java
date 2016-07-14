package com.example.tjusyj.intent;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button bt_kcl,bt_share;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_kcl = (Button)findViewById(R.id.bt_kcl);
        //创建查看地图的意向创建查看地图的意向创建查看地图的意向创建查看地图的意向创建查看地图的意向创建查看地图的意向Intent to view a map
        bt_kcl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Build the intent
                Uri location = Uri.parse("geo:0,0?q=King's+College+London");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
                // Verify it resolves
                PackageManager packageManager = getPackageManager();
                List<ResolveInfo> activities = packageManager.queryIntentActivities(mapIntent, 0);
                boolean isIntentSafe = activities.size() > 0;
                // Start an activity if it's safe
                if (isIntentSafe) {
                    startActivity(mapIntent);
                }
            }
        });

        //Intent Chooser
        bt_share = (Button)findViewById(R.id.bt_share);
        bt_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                //type is compulsory
                intent.setType("text/plain");
                //info is optional
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] {"tjusyj@gmail.com"}); // recipients
                intent.putExtra(Intent.EXTRA_SUBJECT, "Email subject");
                intent.putExtra(Intent.EXTRA_TEXT, "Email message");

                // Always use string resources for UI text.
                // This says something like "Share this photo with"
                String title = getResources().getString(R.string.chooser_title);
                // Create intent to show chooser
                Intent chooser = Intent.createChooser(intent, title);
                // Verify the intent will resolve to at least one activity
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(chooser);
                }
            }
        });


    }
}
