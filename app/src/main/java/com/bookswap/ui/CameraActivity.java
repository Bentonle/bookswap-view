package com.bookswap.ui;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.bookswap.R;
import com.google.zxing.Result;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import okhttp3.OkHttpClient;

import static android.Manifest.permission.CAMERA;

public class CameraActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView scannerView;
    private static int camId = Camera.CameraInfo.CAMERA_FACING_BACK;

    private static HttpURLConnection con;
    private String isbn_scanned;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        int currentApiVersion = Build.VERSION.SDK_INT;
        if(currentApiVersion >=  Build.VERSION_CODES.M)
        {
            if(checkPermission())
                Toast.makeText(getApplicationContext(), "Permission already granted!", Toast.LENGTH_LONG).show();
            else
                requestPermission();
        }
    }

    private boolean checkPermission() { return (ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA) == PackageManager.PERMISSION_GRANTED); }
    private void requestPermission() { ActivityCompat.requestPermissions(this, new String[]{CAMERA}, REQUEST_CAMERA); }

    @Override
    public void onResume() {
        super.onResume();

        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.M) {
            if (checkPermission()) {
                if(scannerView == null) {
                    scannerView = new ZXingScannerView(this);
                    setContentView(scannerView);
                }
                scannerView.setResultHandler(this);
                scannerView.startCamera();
            } else {
                requestPermission();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        scannerView.stopCamera();
    }

    /**
     * This function will handle the results to when the camera permissions are requested.
     * If user declines permission usage, the application will back out and display a message.
     * If user accepts permission usage, the camera will be displayed for barcode scanning.
     *
     * @param requestCode // Sends the requested function.
     * @param permissions // A string of permissions to be asked.
     * @param grantResults // Stores the permission accepted results.
     * @return Exits function.
     */
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if(requestCode == REQUEST_CAMERA)
            if (grantResults.length > 0) {

                boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                if (cameraAccepted)
                    Toast.makeText(getApplicationContext(), "Permission Granted, Now you can access camera", Toast.LENGTH_LONG).show();
                else {
                    Toast.makeText(getApplicationContext(), "Permission Denied, You cannot access and camera", Toast.LENGTH_LONG).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(CAMERA)) {
                            showMessageOKCancel("You need to allow access to both the permissions", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                                        requestPermissions(new String[]{CAMERA}, REQUEST_CAMERA);
                                }
                            });
                            return;
                        }
                    }
                }
            }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new android.support.v7.app.AlertDialog.Builder(CameraActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void handleResult(Result result) {
        final String myResult = result.getText();
        Log.d("QRCodeScanner", result.getText());
        Log.d("QRCodeScanner", result.getBarcodeFormat().toString());

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Scan Result");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //scannerView.resumeCameraPreview(CameraActivity.this);
                scannerView.stopCamera();
            }
        });
        builder.setNeutralButton("Visit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(myResult));
                startActivity(browserIntent);
            }
        });

        isbn_scanned = result.getText();

        try {
            JavaRequest();
        } catch (IOException e) {
            e.printStackTrace();
        }


        builder.setMessage(result.getText());
        AlertDialog alert1 = builder.create();
        alert1.show();
    }
    public void JavaRequest() throws MalformedURLException, ProtocolException, IOException{

        String url = "https://api2.isbndb.com/book/" + isbn_scanned;

        try {
            URL myurl = new URL(url);
            con = (HttpURLConnection) myurl.openConnection();
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Authorization", "42512_8f862da499948578cb7c8c7ce3e0c12a");
            con.setRequestMethod("GET");

            con.connect();

            StringBuilder content;
            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {

                String line;
                content = new StringBuilder();

                Bundle results = new Bundle();
                while ((line = in.readLine()) != null) {

                    if (line.contains("\"")) {
                        int splitString, firstIndex = line.indexOf("\"");
                        String cleanedString = "", stringTitle = "", stringValue = "";

                        // Removes whitespaces at the start of the string.
                        cleanedString = line.substring(firstIndex);

                        // Obtains the index that splits 'title' from 'value'.
                        splitString = cleanedString.indexOf(":");

                        // Splits the title off.
                        stringTitle = cleanedString.substring(0, splitString);
                        // Splits the value off.
                        stringValue = cleanedString.substring(splitString + 1, cleanedString.length() - 1);


                        content.append(stringValue + "\n");
                        if(stringTitle == "publisher") {
                            results.putString("publisher", stringValue);
                            toastMessage("This is:" + stringValue);
                        }
                        else if(stringTitle == "\"title\"")
                            results.putString("title", stringValue);


                    }
                }


                toastMessage(content.toString());
                //Intent intentResults = new Intent();
                //intentResults.putExtras(results);
                //setResult(RESULT_OK, intentResults);
                //finish();
            }
        } finally {
            con.disconnect();
        }

    }

    private void toastMessage(String message){ Toast.makeText(this, message, Toast.LENGTH_LONG).show(); }
}