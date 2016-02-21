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
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class SelIngredients extends Activity {
    public static String[] ingredient = new String[100];
    public static int i,j;
    public static String Tot_col,Tot_val;
    public static String[] ingtext=new String[100];
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar=getActionBar();
        Tot_col="";
        Tot_val="";
        setContentView(R.layout.activity_sel_ingredients);
        if (savedInstanceState != null) {
             i=savedInstanceState.getInt("i");
        }
        if (Grocery.grocery==true) {
            TextView choose = (TextView) findViewById(R.id.choose_text);
            choose.setText("Select items you need to buy:-");
        }
        new displayCheckboxes().execute();
    }

    private class displayCheckboxes extends AsyncTask<Integer, Void, Boolean> {
        protected void onPreExecute() {
        }

        protected Boolean doInBackground(Integer... drinks) {
            j = 0;
            SQLiteOpenHelper recipedb = new RecipeDatabase(SelIngredients.this);
            try{
                SQLiteDatabase db = recipedb.getWritableDatabase();
                Cursor cursor = db.rawQuery("PRAGMA table_info(RECIPE)", null);
                cursor.moveToFirst();
                while (cursor.isAfterLast() == false) {
                    ingtext[j] = cursor.getString(1);
                    j++;
                    cursor.moveToNext();
                }
                cursor.close();
                db.close();
                return true;
            }
            catch (SQLiteException e) {
                return false;
            }
        }

        protected void onPostExecute(Boolean success) {
            if (j > 5) {
                //  Button add_ingredient=(Button)findViewById(R.id.add_ingredient);

                Button ins = (Button) findViewById(R.id.insert_to_db);
                ins.setVisibility(View.VISIBLE);
                TextView choose = (TextView) findViewById(R.id.choose_text);
                choose.setVisibility(View.VISIBLE);

            }
            LinearLayout layout = (LinearLayout) findViewById(R.id.layout_ingredient);

            for (int k = 5; k < j; k++) {
                CheckBox chkTeamName = new CheckBox(SelIngredients.this);
                chkTeamName.setId(k);
                chkTeamName.setText(ingtext[k].replaceAll("_", " "));
                chkTeamName.setTextSize(18);
                layout.addView(chkTeamName);
            }
        }

   }



   public void addIngredient(View view){
        Intent intent=new Intent(this,AddIngredients.class);
        startActivity(intent);
   }


   public void insert_Ingredients(View view) {
       int flag=0;
       String list="";
        for (int k = 5; k <  j; k++) {
            CheckBox chkTeamName = (CheckBox) findViewById(k);
            if (chkTeamName.isChecked()) {
                flag=1;
                list=list.concat(ingtext[k]+"\n");
                Tot_col = Tot_col.concat(","+ingtext[k]);
                Tot_val= Tot_val.concat(",'YES'");
            }
        }
       if (flag==1) {
           if (Grocery.grocery==true) {
               Intent intent = new Intent(this, CreateGroceryList.class);
               intent.putExtra("list", list);
               startActivity(intent);

           }

           else {
               Intent intent = new Intent(this, CreateRecipe.class);
               intent.putExtra("Tot_col", Tot_col);
               intent.putExtra("Tot_val", Tot_val);
               startActivity(intent);
           }
       }

       else{
            Toast toast=Toast.makeText(this,"Select ingredient first.",Toast.LENGTH_SHORT);
            toast.show();
        }


   }




}
