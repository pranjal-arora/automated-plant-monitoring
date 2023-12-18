package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class register extends AppCompatActivity {

    TextView reg;
    TextInputLayout ti1,ti2,ti3,ti4;
    EditText ed1,ed2,ed3,ed4;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        reg = findViewById(R.id.txreg);
        ti1 = findViewById(R.id.usename);
        ed1 = ti1.getEditText();
        ti2 = findViewById(R.id.email);
        ed2 = ti2.getEditText();
        ti3 = findViewById(R.id.mobile);
        ed3 = ti3.getEditText();
        ti4 = findViewById(R.id.password);
        ed4 = ti4.getEditText();

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = ed1.getText().toString();
                String email = ed2.getText().toString();
                String mobile = ed3.getText().toString();
                String password = ed4.getText().toString();
                Pattern pattern = Patterns.EMAIL_ADDRESS;

                if(username.equals("") || email.equals("")|| mobile.equals("")|| password.equals("")){
//                    Toast.makeText(register.this, "Please fill details", Toast.LENGTH_SHORT).show();
                    Snackbar.make(v, "Please fill details", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
                else if(!pattern.matcher(email).matches()){
//                    Toast.makeText(register.this, "Please enter valid email", Toast.LENGTH_SHORT).show();
                    Snackbar.make(v, "Please enter valid email", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
                else if(mobile.length() != 10){
//                    Toast.makeText(register.this, "Please enter valid mobile number", Toast.LENGTH_SHORT).show();
                    Snackbar.make(v, "Please enter valid mobile number", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
                else {

                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    String url = UrlLinks.pyregister;

                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);

                    nameValuePairs.add(new BasicNameValuePair("username", username));
                    nameValuePairs.add(new BasicNameValuePair("password", password));
                    nameValuePairs.add(new BasicNameValuePair("emailid", email));
                    nameValuePairs.add(new BasicNameValuePair("mobilenumber", mobile));

                    String result = null;
                    try {
                        result = jSOnClassforData.forCallingStringAndreturnSTring(url,nameValuePairs);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if (result.equals("success")) {

//                        Toast.makeText(register.this, "User Added successfully", Toast.LENGTH_SHORT).show();
                        Snackbar.make(v, "User Added successfully", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                        Intent io = new Intent(register.this, login.class);

                        startActivity(io);
                        finish();

                    } else {

//                        Toast.makeText(register.this, "Something went wrong !", Toast.LENGTH_SHORT).show();
                        Snackbar.make(v, "Something went wrong !", Snackbar.LENGTH_LONG).setAction("Action", null).show();

                    }
                }

            }
        });

    }
}