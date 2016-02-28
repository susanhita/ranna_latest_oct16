package com.habijabi.mealplanner;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CreateRecipe1 extends Activity {
    String columns, values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe1);
        Intent intent = getIntent();
        columns = intent.getStringExtra("Tot_col");
        values = intent.getStringExtra("Tot_val");
        EditText ed=(EditText)findViewById(R.id.ingredients);
        ed.setText("Ingredients:-"+columns.replaceAll(",","\n"));
        ActionBar actionBar = getActionBar();

    }
    public void next(View view){
        EditText editText = (EditText) findViewById(R.id.recipe_name);
        String recipe_name = editText.getText().toString();
        if (recipe_name.length() == 0) {
            Toast toast = Toast.makeText(this, "Please enter a valid recipe name", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        EditText editText2 = (EditText) findViewById(R.id.ingredients);
        String ingredients = editText2.getText().toString();
        Intent intent =new Intent(this,CreateRecipe.class);
        intent.putExtra("Tot_col", columns);
        intent.putExtra("Tot_val", values);
        intent.putExtra("recipe_name",recipe_name);
        intent.putExtra("ingredients",ingredients);
        startActivity(intent);
        finish();
    }
}
