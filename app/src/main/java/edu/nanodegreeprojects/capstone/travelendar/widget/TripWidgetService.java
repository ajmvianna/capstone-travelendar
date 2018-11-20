package edu.nanodegreeprojects.capstone.travelendar.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.SystemClock;
import android.util.Log;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import edu.nanodegreeprojects.capstone.travelendar.R;
import edu.nanodegreeprojects.capstone.travelendar.data.ContentProviderContract;
import edu.nanodegreeprojects.capstone.travelendar.data.TripContract;
import edu.nanodegreeprojects.capstone.travelendar.model.Trip;

public class TripWidgetService extends RemoteViewsService {

    private ListView listView;

    public final static String SHOW_INGREDIENTS_LIST = "edu.nanodegreeprojects.bakingapp.ingredients_list";

    public Trip getMostUpComingTrip() {
        //Trip trip = new Trip();
//        Uri INGREDIENT = ContentProviderContract.BASE_CONTENT_URI;
//        Cursor cursor = getContentResolver().query(INGREDIENT, null, null, null, null);
        return new Trip();
    }

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetItem(getApplicationContext(), getMostUpComingTrip());
    }



    public class WidgetItem implements  RemoteViewsFactory {

        private Context context;
        private Trip trip;
        private String[] exampleData = {"one", "two", "three", "four",
                "five", "six", "seven", "eight", "nine", "ten"};

        public WidgetItem(Context context, Trip trip) {
            this.context = context;
            this.trip = trip;
        }

        @Override
        public void onCreate() {
            SystemClock.sleep(500);
        }

        @Override
        public void onDataSetChanged() {
            //trip.setToWhere("deuBao");
            //trip.setFromWhere("deuBao");
            trip.setInitialDate("deuBao");
            trip.setEndDate("deuBao");

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
//            if (trip != null)
//                return trip.getCount();
//            else
                return 1;
        }


        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.trip_widget);

            if (trip != null) {
                Log.d("entrou no update", String.valueOf(getCount()));
//                int indexIngredient = cursor.getColumnIndex(TripContract.TripContractEntry.COLUMN_TO_WHERE);
//                cursor.moveToPosition(position);

                //String toWhere = trip.getToWhere();
                //String fromWhere = trip.getFromWhere();
                String initialDate = trip.getInitialDate();
                String endDate = trip.getEndDate();

//                String ingredient = cursor.getString(indexIngredient);
                //remoteViews.setTextViewText(R.id.tv_trip_to_where_label_widget, toWhere);
                //remoteViews.setTextViewText(R.id.tv_trip_from_where_label_widget, fromWhere);
                remoteViews.setTextViewText(R.id.tv_trip_initial_date_label_widget, initialDate);
                remoteViews.setTextViewText(R.id.tv_trip_end_date_label_widget, endDate);
            } else {
                Log.d("nao entrou no update", String.valueOf(getCount()));
                //remoteViews.setTextViewText(R.id.txt_ingredient_widget, "No data");
            }
            //return null;
            return remoteViews;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }

}

