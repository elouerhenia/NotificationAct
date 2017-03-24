package com.rihab.notificationact.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.rihab.notificationact.R;
import com.rihab.notificationact.utils.CheckNetwork;


public class ActFragment extends android.support.v4.app.Fragment {


    // Log tag
    private static final String TAG = ActFragment.class.getSimpleName();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public static ActFragment fragment;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_act, container, false);

        fragment = this;
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setShowHideAnimationEnabled(false);


        return rootView;
    }





    public static ActFragment newInstance() {
        if (fragment == null){

            fragment = new ActFragment();
        }
        return fragment;
    }


}