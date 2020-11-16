package me.gustavozapata.spotter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SpotCheckActivity extends AppCompatActivity {

    final Calendar myCalendar = Calendar.getInstance();
    TextView spotCheckDate;
    EditText plateNumber;
    EditText carMake;
    EditText carModel;
    EditText location;
    Spinner resultsDropdown;
    EditText notes;

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            displayDate();
        }
    };

    public void selectDate(View view) {
        new DatePickerDialog(SpotCheckActivity.this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void displayDate() {
        String dateFormat = "dd MMM, yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.UK);
        spotCheckDate.setText(simpleDateFormat.format(myCalendar.getTime()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spot_check);

        resultsDropdown = findViewById(R.id.spinner);
        spotCheckDate = findViewById(R.id.editTextDate);
        plateNumber = findViewById(R.id.editTextPlate);
        carMake = findViewById(R.id.editTextCarMake);
        carModel = findViewById(R.id.editTextCarModel);
        location = findViewById(R.id.editTextLocation);
        notes = findViewById(R.id.editTextNotes);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.results_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        resultsDropdown.setAdapter(adapter);

        displayDate();
    }

    //When 'back' button on toolbar is pressed
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void createSpotCheck(View view) {
        if(!plateNumber.getText().toString().trim().isEmpty()){
            Intent replyIntent = new Intent();
            replyIntent.putExtra("numberPlate", plateNumber.getText().toString());
            replyIntent.putExtra("carMake", carMake.getText().toString());
            replyIntent.putExtra("carModel", carModel.getText().toString());
            replyIntent.putExtra("result", resultsDropdown.getSelectedItem().toString());
            replyIntent.putExtra("date", spotCheckDate.getText().toString());
            replyIntent.putExtra("location", location.getText().toString());
            replyIntent.putExtra("notes", notes.getText().toString());
            setResult(RESULT_OK, replyIntent);
            finish();
        } else {
            Toast.makeText(this, "Please enter a number plate", Toast.LENGTH_SHORT).show();
        }
    }
}