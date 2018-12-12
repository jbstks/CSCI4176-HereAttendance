package com.csci4176.group13.hereattendance;

import android.content.Intent;
import android.media.Image;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.csci4176.group13.hereattendance.Fragments.Professor.ProfAttendanceHistoryFragment;
import com.csci4176.group13.hereattendance.Fragments.Student.Maps;
import com.csci4176.group13.hereattendance.Fragments.Student.StudentAttendanceHistoryFragment;
import com.csci4176.group13.hereattendance.Fragments.Student.QRScannerFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/** MainActivity
 *
 * The main activity for the app
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseUser signedInUser = FirebaseAuth.getInstance().getCurrentUser();
    String user;

    /**
     * Things to be done on activity creation
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setting up toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Checking if user is signed in
        if (signedInUser != null)
            user = signedInUser.getEmail();

        // Setting up navigation menu
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        TextView nav_name = (TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_name);
        ImageView nav_avatar = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.avatar);

        navigationView.setNavigationItemSelectedListener(this);
        // Defaulting to the Attendance History fragment
        navigationView.setCheckedItem(R.id.nav_attendancehistory);
        FragmentManager fm = getSupportFragmentManager();

        // show/hide content depending on user
        if ( user.equals("professor@here.com") ) {
            fm.beginTransaction().replace(R.id.content, new ProfAttendanceHistoryFragment()).commit();
            Menu navMenu = navigationView.getMenu();
            navMenu.findItem(R.id.nav_qrscanner).setVisible(false);
            navMenu.findItem(R.id.nav_map).setVisible(false);
            nav_name.setText("Dr. Benjamin Kenobi");
            // Photo by Benjamin Parker on Unsplash. However, crediting isn't required as per https://unsplash.com/license
            nav_avatar.setImageResource(R.drawable.avatar_ben_kenobi);
        } else {
            fm.beginTransaction().replace(R.id.content, new StudentAttendanceHistoryFragment()).commit();
            Menu navMenu = navigationView.getMenu();
            navMenu.findItem(R.id.nav_qrscanner).setVisible(true);
            nav_name.setText("John Appleseed");
        }
    }

    /**
     * If the back button (virtual or physical) is pressed while the navigation menu is open,
     * it will hide the navigation menu
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Depending on the navigation menu item selected, the screen (fragment) will change
     *
     * @param item the MenuItem that was selected
     * @return true if everything went to plan
     *         false if something went wrong
     */
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
        else if (id == R.id.nav_map) {
            currentPage = new Maps();
            getSupportActionBar().setTitle("Map");
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
        // Load up the selected fragment
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.content, currentPage).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
