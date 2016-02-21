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
    private static final String DB_NAME="RecipeDatabaseq";

    RecipeDatabase(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE RECIPE(_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "NAME TEXT, "
                + "DESCRIPTION TEXT,"
                + "IMAGE_RESOURCE_ID TEXT,"
                + "GROCERY_LIST TEXT" +
                ");");

//The grocery_list column will only be used in the pancake row.
// This was done to prevent another table for grocery list

        Uri path1=Uri.parse("android.resource://com.habijabi.mealplanner/"+R.drawable.pancake);
        String recipe_description="\n" + "Ingredients needed\n"+
                "2 eggs \n  1 cup flour \n 1/2 cup sugar \n 1 tbps butter \n 1/2 teaspoon baking powder \n 1/2 cup milk\n\n"+
                "    Combine flour, sugar, baking powder, baking soda in a large mixing bowl. Whisk egg and butter into milk. Pour the flour mixture into the wet ingredients and whisk until lumps are gone." +
                "    Heat a large skillet over medium heat, and coat with cooking spray. Pour 1/4 cupfuls of batter onto the skillet, and cook until bubbles appear on the surface. Flip with a spatula, and cook until browned on the other side.\n";
        insertDrink(db, "pancake",recipe_description,path1 );


    }
    public static void insertDrink(SQLiteDatabase db, String name, String description, Uri image){
        ContentValues drinkValues=new ContentValues();
        drinkValues.put("NAME", name);
        drinkValues.put("DESCRIPTION", description);
        drinkValues.put("IMAGE_RESOURCE_ID", image.toString());
        drinkValues.put("GROCERY_LIST","No Grocery List saved.");
        db.insert("RECIPE", null, drinkValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }


}


