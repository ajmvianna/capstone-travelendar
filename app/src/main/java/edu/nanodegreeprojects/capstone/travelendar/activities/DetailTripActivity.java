package edu.nanodegreeprojects.capstone.travelendar.activities;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import edu.nanodegreeprojects.capstone.travelendar.R;
import edu.nanodegreeprojects.capstone.travelendar.model.Trip;


public class DetailTripActivity extends AppCompatActivity {

    FloatingActionButton fabBack;
    Trip trip;

    private EditText edtTo;
    private EditText edtFrom;
    private EditText edtinitialDate;
    private EditText edtendDate;
    private EditText edtrate;
    private EditText edtbudget;
    private EditText edtgeneralNotes;
    private EditText edtstatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_trip);

        if (getIntent().getSerializableExtra("trip") != null)
            trip = (Trip)getIntent().getSerializableExtra("trip");


        loadComponents();
        loadData(trip);
    }

    private void loadComponents() {

        edtTo = findViewById(R.id.et_trip_to);
        edtFrom = findViewById(R.id.et_trip_from);
        edtinitialDate = findViewById(R.id.et_trip_initial_date);
        edtendDate = findViewById(R.id.et_trip_end_date);
        edtrate = findViewById(R.id.et_trip_rate);
        edtbudget = findViewById(R.id.et_trip_budget);
        edtgeneralNotes = findViewById(R.id.et_trip_general_notes);
        edtstatus = findViewById(R.id.et_trip_status);

        fabBack = findViewById(R.id.fab_back);
        fabBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void loadData(Trip trip) {

        edtTo.setText(trip.getToWhere());
        edtFrom.setText(trip.getFromWhere());
        edtinitialDate.setText(trip.getInitialDate());
        edtendDate.setText(trip.getEndDate());
        edtrate.setText(String.valueOf(trip.getRate()));
        edtbudget.setText(String.valueOf(trip.getBudget()));
        edtgeneralNotes.setText(trip.getGeneralNotes());
        edtstatus.setText(trip.getStatus());
    }

}
