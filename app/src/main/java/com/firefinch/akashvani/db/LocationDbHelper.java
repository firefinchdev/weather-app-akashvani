package com.firefinch.akashvani.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.firefinch.akashvani.db.tables.LocationTable;
import com.firefinch.akashvani.utils.Consts;

/**
 * Created by Nitin on 10/6/2017.
 */

public class LocationDbHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = Consts.LOCATION_DB_NAME;
    public static final int DB_VER = Consts.LOCATION_DB_VER;

    public LocationDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(LocationTable.CMD_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
