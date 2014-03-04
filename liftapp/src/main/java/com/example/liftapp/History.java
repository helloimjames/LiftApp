package com.example.liftapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.Toast;

public class History extends Activity {
    public static String dateFromCalendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        History();
    }



    private void History(){
    CalendarView calendarView=(CalendarView) findViewById(R.id.calendarView);
    calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
    String x;
    String y;

        @Override
        public void onSelectedDayChange(CalendarView view, int year, int month,
        int dayOfMonth) {

            if(month <10){ x = ""+0+month ;}

            if(dayOfMonth <10){y = ""+0+dayOfMonth ;}
            dateFromCalendar = String.valueOf(year+"-"+x+"-"+y);
            Toast.makeText(getApplicationContext(), dateFromCalendar, 0).show();
            startActivity(new Intent(History.this, ArchieveHistory.class));

        }
    });
}
    public static String ReturnDateFromCalendar(){
        return dateFromCalendar;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.history, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
