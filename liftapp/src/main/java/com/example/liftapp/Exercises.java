package com.example.liftapp;

//import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import java.util.ArrayList;
import java.util.List;



public class Exercises extends ActionBarActivity {
    public static int sets;
    public static int reps;
    public static long LongRowID;
    public static String exerciseNamexercise;
    private List<Workouts> myWorkouts2 = new ArrayList<Workouts>();
    DBAdapter myDb;
    MainActivity fromMainActivity = new MainActivity();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);

        openDB();
        ReturnSets();

        populateWorkoutList();
        populateListView();
        registerClickCallback();
        nextActivity();
        //registerLongClickCallback();

    }

    public void populateWorkoutList() {
        myWorkouts2.add(new Workouts("+",0));
        Cursor cursor = myDb.getAllRows2();
        displayRecordSetz(cursor);


    }

    public static int ReturnSets(){
        return sets;
    }
    public static int ReturnReps(){
        return reps;
    }
    public static String ReturnName(){
        return exerciseNamexercise;
    }
    public static long IntRowID(){
        return LongRowID;
    }

    private void registerClickCallback() {

        ListView list = (ListView) findViewById(R.id.listViewExercises);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked,int position, long id) {
                Workouts clickedCar = myWorkouts2.get(position);
                Cursor cursor = myDb.getRow2(clickedCar.getID());
                //Long rowIDLong = cursor.getLong(DBAdapter.COL_ROWID);
                //String
                //String LongRowID = Long.toString(rowIDLong);
                //int IntRowID = Integer.parseInt(LongRowID);
                //String name = cursor.getString(DBAdapter.COL_NAME2);


                //int sets = cursor.getInt(IntRowID);
                //int
                if(clickedCar.getExercise() == "+"){

                    startActivity(new Intent(Exercises.this, AddExercise.class));

                }
                else{
                    sets = cursor.getInt(DBAdapter.COL_STUDENTNUM2);
                    reps = cursor.getInt(DBAdapter.COL_FAVCOLOUR1);
                    exerciseNamexercise = cursor.getString(DBAdapter.COL_NAME2);
                    LongRowID = cursor.getLong(DBAdapter.COL_ROWID2);
                    //ReturnSets();
                   // ReturnName(exerciseName);
                    //ReturnReps(reps);
                    startActivity(new Intent(Exercises.this, EditExercise.class));
                }

            }
        });
    }
    private void populateListView(){

        ArrayAdapter<Workouts> adapter = new MyListAdapter();
        ListView list = (ListView) findViewById(R.id.listViewExercises);
        list.setAdapter(adapter);



    }
    private class MyListAdapter extends ArrayAdapter<Workouts>{

        public MyListAdapter(){
            super(Exercises.this,R.layout.fragment_exercises, myWorkouts2);


        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Make sure we have a view to work with (may have been given null)
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.fragment_exercises, parent, false);
            }

            // Find the car to work with.
            Workouts currentCar = myWorkouts2.get(position);


            TextView exerciseText = (TextView) itemView.findViewById(R.id.item_txtExercise2);
            exerciseText.setText(currentCar.getExercise());


            return itemView;
        }

    }

    private void nextActivity(){
        Button btnCounterStart = (Button) findViewById(R.id.btnTest);

        btnCounterStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Exercises.this, Counter.class));
            }
        });


    }

    private void displayRecordSetz(Cursor cursor) {
        String message = "";
        // populate the message from the cursor
        //int LongRow = cursor.getInt(DBAdapter.COL_FAVCOLOUR2);
        String LongRowID = Long.toString(fromMainActivity.rowIDLong());
        int IntRowID = Integer.parseInt(LongRowID);
        // Reset cursor to start, checking to see if there's data:
        if (cursor.moveToFirst()) {

                do {
                    // Process the data:
                    int id = cursor.getInt(DBAdapter.COL_ROWID2);
                    String name = cursor.getString(DBAdapter.COL_NAME2);
                    int studentNumber = cursor.getInt(DBAdapter.COL_STUDENTNUM2);
                    int favColour = cursor.getInt(DBAdapter.COL_FAVCOLOUR1);
                    int RowNumber = cursor.getCount();
                    int LongRow = cursor.getInt(DBAdapter.COL_FAVCOLOUR2);
                    // Append data to the message:
                    message += " name =" + name

                            +"\n";
                    if(LongRow == IntRowID){
                    myWorkouts2.add(new Workouts(name,id));
                    }
                } while(cursor.moveToNext());

        }
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
            startActivity(new Intent(Exercises.this, MainActivity.class));
            Toast.makeText(Exercises.this, "Worked", Toast.LENGTH_LONG).show();
        }
        return super.onKeyDown(keyCode, event);
    }
}
