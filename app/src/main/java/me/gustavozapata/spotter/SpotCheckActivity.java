package me.gustavozapata.spotter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SpotCheckActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spot_check);

        String timeCreated = getIntent().getStringExtra("timeCreated");
        TextView notes = findViewById(R.id.textViewDate);
        notes.setText(timeCreated);
    }

    public void createSpotCheck(View view) {
        EditText plateNumber = findViewById(R.id.editTextPlate);
        Intent replyIntent = new Intent();
        replyIntent.putExtra("plateNumber", plateNumber.getText().toString());
        setResult(RESULT_OK, replyIntent);
        finish();
    }
}