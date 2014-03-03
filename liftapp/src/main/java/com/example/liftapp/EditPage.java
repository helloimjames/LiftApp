package com.example.liftapp;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class EditPage extends ActionBarActivity {

    MainActivity fromMainActivity = new MainActivity();
    DBAdapter myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_edit_page);
        openDB();



        //DeleteCurrentRecord();
        String a = fromMainActivity.getName();
        TextView textView = (TextView) findViewById(R.id.editTxtNewWorkout);
        textView.setText(a);

    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.KEYCODE_ENTER && e.getAction()==0) {
            Toast.makeText(EditPage.this, "You hit enter", Toast.LENGTH_LONG).show();
            TextView editTxtNewWorkout = (TextView) findViewById(R.id.editTxtNewWorkout);
            String TxtNewWorkout = editTxtNewWorkout.getText().toString();

            if(TxtNewWorkout.length() ==0){

                Toast.makeText(EditPage.this,"Add a name to the Workout" , Toast.LENGTH_LONG).show();
                //return true;
            }

            else if(TxtNewWorkout.length() > 0){

                myDb.insertRow(TxtNewWorkout);
                Cursor cursor = myDb.getAllRows();
                displayRecordSet(cursor);

                Toast.makeText(EditPage.this,"Added "+TxtNewWorkout , Toast.LENGTH_LONG).show();

                startActivity(new Intent(EditPage.this, MainActivity.class));
                //finish();
            }


            //return true;
        }
        //return super.dispatchKeyEvent(e);
        return false;
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
                int id = cursor.getInt(DBAdapter.COL_ROWID_WORKOUT);
                String name = cursor.getString(DBAdapter.COL_NAME_WORKOUT);
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
