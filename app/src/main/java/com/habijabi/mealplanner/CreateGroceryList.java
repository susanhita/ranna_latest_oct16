package com.habijabi.mealplanner;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CreateGroceryList extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_grocery_list);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String list = intent.getStringExtra("list");
        TextView textList = (TextView) findViewById(R.id.groc_list);
        list = list.replaceAll("_", " ");
        textList.setText(list);
        new UpdateListDB().execute(list);
    }


    private class UpdateListDB extends AsyncTask<String, Void, Boolean> {
        protected void onPreExecute() {
        }

        protected Boolean doInBackground(String... list1) {
            String list=list1[0];
            SQLiteOpenHelper recipedb = new RecipeDatabase(CreateGroceryList.this);
            SQLiteDatabase db = recipedb.getWritableDatabase();
            db.execSQL("UPDATE RECIPE SET GROCERY_LIST ='" + list + "'  WHERE name='pancake'");
           // db.execSQL("UPDATE RECIPE SET GROCERY_LIST = 'bullshit'  WHERE name='pancake'");
            return true;
        }

        protected void onPostExecute(Boolean success) {
            TextView textView = new TextView(CreateGroceryList.this);
            textView.setText("yupppppp success");

        }
    }
}