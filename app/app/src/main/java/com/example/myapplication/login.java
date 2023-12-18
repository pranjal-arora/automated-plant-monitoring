package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class login extends AppCompatActivity {
    TextView txreg,login,adminlog,driverlog;
    TextInputLayout ti1,ti2;
    EditText ed1,ed2;
    public static String usersession;
    private boolean READ_PHONE_STATE_granted = false;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        int PERMISSION_ALL = 1;
        if (Build.VERSION.SDK_INT >= 23) {
            String[] PERMISSIONS = {
                    android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
            };

            if (!hasPermissions(this, PERMISSIONS)) {
                ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
                READ_PHONE_STATE_granted = true;
            }

        }

        txreg = findViewById(R.id.newreg);

        login = findViewById(R.id.log);
        ti1 = findViewById(R.id.username1);
        ed1 = ti1.getEditText();
        ti2 = findViewById(R.id.password1);
        ed2 = ti2.getEditText();

        txreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(com.example.myapplication.login.this,register.class));
            }
        });



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = ed1.getText().toString();
                String password = ed2.getText().toString();

                if(username.equals("") || password.equals("")){
//                    Toast.makeText(login.this, "Please fill details.", Toast.LENGTH_SHORT).show();

                    Snackbar.make(v, "Please fill details.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
                else {

                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    String url = UrlLinks.pylogin;

                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);

                    nameValuePairs.add(new BasicNameValuePair("username", username));
                    nameValuePairs.add(new BasicNameValuePair("password", password));

                    String result = null;
                    try {
                        result = jSOnClassforData.forCallingStringAndreturnSTring(url,nameValuePairs);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if (result.equals("success")) {
                        usersession=username;
//
                        Snackbar.make(v, "Successfully", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                        Intent io = new Intent(com.example.myapplication.login.this, MainActivity.class);
                        startActivity(io);
                        finish();

                    } else {

//                        Toast.makeText(login.this, "Wrong username or password", Toast.LENGTH_SHORT).show();
                        Snackbar.make(v, "Wrong username or password", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    }
                }
            }
        });
    }
    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
}