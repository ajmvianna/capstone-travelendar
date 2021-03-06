package edu.nanodegreeprojects.capstone.travelendar.activities;

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
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

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
import edu.nanodegreeprojects.capstone.travelendar.widget.TripWidget;

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

    @BindView(R.id.main_toolbar)
    Toolbar mainToolbar;

    @BindString(R.string.app_name)
    String appName;

    @BindString(R.string.error_loading_trips_message)
    String errorLoadingTripsMessage;

    private TripDbHelper tripDbHelper = new TripDbHelper(this);
    private List<Trip> concludedTripsList = new ArrayList<>();
    private List<Trip> upComingTripsList = new ArrayList<>();

    private static final int TASK_LOADER_ID = 0;
    private int CURRENT_TAB_POSITION = 0;
    public static final String TRIP_EXTRA_TAG = "trip";
    public static boolean updateData = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mainToolbar);

        loadMainTabs();
        loadContentTabs(0);
        TripWidget.updateWidget(this);
    }

    /**
     * Method responsible for load the content from main tabs
     */
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
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    /**
     * Method responsible for start loader to get the trips
     */
    public void fetchTrips() {
        getSupportLoaderManager().restartLoader(TASK_LOADER_ID, null, this);
    }

    /**
     * Method responsible for load a content from a specific tab
     */
    private void loadContentTabs(int tab) {

        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        TripAdapter tripAdapter = new TripAdapter(this);
        switch (tab) {
            case 0:
                ButterKnife.bind(this);
                if (recyclerViewTabOne != null) {
                    recyclerViewTabOne.setLayoutManager(layoutManager);
                    tripAdapter.setTripData(upComingTripsList);
                    recyclerViewTabOne.setAdapter(tripAdapter);
                }
                break;
            case 1:

                ButterKnife.bind(this);
                if (recyclerViewTabTwo != null) {
                    recyclerViewTabTwo.setLayoutManager(layoutManager);
                    tripAdapter.setTripData(concludedTripsList);
                    recyclerViewTabTwo.setAdapter(tripAdapter);
                }
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
        Intent intent = new Intent(this, DetailTripActivity.class);
        intent.putExtra(TRIP_EXTRA_TAG, trip);
        startActivity(intent);
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
                if (!updateData) {
                    // Delivers any previously loaded data immediately
                    deliverResult(mTaskData);
                } else {
                    // Force a new load
                    forceLoad();
                }
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
        MainActivity.updateData = false;
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

    /**
     * Method responsible show the progress bar while data is loading
     */
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.main_menu_add_trip:
                Intent intent = new Intent(this, AddTripActivity.class);
                startActivity(intent);

                break;
        }

        return true;
    }


}
