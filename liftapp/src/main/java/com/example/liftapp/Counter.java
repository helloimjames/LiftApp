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

public class Counter extends ActionBarActivity {

    DBAdapter myDb;
    Exercises fromExerciseActivity = new Exercises();
    private Incrementor mIncrementor = new Incrementor();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_counter);

        final TextView txtCounterReps = (TextView) findViewById(R.id.txtCounterReps1);
        Button upButton = (Button)findViewById(R.id.btnUp);
        checkHistory();


        upButton.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View view) {
                int newNumberValue = mIncrementor.AddOne();
                //if
                //myDb.getRow3()
                //if(newNumberValue = ){

               // }
               // else(){

                //}
                txtCounterReps.setText(String.valueOf(newNumberValue));

                //add to database
            }
        });


    }


    private void checkHistory(){
        Button checkHistory = (Button) findViewById(R.id.btnCheck);
        final TextView txtHistory = (TextView) findViewById(R.id.tvHistory);
        checkHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String LongRowID = Long.toString(fromExerciseActivity.IntRowID());
                int IntRowID = Integer.parseInt(LongRowID);
                //Cursor cursor = myDb.getAllRows2();
                //myDb.insertRow3(4,4, IntRowID);
                //txtHistory.setText(displayRecordSet(cursor));
                txtHistory.setText("Worked");

            }
        });


    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {

            startActivity(new Intent(Counter.this, EditExercise.class));
            //Toast.makeText(Counter.this, "Worked", Toast.LENGTH_LONG).show();
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
                int studentNumber = cursor.getInt(DBAdapter.COL_HISTORY_SETS);
                int favColour = cursor.getInt(DBAdapter.COL_HISTORY_REPS);
                int RowNumber = cursor.getInt(DBAdapter.COL_HISTORY_EXERCISE_ID);
                int LongRow = cursor.getInt(DBAdapter.COL_DATETIME);
                // Append data to the message:
                //if(LongRow == IntRowID){
                    message += "id=" + id
                            //+", name =" + name
                            +", Sets=" + studentNumber
                            +", Reps=" + favColour
                            +", Total Rows=" + RowNumber
                            +", RowID=" + LongRow
                            +"\n";
                //}
            } while(cursor.moveToNext());
            //TextView editTxtNewWorkout = (TextView) findViewById(R.id.textView4);
            //editTxtNewWorkout.setText(message);


        }
        return  message;
    }
}



