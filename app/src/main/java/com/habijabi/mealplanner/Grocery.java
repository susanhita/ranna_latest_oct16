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
import android.graphics.Typeface;
import android.os.SystemClock;
import android.provider.CalendarContract;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Calendar;

public class Grocery extends Activity {
    public static Boolean grocery=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery);
        ActionBar actionBar=getActionBar();
        actionBar.setTitle("Grocery List");
        actionBar.setDisplayShowHomeEnabled(true);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/newfont.TTF");
        Button select_ingredients=(Button)findViewById(R.id.select_ingredients);
        Button view_list=(Button)findViewById(R.id.view_list);

        select_ingredients.setTypeface(custom_font);
        view_list.setTypeface(custom_font);


    }



    public void sel_ingredients(View view){
        Intent intent=new Intent(this,SelIngredients.class);
        grocery=true;
        startActivity(intent);
        finish();
    }

    public void view_list(View view)
    {
        Intent intent=new Intent(this,ViewGroceryList.class);
        startActivity(intent);
    }
}
