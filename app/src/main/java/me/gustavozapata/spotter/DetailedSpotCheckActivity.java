package me.gustavozapata.spotter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import me.gustavozapata.spotter.model.viewmodel.SpotCheckViewModel;

import static me.gustavozapata.spotter.utils.SpotCheckUtils.colourResult;

public class DetailedSpotCheckActivity extends AppCompatActivity {

    private SpotCheckViewModel spotCheckViewModel;
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

        spotCheckViewModel = ViewModelProviders.of(this).get(SpotCheckViewModel.class);

        populateFields();
    }

    public void populateFields() {
        detailedNumberPlate = findViewById(R.id.detailedNumberPlate);
        detailedCar = findViewById(R.id.detailedCar);
        detailedDate = findViewById(R.id.detailedDate);
        detailedResult = findViewById(R.id.detailedResult);
        detailedNotes = findViewById(R.id.detailedNotes);
        detailedLocation = findViewById(R.id.detailedLocation);

        String numberPlate = getIntent().getStringExtra("spotCheckNumberPlate");
        String car = getIntent().getStringExtra("spotCheckCar");
        String date = getIntent().getStringExtra("spotCheckDate");
        String location = getIntent().getStringExtra("spotCheckLocation");
        String notes = getIntent().getStringExtra("spotCheckNotes");
        if (location != null && !location.isEmpty()) detailedLocation.setText(location);
        if (car != null && !car.equals(" ")) detailedCar.setText(car);
        String result = getIntent().getStringExtra("spotCheckResult");
        colourResult(result, detailedResult);
        detailedNumberPlate.setText(numberPlate);
        detailedDate.setText(date);
        detailedResult.setText(result);
        detailedNotes.setText(notes);
    }

    public void onDeleteSpot(View view) {
        Intent deleteSpotCheck = new Intent();
        setResult(RESULT_OK, deleteSpotCheck);
        finish();
        Toast.makeText(this, "Spot Check deleted", Toast.LENGTH_SHORT).show();
    }
}