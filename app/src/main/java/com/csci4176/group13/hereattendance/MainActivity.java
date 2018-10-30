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
import com.csci4176.group13.hereattendance.Fragments.Professor.QRGeneratorFragment;
import com.csci4176.group13.hereattendance.Fragments.Student.StudentAttendanceHistoryFragment;
import com.csci4176.group13.hereattendance.Fragments.Student.QRScannerFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseUser signedInUser = FirebaseAuth.getInstance().getCurrentUser();
    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (signedInUser != null)
            user = signedInUser.getEmail();

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
        if ( user.equals("professor@here.com") ) {
            fm.beginTransaction().replace(R.id.content, new ProfAttendanceHistoryFragment()).commit();
            Menu navMenu = navigationView.getMenu();
            navMenu.findItem(R.id.nav_qrscanner).setVisible(false);
            navMenu.findItem(R.id.nav_qrgenerator).setVisible(true);
        } else {
            fm.beginTransaction().replace(R.id.content, new StudentAttendanceHistoryFragment()).commit();
            Menu navMenu = navigationView.getMenu();
            navMenu.findItem(R.id.nav_qrscanner).setVisible(true);
            navMenu.findItem(R.id.nav_qrgenerator).setVisible(false);
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
        else if (id == R.id.nav_qrgenerator) {
            currentPage = new QRGeneratorFragment();
            getSupportActionBar().setTitle("QR Generator");
        }
        else if (id == R.id.nav_logout) {
            // Attempt logout
            try {
                FirebaseAuth.getInstance().signOut();
            }
            catch(Exception e) {
                e.printStackTrace();
                return false;
            }
            // If successful go back to LoginActivity
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            return true;
        }

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.content, currentPage).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
