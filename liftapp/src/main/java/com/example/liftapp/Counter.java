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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_counter);

        final TextView txtCounterReps = (TextView) findViewById(R.id.txtCounterReps1);
        openDB();
        int newNumberValue = myDb.getRow3(fromSetsView.ReturnSetNumberRowId()).getInt(DBAdapter.COL_HISTORY_REPS);
        txtCounterReps.setText(String.valueOf(newNumberValue));
        Button upButton = (Button)findViewById(R.id.btnUp);
        upButton.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View view) {

                int newNumberValue = myDb.getRow3(fromSetsView.ReturnSetNumberRowId()).getInt(DBAdapter.COL_HISTORY_REPS);
                newNumberValue += 1 ;
                txtCounterReps.setText(String.valueOf(newNumberValue));
                intCounterReps = Integer.parseInt(txtCounterReps.getText().toString());
                Cursor cursor = myDb.getAllRows3();
                final TextView txtHistory = (TextView) findViewById(R.id.tvHistory);
                myDb.updateRow3(fromSetsView.ReturnSetNumberRowId(), intCounterReps);
                txtHistory.setText(displayRecordSet(cursor));
            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {


            //myDb.updateRow3(fromSetsView.ReturnSetNumberRowId(), intCounterReps);
            startActivity(new Intent(Counter.this, SetsView.class));
            //finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    private String displayRecordSet(Cursor cursor) {
        String message = "";
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
                //if(LongRow == longRowIDExercise){
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



