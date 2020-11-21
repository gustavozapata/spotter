package me.gustavozapata.spotter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import me.gustavozapata.spotter.model.SpotCheck;
import me.gustavozapata.spotter.model.viewmodel.SpotCheckViewModel;
import me.gustavozapata.spotter.utils.GridAdapter;
import me.gustavozapata.spotter.utils.ListAdapter;

import static me.gustavozapata.spotter.utils.SpotCheckUtils.convertDateToString;
import static me.gustavozapata.spotter.utils.SpotCheckUtils.convertStringToDate;
import static me.gustavozapata.spotter.utils.SpotCheckUtils.pickDate;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_SPOT_CHECK = 1;
    public static final int OPEN_SPOT_CHECK = 2;
    public static final int EMAIL_SPOTS = 3;

    private SharedPreferences mPreferences;
    private String sharedPrefFile = "me.gustavozpata.spotter.sharedprefs";

    private SpotCheckViewModel spotCheckViewModel;

    ListView listView;
    ImageView listGridIcon;
    SearchView searchView;
    TextView searchByDateView;
    TextView resultsMessage;

    List<SpotCheck> list = new ArrayList<>();
    ArrayList<String> listEmail = new ArrayList<>();

    boolean isList;
    boolean clearSearch = true;
    boolean readyToEmail = false;
    int selectedSpot;

    final ListAdapter listAdapter = new ListAdapter(this, list);
    final GridAdapter gridAdapter = new GridAdapter(this, list);
    final Calendar calendar = Calendar.getInstance();

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
            public void onChanged(List<SpotCheck> spotChecks) {
                renderList(spotChecks);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SpotCheck spot = isList ? listAdapter.getSpotAt(--i) : gridAdapter.getSpotAt(--i);
                selectedSpot = i;
                Intent detailedScreen = new Intent(MainActivity.this, DetailedSpotCheckActivity.class);
                detailedScreen.putExtra("spotCheckNumberPlate", spot.getNumberPlate());
                detailedScreen.putExtra("spotCheckCar", spot.getCarMake() + " " + spot.getCarModel());
                detailedScreen.putExtra("spotCheckDate", convertDateToString(spot.getDate()));
                detailedScreen.putExtra("spotCheckLocation", spot.getLocation());
                detailedScreen.putExtra("spotCheckResult", spot.getResult());
                detailedScreen.putExtra("spotCheckNotes", spot.getNotes());
                detailedScreen.putExtra("deleteItem", i);
                startActivityForResult(detailedScreen, OPEN_SPOT_CHECK);
            }
        });

        searchViewFunctionality();
    }

    public void searchViewFunctionality() {
        searchView = findViewById(R.id.searchView);
        spotCheckViewModel.searchByFields.observe(this, new Observer<List<SpotCheck>>() {
            @Override
            public void onChanged(List<SpotCheck> spotChecks) {
                renderList(spotChecks);
                if (readyToEmail) {
                    prepareEmailSpots();
                }
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                spotCheckViewModel.filterLiveData.setValue(s);
                resultsMessage = findViewById(R.id.resultsMessage);
                resultsMessage.setVisibility(View.VISIBLE);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s.equals("")) {
                    spotCheckViewModel.filterLiveData.setValue("");
                    resultsMessage = findViewById(R.id.resultsMessage);
                    resultsMessage.setVisibility(View.INVISIBLE);
                    hideKeyboard();
                }
                return false;
            }
        });
    }

    public void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
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
            insertNewSpotCheck(data);
        } else if (requestCode == OPEN_SPOT_CHECK && resultCode == RESULT_OK) {
            SpotCheck spot = isList ? listAdapter.getSpotAt(selectedSpot) : gridAdapter.getSpotAt(selectedSpot);
            spotCheckViewModel.delete(spot);
        } else if (requestCode == EMAIL_SPOTS && resultCode == RESULT_OK) {
            for (SpotCheck spotCheck : list) {
                for (String emailedSpot : listEmail) {
                    if (spotCheck.getId().equals(emailedSpot)) {
                        SpotCheck updateSpot = new SpotCheck(
                                spotCheck.getNumberPlate(),
                                spotCheck.getDate(),
                                spotCheck.getLocation(),
                                spotCheck.getCarMake(),
                                spotCheck.getCarModel(),
                                spotCheck.getResult(),
                                spotCheck.getNotes());
                        updateSpot.setId(spotCheck.getId());
                        updateSpot.setSent(true);
                        spotCheckViewModel.update(updateSpot);
                    }
                }
            }
            displayEmailToast();
        }
    }

    private void displayEmailToast() {
        Toast toast = Toast.makeText(this, "Email sent!", Toast.LENGTH_SHORT);
        View toastView = toast.getView();
        toastView.setBackgroundResource(R.drawable.toast);
        TextView text = toastView.findViewById(android.R.id.message);
        text.setTextColor(Color.WHITE);
        toast.show();
    }

    private void insertNewSpotCheck(Intent data) {
        String numberPlate = data.getStringExtra("numberPlate");
        String carMake = data.getStringExtra("carMake");
        String carModel = data.getStringExtra("carModel");
        String result = data.getStringExtra("result");
        String location = data.getStringExtra("location");
        String notes = data.getStringExtra("notes");
        Date date = convertStringToDate(data.getStringExtra("date"));
        SpotCheck newSpotCheck = new SpotCheck(numberPlate, date, location, carMake, carModel, result, notes);
        spotCheckViewModel.insert(newSpotCheck);
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
        readyToEmail = true;
        spotCheckViewModel.filterLiveData.setValue("");
        styleSearchDate(R.drawable.corner_radius, R.string.search_dates, "#797979");
        resultsMessage = findViewById(R.id.resultsMessage);
        resultsMessage.setVisibility(View.INVISIBLE);
        clearSearch = true;
    }

    public void prepareEmailSpots() {
        readyToEmail = false;
        Intent emailScreen = new Intent(this, EmailActivity.class);
        String spots = "";
        String allSpots = "";
        for (SpotCheck spotCheck : list) {
            allSpots += contentsOfEmail(spotCheck);
            if (!spotCheck.isSent()) {
                listEmail.add(spotCheck.getId());
                spots += contentsOfEmail(spotCheck);
            }
        }
        emailScreen.putExtra("spotsToSend", spots);
        emailScreen.putExtra("allSpotsToSend", allSpots);
        startActivityForResult(emailScreen, EMAIL_SPOTS);
    }

    public String contentsOfEmail(SpotCheck spotCheck) {
        String spots = "";
        spots += "•Number plate: " + spotCheck.getNumberPlate() + "\n";
        spots += "•Car make: " + spotCheck.getCarMake() + "\n";
        spots += "•Car model: " + spotCheck.getCarModel() + "\n";
        spots += "•Location: " + spotCheck.getLocation() + "\n";
        spots += "•Date: " + convertDateToString(spotCheck.getDate()) + "\n";
        spots += "•Result: " + spotCheck.getResult() + "\n";
        spots += "•Notes : " + spotCheck.getNotes() + "\n";
        spots += "\n";
        return spots;
    }

    public void sortByResult(View view) {
        Collections.sort(list, new Comparator<SpotCheck>() {
            public int compare(SpotCheck spot1, SpotCheck spot2) {
                return spot1.getResult().compareTo(spot2.getResult());
            }
        });
        renderList(list);
    }

    public void sortByDate(View view) {
        Collections.sort(list, new Comparator<SpotCheck>() {
            public int compare(SpotCheck spot1, SpotCheck spot2) {
                return spot1.getDate().compareTo(spot2.getDate());
            }
        });
        renderList(list);
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            spotCheckViewModel.filterLiveDataDate.setValue(pickDate(year, monthOfYear, dayOfMonth));
            spotCheckViewModel.searchByDate.observe(MainActivity.this, new Observer<List<SpotCheck>>() {
                @Override
                public void onChanged(List<SpotCheck> spotChecks) {
                    renderList(spotChecks);
                    if (!clearSearch) {
                        styleSearchDate(R.drawable.container_shadow, R.string.clear, "#FFFFFF");
                        searchView = findViewById(R.id.searchView);
                    }
                }
            });
        }
    };

    public void searchByDate(View view) {
        styleSearchDate(R.drawable.corner_radius, R.string.search_dates, "#797979");
        if (clearSearch) {
            clearSearch = false;
            new DatePickerDialog(MainActivity.this, date, calendar
                    .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)).show();
        } else {
            clearSearch = true;
            spotCheckViewModel.filterLiveData.setValue("");
        }
    }

    public void styleSearchDate(int background, int label, String color) {
        searchByDateView = findViewById(R.id.searchByDateView);
        searchByDateView.setBackground(ContextCompat.getDrawable(MainActivity.this, background));
        searchByDateView.setText(getString(label));
        searchByDateView.setTextColor(Color.parseColor(color));
    }
}