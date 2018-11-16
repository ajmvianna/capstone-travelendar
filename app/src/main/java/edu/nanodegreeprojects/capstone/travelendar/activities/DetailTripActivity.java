package edu.nanodegreeprojects.capstone.travelendar.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.nanodegreeprojects.capstone.travelendar.R;
import edu.nanodegreeprojects.capstone.travelendar.model.Trip;


public class DetailTripActivity extends AppCompatActivity {

    Trip trip;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_trip);
        ButterKnife.bind(this);

        if (getIntent().getSerializableExtra("trip") != null)
            trip = (Trip) getIntent().getSerializableExtra("trip");


        settingUpComponents();
        loadData(trip);
    }

    private void settingUpComponents() {


    }

    @OnClick(R.id.fab_back)
    public void back(View view) {
        onBackPressed();
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
