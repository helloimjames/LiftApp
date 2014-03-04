package com.example.liftapp;
//This is a test
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity {
    public static String workoutToEdit = "";
    public static Long rowIDLong;

    private List<Workouts> myWorkouts = new ArrayList<Workouts>();
    DBAdapter myDb;
    // tests

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openDB();
        populateWorkoutList();
        populateListView();
        registerClickCallback();
        registerLongClickCallback();
       // ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 50);
        //toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200);
    }
    private void openDB() {
        myDb = new DBAdapter(this);
        myDb.open();
    }
    private void populateWorkoutList() {
        myWorkouts.add(new Workouts("+",0,0,0));
        Cursor cursor = myDb.getAllRows();
        displayRecordSet(cursor);


    }

    public void displayRecordSet(Cursor cursor) {
        // Reset cursor to start, checking to see if there's data:
        if (cursor.moveToFirst()) {
            do {
                // Process the data:
                int id = cursor.getInt(DBAdapter.COL_ROWID_WORKOUT);
                String name = cursor.getString(DBAdapter.COL_NAME_WORKOUT);
                int RowNumber = cursor.getCount();
                myWorkouts.add(new Workouts(name,id,0,0));
            } while(cursor.moveToNext());
        }

        // Close the cursor to avoid a resource leak.
        cursor.close();


    }



    private void displayText(String message) {
        TextView textView = (TextView) findViewById(R.id.item_txtExercise);
        textView.setText(message);
    }

    // Event of regular click
    private void registerClickCallback() {
        ListView list = (ListView) findViewById(R.id.listViewWorkouts);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked,
                                    int position, long id) {

                Workouts clickedCar = myWorkouts.get(position);
                if(clickedCar.getExercise() == "+"){
                    workoutToEdit = "";
                    startActivity(new Intent(MainActivity.this, EditPage.class));

                }
                else{
                    Cursor cursorOfWorkout = myDb.getRow(clickedCar.getID());
                    rowIDLong = cursorOfWorkout.getLong(DBAdapter.COL_ROWID_WORKOUT);
                    startActivity(new Intent(MainActivity.this, Exercises.class));
                }
            }
        });
    }

    public static String getName(){
        return workoutToEdit;
    }

    public static Long rowIDLong(){
        return rowIDLong;
    }
    // Event of Long Click
    private void registerLongClickCallback() {
        ListView list = (ListView) findViewById(R.id.listViewWorkouts);
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {

                // needs the event of long clicking the + by accident
                Workouts clickedCar = myWorkouts.get(pos);
                if(clickedCar.getExercise() == "+"){
                    workoutToEdit = "";
                    startActivity(new Intent(MainActivity.this, EditPage.class));

                }
                else{
               // Workouts clickedCar = myWorkouts.get(pos);
                Cursor cursorOfWorkout = myDb.getRow(clickedCar.getID());
                workoutToEdit = cursorOfWorkout.getString(DBAdapter.COL_NAME_WORKOUT);
                rowIDLong = cursorOfWorkout.getLong(DBAdapter.COL_ROWID_WORKOUT);
                startActivity(new Intent(MainActivity.this, EditWorkout.class));
                }



                return true;
            }
        });
    }
    private void populateListView(){

        ArrayAdapter<Workouts> adapter = new MyListAdapter();
        ListView list = (ListView) findViewById(R.id.listViewWorkouts);
        list.setAdapter(adapter);



    }
    private class MyListAdapter extends ArrayAdapter<Workouts>{

        public MyListAdapter(){
            super(MainActivity.this,R.layout.item_view, myWorkouts);

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Make sure we have a view to work with (may have been given null)
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.item_view, parent, false);
            }

            // Find the car to work with.
            Workouts currentCar = myWorkouts.get(position);

            // Fill the view



            TextView exerciseText = (TextView) itemView.findViewById(R.id.item_txtExercise);
            exerciseText.setText(currentCar.getExercise());



            return itemView;
        }


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

}
