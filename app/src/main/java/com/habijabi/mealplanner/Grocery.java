package com.habijabi.mealplanner;

import android.content.Intent;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Grocery extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery);

    }

    public void Clicktocalender(View view){
        EditText EstrTitle=(EditText)findViewById(R.id.title1);
        String strTitle= String.valueOf(EstrTitle.getText());
        EditText Edesciption=(EditText)findViewById(R.id.description1);
        String strDescription=Edesciption.getText().toString();
        EditText estartTime=(EditText)findViewById(R.id.start_time);
        String startDateMillis=estartTime.getText().toString();
        EditText EdndDateMills=(EditText)findViewById(R.id.end_time);
        String endDateMillis=estartTime.getText().toString();

       // Intent intent=new Intent(Intent.ACTION_INSERT);
        //intent.putExtra(CalendarContract.Events.TITLE, strTitle);
       // intent.putExtra(CalendarContract.Events.DESCRIPTION,strDescription);

        Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra(CalendarContract.Events.TITLE, strTitle);
        intent.putExtra(CalendarContract.Events.DESCRIPTION,strDescription);
        intent.putExtra(CalendarContract.Events.ALL_DAY, false);// periodicity
        startActivity(intent);
    }


}