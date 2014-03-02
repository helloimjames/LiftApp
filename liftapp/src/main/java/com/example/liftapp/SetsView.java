package com.example.liftapp;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SetsView extends Activity {
    Exercises fromExerciseActivity = new Exercises();
    DBAdapter myDb;
    private List<Workouts> myWorkouts2 = new ArrayList<Workouts>();
    public static int SetNumber;
    public static long returnedHistoryIDORNOT = 0;
    public static long SetNumberRowId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sets_view);
        registerClickCallback();

        //populateWorkoutList();
        openDB();
        TextView txtSetsSpecified = (TextView) findViewById(R.id.txtSetsSpecified);
        txtSetsSpecified.setText(fromExerciseActivity.ReturnName());
        populateListView();

    }

    private void populateListView(){

        ArrayAdapter<Workouts> adapter = new MyListAdapter();
        int numberOfSets = fromExerciseActivity.ReturnSets();
        int numberOfReps= fromExerciseActivity.ReturnReps();
        int counter = 1;
        numberOfSets += 1;
        while(counter != numberOfSets){
            String numberOfSets2 = ""+counter;
            myWorkouts2.add(new Workouts(numberOfSets2,numberOfReps,SetNumber));

            counter++;
        }


        ListView list = (ListView) findViewById(R.id.lvSetsList);
        list.setAdapter(adapter);



    }
    private class MyListAdapter extends ArrayAdapter<Workouts>{

        public MyListAdapter(){
            super(SetsView.this,R.layout.sets_view, myWorkouts2);


        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Make sure we have a view to work with (may have been given null)
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.sets_view, parent, false);
            }

            // Find the car to work with.
            Workouts currentCar = myWorkouts2.get(position);


            TextView exerciseText = (TextView) itemView.findViewById(R.id.tvSetNumber);
            exerciseText.setText(currentCar.getExercise());

            TextView exerciseText2 = (TextView) itemView.findViewById(R.id.tvRepsSpecified);
            String b = ""+ currentCar.getID();
            exerciseText2.setText(b);



            return itemView;
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

    public static int ReturnSetNumber() {

       return SetNumber;
    }

    public static long ReturnSetNumberRowId() {

        return SetNumberRowId;
    }

    private void registerClickCallback() {

        ListView list = (ListView) findViewById(R.id.lvSetsList);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked,int position, long id) {
                Workouts clickedCar = myWorkouts2.get(position);


                SetNumber = clickedCar.getSet();
                SetNumber= position+1;
                Cursor cursor = myDb.getAllRows3();

                if (checkForExistingHistory(cursor) > 0){
                    startActivity(new Intent(SetsView.this, Counter.class));
                    SetNumberRowId = returnedHistoryIDORNOT;
                    Toast.makeText(SetsView.this, "in if "+String.valueOf(SetNumberRowId), Toast.LENGTH_LONG).show();
                }

                else{
                    String LongRowID = Long.toString(fromExerciseActivity.IntRowID());
                    int IntRowID = Integer.parseInt(LongRowID);
                    SetNumberRowId = myDb.insertRow3(0, 0, IntRowID, SetNumber);
                    Toast.makeText(SetsView.this, "in else "+String.valueOf(SetNumberRowId), Toast.LENGTH_LONG).show();
                    //String a = ""+ SetNumberRowId;
                    //Toast.makeText(SetsView.this, a, Toast.LENGTH_LONG).show();
                    startActivity(new Intent(SetsView.this, Counter.class));
                }
            }


        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {

            startActivity(new Intent(SetsView.this, Exercises.class));
            //Toast.makeText(Counter.this, "Worked", Toast.LENGTH_LONG).show();
        }
        return super.onKeyDown(keyCode, event);
    }



    private long checkForExistingHistory(Cursor cursor) {

        String message = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String currentDate = dateFormat.format(date);
        //long returnedHistoryIDORNOT;
        if (cursor.moveToFirst()) {

            do {
                // Process the data:

                int id = cursor.getInt(DBAdapter.COL_HISTORY_EXERCISE_ID);
                String date2 = cursor.getString(DBAdapter.COL_DATETIME);
                int setNumberFromHistory = cursor.getInt(DBAdapter.COL_SET_NUMBER);
                long historyLongID = cursor.getInt(DBAdapter.COL_ROWID_HISTORY);

                message += " date =" + date

                        +"\n";
                if(currentDate.equals(date2) && id == SetNumber && setNumberFromHistory == fromExerciseActivity.IntRowID() ){

                    Toast.makeText(SetsView.this,"all match will grab id of "+ String.valueOf(returnedHistoryIDORNOT) , Toast.LENGTH_LONG).show();
                    returnedHistoryIDORNOT = historyLongID;
                    break;
                    //TODO its when you go back it fucks up

                }


            } while(cursor.moveToNext());

        }

        return returnedHistoryIDORNOT;
    }

}