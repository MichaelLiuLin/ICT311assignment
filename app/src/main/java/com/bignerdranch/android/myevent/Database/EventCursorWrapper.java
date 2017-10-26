package com.bignerdranch.android.myevent.Database;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.util.Log;

import com.bignerdranch.android.myevent.Database.EventDbSchema.EventTable;
import com.bignerdranch.android.myevent.Database.EventDbSchema.SettingsTable;
import com.bignerdranch.android.myevent.Model.Event;
import com.bignerdranch.android.myevent.Model.Settings;

import java.util.Date;
import java.util.UUID;

/**
 * Created by linliu on 8/10/17.
 */

public class EventCursorWrapper extends CursorWrapper {
    public EventCursorWrapper(Cursor cursor) {
    super(cursor);
    }
    public Event getEvent() {
        String uuidString = getString(getColumnIndex(EventTable.Cols.UUID));
        String title = getString(getColumnIndex(EventTable.Cols.TITLE));
        String place = getString(getColumnIndex(EventTable.Cols.PLACE));
        String comments = getString(getColumnIndex(EventTable.Cols.COMMENTS));
        String duration = getString(getColumnIndex(EventTable.Cols.DURATION));
        int type = getInt(getColumnIndex(EventTable.Cols.TYPE));
        long date = getLong(getColumnIndex(EventTable.Cols.DATE));
        String gps = getString(getColumnIndex(EventTable.Cols.GPS));



        Event event = new Event(UUID.fromString(uuidString));
        event.setTitle(title);
        event.setPlace(place);
        event.setDate(new Date(date));
        event.setComments(comments);
        event.setDuration(duration);
        event.setType(type);
        event.setGPS(gps);

        return event;
    }


    //For the User Profile(Settings), SettingsActivity
    public Settings getSetting() {
        String uuidString = getString(getColumnIndex(SettingsTable.Cols.UUID));
        String name = getString(getColumnIndex(SettingsTable.Cols.NAME));
        String id = getString(getColumnIndex(SettingsTable.Cols.ID));
        String email = getString(getColumnIndex(SettingsTable.Cols.EMAIL));
        String gender = getString(getColumnIndex(SettingsTable.Cols.GENDER));
        String comment = getString(getColumnIndex(SettingsTable.Cols.COMMENT));

        Settings setting = new Settings();
        setting.setName(name);
        setting.setIdNum(id);
        setting.setEmail(email);
        setting.setGender(gender);
        setting.setComment(comment);

        return setting;

    }
}

