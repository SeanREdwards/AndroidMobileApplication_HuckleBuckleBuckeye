package com.example.hucklebucklebuckeye.ui.playgame;

import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.hucklebucklebuckeye.R;

public class GameDestinationFragment extends Fragment {

    private String destination;

    public void setDestination(String destination){
        this.destination = destination;
    }
    public GameDestinationFragment () {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_game_destination, container, false);
        RelativeLayout background = view.findViewById(R.id.dest_card);
        int picture = 0;
        switch(this.destination){
            case("The Oval"):
                picture = R.drawable.the_oval;
                break;
            case("Thompson Library"):
                picture = R.drawable.thompson_library;
                break;
            case("Thompson Statue"):
                picture = R.drawable.thompson_statue;
                break;
            case("Hansford Quadrangle"):
                picture = R.drawable.hansford_quadrangle;
                break;
            case("Wexner Center For the Arts"):
                picture = R.drawable.wexner_center;
                break;
            case("Hitchcock Hall"):
                picture = R.drawable.hitchcock_hall;
                break;
            case("Ohio Stadium"):
                picture = R.drawable.ohio_stadium;
                break;
            case("Numbers Garden"):
                picture = R.drawable.numbers_garden;
                break;
            case("Dreese Lab"):
                picture = R.drawable.dreese_lab;
                break;
            case("Fisher College of Business"):
                picture = R.drawable.fisher;
                break;
            case("Mirror Lake"):
                picture = R.drawable.mirror_lake;
                break;
            case("Younkin Success Center"):
                picture = R.drawable.younkin_success_center;
                break;
            case("Tom W. Davis Clock Tower"):
                picture = R.drawable.clock_tower;
                break;
            case("RPAC"):
                picture = R.drawable.rpac;
                break;
            case("Ohio Union"):
                picture = R.drawable.ohio_union;
                break;
        }
        background.setBackground(ContextCompat.getDrawable(getContext(), picture));

        return view;
    }
}
