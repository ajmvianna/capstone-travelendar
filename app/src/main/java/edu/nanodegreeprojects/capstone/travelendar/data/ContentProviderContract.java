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

    public static final int ALL_TRIPS_CODE = 100;
    public static final int TRIP_ITEM_CODE = 101;
    public static final int CONCLUDED_TRIPS = 102;
    public static final int UPCOMING_TRIPS = 103;
    public static final int TRIP = 104;
    public static final int MOST_UPCOMING_TRIP = 105;

    public static final String PATH_ALL_TRIPS = "all";
    public static final String PATH_TRIP = "trip";
    public static final String PATH_TRIP_ITEM = "#";
    public static final String PATH_CONCLUDED_TRIPS = "concluded";
    public static final String PATH_UPCOMING_TRIPS = "upcoming";
    public static final String PATH_MOST_UPCOMING_TRIP = "most_upcoming";

    public static final Uri CONTENT_URI_TRIPS = BASE_CONTENT_URI.buildUpon().appendPath(PATH_ALL_TRIPS).build();



}

