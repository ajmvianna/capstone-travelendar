//package edu.nanodegreeprojects.capstone.travelendar.utils;
//
//import android.net.Uri;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.Scanner;
//
//public final class NetworkUtils {
//
//    private static final String WEATHER_URL = "https://openweathermap.org";
//
//    private static final String API_KEY_TAG = "api_key";
//    private static final String KEY = "50d046d30e1f14f3ad537ddb151282e5";
//    private static final String THUMBNAILS_SIZE = "w185";
//
//    public static URL buildUrl(String type, String thumbnailPath, String movieId, String queryType) {
//        Uri builtUri = null;
//        switch (type) {
//            case THUMBNAIL_QUERY_TYPE:
//                builtUri = Uri.parse(THUMBNAILS_DB_URL).buildUpon()
//                        .appendPath(THUMBNAILS_SIZE)
//                        .appendEncodedPath(thumbnailPath)
//                        .build();
//                break;
//            case REVIEW_QUERY_TYPE:
//                builtUri = Uri.parse(MOVIES_DB_URL).buildUpon()
//                        .appendPath(movieId)
//                        .appendPath(REVIEW_QUERY_TYPE)
//                        .appendQueryParameter(API_KEY_TAG, MY_API_KEY)
//                        .appendQueryParameter(LANGUAGE_PREFERENCE_TAG, MY_LANGUAGE_PREFERENCE)
//                        .build();
//                break;
//            case TRAILER_QUERY_TYPE:
//                builtUri = Uri.parse(MOVIES_DB_URL).buildUpon()
//                        .appendPath(movieId)
//                        .appendPath(TRAILER_QUERY_TYPE)
//                        .appendQueryParameter(API_KEY_TAG, MY_API_KEY)
//                        .appendQueryParameter(LANGUAGE_PREFERENCE_TAG, MY_LANGUAGE_PREFERENCE)
//                        .build();
//                break;
//            case MOVIES_QUERY_TYPE:
//                builtUri = Uri.parse(MOVIES_DB_URL).buildUpon()
//                        .appendPath(queryType)
//                        .appendQueryParameter(API_KEY_TAG, MY_API_KEY)
//                        .appendQueryParameter(LANGUAGE_PREFERENCE_TAG, MY_LANGUAGE_PREFERENCE)
//                        .build();
//                break;
//            default:
//
//        }
//
//        URL url = null;
//        try {
//            assert builtUri != null;
//            url = new URL(builtUri.toString());
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        return url;
//    }
//
//
//    public static String getResponseFromHttpUrl(URL url) throws IOException {
//        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//        try {
//            InputStream in = urlConnection.getInputStream();
//
//            Scanner scanner = new Scanner(in);
//            scanner.useDelimiter("\\A");
//
//            boolean hasInput = scanner.hasNext();
//            if (hasInput) {
//                return scanner.next();
//            } else {
//                return null;
//            }
//        } finally {
//            urlConnection.disconnect();
//        }
//    }
//}
