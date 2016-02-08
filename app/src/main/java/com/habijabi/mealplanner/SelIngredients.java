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
    public static int i,j;
    public static String Tot_col,Tot_val;
    public static String[] ingtext=new String[100];
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Tot_col="";
        Tot_val="";
        setContentView(R.layout.activity_sel_ingredients);
        if (savedInstanceState != null) {
             i=savedInstanceState.getInt("i");
        }
        displayCheckboxes();
    }

    void displayCheckboxes(){
        j=0;
        SQLiteOpenHelper recipedb = new RecipeDatabase(this);
        SQLiteDatabase db = recipedb.getWritableDatabase();
        Cursor cursor=db.rawQuery("PRAGMA table_info(RECIPE)",null);
        cursor.moveToFirst();
        while (cursor.isAfterLast()==false) {
            ingtext[j] = cursor.getString(1);
            j++;
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        if (j>4){
          //  Button add_ingredient=(Button)findViewById(R.id.add_ingredient);

            Button ins=(Button)findViewById(R.id.insert_to_db);
            ins.setVisibility(View.VISIBLE);
            TextView choose=(TextView)findViewById(R.id.choose_text);
            choose.setVisibility(View.VISIBLE);

        }
        LinearLayout layout = (LinearLayout) findViewById(R.id.layout_ingredient);

        for(int k=4;k<j;k++) {
                CheckBox chkTeamName = new CheckBox(this);
                chkTeamName.setId(k);
                chkTeamName.setText(ingtext[k].replaceAll("_", " "));
                chkTeamName.setTextSize(18);
                layout.addView(chkTeamName);
        }


   }



   public void addIngredient(View view){
        Intent intent=new Intent(this,AddIngredients.class);
        startActivity(intent);
   }


   public void insert_Ingredients(View view) {
        for (int k = 4; k <  j; k++) {
            CheckBox chkTeamName = (CheckBox) findViewById(k);
            if (chkTeamName.isChecked()) {
                Tot_col = Tot_col.concat(","+ingtext[k]);
                Tot_val= Tot_val.concat(",'YES'");
            }
        }
        Toast toast = Toast.makeText(SelIngredients.this, Tot_col, Toast.LENGTH_LONG);
        toast.show();
        Intent intent = new Intent(this, CreateRecipe.class);
        intent.putExtra("Tot_col",Tot_col);
        intent.putExtra("Tot_val", Tot_val);

        startActivity(intent);
   }




}









