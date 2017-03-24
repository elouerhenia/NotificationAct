package com.rihab.notificationact.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rihab.notificationact.R;


public class ProfilFragment extends android.support.v4.app.Fragment {


    // Log tag
    private static final String TAG = ProfilFragment.class.getSimpleName();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public static ProfilFragment fragment;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profil, container, false);

        fragment = this;
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setShowHideAnimationEnabled(false);


        return rootView;
    }





    public static ProfilFragment newInstance() {
        if (fragment == null){

            fragment = new ProfilFragment();
        }
        return fragment;
    }


}