package edu.nanodegreeprojects.capstone.travelendar.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import edu.nanodegreeprojects.capstone.travelendar.fragments.TabOneUpComingTrip;
import edu.nanodegreeprojects.capstone.travelendar.fragments.TabTwoConcludedTrip;

public class PageAdapter extends FragmentStatePagerAdapter {

    private int numberOfTabs;

    public PageAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
    }

    @Override
    public Fragment getItem(int i) {

        switch (i) {
            case 0:
                return new TabOneUpComingTrip();
            case 1:
                return new TabTwoConcludedTrip();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
