package edu.nanodegreeprojects.capstone.travelendar.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.nanodegreeprojects.capstone.travelendar.R;
import edu.nanodegreeprojects.capstone.travelendar.data.TripDbHelper;
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

    @BindView(R.id.et_trip_status)
    EditText edtStatus;

    @BindString(R.string.add_trip_successful)
    String addTripSuccessfulMessage;

    @BindString(R.string.add_trip_error)
    String addTripErrorMessage;

    @BindString(R.string.fill_all_fields_message)
    String fillAllFieldsMessage;

    private TripDbHelper tripDbHelper = new TripDbHelper(this);

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
            Trip trip = new Trip(edtToWhere.getText().toString(),
                    edtFromWhere.getText().toString(),
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
        }
    }

    private boolean areFieldsFilled() {
        return (!edtToWhere.getText().toString().equals("") &&
                !edtFromWhere.getText().toString().equals("") &&
                !edtInitialDate.getText().toString().equals("") &&
                !edtEndDate.getText().toString().equals("") &&
                !edtBudget.getText().toString().equals("") &&
                !edtGeneralNotes.getText().toString().equals("") &&
                !edtStatus.getText().toString().equals(""));
    }

}
