package me.gustavozapata.spotter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EmailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
    }

    public void visitURL(View view) {
        EditText enterURL = findViewById(R.id.enterURLEditText);
        String url = enterURL.getText().toString();
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        } else {
            Toast.makeText(this, "What in the name of sweet Nelly Furtado is going on?", Toast.LENGTH_SHORT).show();
        }
    }

    // TODO: email all previous spot checks. Email latest spot check only - do not send the ones already sent, only the ones that haven't been sent
    public void composeAndSend(View view) {
        String[] TO = {"tavordie@hotmail.com"};
        Uri mailUri = Uri.parse("mailto:");
        String subject = "This is a composed email";
        String body = "This is my wonderful test email.\n\n";
        body += "This is not spam at all, but very very useful.\n\n";
        body += "I could click the button many times and send lots of them.";

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, mailUri);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, body);
        if(emailIntent.resolveActivity(getPackageManager()) != null){
            startActivity(emailIntent);
        } else {
            Toast.makeText(this, "No email app installed", Toast.LENGTH_SHORT).show();
        }
    }
}