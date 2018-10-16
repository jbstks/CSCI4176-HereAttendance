package com.csci4176.group13.hereattendance.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csci4176.group13.hereattendance.R;

/**
 * Fragment for the QR camera scanner
 */
public class QRScannerFragment extends android.support.v4.app.Fragment {

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public QRScannerFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_qrscanner, container, false);
        return view;
    }
}