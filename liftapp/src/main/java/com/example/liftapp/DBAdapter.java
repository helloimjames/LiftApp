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
    public static final String KEY_ROWID = "_id";
    public static final int COL_ROWID = 0;
    /*
     * CHANGE 1:
     */
    //
    public static final String KEY_NAME = "name";
    public static final String KEY_STUDENTNUM = "studentnum";
    public static final String KEY_FAVCOLOUR = "favcolour";



    public static final String KEY_ROWID2 = "_id";
    public static final String KEY_NAME2 = "name";
    public static final String KEY_STUDENTNUM2 = "studentnum";
    public static final String KEY_FAVCOLOUR1 = "favcolour";
    public static final String KEY_FAVCOLOUR2 = "favcolour2";

    // Setup your field numbers here (0 = KEY_ROWID, 1=...)
    public static final int COL_NAME = 1;
    public static final int COL_STUDENTNUM = 2;
    public static final int COL_FAVCOLOUR = 3;

    public static final int COL_ROWID2 = 0;
    public static final int COL_NAME2 = 1;
    public static final int COL_STUDENTNUM2 = 2;
    public static final int COL_FAVCOLOUR1 = 3;
    public static final int COL_FAVCOLOUR2 = 4;

    public static final String KEY_ROWID3 = "_id";
    public static final String KEY_NAME3 = "name";
    public static final String KEY_HISTORY_SETS= "sets";
    public static final String KEY_HISTORY_REPS= "reps";
    public static final String KEY_HISTORY_EXERCISE_ID= "id";
    public static final int COL_ROWID3 = 0;
    public static final int COL_NAME3 = 1;
    public static final int COL_HISTORY_SETS = 2;
    public static final int COL_HISTORY_REPS = 3;
    public static final int COL_HISTORY_EXERCISE_ID= 4;

    public static final String[] ALL_KEYS = new String[] {KEY_ROWID, KEY_NAME, KEY_STUDENTNUM, KEY_FAVCOLOUR};
    public static final String[] ALL_KEYS2 = new String[] {KEY_ROWID2, KEY_NAME2, KEY_STUDENTNUM2, KEY_FAVCOLOUR1,KEY_FAVCOLOUR2};
    public static final String[] ALL_KEYS3 = new String[] {KEY_ROWID3, KEY_NAME3, KEY_HISTORY_SETS, KEY_HISTORY_REPS,KEY_HISTORY_EXERCISE_ID};
    // DB info: it's name, and the table we are using (just one).
    public static final String DATABASE_NAME = "MyDb";
    public static final String DATABASE_TABLE = "mainTable";
    public static final String DATABASE_TABLE2 = "mainTable2";
    public static final String DATABASE_TABLE3 = "mainTable3";

    // Track DB version if a new version of your app changes the format.
    public static final int DATABASE_VERSION = 3;

    protected static final String DATABASE_CREATE_SQL2 =
            "create table " + DATABASE_TABLE2
                    + " (" + KEY_ROWID2 + " integer primary key autoincrement, "

			/*
			 * CHANGE 2:
			 */
                    //Place your fields here!
                    // + KEY_{...} + " {type} not null"
                    //	- Key is the column name you created above.
                    //	- {type} is one of: text, integer, real, blob
                    //		(http://www.sqlite.org/datatype3.html)
                    //  - "not null" means it is a required field (must be given a value).
                    // NOTE: All must be comma separated (end of line!) Last one must have NO comma!!
                    + KEY_NAME2 + " text not null, "
                    + KEY_STUDENTNUM2 + " integer not null, "
                    + KEY_FAVCOLOUR1 + " integer not null, "
                    + KEY_FAVCOLOUR2 + " integer not null "

                    // Rest  of creation:
                    + ");";

    protected static final String DATABASE_CREATE_SQL =
            "create table " + DATABASE_TABLE
                    + " (" + KEY_ROWID + " integer primary key autoincrement, "

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
                    + KEY_NAME + " text not null, "
                    + KEY_STUDENTNUM + " integer not null, "
                    + KEY_FAVCOLOUR + " string not null "

                    // Rest  of creation:
                    + ");";
    protected static final String DATABASE_CREATE_SQL3 =
            "create table " + DATABASE_TABLE3
                    + " (" + KEY_ROWID3 + " integer primary key autoincrement, "

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
                    + KEY_NAME3 + " text not null, "
                    + KEY_HISTORY_SETS + " integer not null, "
                    + KEY_HISTORY_REPS + " integer not null, "
                    + KEY_HISTORY_EXERCISE_ID + " integer not null "

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
    public long insertRow(String name, int studentNum, String favColour){
    //public long insertRow(String name) {
		/*
		 * CHANGE 3:
		 */
        // Update data in the row with new fields.
        // Also change the function's arguments to be what you need!
        // Create row's data:
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_STUDENTNUM, studentNum);
        initialValues.put(KEY_FAVCOLOUR, favColour);

        // Insert it into the database.
        return db.insert(DATABASE_TABLE, null, initialValues);
    }


    public long insertRow2(String name, int studentNum, int favColour, int favColour2){

        // Create row's data:
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME2, name);
        initialValues.put(KEY_STUDENTNUM2, studentNum);
        initialValues.put(KEY_FAVCOLOUR1, favColour);
        initialValues.put(KEY_FAVCOLOUR2, favColour2);

        // Insert it into the database.
        return db.insert(DATABASE_TABLE2, null, initialValues);
    }

    public long insertRow3(String name, int sets, int reps, int id){

        // Create row's data:
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME3, name);
        initialValues.put(KEY_HISTORY_SETS, sets);
        initialValues.put(KEY_HISTORY_REPS, reps);
        initialValues.put(KEY_HISTORY_EXERCISE_ID, id);

        // Insert it into the database.
        return db.insert(DATABASE_TABLE3, null, initialValues);
    }

    // Delete a row from the database, by rowId (primary key)
    public boolean deleteRow(long rowId) {
        String where = KEY_ROWID + "=" + rowId;
        return db.delete(DATABASE_TABLE, where, null) != 0;
    }

    public boolean deleteRow2(long rowId) {
        String where = KEY_ROWID2 + "=" + rowId;
        return db.delete(DATABASE_TABLE2, where, null) != 0;
    }

    public void deleteAll() {
        Cursor c = getAllRows();
        long rowId = c.getColumnIndexOrThrow(KEY_ROWID);
        if (c.moveToFirst()) {
            do {
                deleteRow(c.getLong((int) rowId));
            } while (c.moveToNext());
        }
        c.close();
    }

    public void deleteAll2() {
        Cursor c = getAllRows2();
        long rowId = c.getColumnIndexOrThrow(KEY_ROWID2);
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

    // Get a specific row (by rowId)
    public Cursor getRow(long rowId) {
        String where = KEY_ROWID + "=" + rowId;
        Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public Cursor getRow2(long rowId) {
        String where = KEY_ROWID2 + "=" + rowId;
        Cursor c = 	db.query(true, DATABASE_TABLE2, ALL_KEYS2,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public Cursor getRow3(long rowId) {
        String where = KEY_ROWID3 + "=" + rowId;
        Cursor c = 	db.query(true, DATABASE_TABLE3, ALL_KEYS3,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }


    public Cursor getRowByString(String name) {
        String where = KEY_NAME + "=" + name;
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
        String where = KEY_ROWID + "=" + rowId;

		/*
		 * CHANGE 4:
		 */
        //  Update data in the row with new fields.
        // Also change the function's arguments to be what you need!
        // Create row's data:
        ContentValues newValues = new ContentValues();
        newValues.put(KEY_NAME, name);
        //newValues.put(KEY_STUDENTNUM, studentNum);
        //newValues.put(KEY_FAVCOLOUR, favColour);

        // Insert it into the database.
        return db.update(DATABASE_TABLE, newValues, where, null) != 0;
    }
    public boolean updateRow2(long rowId,int sets, int reps) {
        String where = KEY_ROWID2 + "=" + rowId;

		/*
		 * CHANGE 4:
		 */
        // Update data in the row with new fields.
        //  Also change the function's arguments to be what you need!
        // Create row's data:
        ContentValues newValues = new ContentValues();
        //newValues.put(KEY_NAME, name);
        newValues.put(KEY_STUDENTNUM2, sets);
        newValues.put(KEY_FAVCOLOUR1, reps);

        // Insert it into the database.
        return db.update(DATABASE_TABLE2, newValues, where, null) != 0;
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
