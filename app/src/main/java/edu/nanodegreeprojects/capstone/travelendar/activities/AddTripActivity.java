package edu.nanodegreeprojects.capstone.travelendar.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.util.Calendar;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.nanodegreeprojects.capstone.travelendar.R;
import edu.nanodegreeprojects.capstone.travelendar.data.ContentProviderContract;
import edu.nanodegreeprojects.capstone.travelendar.data.TripDbHelper;
import edu.nanodegreeprojects.capstone.travelendar.model.PlaceItem;
import edu.nanodegreeprojects.capstone.travelendar.model.Trip;
import edu.nanodegreeprojects.capstone.travelendar.widget.TripWidget;


public class AddTripActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

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

    @BindView(R.id.main_toolbar_add)
    Toolbar addTripToolbar;

    @BindString(R.string.add_trip_successful)
    String addTripSuccessfulMessage;

    @BindString(R.string.add_trip_error)
    String addTripErrorMessage;

    @BindString(R.string.fill_all_fields_message)
    String fillAllFieldsMessage;

    @BindString(R.string.date_picker_error_message)
    String datePickerErrorMessage;

    private TripDbHelper tripDbHelper = new TripDbHelper(this);
    private static final int PLACE_PICKER_REQUEST_TO = 1;
    private static final int PLACE_PICKER_REQUEST_FROM = 2;
    private PlaceItem placeTo;
    private PlaceItem placeFrom;
    private DatePickerDialog.OnDateSetListener datePickerInitialDate, datePickerEndDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);
        ButterKnife.bind(this);

        setSupportActionBar(addTripToolbar);
        if (getActionBar() != null)
            getActionBar().setHomeButtonEnabled(true);

        loadDatePickerListeners();

    }

    /**
     * Load listeners from datepicker
     */
    private void loadDatePickerListeners() {
        datePickerInitialDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                int monthFixed = month + 1;
                String date = dayOfMonth + "/" + monthFixed + "/" + year;
                edtInitialDate.setText(date);
            }
        };

        datePickerEndDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                int monthFixed = month + 1;
                String date = dayOfMonth + "/" + monthFixed + "/" + year;
                edtEndDate.setText(date);
            }
        };
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
    }

    /**
     * Add a new trip on the database
     */
    public void addTrip() {
        int addRes;
        if (areFieldsFilled()) {
            Trip trip = new Trip(placeTo,
                    placeFrom,
                    edtInitialDate.getText().toString(),
                    edtEndDate.getText().toString(),
                    0,
                    Float.parseFloat(edtBudget.getText().toString()),
                    edtGeneralNotes.getText().toString(),
                    ContentProviderContract.PATH_UPCOMING_TRIPS
            );
            addRes = tripDbHelper.insertTrip(trip);
            switch (addRes) {
                case 1:
                    TripWidget.updateWidget(this);
                    MainActivity.updateData = true;
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

    /**
     * Ensure that all fields are filled correctly
     */
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

    /**
     * Open the Maps API to provide to the user an interface to get information regarding a place
     */
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

    @OnClick({R.id.et_trip_initial_date, R.id.et_trip_end_date})
    public void openDatePicker(View view) {

        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog dialog = null;
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        switch (view.getId()) {
            case R.id.et_trip_initial_date:
                dialog = new DatePickerDialog(this, datePickerInitialDate, year, month, day);
                break;
            case R.id.et_trip_end_date:
                dialog = new DatePickerDialog(this, datePickerEndDate, year, month, day);
                break;
        }
        if (dialog != null)
            dialog.show();
        else
            Toast.makeText(this, datePickerErrorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add_trip:
                addTrip();
                break;
        }
        return true;
    }
}
