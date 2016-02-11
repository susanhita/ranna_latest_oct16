package com.habijabi.mealplanner;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import android.widget.ListView;
import android.app.ListActivity;





public class PrintSuggestedRecipe extends ListActivity {
    private SQLiteDatabase db;
    private Cursor cursor;
    private CursorAdapter listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        String recipes_matched = intent.getStringExtra("Tot_suggest");
       // ListView listDrinks = getListView();
        setContentView(R.layout.activity_print_suggested_recipe);

        try {
            SQLiteOpenHelper recipedb = new RecipeDatabase(PrintSuggestedRecipe.this);
            db = recipedb.getWritableDatabase();
          //  recipes_matched="select  ";
            cursor=db.rawQuery(recipes_matched, null);

            //cursor = db.query("RECIPE", new String[]{"_id", "NAME"}, null, null, null, null, null);
            cursor.moveToFirst();
            Log.v("Cursor Object", DatabaseUtils.dumpCursorToString(cursor));
            listAdapter = new SimpleCursorAdapter(PrintSuggestedRecipe.this, android.R.layout.simple_list_item_1, cursor, new String[]{"NAME"}, new int[]{android.R.id.text1}, 0);
            setListAdapter(listAdapter);
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
        Intent intent = new Intent(this, RecipeActivity.class);
        intent.putExtra(RecipeActivity.EXTRA_RECIPENO, (int) id);
        startActivity(intent);

    }
}


