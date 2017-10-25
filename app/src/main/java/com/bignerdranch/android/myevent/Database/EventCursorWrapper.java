package com.bignerdranch.android.myevent.Helper;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.bignerdranch.android.myevent.Database.EventDbSchema.EventTable;
import com.bignerdranch.android.myevent.Model.Event;

import java.util.Date;
import java.util.UUID;

/**
 * Created by linliu on 8/10/17.
 */

public class EventCursorWrapper extends CursorWrapper {
    public EventCursorWrapper(Cursor cursor) {
    super(cursor);
    }
    public Event getCrime() {
        String uuidString = getString(getColumnIndex(CrimeTable.Cols.UUID));
        String title = getString(getColumnIndex(CrimeTable.Cols.TITLE));
        long date = getLong(getColumnIndex(CrimeTable.Cols.DATE));
        int isSolved = getInt(getColumnIndex(CrimeTable.Cols.SOLVED));
        Event crime = new Event(UUID.fromString(uuidString));
        crime.setTitle(title);
        crime.setDate(new Date(date));
        //crime.setSolved(isSolved != 0);
        return crime;
    }
}

