package com.csci4176.group13.hereattendance.Fragments.Student;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.csci4176.group13.hereattendance.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class Maps extends Fragment implements OnMapReadyCallback {
    //Initialise Google Map
    private GoogleMap mMap;
    private MapView mapView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.activity_maps, container, false);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.maps);
        mapFragment.getMapAsync(this);
        return view;
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Define LatLng For the location
        final LatLng TB = new LatLng(44.639235, -63.583832);
        final LatLng LSC = new LatLng(44.635908, -63.593811);
        final LatLng GB = new LatLng(44.637413, -63.587184);

        //Define Marker
        Marker mTB,mLSC,mGB;

        //Creating an ArrayList to store Markers
        List<Marker> markerList = new ArrayList<>();

        mTB = mMap.addMarker(new MarkerOptions().position(TB).title("Tupper Building"));
        markerList.add(mTB);

        mLSC = mMap.addMarker(new MarkerOptions().position(LSC).title("Life Science Centre"));
        markerList.add(mLSC);

        mGB = mMap.addMarker(new MarkerOptions().position(GB).title("Goldberg Computer Science Building"));
        markerList.add(mGB);

        for (Marker ma : markerList) {
            LatLng latlng = new LatLng(ma.getPosition().latitude, (ma.getPosition().longitude));
            googleMap.addMarker(new MarkerOptions().position(latlng));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 15));
        }
    }
}


