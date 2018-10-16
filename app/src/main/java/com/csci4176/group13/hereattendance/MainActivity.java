package com.csci4176.group13.hereattendance;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.csci4176.group13.hereattendance.Fragments.CurrCoursesFragment;
import com.csci4176.group13.hereattendance.Fragments.QRScannerFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        // Defaulting to the Attendance History fragment
        navigationView.setCheckedItem(R.id.nav_attendancehistory);
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.content, new CurrCoursesFragment()).commit();
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment currentPage = null;

        if (id == R.id.nav_attendancehistory) {
            currentPage = new CurrCoursesFragment();
            getSupportActionBar().setTitle("Attendance History");
        } else if (id == R.id.nav_qrscanner) {
            currentPage = new QRScannerFragment();
            getSupportActionBar().setTitle("QR Scanner");
        } else if (id == R.id.nav_logout) {

        }

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.content, currentPage).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
