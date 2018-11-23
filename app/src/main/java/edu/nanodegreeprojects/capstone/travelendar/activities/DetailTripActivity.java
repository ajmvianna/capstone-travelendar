package edu.nanodegreeprojects.capstone.travelendar.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import edu.nanodegreeprojects.capstone.travelendar.R;
import edu.nanodegreeprojects.capstone.travelendar.data.ContentProviderContract;
import edu.nanodegreeprojects.capstone.travelendar.data.TripDbHelper;
import edu.nanodegreeprojects.capstone.travelendar.model.Trip;
import edu.nanodegreeprojects.capstone.travelendar.widget.TripWidget;


public class DetailTripActivity extends AppCompatActivity implements com.google.android.gms.maps.OnMapReadyCallback {

    @BindView(R.id.et_trip_to_where)
    EditText edtToWhere;

    @BindView(R.id.et_trip_from_where)
    EditText edtFromWhere;

    @BindView(R.id.et_trip_initial_date)
    EditText edtInitialDate;

    @BindView(R.id.tv_trip_rate_label)
    TextView tvRate;

    @BindView(R.id.et_trip_rate)
    EditText edtRate;

    @BindView(R.id.et_trip_end_date)
    EditText edtEndDate;

    @BindView(R.id.et_trip_budget)
    EditText edtBudget;

    @BindView(R.id.et_trip_general_notes)
    EditText edtGeneralNotes;

    @BindView(R.id.details_toolbar)
    Toolbar detailsToolbar;

    @BindDrawable(R.drawable.star_blank)
    Drawable drwStarBlank;

    @BindDrawable(R.drawable.star_filled)
    Drawable drwStarFilled;

    @BindString(R.string.end_trip_successful_message)
    String endTripSuccessfulMessage;

    @BindString(R.string.end_trip_error_message)
    String endTripErrorMessage;

    @BindString(R.string.delete_trip_successful_message)
    String deleteTripSuccessfulMessage;

    @BindString(R.string.delete_trip_error_message)
    String deleteTripErrorMessage;

    @BindString(R.string.rate_trip_message_title)
    String rateTripMessageTitle;

    @BindString(R.string.share_trip_title)
    String shareTripTitle;

    @BindString(R.string.share_trip_message)
    String shareTripMessage;

    @BindString(R.string.trip_from_where_label)
    String tripWhereFromLabel;

    @BindString(R.string.trip_to_where_label)
    String tripWhereToLabel;

    @BindString(R.string.trip_initial_date_label)
    String tripInitialDateLabel;

    @BindString(R.string.trip_end_date_label)
    String tripEndDateLabel;

    @BindString(R.string.trip_rate_label)
    String tripRateLabel;

    @BindString(R.string.share_app_link)
    String shareAppLink;

    @BindString(R.string.app_name)
    String appName;

    private Trip trip;
    private TripDbHelper tripDbHelper = new TripDbHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_trip);
        ButterKnife.bind(this);

        setSupportActionBar(detailsToolbar);
        if (getActionBar() != null)
            getActionBar().setHomeButtonEnabled(true);

        if (getIntent().getSerializableExtra(MainActivity.TRIP_EXTRA_TAG) != null)
            trip = (Trip) getIntent().getSerializableExtra(MainActivity.TRIP_EXTRA_TAG);

        loadData(trip);
    }

    /**
     * Method responsible to rate a trip
     */
    @Optional
    @OnClick({R.id.iv_rate_star_1, R.id.iv_rate_star_2, R.id.iv_rate_star_3, R.id.iv_rate_star_4, R.id.iv_rate_star_5})
    public void rateClick(View view) {

        int maxRate = 5;

        ImageView ivRateStar1 = view.getRootView().findViewById(R.id.iv_rate_star_1);
        ImageView ivRateStar2 = view.getRootView().findViewById(R.id.iv_rate_star_2);
        ImageView ivRateStar3 = view.getRootView().findViewById(R.id.iv_rate_star_3);
        ImageView ivRateStar4 = view.getRootView().findViewById(R.id.iv_rate_star_4);
        ImageView ivRateStar5 = view.getRootView().findViewById(R.id.iv_rate_star_5);
        List<ImageView> listStars = new ArrayList<>();
        listStars.add(ivRateStar1);
        listStars.add(ivRateStar2);
        listStars.add(ivRateStar3);
        listStars.add(ivRateStar4);
        listStars.add(ivRateStar5);

        switch (view.getId()) {
            case R.id.iv_rate_star_1:
                trip.setRate(1);
                break;
            case R.id.iv_rate_star_2:
                trip.setRate(2);
                break;
            case R.id.iv_rate_star_3:
                trip.setRate(3);
                break;
            case R.id.iv_rate_star_4:
                trip.setRate(4);
                break;
            case R.id.iv_rate_star_5:
                trip.setRate(5);
                break;
            case R.id.bt_rate_trip_ok:
                trip.setStatus(ContentProviderContract.PATH_CONCLUDED_TRIPS);
                int res = tripDbHelper.updateTrip(trip);
                if (res == 1) {
                    TripWidget.updateWidget(this);
                    MainActivity.updateData = true;
                    Toast.makeText(this, endTripSuccessfulMessage, Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(this, endTripErrorMessage, Toast.LENGTH_SHORT).show();
                finish();
                break;

        }

        for (int i = 0; i < maxRate; i++) {
            if (i < trip.getRate())
                listStars.get(i).setImageDrawable(drwStarFilled);
            else
                listStars.get(i).setImageDrawable(drwStarBlank);
        }
    }

    /**
     * Method responsible for load the information on screen regarding current trip
     */
    private void loadData(Trip trip) {

        edtToWhere.setText(trip.getToWhere().getPlaceName());
        edtFromWhere.setText(trip.getFromWhere().getPlaceName());
        edtInitialDate.setText(trip.getInitialDate());
        edtEndDate.setText(trip.getEndDate());
        edtBudget.setText(String.valueOf(trip.getBudget()));
        edtGeneralNotes.setText(trip.getGeneralNotes());
        if (trip.getStatus().equals(ContentProviderContract.PATH_CONCLUDED_TRIPS)) {
            tvRate.setVisibility(View.VISIBLE);
            edtRate.setVisibility(View.VISIBLE);
            edtRate.setText(String.valueOf(trip.getRate()));
        }

        loadMapDetails();

    }

    /**
     * Load the Maps information regarding the trip selected
     */
    private void loadMapDetails() {
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_detail);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {

        final GoogleMap gMaps = map;
        map.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                gMaps.addMarker(new MarkerOptions().position(new LatLng(trip.getFromWhere().getLatitude(), trip.getFromWhere().getLongitude())).title(trip.getFromWhere().getPlaceName()));
                gMaps.addMarker(new MarkerOptions().position(new LatLng(trip.getToWhere().getLatitude(), trip.getToWhere().getLongitude())).title(trip.getToWhere().getPlaceName()));
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                builder.include(new LatLng(trip.getFromWhere().getLatitude(), trip.getFromWhere().getLongitude()));
                builder.include(new LatLng(trip.getToWhere().getLatitude(), trip.getToWhere().getLongitude()));

                LatLngBounds bounds = builder.build();
                int padding = 200;
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                gMaps.animateCamera(cameraUpdate);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details_menu, menu);

        if (!trip.getStatus().equals(ContentProviderContract.PATH_CONCLUDED_TRIPS)) {
            menu.getItem(0).setVisible(true);
            menu.getItem(1).setVisible(false);
        } else {
            menu.getItem(0).setVisible(false);
            menu.getItem(1).setVisible(true);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_details_end_trip:
                endTrip();
                break;
            case R.id.menu_details_share_trip:
                shareTrip();
                break;
            case R.id.menu_details_delete_trip:
                deleteTrip();
                break;
        }
        return true;
    }

    /**
     * Convert current trip to string to be shared
     */
    public String tripToString() {
        return "*" + appName + ":* \n\n" +
                shareTripMessage + "\n\n" +
                "*" + tripWhereFromLabel + "* " + trip.getFromWhere().getPlaceName() + "\n" +
                "*" + tripWhereToLabel + "* " + trip.getToWhere().getPlaceName() + "\n" +
                "*" + tripInitialDateLabel + "* " + trip.getInitialDate() + "\n" +
                "*" + tripEndDateLabel + "* " + trip.getEndDate() + "\n\n" +
                "*" + tripRateLabel + "* " + trip.getRate() + "\n\n" +
                shareAppLink;


    }

    /**
     * Method used to show the evaluation screen and end a trip
     */
    public void endTrip() {
        View view = getLayoutInflater().inflate(R.layout.rate_trip_message, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(rateTripMessageTitle);
        builder.setView(view);
        builder.show();
        ButterKnife.bind(this);
    }

    /**
     * Method responsible for share the current trip
     */
    public void shareTrip() {
        startActivity(Intent.createChooser(ShareCompat.IntentBuilder.from(this)
                .setType("text/plain")
                .setText(tripToString())
                .getIntent(), shareTripTitle));
    }

    /**
     * Method responsible for delete the current trip
     */
    public void deleteTrip() {
        int res = tripDbHelper.deleteTrip(trip);
        if (res == 1) {
            TripWidget.updateWidget(this);
            MainActivity.updateData = true;
            Toast.makeText(this, deleteTripSuccessfulMessage, Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(this, deleteTripErrorMessage, Toast.LENGTH_SHORT).show();
        finish();
    }

}
