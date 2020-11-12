package me.gustavozapata.spotter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

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

import me.gustavozapata.spotter.utils.GridAdapter;
import me.gustavozapata.spotter.utils.ListAdapter;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences mPreferences;
    private String sharedPrefFile = "me.gustavozpata.spotter.sharedprefs";

    ListView spotChecksListView;
    ImageView listGridIcon;
    boolean isList = false;

    //When activity is created (or recreated)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spotChecksListView = findViewById(R.id.spotChecksListView);

        ViewGroup headerList = (ViewGroup) getLayoutInflater().inflate(R.layout.list_header, null);
        spotChecksListView.addHeaderView(headerList);

        //USING INSTANCE_STATE
//        if (savedInstanceState != null) {
//            isList = savedInstanceState.getBoolean("listView");
//            renderList(isList);
//        } else {
//            renderList(isList);
//        }
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        renderList(mPreferences.getBoolean("isList", isList));
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putBoolean("isList", isList);
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
    }

    public void toggleListView(View view) {
        isList = !isList;
        renderList(isList);
    }

    public void renderList(boolean isListView) {
        if (isListView) {
            final ListAdapter listAdapter = new ListAdapter(this);
            drawSpotCheckItems(listAdapter, R.drawable.grid, Color.GRAY, 1);
        } else {
            final GridAdapter gridAdapter = new GridAdapter(this);
            drawSpotCheckItems(gridAdapter, R.drawable.list, Color.TRANSPARENT, 80);
        }
    }

    public void drawSpotCheckItems(BaseAdapter adapter, int layout, int color, int height) {
        spotChecksListView = findViewById(R.id.spotChecksListView);
        listGridIcon = findViewById(R.id.listViewButton);

        spotChecksListView.setAdapter(adapter);
        listGridIcon.setImageDrawable(ContextCompat.getDrawable(this, layout));
        ColorDrawable lineDivider = new ColorDrawable(color);
        spotChecksListView.setDivider(lineDivider);
        spotChecksListView.setDividerHeight(height);
    }

    public void openSpotCheckScreen(View view) {
        Intent spotCheckScreen = new Intent(this, SpotCheckActivity.class);
        spotCheckScreen.putExtra("timeCreated", "Nov 2020");
        startActivityForResult(spotCheckScreen, 0);
    }

    public void openDetailedScreen(View view) {
        Intent detailedScreen = new Intent(this, DetailedSpotCheck.class);
        startActivityForResult(detailedScreen, 0);
    }

    public void openEmailSpotChecks(View view) {
        Intent emailScreen = new Intent(this, EmailActivity.class);
        startActivityForResult(emailScreen, 0);
    }
}
//CAR MAKERS API
//https://private-anon-bd2fed49be-carsapi1.apiary-mock.com/manufacturers