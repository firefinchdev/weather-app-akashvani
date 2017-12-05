package com.firefinch.akashvani.db.tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.firefinch.akashvani.models.Location;
import com.firefinch.akashvani.utils.Consts;

import java.util.ArrayList;

import static com.firefinch.akashvani.utils.Consts.*;

/**
 * Created by Nitin on 10/6/2017.
 */

public class LocationTable {
    public static final String TABLE_NAME = Consts.LOCATION_TABLE_NAME;

    public interface Columns {
        String ID = "id";
        String NAME = "name";
        String LATITUDE = "latitude";
        String LONGITUDE = "longitude";
    }

    public static final String CMD_CREATE =
            CMD_CREATE_TABLE_INE + TABLE_NAME +
                    LBR +
                    Columns.ID + TYPE_INT + TYPE_PK + TYPE_AI + COMMA +
                    Columns.NAME + TYPE_TEXT + COMMA +
                    Columns.LATITUDE + TYPE_DOUBLE + COMMA +
                    Columns.LONGITUDE + TYPE_DOUBLE +
                    RBR +
                    SEMI;

    public static ArrayList<Location> getAllLocations(SQLiteDatabase db) {
        ArrayList<Location> locations = new ArrayList<>();

        Cursor c = db.query(
                TABLE_NAME,
                new String[]{Columns.ID, Columns.NAME, Columns.LATITUDE, Columns.LONGITUDE},
                null,
                null,
                null,
                null,
                null
        );
        int colForId = c.getColumnIndex(Columns.ID);
        int colForName = c.getColumnIndex(Columns.NAME);
        int colForLatitude = c.getColumnIndex(Columns.LATITUDE);
        int colForLongitude = c.getColumnIndex(Columns.LONGITUDE);
        while (c.moveToNext()) {
            locations.add(
                    new Location(
                            c.getInt(colForId),
                            c.getString(colForName),
                            c.getDouble(colForLatitude),
                            c.getDouble(colForLongitude)
                    )
            );
        }
        return locations;
    }

    public static long insertLocation(SQLiteDatabase db, Location location) {
        ContentValues locationData = new ContentValues();
        locationData.put(Columns.NAME, location.getName());
        locationData.put(Columns.LATITUDE, location.getLatitude());
        locationData.put(Columns.LONGITUDE, location.getLongitude());
        return db.insert(
                TABLE_NAME,
                null,
                locationData
        );
    }

    public static void deleteLocation(SQLiteDatabase db, int locationId) {
        db.delete(TABLE_NAME, Columns.ID + " = ?", new String[]{String.valueOf(locationId)});
    }

    public static int getCount(SQLiteDatabase db) {
        String count = "SELECT count(*) FROM " + TABLE_NAME;
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        return icount;
    }
}
