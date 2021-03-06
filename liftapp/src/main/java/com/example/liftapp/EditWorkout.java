package com.example.liftapp;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class EditWorkout extends Activity {
    MainActivity fromMainActivity = new MainActivity();
    DBAdapter myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_edit_workout);
        openDB();
        DeleteCurrentRecord();
        editWorkout();
        String a = fromMainActivity.getName();
        TextView textView = (TextView) findViewById(R.id.editTxtNewWorkout);
        TextView textView2 = (TextView) findViewById(R.id.textView4);

        textView.setText(a);
        Cursor cursor = myDb.getAllRows2();
        String Row = "Row ID is "+Long.toString(fromMainActivity.rowIDLong()) +"\n"+ displayRecordSet( cursor);
        textView2.setText(Row);


    }

    private void openDB() {
        myDb = new DBAdapter(this);
        myDb.open();
    }
    private void DeleteCurrentRecord(){
        Button DeleteCurrentRecord = (Button) findViewById(R.id.btnDeleteRecord);
        DeleteCurrentRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Long LongRow = fromMainActivity.rowIDLong();
                //myDb.insertRow("test",1,"green");
                Cursor cursor = myDb.getAllRows2();
                deleteAssociatedExercises(cursor);
                myDb.deleteRow(LongRow);
                displayRecordSet( cursor);

                startActivity(new Intent(EditWorkout.this, MainActivity.class));
                finish();


            }
        });
    }


    private void deleteAssociatedExercises(Cursor cursor) {
        String message = "";
        // populate the message from the cursor
        //int LongRow = cursor.getInt(DBAdapter.COL_OF_PARENT_WORKOUT_ID);
        String LongRowID = Long.toString(fromMainActivity.rowIDLong());
        int IntRowID = Integer.parseInt(LongRowID);
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
                message += " name =" + name

                        +"\n";
                if(LongRow == IntRowID){
                    myDb.deleteRow2(LongRow);
                }
            } while(cursor.moveToNext());

        }
    }
    private void editWorkout(){
        Button secondActivity = (Button) findViewById(R.id.btnEditWorkout);

        secondActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TextView editTxtNewWorkout = (TextView) findViewById(R.id.editTxtNewWorkout);
                String TxtNewWorkout = editTxtNewWorkout.getText().toString();

                if(TxtNewWorkout.length() ==0){

                    Toast.makeText(EditWorkout.this,"Add a name to the Workout" , Toast.LENGTH_LONG).show();
                    return;
                }
                else if(TxtNewWorkout.length() > 0){
                    //myDb.insertRow(TxtNewWorkout,1,"green");
                    Cursor cursor = myDb.getAllRows();
                    long rowID = fromMainActivity.rowIDLong();
                    //Toast.makeText(EditWorkout.this,"Updated "+TxtNewWorkout , //Toast.LENGTH_LONG).show();

                    myDb.updateRow(rowID, TxtNewWorkout);
                    startActivity(new Intent(EditWorkout.this, MainActivity.class));
                    finish();
                }

            }
        });


    }
    private String displayRecordSet(Cursor cursor) {
        String message = "";
        // populate the message from the cursor
        String LongRowID = Long.toString(fromMainActivity.rowIDLong());
        int IntRowID = Integer.parseInt(LongRowID);
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
                if(LongRow == IntRowID){
                message += "id=" + id
                        +", name =" + name
                        +", Sets=" + studentNumber
                        +", Reps=" + favColour
                        +", Total Rows=" + RowNumber
                        +", RowID=" + LongRow
                        +"\n";
                }
            } while(cursor.moveToNext());
            //TextView editTxtNewWorkout = (TextView) findViewById(R.id.textView4);
            //editTxtNewWorkout.setText(message);


        }
        return  message;
    }
}

