package com.habijabi.mealplanner;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import android.widget.ListView;
import android.app.ListActivity;

import java.util.ArrayList;


public class PrintSuggestedRecipe extends ListActivity {
    private SQLiteDatabase db;
    private Cursor cursor;
    private CursorAdapter listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String recipes_matched = intent.getStringExtra("Tot_suggest");
        // ListView listDrinks = getListView();
        setContentView(R.layout.activity_print_suggested_recipe);
        ActionBar actionBar=getActionBar();
        actionBar.setTitle("Possible recipes");
        actionBar.setDisplayShowHomeEnabled(true);


        new MatchRecipeClass().execute(recipes_matched);
    }
        private class MatchRecipeClass extends AsyncTask<String, Void, Boolean> {
            protected void onPreExecute() {
            }
            protected Boolean doInBackground(String...recipes_matched) {
                try {
                    SQLiteOpenHelper recipedb = new RecipeDatabase(PrintSuggestedRecipe.this);
                    db = recipedb.getWritableDatabase();
                    cursor = db.rawQuery(recipes_matched[0], null);

                    cursor.moveToFirst();
                    listAdapter = new SimpleCursorAdapter(PrintSuggestedRecipe.this, android.R.layout.simple_list_item_1, cursor, new String[]{"NAME"}, new int[]{android.R.id.text1}, 0);
                    setListAdapter(listAdapter);
                    return true;
                } catch (SQLiteException e) {
                    return false;
                }
            }
            protected void onPostExecute(Boolean success) {
                Toast toast = Toast.makeText(PrintSuggestedRecipe.this, "database cant be accessed", Toast.LENGTH_SHORT);

            }
        }

    public void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();

    }

    public void onListItemClick(ListView listView, View itemView, int position, long id) {
        Intent intent = new Intent(this, RecipeActivity.class);
        intent.putExtra(RecipeActivity.EXTRA_RECIPENO, (int) id);
        startActivity(intent);

    }
}


