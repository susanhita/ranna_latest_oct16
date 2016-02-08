package com.habijabi.mealplanner;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.net.URI;

/**
 * Created by susanhita on 17-01-2016.
 */

public class RecipeDatabase  extends SQLiteOpenHelper {
    private static final int DB_VERSION=1;
    private static final String DB_NAME="RecipeDatabase6";

    RecipeDatabase(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE RECIPE(_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "NAME TEXT, "
                + "DESCRIPTION TEXT,"
                + "IMAGE_RESOURCE_ID TEXT);");


        Uri path1=Uri.parse("android.resource://com.habijabi.mealplanner/"+R.drawable.cappuccino);
        Uri path2=Uri.parse("android.resource://com.habijabi.mealplanner/"+R.drawable.latte);
        Uri path3=Uri.parse("android.resource://com.habijabi.mealplanner/"+R.drawable.filter);

        insertDrink(db, "capuccino", "espresso,hot milk,steam",path1 );
        insertDrink(db,"latte","espresso , steamed milk",path2);
        insertDrink(db, "Filter", "fresh roasted beans", path3);


    }
    public static void insertDrink(SQLiteDatabase db, String name, String description, Uri image){
        ContentValues drinkValues=new ContentValues();
        drinkValues.put("NAME", name);
        drinkValues.put("DESCRIPTION", description);
        drinkValues.put("IMAGE_RESOURCE_ID", image.toString());
        db.insert("RECIPE", null, drinkValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }


}


