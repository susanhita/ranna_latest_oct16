package com.habijabi.mealplanner;

import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ViewRecipe extends ListActivity {

    private SQLiteDatabase db;
    private Cursor cursor;
    private CursorAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar=getActionBar();
        ListView listDrinks = getListView();


      try {
            SQLiteOpenHelper recipedb = new RecipeDatabase(ViewRecipe.this);
            db = recipedb.getWritableDatabase();
            cursor = db.query("RECIPE", new String[]{"_id","NAME"}, null, null, null, null, null);
            listAdapter = new SimpleCursorAdapter(ViewRecipe.this, android.R.layout.simple_list_item_1, cursor, new String[]{"NAME"}, new int[]{android.R.id.text1}, 0);
            listDrinks.setAdapter(listAdapter);
        }
        catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "database cant be accessed", Toast.LENGTH_SHORT);
            toast.show();
        }


    }

    public void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();

    }

    public void onListItemClick(ListView listView, View itemView, int position, long id) {
        if (MainActivity.delete==true){
            MainActivity.delete=false;
            Intent intent = new Intent(ViewRecipe.this, DeleteActivity.class);
            intent.putExtra(DeleteActivity.DELETE_RECIPENO, (int) id);
            startActivity(intent);
            finish();

        }
        else {
            Intent intent = new Intent(this, RecipeActivity.class);
            intent.putExtra(RecipeActivity.EXTRA_RECIPENO, (int) id);
            startActivity(intent);
        }

    }
}