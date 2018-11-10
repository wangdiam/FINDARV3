package com.findar_tech.findarv3.Activities;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.findar_tech.findarv3.Fragments.HelpFragment;
import com.findar_tech.findarv3.Fragments.LearnFragment;
import com.findar_tech.findarv3.Fragments.MusicFragment;
import com.findar_tech.findarv3.R;
import com.findar_tech.findarv3.Services.NewBackgroundMusicService;

public class MainActivity extends AppCompatActivity implements
        HelpFragment.OnHelpFragmentInteractionListener,
        LearnFragment.OnLearnFragmentInteractionListener,
        MusicFragment.OnMusicFragmentInteractionListener, NavigationView.OnNavigationItemSelectedListener {
    private Fragment currentFragment;
    private static final String MUSICFRAGMENT = "MUSICFRAGMENT";
    private static final String LEARNFRAGMENT = "LEARNFRAGMENT";
    private static final String HELPFRAGMENT = "HELPFRAGMENT";
    public static boolean isServiceOn = false;
    public static int songProgress = 0;
    private BottomNavigationView navigation;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
    }

    private void loadFragment(Fragment fragment, String tag) {
        if (!fragment.equals(currentFragment)) {
            getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.animator.fade_in,
                    android.R.animator.fade_out,android.R.animator.fade_in,
                    android.R.animator.fade_out).replace(R.id.main_layout, fragment,tag).commit();
            currentFragment = fragment;
        }
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_music:
                    fragment = getSupportFragmentManager().findFragmentByTag(MUSICFRAGMENT);
                    if (fragment == null ) {
                        fragment = new MusicFragment();
                    }
                    getFragmentManager().popBackStack();
                    loadFragment(fragment,MUSICFRAGMENT);
                    return true;
                case R.id.navigation_learn:
                    fragment = getSupportFragmentManager().findFragmentByTag(LEARNFRAGMENT);
                    if (fragment == null ) {
                        fragment = new LearnFragment();
                    }
                    getFragmentManager().popBackStack();
                    loadFragment(fragment,LEARNFRAGMENT);
                    return true;
                case R.id.navigation_help:
                    fragment = new HelpFragment();
                    getFragmentManager().popBackStack();
                    loadFragment(fragment,HELPFRAGMENT);
                    return true;
            }
            return false;
        }
    };

    public class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // Here you have the received broadcast
            // And if you added extras to the intent get them here too
            // this needs some null checks
            Log.v("RECIEVEDPROGRESS","CHECK");
            Bundle b = intent.getExtras();
            if (!b.isEmpty()) songProgress = b.getInt("SONGPROGRESS");
            ///do something with someDouble
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(MUSICFRAGMENT);
        if (fragment == null ) {
            fragment = new MusicFragment();
        }

        getFragmentManager().popBackStack();
        loadFragment(fragment,MUSICFRAGMENT);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_home){
            navigation.getMenu().getItem(0).setChecked(true);
        } else if (id == R.id.nav_share) {
            String url = "http://www.google.com";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        } else if (id == R.id.nav_settings) {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            dialogBuilder.setMessage("Are you sure you want to quit?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    stopService(new Intent(getApplicationContext(), NewBackgroundMusicService.class));
                    finishAffinity();
                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            Dialog dialog = dialogBuilder.create();
            dialog.show();

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onLearnFragmentInteraction() {

    }

}
