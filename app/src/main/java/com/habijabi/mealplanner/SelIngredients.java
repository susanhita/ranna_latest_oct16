package com.habijabi.mealplanner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;


public class SelIngredients extends AppCompatActivity {
    public static String[] ingredient = new String[100];
    public static int i,j=0;
    public static String Tot_col="",Tot_val="";
    public static String[] ingtext=new String[100];
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sel_ingredients);
        if (savedInstanceState != null) {
             i=savedInstanceState.getInt("i");
        }
        displayCheckboxes();
    }

    void displayCheckboxes(){
        i=0;
        int j=0;
        SQLiteOpenHelper recipedb = new RecipeDatabase(this);
        SQLiteDatabase db = recipedb.getWritableDatabase();
       // Cursor cursor = db.rawQuery("USE [RecipeDatabase2] SELECT COLUMN_NAME,* FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'RECIPE' AND TABLE_SCHEMA='dbo'",null);
        Cursor cursor=db.rawQuery("PRAGMA table_info(RECIPE)",null);
        cursor.moveToFirst();
        while (cursor.isAfterLast()==false) {
            ingtext[j] = cursor.getString(1);
            j++;
            cursor.moveToNext();
        }
        cursor.close();
        db.close();

        LinearLayout layout = (LinearLayout) findViewById(R.id.layout_ingredient);

        for(int k=4;k<j;k++) {
                CheckBox chkTeamName = new CheckBox(this);
                chkTeamName.setId(k);
                chkTeamName.setText(ingtext[k]);
                layout.addView(chkTeamName);
            }
    }










    public void add_more_done(View view) {
        //This prints the new ingredients
        //clean_all();
        EditText add_ingredient = (EditText) findViewById(R.id.editText);
        String string1 = add_ingredient.getText().toString();
        if (add_ingredient.getText().length()==0) {
            Toast toast = Toast.makeText(this, "Please enter valid text", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        add_ingredient.setText("");
        ingredient[i] = string1;
        i++;
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.frameLayout2);
        layout.setVisibility(View.VISIBLE);
        TextView textView3 = (TextView) findViewById(R.id.textView3);
        String final_string = new String("You added the following ingredients :-");


        for (int l = 0; l < i; l++) {
            final_string = final_string + (String.format("\n %s and l is %d", ingredient[l],i));
        }
        textView3.setText(final_string);
    }









    public void reset_Ingredients(View view) {
      clean_all();
    }
















    public void clean_all()
    {
        for (int l = 0; l <= i; l++) {
            ingredient[l] = "";
        }
        i = 0;
        TextView textView3 = (TextView) findViewById(R.id.textView3);
        textView3.setText("");
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.frameLayout2);
        layout.setVisibility(View.INVISIBLE);
    }





    public void add_ingredients_to_db(View view) {
        //This creates NEW COLUMNS af ingredients
        new AddRecipeClass().execute();
        //displayCheckboxes();
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.frameLayout2);
        layout.setVisibility(View.INVISIBLE);
    }
    private class AddRecipeClass extends AsyncTask<Integer, Void, Boolean> {
        String desctext, nametext;
        Integer resourceid;

        protected void onPreExecute() {
        }

        protected Boolean doInBackground(Integer... drinks) {
            SQLiteOpenHelper starbuzzdb1 = new RecipeDatabase(SelIngredients.this);
            try {
                SQLiteDatabase db = starbuzzdb1.getWritableDatabase();
                for (int l = 0; l < i; l++) {
                    db.execSQL("ALTER TABLE RECIPE ADD COLUMN " + ingredient[l] + " TEXT ;");
                }
                db.close();
                return true;
            } catch (SQLiteException e) {
                return false;
            }
        }

        protected void onPostExecute(Boolean success) {
            if (!success) {
                Toast toast = Toast.makeText(SelIngredients.this, "this database is unavailable", Toast.LENGTH_SHORT);
                toast.show();
            }
        }

    }









    public void insert_Ingredients(View view) {
        for (int k = 4; k < j; k++) {
            CheckBox chkTeamName = (CheckBox) findViewById(k);
            if (chkTeamName.isChecked()) {
                Tot_col = Tot_col.concat(","+ingtext[k]);
                Tot_val= Tot_val.concat(",'1'");
            }
        }
        Toast toast = Toast.makeText(SelIngredients.this, Tot_col+Tot_val, Toast.LENGTH_LONG);
        toast.show();
        Intent intent = new Intent(this, CreateRecipe.class);
        intent.putExtra("Tot_col",Tot_col);
        intent.putExtra("Tot_val",Tot_val);
        startActivity(intent);
    }




}









