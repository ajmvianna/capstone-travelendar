package edu.nanodegreeprojects.capstone.travelendar.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.widget.RemoteViews;

import java.util.ArrayList;
import java.util.List;

import edu.nanodegreeprojects.capstone.travelendar.R;
import edu.nanodegreeprojects.capstone.travelendar.data.ContentProviderContract;
import edu.nanodegreeprojects.capstone.travelendar.data.TripDbHelper;
import edu.nanodegreeprojects.capstone.travelendar.model.Trip;

public class TripWidget extends AppWidgetProvider {

    public static Trip tripWidget;

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                       int appWidgetId) {

        RemoteViews views = null;
        if (tripWidget != null) {
            views = new RemoteViews(context.getPackageName(), R.layout.trip_widget);
            views.setTextViewText(R.id.tv_trip_to_where_value_widget, tripWidget.getToWhere().getPlaceName());
            views.setTextViewText(R.id.tv_trip_from_where_value_widget, tripWidget.getFromWhere().getPlaceName());
            views.setTextViewText(R.id.tv_trip_initial_date_value_widget, tripWidget.getInitialDate());
            views.setTextViewText(R.id.tv_trip_end_date_value_widget, tripWidget.getEndDate());

        } else {
            views = new RemoteViews(context.getPackageName(), R.layout.trip_widget_empty);
        }
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }


    public static void updateWidget(Context context) {
        List<Trip> tripList = new ArrayList<>();
        TripDbHelper tripDbHelper = new TripDbHelper(context);
        tripList = tripDbHelper.getTripList(ContentProviderContract.PATH_MOST_UPCOMING_TRIP, null);
        if (tripList != null && tripList.size() > 0)
            tripWidget = tripList.get(0);
        else
            tripWidget = null;

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, TripWidget.class));

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }

    }

}


