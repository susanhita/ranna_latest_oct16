package com.habijabi.mealplanner;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AddIngredients extends Activity {
    public static String[] ingredient = new String[100];
    public static int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        i=0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredients);
        ActionBar actionBar=getActionBar();
        actionBar.setTitle("Add new ingredients");


    }

    public void add_more_done(View view) {
        //This prints the new ingredients(works!!)
        //clean_all();
        EditText add_ingredient = (EditText) findViewById(R.id.editText);
        String string1 = add_ingredient.getText().toString().replaceAll(" ","_");
        if (add_ingredient.getText().length() == 0) {
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
            final_string = final_string + (String.format("\n %s ", ingredient[l]));
        }
        textView3.setText(final_string.replaceAll("_"," "));
    }


    public void reset_Ingredients(View view) {
        //works!!
        clean_all();
    }

    public void clean_all()
    {
        //works!!
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
        //works!!
        //This creates NEW COLUMNS af ingredients
        new AddRecipeClass().execute();
        Intent intent=new Intent(this, SelIngredients.class);
        startActivity(intent);
     //   finish();

    }
    private class AddRecipeClass extends AsyncTask<Integer, Void, Boolean> {
        String desctext, nametext;
        Integer resourceid;

        protected void onPreExecute() {
        }

        protected Boolean doInBackground(Integer... drinks) {
            SQLiteOpenHelper starbuzzdb1 = new RecipeDatabase(AddIngredients.this);
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
                Toast toast = Toast.makeText(AddIngredients.this, "Please do not add duplicate or improper ingredient names", Toast.LENGTH_SHORT);
                toast.show();
            }
        }

    }


}