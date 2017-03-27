package com.rihab.notificationact.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rihab.notificationact.dao.ActuJsonParser;
import com.rihab.notificationact.adaptater.ActualiteAdapter;
import com.rihab.notificationact.R;
import com.rihab.notificationact.model.ActualiteModel;
import com.rihab.notificationact.utils.EndPoints;

import java.util.List;


public class ActFragment extends android.support.v4.app.Fragment implements SwipeRefreshLayout.OnRefreshListener{


    // Log tag
    private static final String TAG = ActFragment.class.getSimpleName();

    //Declaration
    List<ActualiteModel> actualitesList;
    protected ListView lv;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public static ActFragment fragment;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_actualite, container, false);

        fragment = this;
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setShowHideAnimationEnabled(false);




        recyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);
        postrequestDataa(EndPoints.URL_GET_ACTUALITE);

        swipeRefreshLayout.setOnRefreshListener(this);

        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);

                                        postrequestDataa(EndPoints.URL_GET_ACTUALITE);
                                    }
                                }
        );




        return rootView;
    }







    @Override
    public void onRefresh() {

        postrequestDataa(EndPoints.URL_GET_ACTUALITE);
    }

    //----------------- Méthode pour récuperer et envoyer des données en utilisant Volley----------------
    public void postrequestDataa(String uri) {
        swipeRefreshLayout.setRefreshing(true);
        StringRequest postRequest = new StringRequest(Request.Method.POST, uri,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        actualitesList = ActuJsonParser.parseActu(response);
                        int c = actualitesList.size();
                        Toast.makeText(getActivity().getApplicationContext(), "total " + c, Toast.LENGTH_SHORT).show();
						/*getSupportActionBar().setTitle("Total : "+ c + " recette(s)");
						if(c >0){
							updateDisplay();
						}else {
							Toast.makeText(getApplicationContext(), "Cette recette n'est pas encore disponible ", Toast.LENGTH_SHORT).show();
						}*/
                        //detailList = RecetteJsonParser.parseDetail(response);
                        updateDisplay();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getActivity().getApplicationContext(),
                        "failed to insert", Toast.LENGTH_SHORT).show();
            }
        }) ;
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(postRequest);

    }
    //----------------- Méthode pour Afficher la liste des actualités ---------------
    protected void updateDisplay() {
        final ActualiteAdapter adapter = new ActualiteAdapter(getActivity(), actualitesList);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);


    }
    //----------------- Méthode afficher les détails de l'actualité----------------







    public static ActFragment newInstance() {
        if (fragment == null){

            fragment = new ActFragment();
        }
        return fragment;
    }


}