package edu.nanodegreeprojects.capstone.travelendar.data;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import edu.nanodegreeprojects.capstone.travelendar.model.PlaceItem;
import edu.nanodegreeprojects.capstone.travelendar.model.Trip;


public class TripDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "tripsDB.db";
    private static final int DATABASE_VERSION = 1;
    private static ContentResolver contentResolver;
    private Context context;

    public TripDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        if (contentResolver == null)
            contentResolver = context.getContentResolver();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_TRIPS_TABLE = "CREATE TABLE " + TripContract.TripContractEntry.TABLE_NAME + " (" +
                TripContract.TripContractEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +

                TripContract.TripContractEntry.COLUMN_TO_WHERE_NAME + " TEXT NOT NULL, " +
                TripContract.TripContractEntry.COLUMN_TO_WHERE_LATITUDE + " TEXT NOT NULL, " +
                TripContract.TripContractEntry.COLUMN_TO_WHERE_LONGITUDE + " TEXT NOT NULL, " +
                TripContract.TripContractEntry.COLUMN_TO_WHERE_ADDRESS + " TEXT NOT NULL, " +

                TripContract.TripContractEntry.COLUMN_FROM_WHERE_NAME + " TEXT NOT NULL, " +
                TripContract.TripContractEntry.COLUMN_FROM_WHERE_LATITUDE + " TEXT NOT NULL, " +
                TripContract.TripContractEntry.COLUMN_FROM_WHERE_LONGITUDE + " TEXT NOT NULL, " +
                TripContract.TripContractEntry.COLUMN_FROM_WHERE_ADDRESS + " TEXT NOT NULL, " +

                TripContract.TripContractEntry.COLUMN_INITIAL_DATE + " TEXT NOT NULL, " +

                TripContract.TripContractEntry.COLUMN_END_DATE + " TEXT NOT NULL, " +
                TripContract.TripContractEntry.COLUMN_RATE + " INT NOT NULL, " +
                TripContract.TripContractEntry.COLUMN_BUDGET + " FLOAT NOT NULL, " +
                TripContract.TripContractEntry.COLUMN_GENERAL_NOTES + " TEXT NOT NULL, " +
                TripContract.TripContractEntry.COLUMN_STATUS + " TEXT NOT NULL" +
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_TRIPS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TripContract.TripContractEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }


    public int insertTrip(Trip trip) {
        Uri insertRes;
        try {
            insertRes = contentResolver.insert(ContentProviderContract.BASE_CONTENT_URI.buildUpon().appendPath(ContentProviderContract.PATH_TRIP).build(), convertTripIntoCV(trip, true));
        } catch (Exception e) {
            return 0;
        }
        if (insertRes != null && insertRes.isAbsolute())
            return 1;
        else
            return 0;

    }

    public int updateTrip(Trip trip) {
        return contentResolver.update(ContentProviderContract.BASE_CONTENT_URI.buildUpon().appendPath(ContentProviderContract.PATH_TRIP).appendEncodedPath(ContentProviderContract.PATH_TRIP_ITEM).build(), convertTripIntoCV(trip, false), TripContract.TripContractEntry.COLUMN_ID + "=?", new String[]{String.valueOf(trip.getId())});

    }

    public int deleteTrip(Trip trip) {
        int deleteRes;
        try {
            deleteRes = contentResolver.delete(ContentProviderContract.BASE_CONTENT_URI.buildUpon().appendPath(ContentProviderContract.PATH_TRIP).appendEncodedPath(ContentProviderContract.PATH_TRIP_ITEM).build(), TripContract.TripContractEntry.COLUMN_ID + "=?", new String[]{String.valueOf(trip.getId())});
        } catch (Exception e) {
            deleteRes = 0;
        }
        return deleteRes;
    }

    public List<Trip> getTripList(String criteria, Trip trip) {
        Cursor resCursor = null;

        switch (criteria) {
            case ContentProviderContract.PATH_ALL_TRIPS:
                resCursor = contentResolver.query(ContentProviderContract.BASE_CONTENT_URI.buildUpon().appendPath(ContentProviderContract.PATH_ALL_TRIPS).build(), null, null, null);
                break;
            case ContentProviderContract.PATH_CONCLUDED_TRIPS:
                resCursor = contentResolver.query(ContentProviderContract.BASE_CONTENT_URI.buildUpon().appendPath(ContentProviderContract.PATH_CONCLUDED_TRIPS).build(), null, null, null);
                break;
            case ContentProviderContract.PATH_UPCOMING_TRIPS:
                resCursor = contentResolver.query(ContentProviderContract.BASE_CONTENT_URI.buildUpon().appendPath(ContentProviderContract.PATH_UPCOMING_TRIPS).build(), null, null, null);
                break;
            case ContentProviderContract.PATH_TRIP:
                resCursor = contentResolver.query(ContentProviderContract.BASE_CONTENT_URI.buildUpon().appendPath(ContentProviderContract.PATH_TRIP).appendPath(String.valueOf(trip.getId())).build(), null, null, null);
                break;
            case ContentProviderContract.PATH_MOST_UPCOMING_TRIP:
                Cursor cursor = contentResolver.query(ContentProviderContract.BASE_CONTENT_URI.buildUpon().appendPath(ContentProviderContract.PATH_MOST_UPCOMING_TRIP).build(), null, null, null);
                List<Trip> tripList = new ArrayList<>();
                Trip tripRes = convertCursorIntoTrip(cursor);
                tripList.add(tripRes);
                return tripList;

        }

        return convertCursorIntoTripList(resCursor);
    }

    private List<Trip> convertCursorIntoTripList(Cursor cursor) {
        List<Trip> tripList = new ArrayList<>();
        Trip trip = null;
        PlaceItem placeItemTo;
        PlaceItem placeItemFrom;

        if (cursor != null && cursor.getCount() > 0) {
            try {
                while (cursor.moveToNext()) {
                    placeItemFrom =
                            new PlaceItem(cursor.getString(cursor.getColumnIndex(TripContract.TripContractEntry.COLUMN_FROM_WHERE_NAME)),
                                    Double.parseDouble(cursor.getString(cursor.getColumnIndex(TripContract.TripContractEntry.COLUMN_FROM_WHERE_LATITUDE))),
                                    Double.parseDouble(cursor.getString(cursor.getColumnIndex(TripContract.TripContractEntry.COLUMN_FROM_WHERE_LONGITUDE))),
                                    cursor.getString(cursor.getColumnIndex(TripContract.TripContractEntry.COLUMN_FROM_WHERE_ADDRESS)));

                    placeItemTo =
                            new PlaceItem(cursor.getString(cursor.getColumnIndex(TripContract.TripContractEntry.COLUMN_TO_WHERE_NAME)),
                                    Double.parseDouble(cursor.getString(cursor.getColumnIndex(TripContract.TripContractEntry.COLUMN_TO_WHERE_LATITUDE))),
                                    Double.parseDouble(cursor.getString(cursor.getColumnIndex(TripContract.TripContractEntry.COLUMN_TO_WHERE_LONGITUDE))),
                                    cursor.getString(cursor.getColumnIndex(TripContract.TripContractEntry.COLUMN_TO_WHERE_ADDRESS)));

                    trip = new Trip(Integer.parseInt(cursor.getString(cursor.getColumnIndex(TripContract.TripContractEntry.COLUMN_ID))),
                            placeItemTo,
                            placeItemFrom,
                            cursor.getString(cursor.getColumnIndex(TripContract.TripContractEntry.COLUMN_INITIAL_DATE)),
                            cursor.getString(cursor.getColumnIndex(TripContract.TripContractEntry.COLUMN_END_DATE)),
                            Integer.parseInt(cursor.getString(cursor.getColumnIndex(TripContract.TripContractEntry.COLUMN_RATE))),
                            Float.parseFloat(cursor.getString(cursor.getColumnIndex(TripContract.TripContractEntry.COLUMN_BUDGET))),
                            cursor.getString(cursor.getColumnIndex(TripContract.TripContractEntry.COLUMN_GENERAL_NOTES)),
                            cursor.getString(cursor.getColumnIndex(TripContract.TripContractEntry.COLUMN_STATUS))
                    );
                    tripList.add(trip);
                }
            } finally {
                cursor.close();
            }
        }

        return tripList;
    }

    private Trip convertCursorIntoTrip(Cursor cursor) {
        Trip trip = null;
        PlaceItem placeItemTo;
        PlaceItem placeItemFrom;

        if (cursor != null && cursor.getCount() > 0) {
            try {
                cursor.moveToFirst();

                placeItemFrom =
                        new PlaceItem(cursor.getString(cursor.getColumnIndex(TripContract.TripContractEntry.COLUMN_FROM_WHERE_NAME)),
                                Double.parseDouble(cursor.getString(cursor.getColumnIndex(TripContract.TripContractEntry.COLUMN_FROM_WHERE_LATITUDE))),
                                Double.parseDouble(cursor.getString(cursor.getColumnIndex(TripContract.TripContractEntry.COLUMN_FROM_WHERE_LONGITUDE))),
                                cursor.getString(cursor.getColumnIndex(TripContract.TripContractEntry.COLUMN_FROM_WHERE_ADDRESS)));

                placeItemTo =
                        new PlaceItem(cursor.getString(cursor.getColumnIndex(TripContract.TripContractEntry.COLUMN_TO_WHERE_NAME)),
                                Double.parseDouble(cursor.getString(cursor.getColumnIndex(TripContract.TripContractEntry.COLUMN_TO_WHERE_LATITUDE))),
                                Double.parseDouble(cursor.getString(cursor.getColumnIndex(TripContract.TripContractEntry.COLUMN_TO_WHERE_LONGITUDE))),
                                cursor.getString(cursor.getColumnIndex(TripContract.TripContractEntry.COLUMN_TO_WHERE_ADDRESS)));

                trip = new Trip(Integer.parseInt(cursor.getString(cursor.getColumnIndex(TripContract.TripContractEntry.COLUMN_ID))),
                        placeItemTo,
                        placeItemFrom,
                        cursor.getString(cursor.getColumnIndex(TripContract.TripContractEntry.COLUMN_INITIAL_DATE)),
                        cursor.getString(cursor.getColumnIndex(TripContract.TripContractEntry.COLUMN_END_DATE)),
                        Integer.parseInt(cursor.getString(cursor.getColumnIndex(TripContract.TripContractEntry.COLUMN_RATE))),
                        Float.parseFloat(cursor.getString(cursor.getColumnIndex(TripContract.TripContractEntry.COLUMN_BUDGET))),
                        cursor.getString(cursor.getColumnIndex(TripContract.TripContractEntry.COLUMN_GENERAL_NOTES)),
                        cursor.getString(cursor.getColumnIndex(TripContract.TripContractEntry.COLUMN_STATUS))
                );


            } finally {
                cursor.close();
            }
        }

        return trip;
    }

    private ContentValues convertTripIntoCV(Trip trip, boolean insert) {
        ContentValues cv = new ContentValues();

        if (!insert)
            cv.put(TripContract.TripContractEntry.COLUMN_ID, trip.getId());

        cv.put(TripContract.TripContractEntry.COLUMN_TO_WHERE_NAME, trip.getToWhere().getPlaceName());
        cv.put(TripContract.TripContractEntry.COLUMN_TO_WHERE_LATITUDE, String.valueOf(trip.getToWhere().getLatitude()));
        cv.put(TripContract.TripContractEntry.COLUMN_TO_WHERE_LONGITUDE, String.valueOf(trip.getToWhere().getLongitude()));
        cv.put(TripContract.TripContractEntry.COLUMN_TO_WHERE_ADDRESS, trip.getToWhere().getAddress());

        cv.put(TripContract.TripContractEntry.COLUMN_FROM_WHERE_NAME, trip.getFromWhere().getPlaceName());
        cv.put(TripContract.TripContractEntry.COLUMN_FROM_WHERE_LATITUDE, String.valueOf(trip.getFromWhere().getLatitude()));
        cv.put(TripContract.TripContractEntry.COLUMN_FROM_WHERE_LONGITUDE, String.valueOf(trip.getFromWhere().getLongitude()));
        cv.put(TripContract.TripContractEntry.COLUMN_FROM_WHERE_ADDRESS, trip.getFromWhere().getAddress());

        cv.put(TripContract.TripContractEntry.COLUMN_INITIAL_DATE, trip.getInitialDate());
        cv.put(TripContract.TripContractEntry.COLUMN_END_DATE, trip.getEndDate());
        cv.put(TripContract.TripContractEntry.COLUMN_RATE, trip.getRate());
        cv.put(TripContract.TripContractEntry.COLUMN_BUDGET, trip.getBudget());
        cv.put(TripContract.TripContractEntry.COLUMN_GENERAL_NOTES, trip.getGeneralNotes());
        cv.put(TripContract.TripContractEntry.COLUMN_STATUS, trip.getStatus());
        return cv;
    }

}




