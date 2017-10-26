package com.bignerdranch.android.myevent.Database;

/**
 * Created by linliu on 1/10/17.
 */

public class EventDbSchema {

    public static final class EventTable {
        //table name
        public static final String NAME = "activities";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String PLACE = "place";
            public static final String COMMENTS = "comments";
            public static final String DURATION = "duration";
            public static final String TYPE = "type";
            public static final String GPS = "gps";
        }
    }
        public static final class SettingsTable {
            public static final String NAME = "settings";

            public static final class Cols {
                public static final String UUID = "uuid";
                public static final String NAME = "name";
                public static final String ID = "id";
                public static final String EMAIL = "email";
                public static final String GENDER = "gender";
                public static final String COMMENT = "comment";
            }
        }

}

