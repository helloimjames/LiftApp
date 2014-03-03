package com.example.liftapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

public class EditExercise extends Activity {

    DBAdapter myDb;
    Exercises fromExerciseActivity = new Exercises();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_edit_exercise);

        openDB();
        btnUpdateExercise();
        btnStart();
        NumberPicker npSets = (NumberPicker) findViewById(R.id.npSetsForExercise);
        NumberPicker npReps = (NumberPicker) findViewById(R.id.npRepsForExercise);
        npSets.setMaxValue(100);
        npSets.setMinValue(0);
        npSets.setValue(fromExerciseActivity.ReturnSets());
        //npSets.setValue(reps);
        npReps.setMaxValue(100);
        npReps.setMinValue(0);
        npReps.setValue(fromExerciseActivity.ReturnReps());
        TextView txtNameOfExercise = (TextView) findViewById(R.id.txtNameOfExercise);
        txtNameOfExercise.setText(fromExerciseActivity.exerciseNamexercise);


    }

    private void btnUpdateExercise(){
        Button btnCounterStart = (Button) findViewById(R.id.btnUpdateExercise);

        btnCounterStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NumberPicker npSets = (NumberPicker) findViewById(R.id.npSetsForExercise);
                NumberPicker npReps = (NumberPicker) findViewById(R.id.npRepsForExercise);
                myDb.updateRow2(fromExerciseActivity.LongRowID, npSets.getValue(), npReps.getValue());
                finish();
            }
        });
    }
 
    private void openDB() {
        myDb = new DBAdapter(this);
        myDb.open();
    }

    private void btnStart(){
        Button btnCounterStart = (Button) findViewById(R.id.btnStartExercise);

        btnCounterStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                String LongRowID = Long.toString(fromExerciseActivity.longRowIDExercise());
                int IntRowID = Integer.parseInt(LongRowID);
                //myDb.insertRow3(fromExerciseActivity.ReturnName(),0,0,longRowIDExercise);
                startActivity(new Intent(EditExercise.this, Counter.class));

            }
        });


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            startActivity(new Intent(EditExercise.this, Exercises.class));
            //Toast.makeText(Exercises.this, "Worked", Toast.LENGTH_LONG).show();
        }
        return super.onKeyDown(keyCode, event);
    }

}
