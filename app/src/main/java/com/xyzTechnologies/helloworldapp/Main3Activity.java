package com.xyzTechnologies.helloworldapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.atomic.AtomicInteger;

public class Main3Activity extends AppCompatActivity {

    private static final String TAG =" Main3Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        textView = (TextView) findViewById(R.id.status);

        createNotificationChannel();
       // LongOperation lo = new LongOperation(this);
       // lo.execute("Test 1", "Test 2", "Test 3");
        sendNotification("Notification",123);


    }

    //First step we need to register for creating notificatio
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = CHANNEL_ID;
            String description = "Create channel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private TextView textView;
    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                String string = bundle.getString(DownloadService.FILEPATH);
                int resultCode = bundle.getInt(DownloadService.RESULT);
                if (resultCode == RESULT_OK) {
                    Toast.makeText(Main3Activity.this,
                            "Download complete. Download URI: " + string,
                            Toast.LENGTH_LONG).show();
                    textView.setText("Download done");
                } else {
                    Toast.makeText(Main3Activity.this, "Download failed",
                            Toast.LENGTH_LONG).show();
                    textView.setText("Download failed");

                    open();

                }
            }
        }
    };



    @Override
    protected void onResume() {
        super.onResume();
        //register dynamically or through manifest.xml create tag <receiver><receiver/>
        registerReceiver(receiver, new IntentFilter(
                DownloadService.NOTIFICATION));
    }
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    public void onClick(View view) {

        Intent intent = new Intent(this, DownloadService.class);
        // add infos for the service which file to download and where to store
        intent.putExtra(DownloadService.FILENAME, "index.html");
        intent.putExtra(DownloadService.URL,
                "https://www.vogella.com/index.html");
        startService(intent);
        textView.setText("Service started");
    }


    public void open(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure, You wanted to make decision");
                alertDialogBuilder.setPositiveButton("yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                Toast.makeText(Main3Activity.this,
                                        "You clicked yes ,button",Toast.LENGTH_LONG).show();
                                Intent serv_intent = new Intent(Main3Activity.this, DownloadService.class);
                                // add infos for the service which file to download and where to store
                                serv_intent.putExtra(DownloadService.FILENAME, "index.html");
                                serv_intent.putExtra(DownloadService.URL,
                                        "https://www.vogella.com/index.html");
                                startService(serv_intent);
                                textView.setText("Service started");
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
    private static final String CHANNEL_ID = "CHANNEL_ID";
    public void sendNotification(String title, int notificationId) {

        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(Main3Activity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(Main3Activity.this, 0, intent, 0);

        //Intent snoozeIntent = new Intent(Main3Activity.this, MainActivity.class);
        //snoozeIntent.setAction(ACTION_SNOOZE);
        //snoozeIntent.putExtra(EXTRA_NOTIFICATION_ID, notificationId);

        //Log.e(TAG, snoozeIntent.getExtras().toString());

        //Log.e(TAG, "snoozeIntent id: " + snoozeIntent.getIntExtra(EXTRA_NOTIFICATION_ID, -1));

        /*PendingIntent snoozePendingIntent =
                PendingIntent.getBroadcast(Main3Activity.this, notificationId, snoozeIntent, 0);
*/
        NotificationCompat.Builder builder = new NotificationCompat.Builder(Main3Activity.this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(String.format("%s (id %d)", title, notificationId))
                .setContentText("Much longer text that cannot fit one line...")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(Main3Activity.this);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(notificationId, builder.build());
    }

    private class LongOperation extends AsyncTask<String, String, String> {

        private static final String TAG = "longoperation";
        private Context ctx;
        private AtomicInteger notificationId = new AtomicInteger(0);

        LongOperation(Context ctx) {
            this.ctx = ctx;
        }

        @Override
        protected String doInBackground(String... params) {
            for (String s : params) {
                Log.e(TAG, s);

                publishProgress(s);

                for (int i = 0; i < 5; i++) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        Thread.interrupted();
                    }
                }
            }
            return "Executed";
        }

        @Override
        protected void onProgressUpdate(String... values) {
            for (String title: values) {
                sendNotification(title, notificationId.incrementAndGet());
            }
        }

        void sendNotification(String title, int notificationId) {

            // Create an explicit intent for an Activity in your app
         Intent intent = new Intent(ctx, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(ctx, 0, intent, 0);

           // Intent snoozeIntent = new Intent(ctx, MyBroadcastReceiver.class);
            //snoozeIntent.setAction(ACTION_SNOOZE);
            //snoozeIntent.putExtra(EXTRA_NOTIFICATION_ID, notificationId);

            //Log.e(TAG, snoozeIntent.getExtras().toString());

            //Log.e(TAG, "snoozeIntent id: " + snoozeIntent.getIntExtra(EXTRA_NOTIFICATION_ID, -1));

//            PendingIntent snoozePendingIntent =
//                    PendingIntent.getBroadcast(ctx, notificationId, snoozeIntent, 0);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(ctx, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentTitle(String.format("%s (id %d)", title, notificationId))
                    .setContentText("Much longer text that cannot fit one line...")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(false)
                    .setContentIntent(pendingIntent);


            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(ctx);

            // notificationId is a unique int for each notification that you must define
            notificationManager.notify(notificationId, builder.build());
        }
    }

}
