package com.habijabi.mealplanner;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class RecipeActivity extends AppCompatActivity {
    public static final String EXTRA_RECIPENO = "drinkNo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        //get the drink from inntent
        Intent intent = getIntent();
        int drinkNo = (Integer) getIntent().getExtras().get(EXTRA_RECIPENO);
        Toast toast = Toast.makeText(RecipeActivity.this,"recipe"+drinkNo, Toast.LENGTH_SHORT);
        toast.show();

        new UpdateRecipeClass().execute(drinkNo);

    }

    private class UpdateRecipeClass extends AsyncTask<Integer,Void,Boolean> {
        String desctext,nametext,resourceid;
        protected void onPreExecute(){}
        protected Boolean doInBackground(Integer...drinks) {
            int drinkNo=drinks[0];
            SQLiteOpenHelper starbuzzdb = new RecipeDatabase(RecipeActivity.this);
            try {
                SQLiteDatabase db = starbuzzdb.getWritableDatabase();

                Cursor cursor = db.query("RECIPE", new String[]{"NAME", "DESCRIPTION", "IMAGE_RESOURCE_ID"}, "_id = ?", new String[]{Integer.toString(drinkNo)}, null, null, null);
                if (cursor.moveToFirst()) {
                    nametext = cursor.getString(0);
                    desctext = cursor.getString(1);
                    resourceid = cursor.getString(2);
                }
                cursor.close();
                db.close();
                return true;
            }
            catch (SQLiteException e) {
                return false;
            }
        }
        protected void onPostExecute(Boolean success){
            if (!success){
                Toast toast = Toast.makeText(RecipeActivity.this,"this database in unavailable", Toast.LENGTH_SHORT);
                toast.show();
            }
            //render
            TextView name = (TextView) findViewById(R.id.name);
            name.setText(nametext+"\n");

            TextView description = (TextView) findViewById(R.id.description);
            description.setText(desctext+"\n");

//            description.setText(Uri.parse(resourceid).toString());
            ImageView photo = (ImageView) findViewById(R.id.photo);
            photo.setImageURI(Uri.parse(resourceid));

            photo.setContentDescription(nametext);


        }
    }



}
