package com.example.myapplication.ui.slideshow;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.UrlLinks;
import com.example.myapplication.databinding.FragmentSlideshowBinding;
import com.example.myapplication.jSOnClassforData;
import com.example.myapplication.login;
import com.google.android.material.textfield.TextInputLayout;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SlideshowFragment extends Fragment {


    TextInputLayout ti1,ti2,ti3,ti4;
    EditText ed1,ed2,ed3,ed4;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SlideshowViewModel slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

        View root = FragmentSlideshowBinding.inflate(inflater, container, false).getRoot();
        ti1 = root.findViewById(R.id.usename);
        ed1 = ti1.getEditText();
        ti2 = root.findViewById(R.id.email);
        ed2 = ti2.getEditText();
        ti3 = root.findViewById(R.id.mobile);
        ed3 = ti3.getEditText();


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String url = UrlLinks.getgetdata;
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);


        String result = null;
        try {
            result = jSOnClassforData.forCallingStringAndreturnSTring(url, nameValuePairs);

            System.out.println("result");
            System.out.println(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            JSONObject jsonObject = new JSONObject(result);

            // Access the "values" array from the JSON object
            JSONArray valuesArray = jsonObject.getJSONArray("values");

            // Assuming the array has four elements
            double value1 = valuesArray.getDouble(0);
            double value2 = valuesArray.getDouble(1);
            double value3 = valuesArray.getDouble(2);


            if (value1 < 30) {
                // Display a pop-up message
                showAlertDialog("Message Title", "Please water the plant!");
            }



            // Display the values in your EditText fields
            ed1.setText(String.valueOf(value1));
            ed2.setText(String.valueOf(value2));
            ed3.setText(String.valueOf(value3));


        } catch (JSONException e) {
            e.printStackTrace(); // Handle exception appropriately
        }






        return root;
    }

    private void showAlertDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Set the dialog title and message
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        dialog.dismiss();
                    }
                });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


}