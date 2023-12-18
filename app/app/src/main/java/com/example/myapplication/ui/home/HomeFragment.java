package com.example.myapplication.ui.home;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.UrlLinks;
import com.example.myapplication.databinding.FragmentHomeBinding;
import com.example.myapplication.jSOnClassforData;
import com.google.android.material.snackbar.Snackbar;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment {
    TextView upload,data,data1;

    static String fileNameOfImage = "";
    public static String result = "";
    public static boolean StopVal = true;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final int REQUEST_GALLERY = 200;
    String file_path = null;
    ImageView imageView;

    private final int CAMERA_REQUEST = 123;
    private final int STORAGE_REQUEST = 124;
    private final String[] cameraPermission = {Manifest.permission.CAMERA};

    private String[] storagePermission = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        View root  = FragmentHomeBinding.inflate(inflater, container, false).getRoot();
        upload = root.findViewById(R.id.upload);
        data = root.findViewById(R.id.data);
        data1 = root.findViewById(R.id.data1);
        imageView = root.findViewById(R.id.imageView2);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImagePicDialog();
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView img = root.findViewById(R.id.imageView2);
                Bitmap bitmap;
                bitmap = ((BitmapDrawable) img.getDrawable()).getBitmap();
                SaveImage(bitmap);

                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                String url = UrlLinks.getResult;

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);

                nameValuePairs.add(new BasicNameValuePair("imagename", fileNameOfImage));

                final ProgressDialog dialog= ProgressDialog.show(getActivity(),"Doing something", "Please wait....",true);
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {

                            try {
                                result = jSOnClassforData.forCallingStringAndreturnSTring(url,nameValuePairs);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            data.post(new Runnable() {
                                public void run() {
                                    try {
                                        JSONArray jsonArray = new JSONArray(result);
                                        String firstElement = jsonArray.getString(1);
                                        System.out.println(firstElement);
                                        data.setText(firstElement);

                                        JSONArray jsonArray1 = new JSONArray(jsonArray.getString(0));
                                        StringBuilder stringBuilder = new StringBuilder();
                                        for (int i = 0; i < jsonArray1.length(); i++) {
                                            stringBuilder.append(jsonArray1.getString(i));
                                            if (i < jsonArray1.length() - 1) {
                                                stringBuilder.append("\n");
                                            }
                                        }
                                        data1.setText(stringBuilder.toString());
                                    } catch (JSONException e) {
                                        e.printStackTrace(); // Handle exception appropriately
                                    }
                                }
                            });

                            Thread.sleep(5000);
                            dialog.dismiss();
                        }
                        catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();

//                Snackbar.make(getActivity().findViewById(android.R.id.content),  result, Snackbar.LENGTH_LONG).setAction("Action", null).show();

            }
        });

        return root;
    }

    private void showImagePicDialog() {
        String options[] = {"Camera", "Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Pick Image From");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    if (!checkCameraPermission()) {
                        requestCameraPermission();
                    } else {
                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST);
                    }
                } else if (which == 1) {
                    requestStoragePermission();
                    filePicker();
                }
            }
        });
        builder.create().show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_GALLERY && resultCode == Activity.RESULT_OK) {
            String filePath = getRealPathFromUri(data.getData(), getActivity());
            Log.d("File Path : ", " " + filePath);
            //now we will upload the file
            //lets import okhttp first
            this.file_path = filePath;
            File file = new File(filePath);
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(),bmOptions);
            // bitmap = Bitmap.createScaledBitmap(bitmap,parent.getWidth(),parent.getHeight(),true);
            imageView.setImageBitmap(bitmap);
        }
        else if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap theImage = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(theImage);
        }
    }

    public String getRealPathFromUri(Uri uri, Activity activity){
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor=activity.getContentResolver().query(uri,proj,null,null,null);
        if(cursor==null){
            return uri.getPath();
        }
        else{
            cursor.moveToFirst();
            int id=cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(id);
        }
    }

    private void SaveImage(Bitmap finalBitmap) {
        String username="a";
        String timeStamp = new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date());
        String fname = username+"-"+timeStamp+".jpg";
        ContextWrapper cw = new ContextWrapper(getActivity());
        File directory = cw.getDir("OTAPP", Context.MODE_PRIVATE);
        if (!directory.exists ()){
            directory.mkdirs();

        }
        File file = new File(directory, fname);

        if (file.exists ()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        fileNameOfImage=fname;
        new UploadFileAsync().execute("");

        Snackbar.make(getActivity().findViewById(android.R.id.content),  "File uploaded.", Snackbar.LENGTH_LONG).setAction("Action", null).show();

    }

    private void filePicker(){

        //.Now Permission Working
//        Toast.makeText(getActivity(), "File Picker Call", Toast.LENGTH_SHORT).show();
        Snackbar.make(getActivity().findViewById(android.R.id.content),  "File Picker Call", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        //Let's Pick File
        Intent opengallery=new Intent(Intent.ACTION_PICK);
        opengallery.setType("image/*");
        startActivityForResult(opengallery,REQUEST_GALLERY);
    }

    private void requestPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)){
//            Toast.makeText(getActivity(), "Please Give Permission to Upload File", Toast.LENGTH_SHORT).show();
            Snackbar.make(getActivity().findViewById(android.R.id.content),  "Please Give Permission to Upload File", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }
        else{
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION_REQUEST_CODE);
        }
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


    private boolean checkPermission(){
        int result= ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(result== PackageManager.PERMISSION_GRANTED){
            return true;
        }
        else{
            return false;
        }
    }

    public class UploadFileAsync extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            try {
                //  File file = new File (Environment.getExternalStorageDirectory(), "OTAPP//"+fileNameOfImage);
                String sourceFileUri = fileNameOfImage;// "/mnt/sdcard/abc.png";


                HttpURLConnection conn = null;
                DataOutputStream dos = null;
                String lineEnd = "\r\n";
                String twoHyphens = "--";
                String boundary = "*****";
                int bytesRead, bytesAvailable, bufferSize;
                byte[] buffer;
                int maxBufferSize = 1 * 1024 * 1024;
                //  File sourceFile = new File(sourceFileUri);
                File sourceFile = new File(Environment.getExternalStorageDirectory(), "OTAPP//" + fileNameOfImage);


                ContextWrapper cw = new ContextWrapper(getActivity());
                File directory = cw.getDir("OTAPP", Context.MODE_PRIVATE);
                File file = new File(directory, fileNameOfImage);
                sourceFile = file;
                sourceFileUri = file.getAbsolutePath();
                if (sourceFile.isFile()) {

                    try {
                        String upLoadServerUri = UrlLinks.uploadfile;

                        // open a URL connection to the Servlet
                        FileInputStream fileInputStream = new FileInputStream(
                                sourceFile);
                        URL url = new URL(upLoadServerUri);

                        // Open a HTTP connection to the URL
                        conn = (HttpURLConnection) url.openConnection();
                        conn.setDoInput(true); // Allow Inputs
                        conn.setDoOutput(true); // Allow Outputs
                        conn.setUseCaches(false); // Don't use a Cached Copy
                        conn.setRequestMethod("POST");
                        conn.setRequestProperty("Connection", "Keep-Alive");
                        conn.setRequestProperty("ENCTYPE",
                                "multipart/form-data");
                        conn.setRequestProperty("Content-Type",
                                "multipart/form-data;boundary=" + boundary);
                        conn.setRequestProperty("bill", sourceFileUri);
                        dos = new DataOutputStream(conn.getOutputStream());

                        dos.writeBytes(twoHyphens + boundary + lineEnd);
                        dos.writeBytes("Content-Disposition: form-data; name=\"bill\";filename=\""
                                + sourceFileUri + "\"" + lineEnd);

                        dos.writeBytes(lineEnd);

                        // create a buffer of maximum size
                        bytesAvailable = fileInputStream.available();

                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        buffer = new byte[bufferSize];

                        // read file and write it into form...
                        bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                        while (bytesRead > 0) {

                            dos.write(buffer, 0, bufferSize);
                            bytesAvailable = fileInputStream.available();
                            bufferSize = Math
                                    .min(bytesAvailable, maxBufferSize);
                            bytesRead = fileInputStream.read(buffer, 0,
                                    bufferSize);

                        }

                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + twoHyphens
                                + lineEnd);
                        int serverResponseCode = conn.getResponseCode();
                        String serverResponseMessage = conn.getResponseMessage();

                        fileInputStream.close();
                        dos.flush();
                        dos.close();

                    } catch (Exception e) {

                        // dialog.dismiss();
                        e.printStackTrace();

                    }

                } // End else block


            } catch (Exception ex) {
                // dialog.dismiss();

                ex.printStackTrace();
            }
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {

        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

    // checking storage permissions
    private Boolean checkStoragePermission() {
        boolean result = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    // Requesting  gallery permission
    private void requestStoragePermission() {
        requestPermissions(storagePermission, STORAGE_REQUEST);
    }

    // checking camera permissions
    private Boolean checkCameraPermission() {
        boolean result = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }

    // Requesting camera permission
    private void requestCameraPermission() {
        requestPermissions(cameraPermission, CAMERA_REQUEST);
    }

}