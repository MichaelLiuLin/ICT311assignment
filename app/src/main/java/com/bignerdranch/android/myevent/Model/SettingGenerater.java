package com.bignerdranch.android.myevent.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bignerdranch.android.myevent.Database.EventBaseHelper;
import com.bignerdranch.android.myevent.Database.EventCursorWrapper;
import com.bignerdranch.android.myevent.Database.EventDbSchema;
import com.bignerdranch.android.myevent.Database.EventDbSchema.SettingsTable;

/**
 * Created by linliu on 25/10/17.
 */


public class SettingGenerater {
    private static SettingGenerater sSettingGenerater;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    private static ContentValues getContentValues(Settings setting) {
        ContentValues value = new ContentValues();
        value.put(SettingsTable.Cols.UUID, "1");
        value.put(SettingsTable.Cols.NAME, setting.getName());
        value.put(SettingsTable.Cols.ID, setting.getIdNum());
        value.put(SettingsTable.Cols.EMAIL, setting.getEmail());
        value.put(SettingsTable.Cols.GENDER, setting.getGender());
        value.put(SettingsTable.Cols.COMMENT, setting.getComment());
        return value;
    }

    public static SettingGenerater get(Context context) {
        if (sSettingGenerater == null) {
            sSettingGenerater = new SettingGenerater(context);
        }
        return sSettingGenerater;
    }

    private SettingGenerater(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new EventBaseHelper(mContext).getWritableDatabase();
    }

    //insert settings
    public void addSetting(Settings s) {
        ContentValues contentValues = SettingGenerater.getContentValues(s);
        mDatabase.insert(SettingsTable.NAME, null, contentValues);
    }

    //get user profile
    public Settings getSetting(String id) {
        EventCursorWrapper cursor = querySettings(
                SettingsTable.Cols.UUID + " = ?", new String[] {"1"}
        );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getSetting();
        } finally {
            cursor.close();
        }
    }

    //update settings
    public void updateSetting(Settings setting) {
        String uuidString = setting.getId();
        ContentValues values = getContentValues(setting);

        mDatabase.update(SettingsTable.NAME, values,
                SettingsTable.Cols.UUID + " =?", new String[]{uuidString});
    }

    public Settings getSetting() {
        EventCursorWrapper cursor = querySettings(null, null);
        Settings setting = null;
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                setting = cursor.getSetting();
            }
        } finally {
            cursor.close();
        }

        return setting;
    }

    private EventCursorWrapper querySettings(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                EventDbSchema.SettingsTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
        );
        return new EventCursorWrapper(cursor);
    }
}
