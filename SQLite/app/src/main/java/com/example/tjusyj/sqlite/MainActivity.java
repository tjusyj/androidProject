package com.example.tjusyj.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button bt_save,bt_read;
    FeedReaderDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDbHelper = new FeedReaderDbHelper(getApplicationContext());

        bt_save = (Button)findViewById(R.id.bt_save);
        bt_save.setOnClickListener(new View.OnClickListener() {
            String id = "001";
            String title = "test";
            @Override
            public void onClick(View view) {
                // Gets the data repository in write mode
                SQLiteDatabase db = mDbHelper.getWritableDatabase();
                // Create a new map of values, where column names are the keys
                ContentValues values = new ContentValues();
                values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_ENTRY_ID, id);
                values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE, title);
                //values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_CONTENT, content);
                // Insert the new row, returning the primary key value of the new row
                long newRowId;
                newRowId = db.insert(
                        FeedReaderContract.FeedEntry.TABLE_NAME,
                        FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE,
                        values);
                Toast.makeText(getApplicationContext(),"save!",Toast.LENGTH_LONG).show();
            }
        });

        //Query
        /*
        SELECT  Name,  Price  FROM  Orders
        WHERE  Country=?
        GROUP   BY  CustomerName
        HAVING   SUM (OrderPrice)>500
        ORDER   BY  CustomerName
        */
        bt_read = (Button)findViewById(R.id.bt_read);
        bt_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = mDbHelper.getReadableDatabase();
                // Define a projection that specifies which columns from the database
                // you will actually use after this query.
                String[] projection = {
                        FeedReaderContract.FeedEntry._ID,
                        FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE,
                        //FeedReaderContract.FeedEntry.COLUMN_NAME_UPDATED,
                };

                // How you want the results sorted in the resulting Cursor
                //String sortOrder = FeedReaderContract.FeedEntry.COLUMN_NAME_UPDATED + " DESC";
                Cursor c = db.query(
                        FeedReaderContract.FeedEntry.TABLE_NAME,  // The table to query
                        projection,                               // The columns to return
                        null,                                // The columns for the WHERE clause
                        null,                            // The values for the WHERE clause
                        null,                                     // don't group the rows
                        null,                                     // don't filter by row groups
                        null                                 // The sort order
                );
                c.moveToFirst();
                String title_read = c.getString(
                        c.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE));
                Toast.makeText(getApplicationContext(),title_read,Toast.LENGTH_LONG).show();
            }
        });

    }
}
