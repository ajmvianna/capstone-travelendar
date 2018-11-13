package edu.nanodegreeprojects.capstone.travelendar.data;

import android.provider.BaseColumns;

public class TripContract {

    public static final class TripContractEntry implements BaseColumns {

        public static final String TABLE_NAME = "tripsList";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_TO_WHERE = "toWhere";
        public static final String COLUMN_FROM_WHERE = "fromWhere";
        public static final String COLUMN_INITIAL_DATE = "initialDate";
        public static final String COLUMN_END_DATE = "endDate";
        public static final String COLUMN_RATE = "rate";
        public static final String COLUMN_BUDGET = "budget";
        public static final String COLUMN_GENERAL_NOTES = "generalNotes";
        public static final String COLUMN_STATUS = "status";

    }

}
