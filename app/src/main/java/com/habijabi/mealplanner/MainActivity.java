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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void create_recipe(View view){
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

    public void suggest(View view){
        Intent intent=new Intent(this,SuggestRecipe.class);
        startActivity(intent);
    }

    public void grocery(View view){
        Intent intent=new Intent(this,Grocery.class);
        startActivity(intent);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
