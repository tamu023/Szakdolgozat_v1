package com.example.bloodline.szakdolgozat_v1.Activities;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.bloodline.szakdolgozat_v1.Fragments.AdminPanelFragment;
import com.example.bloodline.szakdolgozat_v1.Classes.Functions;
import com.example.bloodline.szakdolgozat_v1.Fragments.NewMealFragment;
import com.example.bloodline.szakdolgozat_v1.Fragments.PrepareNowFragment;
import com.example.bloodline.szakdolgozat_v1.Fragments.ProductTypeFragment;
import com.example.bloodline.szakdolgozat_v1.Fragments.ProfileFragment;
import com.example.bloodline.szakdolgozat_v1.Fragments.ShoppingListFragment;
import com.example.bloodline.szakdolgozat_v1.Fragments.StatisticsFragment;
import com.example.bloodline.szakdolgozat_v1.Fragments.StorageFragment;
import com.example.bloodline.szakdolgozat_v1.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Alapértelmezettként a profilt jeleníti meg
        ChangeFragment(R.id.mainframeplace, new ProfileFragment());

    }

    //vissza gomb kezelése
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            AlertDialog.Builder a_builder = new AlertDialog.Builder(MainActivity.this);
            a_builder.setMessage("Are you sure you want to leave the application?").setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Functions.clearAccdata();
                            Functions.getmAuth().signOut();
                            finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = a_builder.create();
            alert.setTitle("Exit Application");
            alert.show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //ellenörzi hogy van e internet elérés ha a felhasználó lerakta tálcára az alkalmazást és folytatná a munkamenetet
        if (!isNetworkAvailable()) {
            Functions.clearAccdata();
            Toast.makeText(MainActivity.this, "Network error.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_newmeal) {
            ChangeFragment(R.id.mainframeplace, new NewMealFragment());
        } else if (id == R.id.nav_prepare_now) {
            ChangeFragment(R.id.mainframeplace, new PrepareNowFragment());
        } else if (id == R.id.nav_profile) {
            ChangeFragment(R.id.mainframeplace, new ProfileFragment());
        } else if (id == R.id.nav_storage) {
            ChangeFragment(R.id.mainframeplace, new StorageFragment());
        } else if (id == R.id.nav_shoppingcart) {
            ChangeFragment(R.id.mainframeplace, new ShoppingListFragment());
        } else if (id == R.id.nav_addfood) {
            ChangeFragment(R.id.mainframeplace, new ProductTypeFragment());
        } else if (id == R.id.nav_statistic) {
            ChangeFragment(R.id.mainframeplace, new StatisticsFragment());
        } else if (id == R.id.nav_verify) {
            if (Functions.getAcctype()) {
                ChangeFragment(R.id.mainframeplace, new AdminPanelFragment());
            } else {
                Toast.makeText(MainActivity.this, "Not Authorized.", Toast.LENGTH_SHORT).show();
            }

        } else if (id == R.id.nav_logout) {
            //kijelentkezéskor törölje az osztályba betöltött adatokat
            Functions.clearAccdata();
            Functions.getmAuth().signOut();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_exit) {
            Functions.clearAccdata();
            Functions.getmAuth().signOut();
            finish();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void ChangeFragment(int position, Fragment fr) {
        Fragment startfragment = fr;
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(position, startfragment);
        ft.commit();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
