package com.habijabi.mealplanner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {
    public static Boolean delete=false;
    public static Boolean add_grocery=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.gc();

    }

    public void create_recipe(View view){
        add_grocery=false;
        Intent intent=new Intent(this,SelIngredients.class);
        startActivity(intent);
    }

    public void view_recipe(View view){
        Intent intent=new Intent(this,ViewRecipe.class);
        startActivity(intent);
    }
    public void random_receipe(View view){
        Intent intent=new Intent(this,RandomReceipe.class);
        startActivity(intent);
    }
    public void delete_receipe(View view){
        this.delete=true;
        Intent intent=new Intent(this,ViewRecipe.class);
        startActivity(intent);
    }

    public void suggest(View view){
        Intent intent=new Intent(this,SuggestRecipe.class);
        startActivity(intent);
    }

    public void grocery(View view){
        Intent intent=new Intent(this,Grocery.class);
        startActivity(intent);
    }



}
