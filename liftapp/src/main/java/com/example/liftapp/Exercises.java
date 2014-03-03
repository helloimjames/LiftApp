package com.example.liftapp;

//import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
        registerLongClickCallback();
        populateWorkoutList();
        populateListView();
        registerClickCallback();

        //registerLongClickCallback();

    }

    public void populateWorkoutList() {
        myWorkouts2.add(new Workouts("+",0,0));
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
                //Long rowIDLong = cursor.getLong(DBAdapter.COL_ROWID_WORKOUT);
                //String
                //String LongRowID = Long.toString(rowIDLong);
                //int IntRowID = Integer.parseInt(LongRowID);
                //String name = cursor.getString(DBAdapter.COL_NAME_EXERCISE);


                //int sets = cursor.getInt(IntRowID);
                //int
                if(clickedCar.getExercise() == "+"){

                    startActivity(new Intent(Exercises.this, AddExercise.class));

                }
                else{
                    sets = cursor.getInt(DBAdapter.COL_EXERCISE_SETS);
                    reps = cursor.getInt(DBAdapter.COL_EXERCISE_REPS);
                    exerciseNamexercise = cursor.getString(DBAdapter.COL_NAME_EXERCISE);
                    LongRowID = cursor.getLong(DBAdapter.COL_ROWID_EXERCISE);

                    //startActivity(new Intent(Exercises.this, EditExercise.class));
                    startActivity(new Intent(Exercises.this, SetsView.class));
                }

            }
        });
    }
    private void populateListView(){

        ArrayAdapter<Workouts> adapter = new MyListAdapter();
        ListView list = (ListView) findViewById(R.id.listViewExercises);
        list.setAdapter(adapter);



    }
    private void registerLongClickCallback() {
        ListView list = (ListView) findViewById(R.id.listViewExercises);
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {

                // needs the event of long clicking the + by accident
                Workouts clickedCar = myWorkouts2.get(pos);

                startActivity(new Intent(Exercises.this, EditExercise.class));




                return true;
            }
        });
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



    private void displayRecordSetz(Cursor cursor) {
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
                    myWorkouts2.add(new Workouts(name,id,0));
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
            //Toast.makeText(Exercises.this, "Worked", Toast.LENGTH_LONG).show();
        }
        return super.onKeyDown(keyCode, event);
    }
}
