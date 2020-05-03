package com.xyzTechnologies.helloworldapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {
    Button btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        System.out.println("Main2Activity:On create method called");
        Intent prev_intent = getIntent();
        String name = prev_intent.getStringExtra("name");
        int age = prev_intent.getIntExtra("age",10);
        Toast.makeText(this, name+" - "+age, Toast.LENGTH_SHORT).show();

        btn2 = findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Main2Activity.this,Main3Activity.class);
                startActivity(i);
                //Intent i = new Intent(Main2Activity.this,MyService.class);
                //startService(i);
                //useFragment(new SecondFragment());
            }
        });
        BlankFragment blankFragment = new BlankFragment();
        //useFragment(blankFragment);
    }

//  creating fragments
    public void useFragment(Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.containr_frag,fragment);
        transaction.commit();
        transaction.addToBackStack(null);
    }


    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("Main2Activity:On Start method called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("Main2Activity:On Resume method called");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        System.out.println("Main2Activity:On restart method called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("Main2Activity:On pause method called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("Main2Activity:On Stop method called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("Main2Activity:On destroy method called");
    }

    public void open(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure, You wanted to make decision");
        alertDialogBuilder.setTitle("Alert!");
        alertDialogBuilder.setIcon(R.mipmap.ic_launcher_round);
        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Toast.makeText(Main2Activity.this,
                                "You clicked yes ,button", Toast.LENGTH_LONG).show();
                        finish();
                    }
                });

        alertDialogBuilder.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        open();
    }
}
//https://android.jlelse.eu/android-activity-launch-mode-e0df1aa72242