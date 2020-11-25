package me.gustavozapata.spotter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

//This activity deals with the email spot checks functionality and is linked to the activity_email layout
public class EmailActivity extends AppCompatActivity {

    String spotChecksTypeEmail = "Latest Spot Checks";
    String spots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        spots = getIntent().getStringExtra("spotsToSend");
    }

    //when any of the radio buttons are pressed this runs
    //checks which one is pressed and it gets the respective information to be sent
    public void selectEmailOption(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.latestEmail:
                if (checked) {
                    spotChecksTypeEmail = "Latest Spot Checks";
                    spots = getIntent().getStringExtra("spotsToSend");
                }
                break;
            case R.id.allEmail:
                if (checked) {
                    spotChecksTypeEmail = "All Spot Checks";
                    spots = getIntent().getStringExtra("allSpotsToSend");
                }
                break;
        }
    }

    //this composes the email: to, subject, body
    public void composeAndSend(View view) {
        String[] to = {"k1715308@kingston.ac.uk"};
        Uri mailUri = Uri.parse("mailto:");
        String subject = spotChecksTypeEmail;
        String body = "•••• SPOT CHECKS REPORT ••••\n\n";
        body += "Please find below " + spotChecksTypeEmail + "\n\n";
        body += spots;
        body += "Best,\nGustavo Zapata.";

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, mailUri);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, body);

        //if an email app to handle the intent is found this runs
        if (emailIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(emailIntent, 0);
        } else { //otherwise, the user gets this message
            Toast.makeText(this, "No email app installed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override //run when activity intent has concluded (finished)
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Intent emailSent = new Intent();
            setResult(RESULT_OK, emailSent);
            finish();
        } else { //if the email wasn't sent (email discard, user closes down the email app, etc), this message appears
            Toast.makeText(this, "The email was not sent", Toast.LENGTH_LONG).show();
        }
    }
}