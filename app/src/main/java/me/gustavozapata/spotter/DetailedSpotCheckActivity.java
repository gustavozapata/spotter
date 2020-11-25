package me.gustavozapata.spotter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import static me.gustavozapata.spotter.utils.SpotCheckUtils.colourResult;

//This activity is linked to the activity_detailed_spot_check layout and deals with the Spot Checks detailed information
public class DetailedSpotCheckActivity extends AppCompatActivity {

    TextView detailedNumberPlate;
    TextView detailedCar;
    TextView detailedDate;
    TextView detailedResult;
    TextView detailedNotes;
    TextView detailedLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_spot_check);

        populateFields();
    }

    //Populates the views with the respective spot check data
    public void populateFields() {
        detailedNumberPlate = findViewById(R.id.detailedNumberPlate);
        detailedCar = findViewById(R.id.detailedCar);
        detailedDate = findViewById(R.id.detailedDate);
        detailedResult = findViewById(R.id.detailedResult);
        detailedNotes = findViewById(R.id.detailedNotes);
        detailedLocation = findViewById(R.id.detailedLocation);

        //Get content from the intent (on the MainActivity)
        String numberPlate = getIntent().getStringExtra("spotCheckNumberPlate");
        String car = getIntent().getStringExtra("spotCheckCar");
        String date = getIntent().getStringExtra("spotCheckDate");
        String location = getIntent().getStringExtra("spotCheckLocation");
        String notes = getIntent().getStringExtra("spotCheckNotes");
        if (location != null && !location.isEmpty()) detailedLocation.setText(location);
        if (car != null && !car.equals(" ")) detailedCar.setText(car);
        String result = getIntent().getStringExtra("spotCheckResult");

        //Colours the result text of the spot check
        colourResult(result, detailedResult);

        detailedNumberPlate.setText(numberPlate);
        detailedDate.setText(date);
        detailedResult.setText(result);
        detailedNotes.setText(notes);
    }

    //When a spot check is deleted
    public void onDeleteSpot(View view) {
        Intent deleteSpotCheck = new Intent();
        setResult(RESULT_OK, deleteSpotCheck);
        finish();
        Toast.makeText(this, "Spot Check deleted", Toast.LENGTH_SHORT).show();
    }
}