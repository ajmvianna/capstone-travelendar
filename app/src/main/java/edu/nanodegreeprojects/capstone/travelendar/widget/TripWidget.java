package edu.nanodegreeprojects.capstone.travelendar.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import edu.nanodegreeprojects.capstone.travelendar.R;
import edu.nanodegreeprojects.capstone.travelendar.activities.MainActivity;

public class TripWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

//        CharSequence widgetText = context.getString(R.string.appwidget_text);

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        Intent widgetIntent = new Intent(context, TripWidgetService.class);

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.trip_widget);
        //views.setRemoteAdapter(R.id.list_ingredients_widget, widgetIntent);
        views.setTextViewText(R.id.tv_trip_to_where_value_widget, "1");
        views.setTextViewText(R.id.tv_trip_from_where_value_widget, "2");
        views.setTextViewText(R.id.tv_trip_initial_date_value_widget, "3");
        views.setTextViewText(R.id.tv_trip_end_date_value_widget, "4");
        // Instruct the widget manager to update the widget
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
}


