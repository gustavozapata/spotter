package me.gustavozapata.spotter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        String reply = data.getStringExtra("plateNumber");
        TextView plateText = findViewById(R.id.textView);
        plateText.setText(reply);
    }

    public void openSpotCheckScreen(View view) {
        Intent spotCheckScreen = new Intent(this, SpotCheckActivity.class);
        spotCheckScreen.putExtra("timeCreated", "Nov 2020");
        startActivityForResult(spotCheckScreen, 0);
    }
}
//CAR MAKERS API
//https://private-anon-bd2fed49be-carsapi1.apiary-mock.com/manufacturers