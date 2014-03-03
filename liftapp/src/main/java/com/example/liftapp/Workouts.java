package com.example.liftapp;

/**
 * Created by james on 2/8/14.
 */
public class Workouts {
    private String exercise;
    private int set;
    private int rep;
    private long id;
    

    public Workouts(String exercise, int id, int setNumber, int repsDone) {
        super();
        this.exercise = exercise;
        this.set = setNumber;
        this.rep = repsDone;
        this.id = id;
        
    }



    public String getExercise() {
        return exercise;
    }


    public int getSet() {
        return set;
    }
    public int getRep() {
        return rep;
    }

    public long getID() {
        return id;
    }

}