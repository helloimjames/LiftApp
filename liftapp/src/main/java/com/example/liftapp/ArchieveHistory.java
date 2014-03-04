package com.example.liftapp;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class ArchieveHistory extends Activity {
    History fromHistoryActivity = new History();
    DBAdapter myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archieve_history);
        openDB();
        Cursor cursor = myDb.getAllRows3();

        TextView txtSetsSpecified = (TextView) findViewById(R.id.tvArchieveHistory);
        txtSetsSpecified.setText(displayRecordSetz(cursor));
    }

    private String displayRecordSetz(Cursor cursor) {
        String message = "";
        // populate the message from the cursor
        //int LongRow = cursor.getInt(DBAdapter.COL_OF_PARENT_WORKOUT_ID);
        ///String LongRowID = Long.toString(fromMainActivity.rowIDLong());
        //int IntRowID = Integer.parseInt(LongRowID);
        // Reset cursor to start, checking to see if there's data:
        if (cursor.moveToFirst()) {

            do {
                // Process the data:
                int id = cursor.getInt(DBAdapter.COL_HISTORY_EXERCISE_ID);
                String date2 = cursor.getString(DBAdapter.COL_DATETIME);
                int setNumberFromHistory = cursor.getInt(DBAdapter.COL_HISTORY_SETS);
                int repNumberFromHistory = cursor.getInt(DBAdapter.COL_HISTORY_REPS);
                long historyLongID = cursor.getInt(DBAdapter.COL_ROWID_HISTORY);
                // Append data to the message:
                //Toast.makeText(ArchieveHistory.this, date2+fromHistoryActivity.ReturnDateFromCalendar()+message, Toast.LENGTH_LONG).show();
                if(date2 == fromHistoryActivity.ReturnDateFromCalendar()){
                    message += " id = " + id
                            +", Sets =  " + setNumberFromHistory
                            +", Reps = " + repNumberFromHistory
                            +", date = " + date2
                            +"\n";
                    Toast.makeText(ArchieveHistory.this, date2+fromHistoryActivity.ReturnDateFromCalendar()+message, Toast.LENGTH_LONG).show();
                }
            } while(cursor.moveToNext());


        }
        return message;


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.archieve_history, menu);
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
    protected void onDestroy() {
        super.onDestroy();
        closeDB();
    }


    private void openDB() {
        myDb = new DBAdapter(this);
        myDb.open();
    }
    private void closeDB() {
        myDb.close();
    }

}
