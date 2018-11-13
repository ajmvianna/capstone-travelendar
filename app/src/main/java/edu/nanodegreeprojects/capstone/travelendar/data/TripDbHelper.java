package edu.nanodegreeprojects.capstone.travelendar.data;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import edu.nanodegreeprojects.capstone.travelendar.model.Trip;


public class TripDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "tripDB.db";
    private static final int DATABASE_VERSION = 1;
    private static ContentResolver contentResolver;
    private static SQLiteDatabase sqLiteDatabase;

    public TripDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        if (contentResolver == null)
            contentResolver = context.getContentResolver();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_TRIPS_TABLE = "CREATE TABLE " + TripContract.TripContractEntry.TABLE_NAME + " (" +
                TripContract.TripContractEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TripContract.TripContractEntry.COLUMN_TO_WHERE + " TEXT NOT NULL, " +
                TripContract.TripContractEntry.COLUMN_FROM_WHERE + " TEXT NOT NULL, " +
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


    public long insertTrip(Trip trip) {
        contentResolver.insert(ContentProviderContract.BASE_CONTENT_URI.buildUpon().appendPath(String.valueOf(trip.getId())).build(), convertTripIntoCV(trip));
        return 1;
        //return sqLiteDatabase.insert(TripContract.TripContractEntry.TABLE_NAME, null, convertTripIntoCV(trip));
    }

    public long updateTrip(Trip trip) {
        return sqLiteDatabase.update(TripContract.TripContractEntry.TABLE_NAME, convertTripIntoCV(trip), TripContract.TripContractEntry.COLUMN_ID + "=?", new String[]{String.valueOf(trip.getId())});
    }

    public int deleteTrip(Trip trip) {
        return sqLiteDatabase.delete(TripContract.TripContractEntry.TABLE_NAME, TripContract.TripContractEntry.COLUMN_ID + "=?", new String[]{String.valueOf(trip.getId())});
    }

//    public List<Trip> getTripList(String type) {
//
//        return sqLiteDatabase.query()
//
//    }

    private ContentValues convertTripIntoCV(Trip trip) {
        ContentValues cv = new ContentValues();
        cv.put(TripContract.TripContractEntry.COLUMN_ID, trip.getId());
        cv.put(TripContract.TripContractEntry.COLUMN_TO_WHERE, trip.getToWhere());
        cv.put(TripContract.TripContractEntry.COLUMN_FROM_WHERE, trip.getFromWhere());
        cv.put(TripContract.TripContractEntry.COLUMN_INITIAL_DATE, trip.getInitialDate());
        cv.put(TripContract.TripContractEntry.COLUMN_END_DATE, trip.getEndDate());
        cv.put(TripContract.TripContractEntry.COLUMN_RATE, trip.getRate());
        cv.put(TripContract.TripContractEntry.COLUMN_BUDGET, trip.getBudget());
        cv.put(TripContract.TripContractEntry.COLUMN_GENERAL_NOTES, trip.getGeneralNotes());
        cv.put(TripContract.TripContractEntry.COLUMN_STATUS, trip.getStatus());
        return cv;
    }


//    public static void insertMovieList(List<Movie> movieList, ContentResolver contentResolver) {
//
//        for (Movie newMovie : movieList) {
//            ContentValues cv = new ContentValues();
//            cv.put(TripContract.MovieContractEntry.COLUMN_ID, newMovie.getId());
//            cv.put(TripContract.MovieContractEntry.COLUMN_TITLE, newMovie.getTitle());
//            cv.put(TripContract.MovieContractEntry.COLUMN_ORIGINAL_TITLE, newMovie.getOriginalTitle());
//            cv.put(TripContract.MovieContractEntry.COLUMN_THUMBNAIL_PATH, newMovie.getThumbnailPath());
//            cv.put(TripContract.MovieContractEntry.COLUMN_OVERVIEW, newMovie.getOverview());
//            cv.put(TripContract.MovieContractEntry.COLUMN_VOTE_AVERAGE, newMovie.getVoteAverage());
//            cv.put(TripContract.MovieContractEntry.COLUMN_RELEASE_DATE, newMovie.getReleaseDate());
//            cv.put(TripContract.MovieContractEntry.COLUMN_VOTE_COUNT, newMovie.getVoteCount());
//            cv.put(TripContract.MovieContractEntry.COLUMN_ORIGINAL_LANGUAGE, newMovie.getOriginalLanguage());
//            cv.put(TripContract.MovieContractEntry.COLUMN_FAVORITE, newMovie.getFavorite());
//            cv.put(TripContract.MovieContractEntry.COLUMN_TRAILERS_ID, "1");
//            cv.put(TripContract.MovieContractEntry.COLUMN_REVIEWS_ID, "1");
//
//            //if (verifyIfMovieExists(newMovie, contentResolver).getCount() == 0)
//            //sqLiteDatabase.insert(TripContract.MovieContractEntry.TABLE_NAME, null, cv);
//        }
//
//    }
//
//    private static Cursor verifyIfMovieExists(Movie movie, ContentResolver contentResolver) {
//
//        //resolver.query(ContentProviderContract.BASE_CONTENT_URI, null, TripContract.MovieContractEntry.COLUMN_FAVORITE + "=?", new String[]{"true"}, null);
//        Cursor cursor = contentResolver.query(ContentProviderContract.CONTENT_URI_FAVORITES.buildUpon().appendPath(movie.getId()).build(), null, null, null, null);
//        return cursor;
////                sqLiteDatabase.rawQuery("SELECT * FROM "
////                        + TripContract.MovieContractEntry.TABLE_NAME
////                        + " WHERE "
////                        + TripContract.MovieContractEntry.COLUMN_ID + "= ?", new String[]{movie.getId()});
//
//    }
//
//    public static boolean isMovieFavorite(Movie movie, ContentResolver contentResolver) {
//        Cursor cursor = null;
//        String res = "";
//        try {
//            cursor = verifyIfMovieExists(movie, contentResolver);
//            if (cursor.getCount() > 0) {
//                cursor.moveToFirst();
//                res = cursor.getString(cursor.getColumnIndex(TripContract.MovieContractEntry.COLUMN_FAVORITE));
//
//                return cursor.getCount() > 0 && "true".equals(res);
//            }
//        } finally {
//            cursor.close();
//        }
//
//        return false;
//    }
//
//    public static List<Movie> getAllFavoriteMovies(Cursor cursor, Context context) {
//
//        List<Movie> listFavoriteMovies = new ArrayList<>();
//        Movie movie = null;
//
//        if (cursor.getCount() > 0) {
//            try {
//                while (cursor.moveToNext()) {
//                    movie = new Movie(cursor.getString(cursor.getColumnIndex(TripContract.MovieContractEntry.COLUMN_ID)),
//                            cursor.getString(cursor.getColumnIndex(TripContract.MovieContractEntry.COLUMN_TITLE)),
//                            cursor.getString(cursor.getColumnIndex(TripContract.MovieContractEntry.COLUMN_ORIGINAL_TITLE)),
//                            cursor.getString(cursor.getColumnIndex(TripContract.MovieContractEntry.COLUMN_THUMBNAIL_PATH)),
//                            cursor.getString(cursor.getColumnIndex(TripContract.MovieContractEntry.COLUMN_OVERVIEW)),
//                            cursor.getString(cursor.getColumnIndex(TripContract.MovieContractEntry.COLUMN_VOTE_AVERAGE)),
//                            cursor.getString(cursor.getColumnIndex(TripContract.MovieContractEntry.COLUMN_RELEASE_DATE)),
//                            cursor.getString(cursor.getColumnIndex(TripContract.MovieContractEntry.COLUMN_VOTE_COUNT)),
//                            cursor.getString(cursor.getColumnIndex(TripContract.MovieContractEntry.COLUMN_ORIGINAL_LANGUAGE)),
//                            cursor.getString(cursor.getColumnIndex(TripContract.MovieContractEntry.COLUMN_FAVORITE)),
//                            null,
//                            null);
//                    listFavoriteMovies.add(movie);
//                }
//            } finally {
//                cursor.close();
//            }
//        } else {
//            Toast.makeText(context, context.getString(R.string.message_error_no_favorites), Toast.LENGTH_SHORT).show();
//        }
//
//        return listFavoriteMovies;
//    }
//
//    public static void updateStatusFavoriteMovie(Movie movie, ContentResolver contentResolver, Context context) {
//        int res = 0;
//        Cursor cursor = verifyIfMovieExists(movie, contentResolver);
//        if (cursor.getCount() > 0) {
//            ContentValues cv = new ContentValues();
//            cv.put(TripContract.MovieContractEntry.COLUMN_FAVORITE, movie.getFavorite());
//            res = contentResolver.update(ContentProviderContract.CONTENT_URI_FAVORITES.buildUpon().appendPath(movie.getId()).build(), cv, null, null);
//            if (res == 0)
//                Toast.makeText(context, context.getString(R.string.error_message_favorite_added), Toast.LENGTH_SHORT).show();
//
//            else {
//                if (movie.getFavorite().equals("false"))
//                    Toast.makeText(context, context.getString(R.string.message_favorite_removed), Toast.LENGTH_SHORT).show();
//                else
//                    Toast.makeText(context, context.getString(R.string.message_favorite_added), Toast.LENGTH_SHORT).show();
//            }
//
//
//        } else {
//            ContentValues cv = new ContentValues();
//            cv.put(TripContract.MovieContractEntry.COLUMN_ID, movie.getId());
//            cv.put(TripContract.MovieContractEntry.COLUMN_TITLE, movie.getTitle());
//            cv.put(TripContract.MovieContractEntry.COLUMN_ORIGINAL_TITLE, movie.getOriginalTitle());
//            cv.put(TripContract.MovieContractEntry.COLUMN_THUMBNAIL_PATH, movie.getThumbnailPath());
//            cv.put(TripContract.MovieContractEntry.COLUMN_OVERVIEW, movie.getOverview());
//            cv.put(TripContract.MovieContractEntry.COLUMN_VOTE_AVERAGE, movie.getVoteAverage());
//            cv.put(TripContract.MovieContractEntry.COLUMN_RELEASE_DATE, movie.getReleaseDate());
//            cv.put(TripContract.MovieContractEntry.COLUMN_VOTE_COUNT, movie.getVoteCount());
//            cv.put(TripContract.MovieContractEntry.COLUMN_ORIGINAL_LANGUAGE, movie.getOriginalLanguage());
//            cv.put(TripContract.MovieContractEntry.COLUMN_FAVORITE, movie.getFavorite());
//            cv.put(TripContract.MovieContractEntry.COLUMN_TRAILERS_ID, "1");
//            cv.put(TripContract.MovieContractEntry.COLUMN_REVIEWS_ID, "1");
//
//            Uri uriInsert = contentResolver.insert(ContentProviderContract.CONTENT_URI_FAVORITES, cv);
//            assert uriInsert != null;
//            if (uriInsert.getLastPathSegment().equals(movie.getId())) {
//                if (movie.getFavorite().equals("false"))
//                    Toast.makeText(context, context.getString(R.string.message_favorite_removed), Toast.LENGTH_SHORT).show();
//                else
//                    Toast.makeText(context, context.getString(R.string.message_favorite_added), Toast.LENGTH_SHORT).show();
//            }
//        }
//

//
//  String whereClause = TripContract.MovieContractEntry.COLUMN_ID + "=" + movie.getId();
//        String[] args = new String[1];
//        args[0] = whereClause;

    //sqLiteDatabase.update(TripContract.MovieContractEntry.TABLE_NAME, cv, TripContract.MovieContractEntry.COLUMN_ID + "=" + movie.getId(), null);
}




