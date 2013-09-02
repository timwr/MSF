
package com.metasploit.msf.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.metasploit.msf.R;

public class ScanFragment extends RpcFragment<Object> {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scan, null);

        return view;
    }

    @Override
    public void onLoaded(Object object) {
    }

}
