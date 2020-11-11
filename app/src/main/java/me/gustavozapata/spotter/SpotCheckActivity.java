package me.gustavozapata.spotter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class SpotCheckActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spot_check);

        String timeCreated = getIntent().getStringExtra("timeCreated");
        TextView notes = findViewById(R.id.textViewDate);
        notes.setText(timeCreated);

        Spinner resultsDropdown = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.results_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        resultsDropdown.setAdapter(adapter);
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
        EditText plateNumber = findViewById(R.id.editTextPlate);
        Intent replyIntent = new Intent();
        replyIntent.putExtra("plateNumber", plateNumber.getText().toString());
        setResult(RESULT_OK, replyIntent);
        finish();
    }
}