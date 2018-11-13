package edu.nanodegreeprojects.capstone.travelendar.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * The contract between the DroidTermsExample provider and other applications. Contains definitions
 * for the supported URIs and columns.
 */

public class ContentProviderContract implements BaseColumns {
    public static final String CONTENT_AUTHORITY = "edu.nanodegreeprojects.capstone.travelendar";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final int TRIPS_CODE = 100;
    public static final int TRIP_ITEM_CODE = 101;
    public static final int CONCLUDED_TRIPS = 102;
    public static final int UPCOMING_TRIPS = 103;

    public static final String PATH_ALL_TRIPS = "all";
    public static final String PATH_TRIP_ITEM = "#";
    public static final String PATH_CONCLUDED_TRIP = "concluded";
    public static final String PATH_UPCOMING_TRIP = "upcoming";

    public static final Uri CONTENT_URI_TRIPS = BASE_CONTENT_URI.buildUpon().appendPath(PATH_ALL_TRIPS).build();

    private static final String COLUMN_ID_WHERE = "id";
    private static final String COLUMN_TO_WHERE = "toWhere";
    private static final String COLUMN_FROM = "fromWhere";
    private static final String COLUMN_INITIAL_DATE = "initialDate";
    private static final String COLUMN_END_DATE = "endDate";
    private static final String COLUMN_RATE = "rate";
    private static final String COLUMN_BUDGET = "budget";
    private static final String COLUMN_GENERAL_NOTES = "generalNotes";
    private static final String COLUMN_STATUS = "status";


//    public static UriMatcher buildUriMatcher() {
//        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
//        uriMatcher.addURI(BASE_CONTENT_URI.toString(), PATH_FAVORITE_MOVIES, FAVORITE_CODE);
//
//        return uriMatcher;
//    }

    public static final String[] COLUMNS =

            {
                    COLUMN_ID_WHERE,
                    COLUMN_TO_WHERE,
                    COLUMN_FROM,
                    COLUMN_INITIAL_DATE,
                    COLUMN_END_DATE,
                    COLUMN_RATE,
                    COLUMN_BUDGET,
                    COLUMN_GENERAL_NOTES,
                    COLUMN_STATUS
            };


}

