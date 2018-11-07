package edu.nanodegreeprojects.capstone.travelendar.activities;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.nanodegreeprojects.capstone.travelendar.adapters.PageAdapter;
import edu.nanodegreeprojects.capstone.travelendar.adapters.TripAdapter;
import edu.nanodegreeprojects.capstone.travelendar.fragments.TabOneUpComingTrip;
import edu.nanodegreeprojects.capstone.travelendar.fragments.TabTwoConcludedTrip;
import edu.nanodegreeprojects.capstone.travelendar.model.Trip;
import travelendar.capstone.nanodegreeprojects.edu.travelendar.R;

public class MainActivity extends AppCompatActivity implements TabOneUpComingTrip.OnFragmentInteractionListener, TabTwoConcludedTrip.OnFragmentInteractionListener, TripAdapter.TripAdapterOnClickHandler {

    TabLayout tabLayout;
    RecyclerView recyclerViewTabOne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadComponents();
    }

    private void loadComponents() {
        loadTabs();
        loadContentTabs();

    }

    private void loadTabs() {
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
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void loadContentTabs() {
        recyclerViewTabOne = findViewById(R.id.recycler_view_tab_one);
        List<Trip> list = new ArrayList<>();
        Trip trip = new Trip();
        for(int i = 0; i < 10; i++)
            list.add(trip);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        recyclerViewTabOne.setLayoutManager(layoutManager);

        TripAdapter tripAdapter = new TripAdapter(this);
        recyclerViewTabOne.setAdapter(tripAdapter);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onClick(Trip trip) {

    }
}
