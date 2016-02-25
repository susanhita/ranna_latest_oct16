package com.habijabi.mealplanner;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.app.DatePickerDialog;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class CreateGroceryList extends Activity {
    TextView datetext,timetext;
    static int day, month,hour,minute1,year1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_grocery_list);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        datetext = (TextView) findViewById(R.id.datetext);
        timetext = (TextView) findViewById(R.id.timetext);


        Intent intent = getIntent();
        String list = intent.getStringExtra("list");
        TextView textList = (TextView) findViewById(R.id.groc_list);
        list = list.replaceAll("_", " ");
        textList.setText(list);
        new UpdateListDB().execute(list);
    }


    private class UpdateListDB extends AsyncTask<String, Void, Boolean> {
        protected void onPreExecute() {
        }

        protected Boolean doInBackground(String... list1) {
            String list = list1[0];
            SQLiteOpenHelper recipedb = new RecipeDatabase(CreateGroceryList.this);
            SQLiteDatabase db = recipedb.getWritableDatabase();
            db.execSQL("UPDATE RECIPE SET GROCERY_LIST ='" + list + "'  WHERE name='pancake'");
            // db.execSQL("UPDATE RECIPE SET GROCERY_LIST = 'bullshit'  WHERE name='pancake'");
            return true;
        }

        protected void onPostExecute(Boolean success) {



        }
    }

    public void setdate(View view) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        int mHour = c.get(Calendar.HOUR);
        int mMinute = c.get(Calendar.MINUTE);


        DatePickerDialog dpd = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        datetext.setText("Reminder set on :-"+dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        day = dayOfMonth;
                        month = monthOfYear;
                        year1 = year;

                    }
                }, mYear, mMonth, mDay);
        dpd.show();

        TimePickerDialog tpd = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        timetext.setText(" at "+hourOfDay + ":" + minute);
                        hour = hourOfDay;
                        minute1 = minute;

                    }
                }, mHour, mMinute, false);
        tpd.show();
        Button button=(Button)findViewById(R.id.confirm);
        button.setVisibility(View.VISIBLE);
        TextView textView=(TextView)findViewById(R.id.textViewlist);
        TextView groc_list=(TextView)findViewById(R.id.groc_list);
        Button but1=(Button)findViewById(R.id.setdate);
        textView.setVisibility(View.INVISIBLE);
        groc_list.setVisibility(View.INVISIBLE);
        but1.setText("Change the time");

    }
        public void confirm(View view) {
            Intent intent = new Intent(this, NotificationGrocery.class);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
            stackBuilder.addParentStack(ViewGroceryList.class);
            stackBuilder.addNextIntent(intent);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

            Calendar c1 = Calendar.getInstance();

            c1.set(Calendar.HOUR_OF_DAY, hour);
            c1.set(Calendar.MINUTE, minute1);
            c1.set(Calendar.SECOND, 0);
            c1.set(Calendar.DAY_OF_MONTH,day);
            c1.set(Calendar.MONTH, month);
            c1.set(Calendar.YEAR, year1);


            long futureInMillis = c1.getTimeInMillis();

            AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarm.set(AlarmManager.RTC_WAKEUP, futureInMillis, pendingIntent);
            Intent intent1=new Intent(this,MainActivity.class);
            startActivity(intent1);
            finish();
        }
}