package com.example.android.bookstore.Data;

import android.provider.BaseColumns;

public final class BookContract {
    public static abstract class BookEntry implements BaseColumns {

        public static final String TABLE_NAME = "BookStore";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_Book_Name = "Name";
        public static final String COLUMN_Book_Price = "Price";
        public static final String COLUMN_Book_Quantity = "Quantity";
        public static final String COLUMN_Book_SupplierName = "SupplierName";
        public static final String COLUMN_Book_SupplierPhone = "SupplierPhone";

    }
}
