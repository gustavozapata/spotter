package me.gustavozapata.spotter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class EmailActivity extends AppCompatActivity {

    String spotChecksEmail = "Latest Emails";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
    }

    // TODO: email all previous spot checks. Email latest spot check only - do not send the ones already sent, only the ones that haven't been sent
    public void composeAndSend(View view) {
        String[] TO = {"tavordie@hotmail.com"}; //k1715308@kingston.ac.uk
        Uri mailUri = Uri.parse("mailto:");
        String subject = spotChecksEmail;
        String body = "This is my wonderful test email.\n\n";
        body += spotChecksEmail + ".\n\n";
        body += "I could click the button many times and send lots of them.";

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, mailUri);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, body);
        if(emailIntent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(emailIntent, 0);
        } else {
            Toast.makeText(this, "No email app installed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override //run when activity intent has concluded (finished)
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null){
            Toast.makeText(this, "Email sent!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "The email was not sent", Toast.LENGTH_LONG).show();
        }
    }

    public void selectEmailOption(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.latestEmail:
                if (checked)
                    spotChecksEmail = "Latest Emails";
                    break;
            case R.id.allEmail:
                if (checked)
                    spotChecksEmail = "All Emails";
                    break;
        }
    }
}