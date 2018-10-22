package com.csci4176.group13.hereattendance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.csci4176.group13.hereattendance.Fragments.Professor.ProfAttendanceHistoryFragment;
import com.csci4176.group13.hereattendance.Fragments.Student.StudentAttendanceHistoryFragment;
import com.csci4176.group13.hereattendance.Fragments.Student.QRScannerFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String user;
    // this needs to be passed around activities and fragments
    Bundle loginActivityBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent loginActivityIntent = getIntent();
        loginActivityBundle = loginActivityIntent.getExtras();

        if (loginActivityBundle != null) {
            user = (String) loginActivityBundle.get("user");
        }

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

        // show/hide content depending on user
        if ( user.equals("professor") ) {
            fm.beginTransaction().replace(R.id.content, new ProfAttendanceHistoryFragment()).commit();
            Menu navMenu = navigationView.getMenu();
            navMenu.findItem(R.id.nav_qrscanner).setVisible(false);
        } else {
            fm.beginTransaction().replace(R.id.content, new StudentAttendanceHistoryFragment()).commit();
        }
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
            currentPage = new StudentAttendanceHistoryFragment();
            getSupportActionBar().setTitle("Attendance History");
        }
        else if (id == R.id.nav_qrscanner) {
            currentPage = new QRScannerFragment();
            getSupportActionBar().setTitle("QR Scanner");
        }
        else if (id == R.id.nav_logout) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            return true;
        }

        FragmentManager fm = getSupportFragmentManager();
        currentPage.setArguments(loginActivityBundle);
        fm.beginTransaction().replace(R.id.content, currentPage).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
