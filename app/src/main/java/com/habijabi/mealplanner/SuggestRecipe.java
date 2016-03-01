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
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SuggestRecipe extends Activity {
    public static String[] ingtext = new String[100];
    int j;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggest_recipe);
        ActionBar actionBar = getActionBar();
        actionBar.setTitle("Select ingredients available");
        new displayCheckboxes1().execute();
    }

    private class displayCheckboxes1 extends AsyncTask<Integer, Void, Boolean> {
        protected void onPreExecute() {
        }
        protected Boolean doInBackground(Integer... drinks) {
            j = 0;
            SQLiteOpenHelper recipedb = new RecipeDatabase(SuggestRecipe.this);
            try {
                SQLiteDatabase db = recipedb.getWritableDatabase();
                Cursor cursor = db.rawQuery("PRAGMA table_info(RECIPE)", null);
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    ingtext[j] = cursor.getString(1);
                    j++;
                    cursor.moveToNext();
                }
                cursor.close();
                db.close();
                return true;
            }
            catch (SQLiteException e){
                return false;
            }
        }
        protected void onPostExecute(Boolean success) {

            LinearLayout layout = (LinearLayout) findViewById(R.id.suggest_recipe);

            for (int k = 5; k < j; k++) {
                CheckBox chkTeamName = new CheckBox(SuggestRecipe.this);
                chkTeamName.setId(k);
                chkTeamName.setText(ingtext[k].replaceAll("_", " "));
                layout.addView(chkTeamName);
            }
        }
    }

    public void suggest(View view){
        String and="";
        String mul="";
        int count=0;
        String new_string="";
        String sum_string="";
        int flag=0;

        String Tot_suggest="SELECT _id,NAME FROM RECIPE WHERE ";
        for (int k = 5; k <  j; k++) {
            CheckBox chkTeamName = (CheckBox) findViewById(k);
            if (chkTeamName.isChecked()) {
               flag=1;
               Tot_suggest = Tot_suggest.concat(and + ingtext[k]+ "='YES'");
               and=" OR ";
            }
            else{
                new_string=new_string.concat(mul+ingtext[k]);
                mul=" * ";
                sum_string=" AND ("+new_string+")<1 ";
            }
        }
        Tot_suggest=Tot_suggest.concat(sum_string);
        Tot_suggest = Tot_suggest.concat(";");
        if (flag==1) {
            Intent intent = new Intent(this, PrintSuggestedRecipe.class);
            intent.putExtra("Tot_suggest", Tot_suggest);
            startActivity(intent);
            finish();
        }
        else{
            Toast toast=Toast.makeText(this,"Select ingredient first.",Toast.LENGTH_SHORT);
            toast.show();
        }
    }

}
