package edu.nanodegreeprojects.capstone.travelendar.activities;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import edu.nanodegreeprojects.capstone.travelendar.R;
import edu.nanodegreeprojects.capstone.travelendar.adapters.PageAdapter;
import edu.nanodegreeprojects.capstone.travelendar.adapters.TripAdapter;
import edu.nanodegreeprojects.capstone.travelendar.data.ContentProviderContract;
import edu.nanodegreeprojects.capstone.travelendar.data.TripDbHelper;
import edu.nanodegreeprojects.capstone.travelendar.fragments.TabOneUpComingTrip;
import edu.nanodegreeprojects.capstone.travelendar.fragments.TabTwoConcludedTrip;
import edu.nanodegreeprojects.capstone.travelendar.model.Trip;
import edu.nanodegreeprojects.capstone.travelendar.widget.TripWidgetService;

public class MainActivity extends AppCompatActivity implements TabOneUpComingTrip.OnFragmentInteractionListener,
        TabTwoConcludedTrip.OnFragmentInteractionListener,
        TripAdapter.TripAdapterOnClickHandler,
        LoaderManager.LoaderCallbacks<List<Trip>> {

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @Nullable
    @BindView(R.id.recycler_view_tab_one)
    RecyclerView recyclerViewTabOne;

    @Nullable
    @BindView(R.id.progress_bar_one)
    ProgressBar progressBarOne;

    @Nullable
    @BindView(R.id.recycler_view_tab_two)
    RecyclerView recyclerViewTabTwo;

    @Nullable
    @BindView(R.id.progress_bar_two)
    ProgressBar progressBarTwo;

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @BindString(R.string.error_loading_trips_message)
    String errorLoadingTripsMessage;

    private TripDbHelper tripDbHelper = new TripDbHelper(this);
    private List<Trip> concludedTripsList = new ArrayList<>();
    private List<Trip> upComingTripsList = new ArrayList<>();

    private static final int TASK_LOADER_ID = 0;
    private int CURRENT_TAB_POSITION = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        loadMainTabs();
        fetchTrips();
    }

    private void loadMainTabs() {

        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.title_tab_one_upcoming_trip)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.title_tab_two_conclued_trip)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final PageAdapter pageAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapter);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                CURRENT_TAB_POSITION = tab.getPosition();
                fetchTrips();
                viewPager.setCurrentItem(CURRENT_TAB_POSITION);
                //loadContentTabs(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    public void fetchTrips() {
        getSupportLoaderManager().restartLoader(TASK_LOADER_ID, null, this);
    }

    private void loadContentTabs(int tab) {

//        tripDbHelper.insertTrip(trip);
//        tripDbHelper.getTripList(ContentProviderContract.PATH_ALL_TRIPS, null);
//        tripDbHelper.getTripList(ContentProviderContract.PATH_CONCLUDED_TRIPS, null);
//        tripDbHelper.getTripList(ContentProviderContract.PATH_UPCOMING_TRIPS, null);
//        tripDbHelper.getTripList(ContentProviderContract.PATH_TRIP, trip);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        TripAdapter tripAdapter = new TripAdapter(this);
        switch (tab) {
            case 0:
                ButterKnife.bind(this);
                recyclerViewTabOne.setLayoutManager(layoutManager);
                tripAdapter.setTripData(upComingTripsList);
                recyclerViewTabOne.setAdapter(tripAdapter);
                break;
            case 1:

                ButterKnife.bind(this);
                recyclerViewTabTwo.setLayoutManager(layoutManager);
                tripAdapter.setTripData(concludedTripsList);
                recyclerViewTabTwo.setAdapter(tripAdapter);
                break;
        }

    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }

    @Override
    public void onClick(Trip trip) {

//        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
//        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, TripWidgetService.class));
//        //Trigger data update to handle the GridView widgets and force a data refresh
//        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.ll_widget);

        //        Intent intent = new Intent(this, DetailTripActivity.class);
//        intent.putExtra("trip", trip);
//        startActivity(intent);

//        Intent intent = new Intent(this, GoogleMaps.class);
//        startActivity(intent);



    }



    @NonNull
    @Override
    public Loader<List<Trip>> onCreateLoader(int i, @Nullable final Bundle bundle) {

        return new AsyncTaskLoader<List<Trip>>(this) {

            List<Trip> mTaskData = null;

            @Override
            protected void onStartLoading() {
                showProgressBar(true);
                forceLoad();
//                if (mTaskData != null) {
//                    // Delivers any previously loaded data immediately
//                    deliverResult(mTaskData);
//                } else {
//                    // Force a new load
//                    forceLoad();
//                }
            }

            // loadInBackground() performs asynchronous loading of data
            @Override
            public List<Trip> loadInBackground() {
                // Will implement to load data

                // COMPLETED (5) Query and load all task data in the background; sort by priority
                //TODO SORT TRIPS BY DATE
                // [Hint] use a try/catch block to catch any errors in loading data
                try {

                    switch (CURRENT_TAB_POSITION) {
                        case 0:
                            return tripDbHelper.getTripList(ContentProviderContract.PATH_UPCOMING_TRIPS, null);
                        case 1:
                            return tripDbHelper.getTripList(ContentProviderContract.PATH_CONCLUDED_TRIPS, null);
                        default:
                            return null;

                    }


                } catch (Exception e) {
                    Log.e("ERROR BD", "Failed to asynchronously load data.");
                    e.printStackTrace();
                    return null;
                }
            }

            // deliverResult sends the result of the load, a Cursor, to the registered listener
            public void deliverResult(List<Trip> data) {
                mTaskData = data;
                super.deliverResult(data);
            }
        };


    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Trip>> loader, List<Trip> trips) {
        showProgressBar(false);
        if (trips != null) {
            int tabPosition = tabLayout.getSelectedTabPosition();
            switch (tabPosition) {
                case 0:
                    upComingTripsList = trips;
                    loadContentTabs(tabPosition);
                    break;
                case 1:
                    concludedTripsList = trips;
                    loadContentTabs(tabPosition);
                    break;
                default:
                    Toast.makeText(this, errorLoadingTripsMessage, Toast.LENGTH_SHORT).show();
                    break;
            }

        }


    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Trip>> loader) {

    }

    private void showProgressBar(boolean visibility) {
        switch (CURRENT_TAB_POSITION) {
            case 0:
                if (progressBarOne != null)
                    if (visibility)
                        progressBarOne.setVisibility(View.VISIBLE);
                    else
                        progressBarOne.setVisibility(View.GONE);
                break;
            case 1:
                if (progressBarTwo != null)
                    if (visibility)
                        progressBarTwo.setVisibility(View.VISIBLE);
                    else
                        progressBarTwo.setVisibility(View.GONE);
                break;
        }
    }

}
