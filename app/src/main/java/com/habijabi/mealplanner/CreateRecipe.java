package com.habijabi.mealplanner;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v7.widget.Toolbar;
import android.content.ClipboardManager;
import android.view.View;
import android.content.DialogInterface;
import android.app.AlertDialog;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.SQLDataException;

public class CreateRecipe extends Activity {
    Uri uriSavedImage1;
    String photo_name,EXTRA = "message",columns,values;
    Uri uriSavedImage=Uri.parse("file:///sdcard/ArtRage/blah.png");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);

        Intent intent =getIntent();
        columns = intent.getStringExtra("Tot_col");
        values = intent.getStringExtra("Tot_val");
        ActionBar actionBar=getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }





    public void recipe_pic(View view) {
        EditText editText=(EditText)findViewById(R.id.recipe_name);
        String recipe_name=editText.getText().toString();
        if (recipe_name.length()==0){
            Toast toast = Toast.makeText(this, "Please enter a valid recipe name", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        photo_name=recipe_name.replaceAll("\\s+", "");

        uriSavedImage=Uri.parse("file:///sdcard/ArtRage/" + photo_name + ".png");
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
                    startActivityForResult(intent, 1);
                //    startActivity(intent);
                } else if (items[item].equals("Choose from Library")) {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
                    startActivityForResult(intent, 1);
                   // startActivity(intent);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
        }






    public void paste(View view) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData.Item pastetext=clipboard.getPrimaryClip().getItemAt(0);
        EditText editText=(EditText)findViewById(R.id.createRecipe);
        editText.append(pastetext.coerceToText(this));

    }






    public void save (View view){
        uriSavedImage1=Uri.parse(uriSavedImage.toString());
        EditText editText=(EditText)findViewById(R.id.recipe_name);
        String recipe_name=editText.getText().toString();
        if (recipe_name.length()==0){
            Toast toast = Toast.makeText(this, "Please enter a valid recipe name", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        EditText editText1=(EditText)findViewById(R.id.createRecipe);
        String description= editText1.getText().toString();
        if (description.length()<=10){
            Toast toast = Toast.makeText(this, "Recipe description should be of more than 40 letters", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        SQLiteOpenHelper starbuzzdb1 = new RecipeDatabase(this);
        try {
            SQLiteDatabase db = starbuzzdb1.getWritableDatabase();

            TextView textView=(TextView)findViewById(R.id.blah);
            String insert="INSERT INTO RECIPE(NAME,DESCRIPTION,IMAGE_RESOURCE_ID"+columns+ ")VALUES('" +  recipe_name   +"','"+   description    +"','"+    uriSavedImage1 +"'"+values+");";
            textView.setText(insert);
            db.execSQL(insert);
            db.close();

        }
        catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "this database is unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }




        Toast toast = Toast.makeText(this, "The recipe is added.", Toast.LENGTH_SHORT);
        toast.show();
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);


    }
}
