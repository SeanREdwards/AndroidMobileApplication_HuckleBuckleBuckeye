package com.example.hucklebucklebuckeye.ui.mainmenu;

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
import com.example.hucklebucklebuckeye.ui.history.HistoryActivity;
import com.example.hucklebucklebuckeye.ui.playgame.GameActivity;
import com.example.hucklebucklebuckeye.ui.profile.ProfileActivity;

public class MainMenuFragment extends Fragment {

    public static MainMenuFragment newInstance() {
        return new MainMenuFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        Log.d("MainMenuFragment", "onCreateView() method called");

        View view = inflater.inflate(R.layout.main_menu_fragment, container, false);

        final Button profileButton = view.findViewById(R.id.profileButton);
        final Button historyButton = view.findViewById(R.id.historyButton);
        final Button playGameButton = view.findViewById(R.id.playGameButton);

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ProfileActivity.class));
            }
        });

        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), HistoryActivity.class));
            }
        });

        playGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), GameActivity.class));
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
        Log.d("MainMenuFragment", "onStart() method called");
        super.onStart();
    }

    @Override
    public void onPause() {
        Log.d("MainMenuFragment", "onPause() method called");
        super.onPause();
    }

    @Override
    public void onResume() {
        Log.d("MainMenuFragment", "onResume() method called");
        super.onResume();
    }

}