package me.gustavozapata.spotter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView spotChecksListView;
    TextView intentMsg;
    String statusText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spotChecksListView = findViewById(R.id.spotChecksListView);

        final ListAdapter listAdapter = new ListAdapter(this);
        spotChecksListView.setAdapter(listAdapter);

        ViewGroup headerList = (ViewGroup) getLayoutInflater().inflate(R.layout.list_header, null);
        spotChecksListView.addHeaderView(headerList);

        if (savedInstanceState != null) {
            String oldStatusText = savedInstanceState.getString("plateNumber");
            intentMsg = findViewById(R.id.intentMsg);
            intentMsg.setText(oldStatusText);
        }
    }

    @Override //run when activity intent has concluded (finished)
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        statusText = data.getStringExtra("plateNumber");
        intentMsg = findViewById(R.id.intentMsg);
        intentMsg.setText(statusText);

        //START SCREEN
//        ConstraintLayout startView = findViewById(R.id.startView);
//        startView.setVisibility(View.VISIBLE);
//        spotChecksListView.setVisibility(View.INVISIBLE);
    }

    @Override //to keep state of app when config changes occur
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("plateNumber", statusText);
    }

    public void openSpotCheckScreen(View view) {
        Intent spotCheckScreen = new Intent(this, SpotCheckActivity.class);
        spotCheckScreen.putExtra("timeCreated", "Nov 2020");
        startActivityForResult(spotCheckScreen, 0);
    }

    public void openDetailedScreen(View view) {
        Intent detailedScreen = new Intent(this, DetailedSpotCheck.class);
        startActivity(detailedScreen);
    }
}

class SpotCheckRow {
    String date;
    String location;
    String plateNumber;
    String carMake;
    String carModel;
    String result;

    public SpotCheckRow(String date, String location, String plateNumber, String carMake, String carModel, String result) {
        this.date = date;
        this.location = location;
        this.plateNumber = plateNumber;
        this.carMake = carMake;
        this.carModel = carModel;
        this.result = result;
    }
}

class ListAdapter extends BaseAdapter {
    ArrayList<SpotCheckRow> list;
    Context c;

    ListAdapter(Context context) {
        c = context;
        list = new ArrayList<>();
        list.add(new SpotCheckRow("18 October 2020", "TW12 2XR", "UK PL8TE", "Mazda", "3 2008", "No action required"));
        list.add(new SpotCheckRow("11 Octover 2020", "KT3 7AS", "UK AER33", "BMW", "XLL", "Produced documents"));
        list.add(new SpotCheckRow("24 September 2020", "SW12 4DG", "RU 34RTY", "Audi", "3000", "No action required"));
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
//        LayoutInflater layoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View row = layoutInflater.inflate(R.layout.spot_check_card, viewGroup, false);
        LayoutInflater layoutInflater = LayoutInflater.from(c);
        View row = layoutInflater.inflate(R.layout.spot_check_card, null);

        TextView date = row.findViewById(R.id.textViewSpotCheckDate);
        TextView location = row.findViewById(R.id.textViewSpotCheckLocation);
        TextView plateNumber = row.findViewById(R.id.textViewCarPlate);
        TextView carMake = row.findViewById(R.id.textViewCarMake);
        TextView result = row.findViewById(R.id.textViewResult);

        SpotCheckRow temp = list.get(i);
        date.setText(temp.date);
        location.setText(temp.location);
        plateNumber.setText(temp.plateNumber);
        carMake.setText(temp.carMake);
        result.setText(temp.result);

        return row;
    }
}
//CAR MAKERS API
//https://private-anon-bd2fed49be-carsapi1.apiary-mock.com/manufacturers