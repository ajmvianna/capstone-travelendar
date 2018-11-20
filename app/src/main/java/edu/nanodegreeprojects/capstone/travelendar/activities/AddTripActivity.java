package edu.nanodegreeprojects.capstone.travelendar.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.nanodegreeprojects.capstone.travelendar.R;
import edu.nanodegreeprojects.capstone.travelendar.data.TripDbHelper;
import edu.nanodegreeprojects.capstone.travelendar.model.PlaceItem;
import edu.nanodegreeprojects.capstone.travelendar.model.Trip;


public class AddTripActivity extends AppCompatActivity {

    @BindView(R.id.et_trip_to_where)
    EditText edtToWhere;

    @BindView(R.id.et_trip_from_where)
    EditText edtFromWhere;

    @BindView(R.id.et_trip_initial_date)
    EditText edtInitialDate;

    @BindView(R.id.et_trip_end_date)
    EditText edtEndDate;

    @BindView(R.id.et_trip_budget)
    EditText edtBudget;

    @BindView(R.id.et_trip_general_notes)
    EditText edtGeneralNotes;

    @BindString(R.string.add_trip_successful)
    String addTripSuccessfulMessage;

    @BindString(R.string.add_trip_error)
    String addTripErrorMessage;

    @BindString(R.string.fill_all_fields_message)
    String fillAllFieldsMessage;

    private TripDbHelper tripDbHelper = new TripDbHelper(this);
    private static final int PLACE_PICKER_REQUEST_TO = 1;
    private static final int PLACE_PICKER_REQUEST_FROM = 2;
    private PlaceItem placeTo;
    private PlaceItem placeFrom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.fab_back)
    public void back(View view) {
        onBackPressed();
    }

    @OnClick(R.id.bt_add_trip)
    public void addTrip(View view) {
        int addRes;
        if (areFieldsFilled()) {
            Trip trip = new Trip(placeTo,
                    placeFrom,
                    edtInitialDate.getText().toString(),
                    edtEndDate.getText().toString(),
                    0,
                    Float.parseFloat(edtBudget.getText().toString()),
                    edtGeneralNotes.getText().toString(),
                    "upcoming"
            );
            addRes = tripDbHelper.insertTrip(trip);
            switch (addRes) {
                case 1:
                    Toast.makeText(this, addTripSuccessfulMessage, Toast.LENGTH_SHORT).show();
                    onBackPressed();
                    break;
                case 0:
                default:
                    Toast.makeText(this, addTripErrorMessage, Toast.LENGTH_SHORT).show();
                    break;
            }
        } else {
            Toast.makeText(this, fillAllFieldsMessage, Toast.LENGTH_SHORT).show();
            if (placeTo == null)
                edtToWhere.setText("");
            if (placeFrom == null)
                edtFromWhere.setText("");
        }
    }

    private boolean areFieldsFilled() {
        return (!edtToWhere.getText().toString().equals("") &&
                !edtFromWhere.getText().toString().equals("") &&
                !edtInitialDate.getText().toString().equals("") &&
                !edtEndDate.getText().toString().equals("") &&
                !edtBudget.getText().toString().equals("") &&
                !edtGeneralNotes.getText().toString().equals("") &&
                placeTo != null &&
                placeFrom != null);
    }

    @OnClick({R.id.et_trip_to_where, R.id.et_trip_from_where})
    public void getLocalScreen(View view) {
        int requestId = -1;
        switch (view.getId()) {
            case R.id.et_trip_to_where:
                placeTo = null;
                edtToWhere.setText("");
                requestId = PLACE_PICKER_REQUEST_TO;
                break;
            case R.id.et_trip_from_where:
                placeFrom = null;
                edtFromWhere.setText("");
                requestId = PLACE_PICKER_REQUEST_FROM;
                break;
        }

        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            Intent intent = builder.build(this);
            startActivityForResult(intent, requestId);
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_PICKER_REQUEST_TO) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(this, data);
                if (place != null) {
                    placeTo = new PlaceItem(place.getName().toString(),
                            place.getLatLng().latitude,
                            place.getLatLng().longitude,
                            place.getAddress().toString());
                    edtToWhere.setText(placeTo.getPlaceName());
                }

            }
        }

        if (requestCode == PLACE_PICKER_REQUEST_FROM) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(this, data);
                if (place != null) {
                    placeFrom = new PlaceItem(place.getName().toString(),
                            place.getLatLng().latitude,
                            place.getLatLng().longitude,
                            place.getAddress().toString());
                    edtFromWhere.setText(placeFrom.getPlaceName());
                }
            }
        }
    }
}
