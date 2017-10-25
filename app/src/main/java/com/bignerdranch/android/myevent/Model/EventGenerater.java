package com.bignerdranch.android.myevent.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import com.bignerdranch.android.myevent.Database.EventBaseHelper;
import com.bignerdranch.android.myevent.Database.EventDbSchema.EventTable;
import com.bignerdranch.android.myevent.Database.EventCursorWrapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by linliu on 23/9/17.
 */

public class EventGenerater {
    private static EventGenerater sEventGenerater;
    private Context mContext;
    private SQLiteDatabase mDatabase;
    private List<Event> mEvents;

    public static EventGenerater get(Context context){
        if (sEventGenerater == null){
            sEventGenerater= new EventGenerater(context);
        }
        return sEventGenerater;
    }

    private EventGenerater(Context context){

            //mEvents =new ArrayList<>();

        mContext = context.getApplicationContext();
        mDatabase = new EventBaseHelper(mContext).getWritableDatabase();
//        for(int i=0; i<100; i++){
//            Event event= new Event();
//            event.setTitle("Event #" + i);
//            mEvents.add(event);
//        }
    }

    public List<Event> getEvents(){
        List<Event> events = new ArrayList<>();
        EventCursorWrapper cursor = queryEvents(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                events.add(cursor.getEvent());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return events;
    }

    public Event getEvent(UUID id){
        Log.e("getEvent","in Event class");
        EventCursorWrapper cursor = queryEvents(

                EventTable.Cols.UUID + " = ?",
                new String[] { id.toString() }
        );


        try {

            if (cursor.getCount() == 0) {

                return null;
            }
            cursor.moveToFirst();
            return cursor.getEvent();
        } finally {
            cursor.close();
        }
    }





    public File getPhotoFile(Event event) {
        File externalFilesDir = mContext
                .getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (externalFilesDir == null) {
            return null;
        }
        return new File(externalFilesDir, event.getPhotoFilename());
    }


    private EventCursorWrapper queryEvents(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
        EventTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null  // orderBy
        );
        return new EventCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(Event event) {
        ContentValues values = new ContentValues();
        values.put(EventTable.Cols.UUID, event.getId().toString());
        values.put(EventTable.Cols.TITLE, event.getTitle());
        values.put(EventTable.Cols.PLACE, event.getPlace());
        values.put(EventTable.Cols.DATE, event.getDate().getTime());
        //values.put(EventTable.Cols.DATE, event.getProperTime()); dosent need the proper formatted time
        values.put(EventTable.Cols.COMMENTS, event.getComments());
        values.put(EventTable.Cols.DURATION, event.getDuration());
        values.put(EventTable.Cols.TYPE, event.getType());  // --event.get()--secondactivity&fragment
        values.put(EventTable.Cols.GPS, event.getGPS());
        return values;
    }
    //for the menu action bar add
    public void addEvent(Event e) {
        ContentValues values = getContentValues(e);
        mDatabase.insert(EventTable.NAME, null, values);
        Log.w("SQL","");  //insert a table/check the slicenerd video.
    }
    public void updateEvent(Event event) {
        String uuidString = event.getId().toString();
        ContentValues values = getContentValues(event);
        mDatabase.update(EventTable.NAME, values,
                EventTable.Cols.UUID + " = ?",
                new String[] { uuidString });
    }

    public void deleteEvent(Event e){
        String uuidString = e.getId().toString();
        mDatabase.delete(EventTable.NAME,
                         EventTable.Cols.UUID + " = ?",
                         new String[] { uuidString});
    }
}
