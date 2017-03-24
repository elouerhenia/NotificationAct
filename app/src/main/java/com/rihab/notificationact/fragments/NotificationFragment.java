package com.rihab.notificationact.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rihab.notificationact.R;


public class NotificationFragment extends android.support.v4.app.Fragment {


    // Log tag
    private static final String TAG = NotificationFragment.class.getSimpleName();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public static NotificationFragment fragment;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_notification, container, false);

        fragment = this;
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setShowHideAnimationEnabled(false);


        return rootView;
    }





    public static NotificationFragment newInstance() {
        if (fragment == null){

            fragment = new NotificationFragment();
        }
        return fragment;
    }


}