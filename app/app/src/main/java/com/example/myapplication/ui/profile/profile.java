package com.example.myapplication.ui.profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.UrlLinks;
import com.example.myapplication.jSOnClassforData;
import com.example.myapplication.login;
import com.google.android.material.textfield.TextInputLayout;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


public class profile extends Fragment {

    TextInputLayout ti1,ti2,ti3,ti4;
    EditText ed1,ed2,ed3,ed4;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_profile, container, false);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        ti1 = root.findViewById(R.id.usename);
        ed1 = ti1.getEditText();
        ti2 = root.findViewById(R.id.email);
        ed2 = ti2.getEditText();
        ti3 = root.findViewById(R.id.mobile);
        ed3 = ti3.getEditText();
        ti4 = root.findViewById(R.id.password);
        ed4 = ti4.getEditText();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String url = UrlLinks.profile;
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
        nameValuePairs.add(new BasicNameValuePair("Username", login.usersession));

        String result = null;
        try {
            result = jSOnClassforData.forCallingStringAndreturnSTring(url, nameValuePairs);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            JSONArray jsonArray = new JSONArray(result);
            for (int i = 0; i < jsonArray.length(); i++) {
                String usename = String.valueOf(jsonArray.getJSONArray(i).getString(1));
                String email = String.valueOf(jsonArray.getJSONArray(i).getString(2));
                String mobile = String.valueOf(jsonArray.getJSONArray(i).getString(3));
                String password = String.valueOf(jsonArray.getJSONArray(i).getString(4));




                ed1.setText(usename+" ");
                ed2.setText(email+" ");
                ed3.setText(mobile+" ");
                ed4.setText(password+" ");





            }
        } catch (JSONException e) {
            e.printStackTrace();



        }




        return root;

    }
}