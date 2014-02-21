package com.example.liftapp;

import android.content.Intent;
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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class Counter extends ActionBarActivity {

    DBAdapter myDb;

    private Incrementor mIncrementor = new Incrementor();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_counter);

        final TextView txtCounterReps = (TextView) findViewById(R.id.txtCounterReps);
        Button upButton = (Button)findViewById(R.id.btnUp);


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


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            startActivity(new Intent(Counter.this, EditExercise.class));
            Toast.makeText(Counter.this, "Worked", Toast.LENGTH_LONG).show();
        }
        return super.onKeyDown(keyCode, event);
    }
}



