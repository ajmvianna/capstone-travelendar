package edu.nanodegreeprojects.capstone.travelendar.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import edu.nanodegreeprojects.capstone.travelendar.R;
import edu.nanodegreeprojects.capstone.travelendar.adapters.PageAdapter;
import edu.nanodegreeprojects.capstone.travelendar.adapters.TripAdapter;
import edu.nanodegreeprojects.capstone.travelendar.data.TripDbHelper;
import edu.nanodegreeprojects.capstone.travelendar.fragments.TabOneUpComingTrip;
import edu.nanodegreeprojects.capstone.travelendar.fragments.TabTwoConcludedTrip;
import edu.nanodegreeprojects.capstone.travelendar.model.Trip;

public class MainActivity extends AppCompatActivity implements TabOneUpComingTrip.OnFragmentInteractionListener, TabTwoConcludedTrip.OnFragmentInteractionListener, TripAdapter.TripAdapterOnClickHandler {

    TabLayout tabLayout;
    RecyclerView recyclerViewTabOne;
    RecyclerView recyclerViewTabTwo;
    GridLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadComponents();
    }

    private void loadComponents() {
        loadMainTabs();
    }

    private void loadMainTabs() {
        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.title_tab_one_upcoming_trip)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.title_tab_two_conclued_trip)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = findViewById(R.id.view_pager);
        final PageAdapter adapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                loadContentTabs(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void loadContentTabs(int tab) {

        Trip trip = new Trip();

        TripDbHelper tripDbHelper = new TripDbHelper(this);
        tripDbHelper.insertTrip(trip);

        trip.setToWhere("bla");
        tripDbHelper.updateTrip(trip);
        tripDbHelper.deleteTrip(trip);


        List<Trip> list = new ArrayList<>();

        layoutManager = new GridLayoutManager(this, 1);
        TripAdapter tripAdapter = new TripAdapter(this);
        switch (tab) {
            case 0:
                recyclerViewTabOne = findViewById(R.id.recycler_view_tab_one);
                for (int i = 0; i < 10; i++)
                    list.add(trip);
                recyclerViewTabOne.setLayoutManager(layoutManager);
                tripAdapter.setTripData(list);
                recyclerViewTabOne.setAdapter(tripAdapter);
                break;
            case 1:
                for (int i = 0; i < 5; i++)
                    list.add(trip);
                recyclerViewTabTwo = findViewById(R.id.recycler_view_tab_two);
                recyclerViewTabTwo.setLayoutManager(layoutManager);
                tripAdapter.setTripData(list);
                recyclerViewTabTwo.setAdapter(tripAdapter);
                break;
        }

    }

//    private void loadOtherTabs(int tab) {
//        tabLayout = findViewById(R.id.tab_layout);
//        tabLayout.removeAllTabs();
//        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.title_tab_three_add_trip)));
//
//        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
//
//
//        final ViewPager viewPager = findViewById(R.id.view_pager);
//        final PageAdapter adapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
//        viewPager.setAdapter(adapter);
//        viewPager.setCurrentItem(0);
//
//    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onClick(Trip trip) {

        Intent intent = new Intent(this, DetailTripActivity.class);
        intent.putExtra("trip", trip);
        startActivity(intent);

    }

}
