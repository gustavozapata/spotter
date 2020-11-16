package me.gustavozapata.spotter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import me.gustavozapata.spotter.model.SpotCheck;
import me.gustavozapata.spotter.model.viewmodel.SpotCheckViewModel;
import me.gustavozapata.spotter.utils.GridAdapter;
import me.gustavozapata.spotter.utils.ListAdapter;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_SPOT_CHECK = 1;

    private SharedPreferences mPreferences;
    private String sharedPrefFile = "me.gustavozpata.spotter.sharedprefs";

    private SpotCheckViewModel spotCheckViewModel;

    ListView listView;
    ImageView listGridIcon;
    boolean isList;
    List<SpotCheck> list = new ArrayList<>();

    final ListAdapter listAdapter = new ListAdapter(this, list);
    final GridAdapter gridAdapter = new GridAdapter(this, list);

    //When activity is created (or recreated)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.spotChecksListView);
        ViewGroup headerList = (ViewGroup) getLayoutInflater().inflate(R.layout.list_header, null);
        listView.addHeaderView(headerList);

        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        isList = mPreferences.getBoolean("sharedIsList", false);

        //ViewModel - Room DB
        spotCheckViewModel = ViewModelProviders.of(this).get(SpotCheckViewModel.class);
        spotCheckViewModel.getAllSpotChecks().observe(this, new Observer<List<SpotCheck>>() {
            @Override
            public void onChanged(List<SpotCheck> spotChecks) { //whenever the ViewModel changes this runs
                renderList(spotChecks);
            }
        });
    }

    public void renderList(List<SpotCheck> spotChecks) {
        list = spotChecks;
        if (isList) {
            listAdapter.setSpotChecks(spotChecks);
            drawSpotCheckItems(listAdapter, R.drawable.grid, Color.GRAY, 1);
        } else {
            gridAdapter.setSpotChecks(spotChecks);
            drawSpotCheckItems(gridAdapter, R.drawable.list, Color.TRANSPARENT, 80);
        }
        startScreen();
    }

    public void drawSpotCheckItems(BaseAdapter adapter, int layout, int color, int height) {
        listView = findViewById(R.id.spotChecksListView);
        listGridIcon = findViewById(R.id.listViewButton);
        listView.setAdapter(adapter);
        listGridIcon.setImageDrawable(ContextCompat.getDrawable(this, layout));
        ColorDrawable lineDivider = new ColorDrawable(color);
        listView.setDivider(lineDivider);
        listView.setDividerHeight(height);
    }

    public void startScreen() {
        ConstraintLayout startView = findViewById(R.id.startView);
        listView = findViewById(R.id.spotChecksListView);
        if (list.size() < 1) {
            listView.setVisibility(View.GONE);
            startView.setVisibility(View.VISIBLE);
        } else {
            listView.setVisibility(View.VISIBLE);
            startView.setVisibility(View.GONE);
        }
    }

    public void openDetailedScreen(View view) {
        TextView elID = findViewById(R.id.elID);
        String id = elID.getText().toString();
        System.out.println("##########################" + id);
        Intent detailedScreen = new Intent(this, DetailedSpotCheck.class);
        detailedScreen.putExtra("spotCheckId", id);
        startActivityForResult(detailedScreen, 0);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putBoolean("sharedIsList", isList);
        preferencesEditor.apply();
    }

    //When activity gets destroyed (finish()) or config changes occur
    @Override //to keep state of app when config changes occur (like rotate device)
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean("listView", isList);
        super.onSaveInstanceState(outState);
    }

    @Override //run when activity intent has concluded (finished)
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_SPOT_CHECK && resultCode == RESULT_OK) {
            String numberPlate = data.getStringExtra("numberPlate");
            String carMake = data.getStringExtra("carMake");
            String carModel = data.getStringExtra("carModel");
            String result = data.getStringExtra("result");
            String location = data.getStringExtra("location");
            String date = data.getStringExtra("date");
            String notes = data.getStringExtra("notes");
            SpotCheck newSpotCheck = new SpotCheck(numberPlate, date, location, carMake, carModel, result, notes);
            spotCheckViewModel.insert(newSpotCheck);
        }
    }

    public void toggleListView(View view) {
        isList = !isList;
        renderList(list);
    }

    public void openSpotCheckScreen(View view) {
        Intent spotCheckScreen = new Intent(this, SpotCheckActivity.class);
        startActivityForResult(spotCheckScreen, ADD_SPOT_CHECK);
    }

    public void openEmailSpotChecks(View view) {
        Intent emailScreen = new Intent(this, EmailActivity.class);
        startActivityForResult(emailScreen, 0);
    }

    public void deleteAll(View view) {
        spotCheckViewModel.deleteAll();
        Toast.makeText(MainActivity.this, "All Spots deleted", Toast.LENGTH_SHORT).show();
    }
}
//CAR MAKERS API
//https://private-anon-bd2fed49be-carsapi1.apiary-mock.com/manufacturers