package edu.nanodegreeprojects.capstone.travelendar.activities;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import edu.nanodegreeprojects.capstone.travelendar.R;
import edu.nanodegreeprojects.capstone.travelendar.data.TripDbHelper;
import edu.nanodegreeprojects.capstone.travelendar.model.Trip;


public class DetailTripActivity extends AppCompatActivity {

    @BindView(R.id.et_trip_to_where)
    EditText edtToWhere;

    @BindView(R.id.et_trip_from_where)
    EditText edtFromWhere;

    @BindView(R.id.et_trip_initial_date)
    EditText edtInitialDate;

    @BindView(R.id.et_trip_rate)
    EditText edtRate;

    @BindView(R.id.et_trip_end_date)
    EditText edtEndDate;

    @BindView(R.id.et_trip_budget)
    EditText edtBudget;

    @BindView(R.id.et_trip_general_notes)
    EditText edtGeneralNotes;

    @BindView(R.id.et_trip_status)
    EditText edtStatus;

    @BindView(R.id.bt_end_trip)
    Button btnEndTrip;

    @BindDrawable(R.drawable.star_blank)
    Drawable drwStarBlank;

    @BindDrawable(R.drawable.star_filled)
    Drawable drwStarFilled;

    @BindString(R.string.end_trip_successful_message)
    String endTripSuccessfulMessage;

    @BindString(R.string.end_trip_error_message)
    String endTripErrorMessage;

    @BindString(R.string.rate_trip_message_title)
    String rateTripMessageTitle;

    private Trip trip;
    private TripDbHelper tripDbHelper = new TripDbHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_trip);
        ButterKnife.bind(this);

        if (getIntent().getSerializableExtra("trip") != null)
            trip = (Trip) getIntent().getSerializableExtra("trip");

        loadData(trip);
    }

    @OnClick(R.id.fab_back)
    public void back(View view) {
        onBackPressed();
    }

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
                trip.setStatus("concluded");
                int res = tripDbHelper.updateTrip(trip);
                if (res == 1)
                    Toast.makeText(this, endTripSuccessfulMessage, Toast.LENGTH_SHORT).show();
                else
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

    @OnClick(R.id.bt_end_trip)
    public void endTrip() {

        View view = getLayoutInflater().inflate(R.layout.rate_trip_message, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(rateTripMessageTitle);
        builder.setView(view);
        builder.show();
        ButterKnife.bind(this);

    }

    private void loadData(Trip trip) {

        edtToWhere.setText(trip.getToWhere());
        edtFromWhere.setText(trip.getFromWhere());
        edtInitialDate.setText(trip.getInitialDate());
        edtEndDate.setText(trip.getEndDate());
        edtRate.setText(String.valueOf(trip.getRate()));
        edtBudget.setText(String.valueOf(trip.getBudget()));
        edtGeneralNotes.setText(trip.getGeneralNotes());
        edtStatus.setText(trip.getStatus());
    }

}
