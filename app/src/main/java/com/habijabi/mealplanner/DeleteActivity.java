package com.habijabi.mealplanner;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DeleteActivity extends Activity {
    public static final String DELETE_RECIPENO = "drinkNo";
    static int drinkNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        Intent intent = getIntent();
        drinkNo = (Integer) getIntent().getExtras().get(DELETE_RECIPENO);
    }

    public void delete_no(View view){
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }

     public void delete_yes(View view){

            new DeleteRecipeClass().execute(drinkNo);
            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);
     }


    private class DeleteRecipeClass extends AsyncTask<Integer,Void,Boolean> {
        String desctext,nametext,resourceid;
        protected void onPreExecute(){}
        protected Boolean doInBackground(Integer...drinks) {
            int drinkNo=drinks[0];
            SQLiteOpenHelper starbuzzdb = new RecipeDatabase(DeleteActivity.this);
            try {
                SQLiteDatabase db = starbuzzdb.getWritableDatabase();
                String delete = "DELETE FROM RECIPE WHERE _id="+Integer.toString(drinkNo)+";";
                db.execSQL(delete);
                db.close();

                return true;
            }
            catch (SQLiteException e) {
                return false;
            }
        }
        protected void onPostExecute(Boolean success){
            if (!success){
                Toast toast = Toast.makeText(DeleteActivity.this,"this database in unavailable", Toast.LENGTH_SHORT);
                toast.show();
            }

        }

    }
}


