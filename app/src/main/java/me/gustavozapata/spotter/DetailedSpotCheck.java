package me.gustavozapata.spotter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import me.gustavozapata.spotter.model.SpotCheck;
import me.gustavozapata.spotter.model.viewmodel.SpotCheckViewModel;

public class DetailedSpotCheck extends AppCompatActivity {

    private SpotCheckViewModel spotCheckViewModel;
    public SpotCheck spotCheck;
    TextView detailedNumberPlate;
    TextView detailedCar;
    TextView detailedDate;
    TextView detailedResult;
    TextView detailedNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_spot_check);

        String id = getIntent().getStringExtra("spotCheckId");
        spotCheckViewModel = ViewModelProviders.of(this).get(SpotCheckViewModel.class);
//        spotCheck = spotCheckViewModel.getSpotCheck(id);
        spotCheckViewModel.getSpotCheck(id).observe(this, new Observer<SpotCheck>() {
            @Override
            public void onChanged(SpotCheck spot) {
                spotCheck = spot;
            }
        });

        populateFields();
    }

    public void populateFields() {
        detailedNumberPlate = findViewById(R.id.detailedNumberPlate);
        detailedCar = findViewById(R.id.detailedCar);
        detailedDate = findViewById(R.id.detailedDate);
        detailedResult = findViewById(R.id.detailedResult);
        detailedNotes = findViewById(R.id.detailedNotes);

//        detailedNumberPlate.setText(spotCheck.getNumberPlate());
//        String car = spotCheck.getCarMake() + " - " + spotCheck.getCarModel();
//        detailedCar.setText(car);
//        detailedDate.setText(spotCheck.getDate());
//        detailedResult.setText(spotCheck.getResult());
//        detailedNotes.setText(spotCheck.getNotes());
    }
}