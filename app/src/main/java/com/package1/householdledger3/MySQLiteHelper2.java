package com.package1.householdledger3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteHelper2 extends SQLiteOpenHelper {

    public static final int DB_VERSION2 = 1;
    public static final String DBFILE_MAIN = "main.db";

    public MySQLiteHelper2(Context context) {
        super(context, DBFILE_MAIN, null, DB_VERSION2 );
    }


    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MySQLite.SQL_CREATE_TABLE2);
        System.out.println("Main onCreate() run***********");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
}
