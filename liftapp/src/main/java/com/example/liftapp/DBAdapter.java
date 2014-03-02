package com.example.liftapp;

/**
 * Created by james on 2/9/14.
 */
// ------------------------------------ DBADapter.java ---------------------------------------------

//


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


// TO USE:
// Change the package (at top) to match your project.
// Search for "TODO", and make the appropriate changes.
//TODO add date and time to Table3
public class DBAdapter {

    /////////////////////////////////////////////////////////////////////
    //	Constants & Data
    /////////////////////////////////////////////////////////////////////
    // For logging:
    private static final String TAG = "DBAdapter";


    // DB Fields
    public static final String KEY_ROWID_WORKOUT = "_id";
    public static final int COL_ROWID_WORKOUT = 0;
    public static final String KEY_NAME_WORKOUT = "name";
    public static final int COL_NAME_WORKOUT = 1;



    public static final String KEY_ROWID_EXERCISE = "_id";
    public static final int COL_ROWID_EXERCISE = 0;
    public static final String KEY_NAME_EXERCISE = "name_exercise";
    public static final int COL_NAME_EXERCISE = 1;
    public static final String KEY_EXERCISE_SETS = "exercise_sets";
    public static final int COL_EXERCISE_SETS = 2;
    public static final String KEY_EXERCISE_REPS = "exercise_reps";
    public static final int COL_EXERCISE_REPS = 3;
    public static final String KEY_OF_PARENT_WORKOUT_ID = "PARENT_WORKOUT_ID";
    public static final int COL_OF_PARENT_WORKOUT_ID = 4;
    public static final String KEY_SET_NUMBER = "set_number";
    public static final int COL_SET_NUMBER = 5;






    // Setup your field numbers here (0 = KEY_ROWID_WORKOUT, 1=...)


    public static final String KEY_ROWID_HISTORY = "_id";
    //public static final String KEY_NAME_HISTORY = "name";
    public static final String KEY_HISTORY_SETS= "sets";
    public static final String KEY_HISTORY_REPS= "reps";
    public static final String KEY_HISTORY_EXERCISE_ID= "PARENT_EXERCISE_ID";
    public static final String KEY_DATETIME = "date";




    public static final int COL_ROWID_HISTORY = 0;
    //public static final int COL_NAME_HISTORY = 1;
    public static final int COL_HISTORY_SETS = 1;
    public static final int COL_HISTORY_REPS = 2;
    public static final int COL_HISTORY_EXERCISE_ID= 3;
    public static final int COL_DATETIME = 4;

    public static final String[] ALL_KEYS = new String[] {KEY_ROWID_WORKOUT, KEY_NAME_WORKOUT};
    public static final String[] ALL_KEYS2 = new String[] {KEY_ROWID_EXERCISE, KEY_NAME_EXERCISE, KEY_EXERCISE_SETS, KEY_EXERCISE_REPS, KEY_OF_PARENT_WORKOUT_ID};
    public static final String[] ALL_KEYS3 = new String[] {KEY_ROWID_HISTORY, KEY_HISTORY_SETS, KEY_HISTORY_REPS,KEY_HISTORY_EXERCISE_ID, KEY_DATETIME, KEY_SET_NUMBER};
    // DB info: it's name, and the table we are using (just one).
    public static final String DATABASE_NAME = "MyDb";
    public static final String DATABASE_TABLE = "mainTable";
    public static final String DATABASE_TABLE2 = "mainTable2";
    public static final String DATABASE_TABLE3 = "mainTable3";

    // Track DB version if a new version of your app changes the format.
    public static final int DATABASE_VERSION = 3;

    protected static final String DATABASE_CREATE_SQL =
            "create table " + DATABASE_TABLE
                    + " (" + KEY_ROWID_WORKOUT + " integer primary key autoincrement, "
                    + KEY_NAME_WORKOUT + " text not null "
                    // + KEY_STUDENTNUM + " integer not null "
                    //+ KEY_FAVCOLOUR + " string not null "

                    + ");";

    protected static final String DATABASE_CREATE_SQL2 =
            "create table " + DATABASE_TABLE2
                    + " (" + KEY_ROWID_EXERCISE + " integer primary key autoincrement, "
                    + KEY_NAME_EXERCISE + " text not null, "
                    + KEY_EXERCISE_SETS + " integer not null, "
                    + KEY_EXERCISE_REPS + " integer not null, "
                    + KEY_OF_PARENT_WORKOUT_ID + " integer not null "

                    // Rest  of creation:
                    + ");";


    protected static final String DATABASE_CREATE_SQL3 =
            "create table " + DATABASE_TABLE3
                    + " (" + KEY_ROWID_HISTORY + " integer primary key autoincrement, "

			/*
			 * CHANGE 2:
			 */
                    //  Place your fields here!
                    // + KEY_{...} + " {type} not null"
                    //	- Key is the column name you created above.
                    //	- {type} is one of: text, integer, real, blob
                    //		(http://www.sqlite.org/datatype3.html)
                    //  - "not null" means it is a required field (must be given a value).
                    // NOTE: All must be comma separated (end of line!) Last one must have NO comma!!
                    //+ KEY_NAME_HISTORY + " text not null, "
                    + KEY_HISTORY_SETS + " integer not null, "
                    + KEY_HISTORY_REPS + " integer not null, "
                    + KEY_HISTORY_EXERCISE_ID + " integer not null, "
                    + KEY_DATETIME + " text not null, "
                    + KEY_SET_NUMBER + " integer not null "

                    // Rest  of creation:
                    + ");";


    // Context of application who uses us.



    // Context of application who uses us.
    private final Context context;

    private DatabaseHelper myDBHelper;
    private SQLiteDatabase db;

    /////////////////////////////////////////////////////////////////////
    //	Public methods:
    /////////////////////////////////////////////////////////////////////

    public DBAdapter(Context ctx) {
        this.context = ctx;
        myDBHelper = new DatabaseHelper(context);
    }

    // Open the database connection.
    public DBAdapter open() {
        db = myDBHelper.getWritableDatabase();
        return this;
    }

    // Close the database connection.
    public void close() {
        myDBHelper.close();
    }

    // Add a new set of values to the database.
    public long insertRow(String name){
    //public long insertRow(String name) {
		/*
		 * CHANGE 3:
		 */
        // Update data in the row with new fields.
        // Also change the function's arguments to be what you need!
        // Create row's data:
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME_WORKOUT, name);
        //initialValues.put(KEY_STUDENTNUM, studentNum);
        //initialValues.put(KEY_FAVCOLOUR, favColour);

        // Insert it into the database.
        return db.insert(DATABASE_TABLE, null, initialValues);
    }


    public long insertRow2(String name, int studentNum, int favColour, int favColour2){

        // Create row's data:
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME_EXERCISE, name);
        initialValues.put(KEY_EXERCISE_SETS, studentNum);
        initialValues.put(KEY_EXERCISE_REPS, favColour);
        initialValues.put(KEY_OF_PARENT_WORKOUT_ID, favColour2);

        // Insert it into the database.
        return db.insert(DATABASE_TABLE2, null, initialValues);
    }

    public long insertRow3( int sets, int reps, int id, int setNumber){

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        // Create row's data:
        ContentValues initialValues = new ContentValues();
        //initialValues.put(KEY_NAME_HISTORY, name);
        initialValues.put(KEY_HISTORY_SETS, sets);
        initialValues.put(KEY_HISTORY_REPS, reps);
        initialValues.put(KEY_HISTORY_EXERCISE_ID, id);
        initialValues.put(KEY_DATETIME, dateFormat.format(date));
        initialValues.put(KEY_SET_NUMBER, setNumber);
        // Insert it into the database.
        return db.insert(DATABASE_TABLE3, null, initialValues);
    }

    // Delete a row from the database, by rowId (primary key)
    public boolean deleteRow(long rowId) {
        String where = KEY_ROWID_WORKOUT + "=" + rowId;
        return db.delete(DATABASE_TABLE, where, null) != 0;
    }

    public boolean deleteRow2(long rowId) {
        String where = KEY_ROWID_EXERCISE + "=" + rowId;
        return db.delete(DATABASE_TABLE2, where, null) != 0;
    }

    public void deleteAll() {
        Cursor c = getAllRows();
        long rowId = c.getColumnIndexOrThrow(KEY_ROWID_WORKOUT);
        if (c.moveToFirst()) {
            do {
                deleteRow(c.getLong((int) rowId));
            } while (c.moveToNext());
        }
        c.close();
    }

    public void deleteAll2() {
        Cursor c = getAllRows2();
        long rowId = c.getColumnIndexOrThrow(KEY_ROWID_EXERCISE);
        if (c.moveToFirst()) {
            do {
                deleteRow2(c.getLong((int) rowId));
            } while (c.moveToNext());
        }
        c.close();
    }
    // Return all data in the database.
    public Cursor getAllRows() {
        String where = null;
        Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }
    public Cursor getAllRows2() {
        String where = null;
        Cursor c = 	db.query(true, DATABASE_TABLE2, ALL_KEYS2,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }
    public Cursor getAllRows3() {
        String where = null;
        Cursor c = 	db.query(true, DATABASE_TABLE3, ALL_KEYS3,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    // Get a specific row (by rowId)
    public Cursor getRow(long rowId) {
        String where = KEY_ROWID_WORKOUT + "=" + rowId;
        Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public Cursor getRow2(long rowId) {
        String where = KEY_ROWID_EXERCISE + "=" + rowId;
        Cursor c = 	db.query(true, DATABASE_TABLE2, ALL_KEYS2,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public Cursor getRow3(long rowId) {
        String where = KEY_ROWID_HISTORY + "=" + rowId;
        Cursor c = 	db.query(true, DATABASE_TABLE3, ALL_KEYS3,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }


    public Cursor getRowByString(String name) {
        String where = KEY_NAME_WORKOUT + "=" + name;
        Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }
    // Change an existing row to be equal to new data.
    //public boolean updateRow(long rowId, String name, int studentNum, String favColour) {
    public boolean updateRow(long rowId, String name) {
        String where = KEY_ROWID_WORKOUT + "=" + rowId;

		/*
		 * CHANGE 4:
		 */
        //  Update data in the row with new fields.
        // Also change the function's arguments to be what you need!
        // Create row's data:
        ContentValues newValues = new ContentValues();
        newValues.put(KEY_NAME_WORKOUT, name);
        //newValues.put(KEY_STUDENTNUM, studentNum);
        //newValues.put(KEY_FAVCOLOUR, favColour);

        // Insert it into the database.
        return db.update(DATABASE_TABLE, newValues, where, null) != 0;
    }
    public boolean updateRow2(long rowId,int sets, int reps) {
        String where = KEY_ROWID_EXERCISE + "=" + rowId;

		/*
		 * CHANGE 4:
		 */
        // Update data in the row with new fields.
        //  Also change the function's arguments to be what you need!
        // Create row's data:
        ContentValues newValues = new ContentValues();
        //newValues.put(KEY_NAME_WORKOUT, name);
        newValues.put(KEY_EXERCISE_SETS, sets);
        newValues.put(KEY_EXERCISE_REPS, reps);

        // Insert it into the database.
        return db.update(DATABASE_TABLE2, newValues, where, null) != 0;
    }


    public boolean updateRow3(long rowId,int reps) {
        String where = KEY_ROWID_HISTORY + "=" + rowId;

		/*
		 * CHANGE 4:
		 */
        // Update data in the row with new fields.
        //  Also change the function's arguments to be what you need!
        // Create row's data:
        ContentValues newValues = new ContentValues();
        //newValues.put(KEY_NAME_WORKOUT, name);
        //newValues.put(KEY_EXERCISE_SETS, sets);
        newValues.put(KEY_HISTORY_REPS, reps);

        // Insert it into the database.
        return db.update(DATABASE_TABLE3, newValues, where, null) != 0;
    }
    /////////////////////////////////////////////////////////////////////
    //	Private Helper Classes:
    /////////////////////////////////////////////////////////////////////

    /**
     * Private class which handles database creation and upgrading.
     * Used to handle low-level database access.
     */
    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase _db) {
            _db.execSQL(DATABASE_CREATE_SQL);
            _db.execSQL(DATABASE_CREATE_SQL2);
            _db.execSQL(DATABASE_CREATE_SQL3);


        }

        @Override
        public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading application's database from version " + oldVersion
                    + " to " + newVersion + ", which will destroy all old data!");

            // Destroy old database:
            _db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            _db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE2);
            _db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE3);

            // Recreate new database:
            onCreate(_db);
        }
    }
}
