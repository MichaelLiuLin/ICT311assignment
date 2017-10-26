package com.bignerdranch.android.myevent.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.bignerdranch.android.myevent.Database.EventDbSchema.EventTable;
import com.bignerdranch.android.myevent.Database.EventDbSchema.SettingsTable;

/**
 * Created by linliu on 1/10/17.
 */

public class EventBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 3;
    private static final String DATABASE_NAME = "eventBase.db";
    public static final String TABLE_NAME = EventTable.NAME;


    public EventBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME+
                "(" + " _id integer primary key autoincrement, "
                + EventTable.Cols.UUID + ", "
                + EventTable.Cols.TITLE + ", "
                + EventTable.Cols.DATE + ", "
                + EventTable.Cols.PLACE + ", "
                + EventTable.Cols.COMMENTS + ", "
                + EventTable.Cols.DURATION + ", "
                + EventTable.Cols.TYPE+ ", "
                + EventTable.Cols.GPS
                + ")"
        );
        Log.e("db",db.toString());

        //Create the settings table
        db.execSQL("create table " + SettingsTable.NAME + "(" +
                SettingsTable.Cols.UUID + ", " +
                SettingsTable.Cols.ID + ", " +
                SettingsTable.Cols.NAME + ", " +
                SettingsTable.Cols.EMAIL + ", " +
                SettingsTable.Cols.GENDER + ", " +
                SettingsTable.Cols.COMMENT +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
