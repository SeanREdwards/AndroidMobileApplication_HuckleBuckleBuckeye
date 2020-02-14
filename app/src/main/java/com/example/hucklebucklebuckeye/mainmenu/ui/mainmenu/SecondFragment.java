package com.example.hucklebucklebuckeye.mainmenu.ui.mainmenu;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hucklebucklebuckeye.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SecondFragment extends Fragment {


    public static SecondFragment newInstance() {
        return new SecondFragment();
    }

    public SecondFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

}
