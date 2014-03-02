package com.example.liftapp;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

public class AddExercise extends Activity {
    DBAdapter myDb;
    MainActivity fromMainActivity = new MainActivity();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add_exercise);


        openDB();
        //KillAllRecords();
        nextActivity();
        NumberPicker npSets = (NumberPicker)findViewById(R.id.npSets);
        npSets.setMinValue(1);
        npSets.setMaxValue(10);

        NumberPicker npReps = (NumberPicker)findViewById(R.id.npReps);
        npReps.setMinValue(1);
        npReps.setMaxValue(10);

    }

    private void nextActivity(){
        Button btnAddExercise = (Button) findViewById(R.id.btnAddExercise);

        btnAddExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TextView editTxtNewWorkout = (TextView) findViewById(R.id.editTxtAddExercise);
                String TxtNewWorkout = editTxtNewWorkout.getText().toString();
                NumberPicker npSets = (NumberPicker)findViewById(R.id.npSets);
                NumberPicker npReps = (NumberPicker)findViewById(R.id.npReps);
                String LongRowID = Long.toString(fromMainActivity.rowIDLong());
                int IntRowID = Integer.parseInt(LongRowID);

                myDb.insertRow2(TxtNewWorkout,npSets.getValue(),npReps.getValue(),IntRowID );
                //TextView displayRecord = (TextView) findViewById(R.id.textView5);
                String message = TxtNewWorkout +" "+npSets +" "+ npReps +" ";
                Toast.makeText(AddExercise.this,message, Toast.LENGTH_LONG).show();
                Cursor cursor = myDb.getAllRows2();
                displayRecordSet(cursor);
                //displayRecord.setText(message);
                startActivity(new Intent(AddExercise.this, Exercises.class));
                finish();
            }
        });


    }

    private void KillAllRecords(){
        Button KillAllRecords = (Button) findViewById(R.id.btnDestroyExerciseRecords);

        KillAllRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myDb.deleteAll2();
                Cursor cursor = myDb.getAllRows();
               // TextView displayRecord = (TextView) findViewById(R.id.textView5);
                displayRecordSet(cursor);


            }
        });


    }
    private void displayRecordSet(Cursor cursor) {
        String message = "";
        // populate the message from the cursor

        // Reset cursor to start, checking to see if there's data:
        if (cursor.moveToFirst()) {
            do {
                // Process the data:
                int id = cursor.getInt(DBAdapter.COL_ROWID_EXERCISE);
                String name = cursor.getString(DBAdapter.COL_NAME_EXERCISE);
                int studentNumber = cursor.getInt(DBAdapter.COL_EXERCISE_SETS);
                int favColour = cursor.getInt(DBAdapter.COL_EXERCISE_REPS);
                int RowNumber = cursor.getCount();
                int LongRow = cursor.getInt(DBAdapter.COL_OF_PARENT_WORKOUT_ID);
                // Append data to the message:
                message += "id=" + id
                        +", name =" + name
                        +", Sets=" + studentNumber
                        +", Reps=" + favColour
                        +", Total Rows=" + RowNumber
                        +", RowID=" + LongRow
                        +"\n";
            } while(cursor.moveToNext());
        }
        //Toast.makeText(EditPage.this, message, Toast.LENGTH_LONG).show();
        // Close the cursor to avoid a resource leak.
        cursor.close();
        //TextView displayRecord = (TextView) findViewById(R.id.textView5);
        //displayRecord.setText(message);

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
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            startActivity(new Intent(AddExercise.this, Exercises.class));
            Toast.makeText(AddExercise.this, "Worked", Toast.LENGTH_LONG).show();
        }
        return super.onKeyDown(keyCode, event);
    }


}
