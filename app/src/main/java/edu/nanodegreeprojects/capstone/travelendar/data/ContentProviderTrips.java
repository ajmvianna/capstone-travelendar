package edu.nanodegreeprojects.capstone.travelendar.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class ContentProviderTrips extends ContentProvider {

    private TripDbHelper tDh = null;
    public static UriMatcher URI_MATCHER;

    @Override
    public boolean onCreate() {
        tDh = new TripDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {

        SQLiteDatabase db = tDh.getReadableDatabase();
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(TripContract.TripContractEntry.TABLE_NAME);
        String idStr = uri.getLastPathSegment();
        switch (URI_MATCHER.match(uri)) {

            case ContentProviderContract.ALL_TRIPS_CODE:
                break;

            case ContentProviderContract.TRIP_ITEM_CODE:
                builder.appendWhere(TripContract.TripContractEntry.COLUMN_ID +
                        "=" +
                        idStr);
                break;

            case ContentProviderContract.CONCLUDED_TRIPS:
                builder.appendWhere(TripContract.TripContractEntry.COLUMN_STATUS +
                        "='" +
                        idStr + "'");
                break;

            case ContentProviderContract.UPCOMING_TRIPS:
                builder.appendWhere(TripContract.TripContractEntry.COLUMN_STATUS +
                        "='" +
                        idStr + "'");
                break;
            default:
                throw new IllegalArgumentException(
                        "Unsupported URI: " + uri);
        }
        return builder.query(db, strings, s, strings1, s1, null, null);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        SQLiteDatabase db = tDh.getWritableDatabase();
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        switch (URI_MATCHER.match(uri)) {
            case ContentProviderContract.TRIP:
                builder.setTables(TripContract.TripContractEntry.TABLE_NAME);
                long id =
                        db.insert(
                                TripContract.TripContractEntry.TABLE_NAME,
                                null,
                                contentValues);
                return getUriForId(id, uri);
            default:
                throw new IllegalArgumentException(
                        "Unsupported URI: " + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {

        SQLiteDatabase db = tDh.getWritableDatabase();
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        String idStr = uri.getLastPathSegment();
        switch (URI_MATCHER.match(uri)) {
            case ContentProviderContract.TRIP_ITEM_CODE:
                builder.setTables(TripContract.TripContractEntry.TABLE_NAME);
                return db.delete(TripContract.TripContractEntry.TABLE_NAME, TripContract.TripContractEntry.COLUMN_ID + "=?", new String[]{idStr});
            default:
                throw new IllegalArgumentException(
                        "Unsupported URI: " + uri);
        }

    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String
            s, @Nullable String[] strings) {


        SQLiteDatabase db = tDh.getWritableDatabase();
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(TripContract.TripContractEntry.TABLE_NAME);
        String idStr = uri.getLastPathSegment();
        int res = 0;
        switch (URI_MATCHER.match(uri)) {
            case ContentProviderContract.ALL_TRIPS_CODE:
                break;
            case ContentProviderContract.CONCLUDED_TRIPS:
                builder.appendWhere(TripContract.TripContractEntry.COLUMN_STATUS +
                        "=" +
                        idStr);
                break;
            case ContentProviderContract.UPCOMING_TRIPS:
                builder.appendWhere(TripContract.TripContractEntry.COLUMN_STATUS +
                        "=" +
                        idStr);

                break;
            case ContentProviderContract.TRIP:
                builder.appendWhere(TripContract.TripContractEntry.COLUMN_ID +
                        "=" +
                        idStr);
                break;

        }
        res = db.update(
                TripContract.TripContractEntry.TABLE_NAME,
                contentValues,
                s,
                strings);
        return res;
    }

    static {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

        URI_MATCHER.addURI(ContentProviderContract.CONTENT_AUTHORITY, ContentProviderContract.PATH_TRIP, ContentProviderContract.TRIP);
        URI_MATCHER.addURI(ContentProviderContract.CONTENT_AUTHORITY, ContentProviderContract.PATH_TRIP.concat("/#"), ContentProviderContract.TRIP_ITEM_CODE);
        URI_MATCHER.addURI(ContentProviderContract.CONTENT_AUTHORITY, ContentProviderContract.PATH_ALL_TRIPS, ContentProviderContract.ALL_TRIPS_CODE);
        URI_MATCHER.addURI(ContentProviderContract.CONTENT_AUTHORITY, ContentProviderContract.PATH_CONCLUDED_TRIP, ContentProviderContract.CONCLUDED_TRIPS);
        URI_MATCHER.addURI(ContentProviderContract.CONTENT_AUTHORITY, ContentProviderContract.PATH_UPCOMING_TRIP, ContentProviderContract.UPCOMING_TRIPS);
    }

    private Uri getUriForId(long id, Uri uri) {
        if (id > 0) {
            return ContentUris.withAppendedId(uri, id);
        }
        throw new SQLException(
                "Problem while inserting into uri: " + uri);
    }
}
