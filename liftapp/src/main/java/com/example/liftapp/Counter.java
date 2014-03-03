package com.example.liftapp;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Counter extends ActionBarActivity {

    DBAdapter myDb;
    public static int intCounterReps;
    Exercises fromExerciseActivity = new Exercises();
    SetsView fromSetsView = new SetsView();
    int newNumberValue;
    private Incrementor mIncrementor = new Incrementor();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date date = new Date();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_counter);

        final TextView txtCounterReps = (TextView) findViewById(R.id.txtCounterReps1);

        Button upButton = (Button)findViewById(R.id.btnUp);

        openDB();

        upButton.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View view) {
                newNumberValue += 1 ;

                //myDb.getRow3()
                //if(newNumberValue = ){

               // }
               // else(){

                //}
                final TextView txtHistory = (TextView) findViewById(R.id.tvHistory);
                txtCounterReps.setText(String.valueOf(newNumberValue));
                //final TextView txtCounterReps = (TextView) findViewById(R.id.txtCounterReps1);
                String CounterReps = txtCounterReps.getText().toString();
                intCounterReps = Integer.parseInt(CounterReps);
                //String LongRowID = Long.toString(fromExerciseActivity.IntRowID());
                //int IntRowID = Integer.parseInt(LongRowID);
                Cursor cursor = myDb.getAllRows3();


                myDb.updateRow3(fromSetsView.ReturnSetNumberRowId(), intCounterReps++);
                txtHistory.setText(displayRecordSet(cursor));


            }
        });


    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {


            myDb.updateRow3(fromSetsView.ReturnSetNumberRowId(), intCounterReps);
            startActivity(new Intent(Counter.this, SetsView.class));
            //finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    private String displayRecordSet(Cursor cursor) {
        String message = "";
        // populate the message from the cursor
        //String LongRowID = Long.toString(fromMainActivity.rowIDLong());
        //int IntRowID = Integer.parseInt(LongRowID);
        // Reset cursor to start, checking to see if there's data:
        if (cursor.moveToFirst()) {
            do {
                // Process the data:
                int id = cursor.getInt(DBAdapter.COL_ROWID_HISTORY);
                //String name = cursor.getString(DBAdapter.COL_NAME_EXERCISE);
                int sets = cursor.getInt(DBAdapter.COL_HISTORY_SETS);
                int reps = cursor.getInt(DBAdapter.COL_HISTORY_REPS);
                int RowNumber = cursor.getInt(DBAdapter.COL_HISTORY_EXERCISE_ID);
                int setNumber = cursor.getInt(DBAdapter.COL_SET_NUMBER);
                String LongRow = cursor.getString(DBAdapter.COL_DATETIME);

                // Append data to the message:
                //if(LongRow == IntRowID){
                    message += "auto id = " + id
                            //+", name =" + name
                            +", Sets =" + sets
                            +", Reps =" + reps
                            +", Exer ID =" + RowNumber
                            +", Set# =" + setNumber
                            +", date = " + LongRow
                            +"\n";
                //}
            } while(cursor.moveToNext());
            //TextView editTxtNewWorkout = (TextView) findViewById(R.id.textView4);
            //editTxtNewWorkout.setText(message);


        }
        return  message;
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



