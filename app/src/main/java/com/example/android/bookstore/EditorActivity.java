package com.example.android.bookstore;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.bookstore.Data.BookContract.BookEntry;
import com.example.android.bookstore.Data.BookDbHelper;

public class EditorActivity extends AppCompatActivity {
    /**
     * EditText field to enter the Book's name
     */
    private EditText mNameEditText;

    /**
     * EditText field to enter the Book's Price
     */
    private EditText mPriceEditText;

    /**
     * EditText field to enter the Books's Quantity
     */
    private EditText mQuantityEditText;

    /**
     * EditText field to enter the Book's SupplierName
     */
    private EditText mSupplierNameEditText;

    /**
     * EditText field to enter the Book's SupplierPhoneNo.
     */
    private EditText mSupplierPhoneEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        // Find all relevant views that we will need to read user input from.
        mNameEditText = (EditText) findViewById(R.id.book_title);
        mPriceEditText = (EditText) findViewById(R.id.Book_price);
        mQuantityEditText = (EditText) findViewById(R.id.Book_quantity);
        mSupplierNameEditText = (EditText) findViewById(R.id.Supplier_name);
        mSupplierPhoneEditText = (EditText) findViewById(R.id.Supplier_phone);

    }

    //Get user input from editor and save new Book into database.
    private void BookInsert() {
        String NameString = mNameEditText.getText().toString().trim();
        String PriceString = mPriceEditText.getText().toString().trim();
        int Price = Integer.parseInt(PriceString);
        String QuantityString = mQuantityEditText.getText().toString().trim();
        int Quantity = Integer.parseInt(QuantityString);
        String SupplierNameString = mSupplierNameEditText.getText().toString().trim();
        String SupplierPhoneString = mSupplierPhoneEditText.getText().toString().trim();

        BookDbHelper mDbHelper = new BookDbHelper(this);

        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BookEntry.COLUMN_Book_Name, NameString);
        values.put(BookEntry.COLUMN_Book_Price, Price);
        values.put(BookEntry.COLUMN_Book_Quantity, Quantity);
        values.put(BookEntry.COLUMN_Book_SupplierName, SupplierNameString);
        values.put(BookEntry.COLUMN_Book_SupplierPhone, SupplierPhoneString);
        Long NewRowId = db.insert(BookEntry.TABLE_NAME, null, values);
        if (NewRowId == -1) {
            Toast.makeText(this, "Error with saving Book", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Book saved with row id: " + NewRowId, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                BookInsert();
                finish();
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
