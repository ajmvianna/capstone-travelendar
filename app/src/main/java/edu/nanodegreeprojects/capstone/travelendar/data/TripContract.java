package edu.nanodegreeprojects.capstone.travelendar.data;

import android.provider.BaseColumns;

public class TripContract {

    public static final class TripContractEntry implements BaseColumns {

        public static final String TABLE_NAME = "tripsList";

        public static final String COLUMN_ID = "id";

        public static final String COLUMN_TO_WHERE_NAME = "toWhereName";
        public static final String COLUMN_TO_WHERE_LATITUDE = "toWhereLatitude";
        public static final String COLUMN_TO_WHERE_LONGITUDE = "toWhereLongitude";
        public static final String COLUMN_TO_WHERE_ADDRESS = "toWhereAddress";

        public static final String COLUMN_FROM_WHERE_NAME = "fromWhereName";
        public static final String COLUMN_FROM_WHERE_LATITUDE = "fromWhereLatitude";
        public static final String COLUMN_FROM_WHERE_LONGITUDE = "fromWhereLongitude";
        public static final String COLUMN_FROM_WHERE_ADDRESS = "fromWhereAddress";

        public static final String COLUMN_INITIAL_DATE = "initialDate";
        public static final String COLUMN_END_DATE = "endDate";
        public static final String COLUMN_RATE = "rate";
        public static final String COLUMN_BUDGET = "budget";
        public static final String COLUMN_GENERAL_NOTES = "generalNotes";
        public static final String COLUMN_STATUS = "status";
    }

    }
