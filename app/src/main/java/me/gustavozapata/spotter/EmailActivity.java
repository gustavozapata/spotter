package me.gustavozapata.spotter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

public class EmailActivity extends AppCompatActivity {

    String spotChecksTypeEmail = "Latest Spot Checks";
    String spots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        spots = getIntent().getStringExtra("spotsToSend");
    }

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
        if (emailIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(emailIntent, 0);
        } else {
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
        } else {
            Toast.makeText(this, "The email was not sent", Toast.LENGTH_LONG).show();
        }
    }
}