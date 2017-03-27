package com.rihab.notificationact.views;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.rihab.notificationact.R;
import com.rihab.notificationact.fragments.ActFragment;
import com.rihab.notificationact.fragments.AddActFragment;
import com.rihab.notificationact.fragments.MessagerieFragment;
import com.rihab.notificationact.fragments.NotificationFragment;
import com.rihab.notificationact.fragments.ProfilFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    public static int selectedPosition = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Action bar
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onStart() {
        super.onStart();
        selectFragment(selectedPosition);

    }







    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Navigation entre fragment.
        int id = item.getItemId();

        if (id == R.id.nav_camara) {
            // Handle the camera action
            selectFragment(0);
        } else if (id == R.id.nav_gallery) {
            selectFragment(1);

        } else if (id == R.id.nav_slideshow) {
            selectFragment(2);

        } else if (id == R.id.nav_manage) {
            selectFragment(3);

        } else if (id == R.id.nav_share) {
            selectFragment(4);

        } else if (id == R.id.nav_send) {
            selectFragment(5);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




    public void selectFragment(int position) {

        selectedPosition = position;
        Fragment fragment = null;
        switch (position) {
            case 0:
                //mTitleTextView.setText("Carte");
                clearSelection();
                //btn_home.setSelected(true);
                ActFragment actfragment = ActFragment.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, actfragment).commit();
                break;
            case 1:
                //mTitleTextView.setText("Liste");
                clearSelection();
                //btn_list_cgc.setSelected(true);
                NotificationFragment notiffragment = NotificationFragment.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, notiffragment).commit();
                break;
            case 2:
                //mTitleTextView.setText("Qr code");
                clearSelection();
                //btn_qr_code.setSelected(true);
                MessagerieFragment msgfragment = MessagerieFragment.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, msgfragment).commit();
                break;
            case 3:
                //mTitleTextView.setText("Actualités");
                clearSelection();
                //btn_news.setSelected(true);
                AddActFragment listecgcfragment = AddActFragment.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, listecgcfragment).commit();
                break;
            case 4:
                //mTitleTextView.setText("Batterie");
                clearSelection();
                //btn_battery.setSelected(true);
                ProfilFragment batteryfragment = ProfilFragment.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, batteryfragment).commit();
                break;
        }





    }

    public void clearSelection() {
       /* btn_home.setSelected(false);
        btn_news.setSelected(false);
        btn_qr_code.setSelected(false);
        btn_list_cgc.setSelected(false);
        btn_battery.setSelected(false);
        */
    }









}
