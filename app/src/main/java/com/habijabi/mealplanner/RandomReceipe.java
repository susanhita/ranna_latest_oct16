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
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;
public class RandomReceipe extends Activity {
    int max;


    public static final String EXTRA_RECIPENO = "drinkNo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_receipe);
        ActionBar actionBar = getActionBar();
        new RandomDB().execute();

    }

    private class RandomDB extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            System.gc();
        }

        protected String doInBackground(String...x) {

            SQLiteOpenHelper recipedb = new RecipeDatabase(RandomReceipe.this);
            SQLiteDatabase db = recipedb.getWritableDatabase();
            try {
                Cursor cursor = db.rawQuery("Select count(*) from RECIPE ", null);
                cursor.moveToFirst();
                String row_number = cursor.getString(0);
                return row_number;
            } catch (SQLiteException e) {
                return "Database problem";
            }
        }

        protected void onPostExecute(String row_number) {
            Log.v("XXXXXXXXXXXXXXXXX", row_number);
            int max = Integer.parseInt(row_number);
            int min = 1;
            Random rand = new Random();
            int id = 1 + rand.nextInt((max - min) + 1);
            Log.v("rowwwwwwwwww", String.format("%s",id));


            Intent intent = new Intent(RandomReceipe.this, RecipeActivity.class);
            intent.putExtra(RecipeActivity.EXTRA_RECIPENO, (int) id);
            startActivity(intent);
            finish();

        }
    }

}