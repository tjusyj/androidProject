package com.example.tjusyj.intent;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    private static final int REQUEST_CAMERA = 0;
    static final int PICK_CONTACT_REQUEST = 1;  // The request code
    static final int REQUEST_IMAGE_CAPTURE = 2;
    private View mLayout;
    Button bt_kcl,bt_share,bt_contact,bt_photo;
    EditText edit_number;
    ImageView img_photo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLayout = findViewById(R.id.main_layout);

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

        bt_contact = (Button)findViewById(R.id.bt_contact);
        bt_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickContact();
            }
        });

        bt_photo = (Button)findViewById(R.id.bt_photo);
        bt_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check permission
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    // Camera permission has not been granted.

                    requestCameraPermission();

                } else {

                    // Camera permissions is already available, show the camera preview.
                    Log.i(TAG,
                            "CAMERA permission has already been granted. Displaying camera preview.");
                }
                dispatchTakePictureIntent();
            }
        });
    }

    //Choose Contact
    private void pickContact() {
        Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
        pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
        startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST);
    }

    //Take Photo
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    //Receive Result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == PICK_CONTACT_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // The user picked a contact.
                // The Intent's data Uri identifies which contact was selected.
                // Get the URI that points to the selected contact
                Uri contactUri = data.getData();
                // We only need the NUMBER column, because there will be only one row in the result
                String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER};

                // Perform the query on the contact to get the NUMBER column
                // Consider using CursorLoader to perform the query.
                Cursor cursor = getContentResolver().query(contactUri, projection, null, null, null);
                cursor.moveToFirst();

                // Retrieve the phone number from the NUMBER column
                int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String number = cursor.getString(column);
                //Display received number
                edit_number = (EditText)findViewById(R.id.edit_number);
                edit_number.setText(number);
            }
        }else if(requestCode == REQUEST_IMAGE_CAPTURE) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK && data!=null) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                //!!!!!!!!!!!!DO NOT FORGET THIS!!!!!!!!!
                img_photo = (ImageView)findViewById(R.id.img_photo);
                try {
                    img_photo.setImageBitmap(imageBitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void requestCameraPermission() {
        Log.i(TAG, "CAMERA permission has NOT been granted. Requesting permission.");

        // BEGIN_INCLUDE(camera_permission_request)
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // For example if the user has previously denied the permission.
            Log.i(TAG,"Displaying camera permission rationale to provide additional context.");
            Snackbar.make(mLayout, "Camera Needed!",
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{Manifest.permission.CAMERA},
                                    REQUEST_CAMERA);
                        }
                    })
                    .show();
        } else {

            // Camera permission has not been granted yet. Request it directly.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    REQUEST_CAMERA);
        }
        // END_INCLUDE(camera_permission_request)
    }
}
