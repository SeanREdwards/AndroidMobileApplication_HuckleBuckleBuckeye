package com.example.hucklebucklebuckeye.ui.history;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.hucklebucklebuckeye.R;
import com.example.hucklebucklebuckeye.model.LogBaseHelper;

public class HistoryFragment extends Fragment {

    public static HistoryFragment newInstance() {
        return new HistoryFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        Log.d("HistoryFragment", "onCreateView() method called");

        View view = inflater.inflate(R.layout.history_fragment, container, false);


        final LogBaseHelper logHandler = new LogBaseHelper(getActivity());

        final Button addButton = view.findViewById(R.id.addButton);
        final Button updateButton = view.findViewById(R.id.updateButton);
        final Button deleteButton = view.findViewById(R.id.deleteButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                logHandler.insertData("Hello");
             }
        });


        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*logHandler.deleteTitle("Hello");*/
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        Log.d("HistoryFragment", "onStart() method called");
        super.onStart();
    }

    @Override
    public void onPause() {
        Log.d("HistoryFragment", "onPause() method called");
        super.onPause();
    }

    @Override
    public void onResume() {
        Log.d("HistoryFragment", "onResume() method called");
        super.onResume();
    }

}