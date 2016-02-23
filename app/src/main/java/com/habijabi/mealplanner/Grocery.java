package com.habijabi.mealplanner;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.provider.CalendarContract;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.EditText;

import java.util.Calendar;

public class Grocery extends Activity {
    public static Boolean grocery=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery);
        ActionBar actionBar=getActionBar();
    }

    public void Clicktocalender(View view) {
        EditText EstrTitle = (EditText) findViewById(R.id.title1);
        String strTitle = String.valueOf(EstrTitle.getText());
        EditText Edesciption = (EditText) findViewById(R.id.description1);
        String strDescription = Edesciption.getText().toString();








        Intent intent = new Intent(this, NotificationGrocery.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(ViewGroceryList.class);
        stackBuilder.addNextIntent(intent);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,intent, 0);


        long futureInMillis = SystemClock.elapsedRealtime() + 15000;
        AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarm.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);


    }





    public void sel_ingredients(View view){
        Intent intent=new Intent(this,SelIngredients.class);
        grocery=true;
        startActivity(intent);
    }

    public void view_list(View view)
    {
        Intent intent=new Intent(this,ViewGroceryList.class);
        startActivity(intent);
    }
}
