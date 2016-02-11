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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;
public class RandomReceipe extends Activity {



            public static final String EXTRA_RECIPENO = "drinkNo";

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_random_receipe);
                ActionBar actionBar=getActionBar();
                int id,max,min;
                max=3;
                min=1;

                Random rand = new Random();
                id = 1 + rand.nextInt((max - min) + 1);

                Intent intent = new Intent(this, RecipeActivity.class);
                intent.putExtra(RecipeActivity.EXTRA_RECIPENO, (int) id);
                startActivity(intent);


            }



}
