package com.example.liftapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by james on 2/9/14.
 */
public class Incrementor {

    int newNumberValue;
    public Integer AddOne(){
        newNumberValue++;
        return newNumberValue;
    }




}

