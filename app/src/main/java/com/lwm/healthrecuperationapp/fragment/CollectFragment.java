package com.lwm.healthrecuperationapp.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lwm.healthrecuperationapp.R;

public class CollectFragment extends Fragment {

    public CollectFragment() {

    }

    public static CollectFragment newInstance() {
        CollectFragment fragment = new CollectFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_collect, container, false);
    }
}