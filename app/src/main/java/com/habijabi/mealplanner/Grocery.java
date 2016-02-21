package com.habijabi.mealplanner;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.provider.CalendarContract;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Grocery extends Activity {
    public static Boolean grocery=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery);
        ActionBar actionBar=getActionBar();
    }

    public void Clicktocalender(View view){
        EditText EstrTitle=(EditText)findViewById(R.id.title1);
        String strTitle= String.valueOf(EstrTitle.getText());
        EditText Edesciption=(EditText)findViewById(R.id.description1);
        String strDescription=Edesciption.getText().toString();

        Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra(CalendarContract.Events.TITLE, strTitle);
        intent.putExtra(CalendarContract.Events.DESCRIPTION, strDescription);
        intent.putExtra(CalendarContract.Events.ALL_DAY, false);// periodicity
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
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
