package com.example.liftapp;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class EditPage extends ActionBarActivity {
    //private List<Workouts> myWorkouts = new ArrayList<Workouts>();
    //public static String name;
    MainActivity fromMainActivity = new MainActivity();
    DBAdapter myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_edit_page);
        openDB();
        //onDestroy();
        addWorkout();
        KillAllRecords();
        //DeleteCurrentRecord();
        String a = fromMainActivity.getName();
        TextView textView = (TextView) findViewById(R.id.editTxtNewWorkout);
        textView.setText(a);

    }


    private void addWorkout(){
        Button secondActivity = (Button) findViewById(R.id.btnAdd);

        secondActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TextView editTxtNewWorkout = (TextView) findViewById(R.id.editTxtNewWorkout);
                String TxtNewWorkout = editTxtNewWorkout.getText().toString();

                if(TxtNewWorkout.length() ==0){

                    Toast.makeText(EditPage.this,"Add a name to the Workout" , Toast.LENGTH_LONG).show();
                    return;
                }
                else if(TxtNewWorkout.length() > 0){
                myDb.insertRow(TxtNewWorkout,1,"green");
                Cursor cursor = myDb.getAllRows();
                displayRecordSet(cursor);

                Toast.makeText(EditPage.this,"Added "+TxtNewWorkout , Toast.LENGTH_LONG).show();
                //fromMainActivity.populateWorkoutList();
                startActivity(new Intent(EditPage.this, MainActivity.class));
                }

            }
        });


    }

    /*private void DeleteCurrentRecord(){
        Button DeleteCurrentRecord = (Button) findViewById(R.id.btnDeleteRecord);
        DeleteCurrentRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            String a = fromMainActivity.getName();
            Toast.makeText(EditPage.this,"Deleted " + a, Toast.LENGTH_LONG).show();
            myDb.deleteRow(fromMainActivity.rowIDLong());
            startActivity(new Intent(EditPage.this, MainActivity.class));

            }
        });
    }*/
    private void KillAllRecords(){
        Button KillAllRecords = (Button) findViewById(R.id.btnDestroy);

        KillAllRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myDb.deleteAll();
                Cursor cursor = myDb.getAllRows();
                displayRecordSet(cursor);


            }
        });


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
    private void displayText(String message) {
        TextView textView = (TextView) findViewById(R.id.textView4);
        textView.setText(message);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_page, menu);
        return true;
    }
    private void displayRecordSet(Cursor cursor) {
        String message = "";
        // populate the message from the cursor

        // Reset cursor to start, checking to see if there's data:
        if (cursor.moveToFirst()) {
            do {
                // Process the data:
                int id = cursor.getInt(DBAdapter.COL_ROWID);
                String name = cursor.getString(DBAdapter.COL_NAME);
                //int studentNumber = cursor.getInt(DBAdapter.COL_STUDENTNUM);
               // String favColour = cursor.getString(DBAdapter.COL_FAVCOLOUR);
                int RowNumber = cursor.getCount();

                // Append data to the message:
                message += "id=" + id
                        +", name=" + name
                        //+", #=" + studentNumber
                        +", Total Rows=" + RowNumber
                        //+", Colour=" + favColour
                        +"\n";
            } while(cursor.moveToNext());
        }
        Toast.makeText(EditPage.this, message, Toast.LENGTH_LONG).show();
        // Close the cursor to avoid a resource leak.
        cursor.close();
        displayText(message);

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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_edit_page, container, false);
            return rootView;
        }
    }

}
