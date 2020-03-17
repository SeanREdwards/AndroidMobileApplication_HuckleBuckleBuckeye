package com.example.hucklebucklebuckeye;


import android.provider.BaseColumns;

public final class Account {

        private Account() {}

        /* Inner class that defines the table contents */
        public static class AccountEntry implements BaseColumns {
            public static final String TABLE_NAME = "account";
            public static final String COLUMN_NAME_USERNAME = "username";
            public static final String COLUMN_NAME_PASSWORD = "password";
        }

        public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + AccountEntry.TABLE_NAME + " (" +
                    AccountEntry._ID + " integer primary key autoincrement, " +
                    AccountEntry.COLUMN_NAME_USERNAME + ", " +
                    AccountEntry.COLUMN_NAME_PASSWORD + ")";

         public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + AccountEntry.TABLE_NAME;
}
