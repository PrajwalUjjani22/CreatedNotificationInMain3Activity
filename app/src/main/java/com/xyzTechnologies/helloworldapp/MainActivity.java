package com.xyzTechnologies.helloworldapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission_group.CAMERA;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class MainActivity extends AppCompatActivity {

    //1.Declare object variable of specified class type
    Button button;
    TextView textView, textView1;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("MainActivity:On create method called");
        System.out.println("BUILD VERSION:" + Build.VERSION.SDK_INT);
        if (!checkPermission()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                requestPermissions(permission, permsRequestCode);
            }
        }

        //2. Initialize your variable
        button = findViewById(R.id.btn1);
        textView = findViewById(R.id.text1);
        textView1 = new TextView(this);
        linearLayout = findViewById(R.id.linearLayout);

        //3.Implementation
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("Hi Prajwal Welcome");
                textView1.setText("Dynamic text view");
                if (linearLayout.getParent() == null)
                    linearLayout.addView(textView1);
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("name", "prajwal");
                intent.putExtra("age", 22);
                startActivity(intent);
                finish();
            }
        });
    }

    /*
    FLAG_ACTIVITY_NEW_TASK
    This flag works similar to “launchMode = singleTask”.
    FLAG_ACTIVITY_CLEAR_TASK
    This flag will cause any existing task that would be associated with the activity to be cleared before the activity is started. The activity becomes the new root of an otherwise empty task, and any old activities are finished.
    FLAG_ACTIVITY_SINGLE_TOP
    This flag works similar to “launchMode = singleTop”.
    FLAG_ACTIVITY_CLEAR_TOP*/
    public int addition(int a, int b) {
        return a + b;
    }

    public void sms(View view) {
        Intent sendIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:15555215556"));
        //Intent sendIntent = new Intent();
        //sendIntent.setAction(Intent.ACTION_SEND);
        //sendIntent.putExtra(Intent.EXTRA_TEXT,"Hello");
        sendIntent.putExtra("sms_body", "The SMS text");
        //sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }


    String[] permission = {"android.permission.READ_EXTERNAL_STORAGE", "android.permission.ACCESS_COARSE_LOCATION", "android.permission.CAMERA"};

    int permsRequestCode = 200;


    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_COARSE_LOCATION);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        int result2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);

        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED
                && result2 == PackageManager.PERMISSION_GRANTED;
    }


    private void requestPermission(String[] permission, int permsRequestCode) {

        ActivityCompat.requestPermissions(this, permission, permsRequestCode);

    }


    @Override
    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions, int[] grantResults) {

        switch (permsRequestCode) {

            case 200:

                boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                boolean storage = grantResults[2] == PackageManager.PERMISSION_GRANTED;

                break;

        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("MainActivity:On Start method called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("MainActivity:On Resume method called");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        System.out.println("MainActivity:On restart method called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("MainActivity:On pause method called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("MainActivity:On Stop method called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("MainActivity:On destroy method called");
    }
}



