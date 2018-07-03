package com.example.android.bookstore;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.android.bookstore.Data.BookContract.BookEntry;
import com.example.android.bookstore.Data.BookDbHelper;

public class CatalogActivity extends AppCompatActivity {
    private BookDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);
        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });
        mDbHelper = new BookDbHelper(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    /**
     * display information in the onscreen TextView about the state of
     * the Book database.
     */
    private void displayDatabaseInfo() {
        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        BookDbHelper mDbHelper = new BookDbHelper(this);

        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                BookEntry._ID,
                BookEntry.COLUMN_Book_Name,
                BookEntry.COLUMN_Book_Price,
                BookEntry.COLUMN_Book_Quantity,
                BookEntry.COLUMN_Book_SupplierName,
                BookEntry.COLUMN_Book_SupplierPhone
        };
        Cursor cursor = db.query(BookEntry.TABLE_NAME, projection, null, null, null, null, null);
        TextView displayView = (TextView) findViewById(R.id.text_view_book);

        try {
            // Create a header in the Text View that looks like this:
            //
            // The Book table contains <number of rows in Cursor> Book.
            // _id - name - Price - Quantity - SupplierName - SupplierPhone
            //
            // In the while loop below, iterate through the rows of the cursor and display
            // the information from each column in this order.
            displayView.setText("The Book table contains " + cursor.getCount() + " Books.\n\n");
            displayView.append(BookEntry._ID + " - " +
                    BookEntry.COLUMN_Book_Name + " - " +
                    BookEntry.COLUMN_Book_Price + " - " +
                    BookEntry.COLUMN_Book_Quantity + " - " +
                    BookEntry.COLUMN_Book_SupplierName + " - " +
                    BookEntry.COLUMN_Book_SupplierPhone + "\n");
            // Figure out the index of each column.
            int idColumnIndex = cursor.getColumnIndex(BookEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_Book_Name);
            int PriceColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_Book_Price);
            int QuantityColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_Book_Quantity);
            int SupplierNamedColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_Book_SupplierName);
            int SupplierPhoneColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_Book_SupplierPhone);

            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                int currentPrice = cursor.getInt(PriceColumnIndex);
                int currentQuantity = cursor.getInt(QuantityColumnIndex);
                String currentSupplierName = cursor.getString(SupplierNamedColumnIndex);
                String currentSupplierPhone = cursor.getString(SupplierPhoneColumnIndex);
                // Display the values from each column of the current row in the cursor in the TextView
                displayView.append(("\n" + currentID + " - " +
                        currentName + " - " +
                        currentPrice + " - " +
                        currentQuantity + " - " +
                        currentSupplierName + " - " +
                        currentSupplierPhone));
            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }

    private void insertBook() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BookEntry.COLUMN_Book_Name, "Android learning");
        values.put(BookEntry.COLUMN_Book_Price, 65);
        values.put(BookEntry.COLUMN_Book_Quantity, 5);
        values.put(BookEntry.COLUMN_Book_SupplierName, "Alef library");
        values.put(BookEntry.COLUMN_Book_SupplierPhone, "01027577137");
        Long NewRowId = db.insert(BookEntry.TABLE_NAME, null, values);
        Log.v("CatalogActivity", "New Row Id" + NewRowId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertBook();
                displayDatabaseInfo();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
