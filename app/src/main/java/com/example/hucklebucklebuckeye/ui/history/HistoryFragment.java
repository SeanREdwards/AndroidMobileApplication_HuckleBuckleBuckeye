package com.example.hucklebucklebuckeye.ui.history;

import androidx.lifecycle.ViewModelProviders;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hucklebucklebuckeye.R;
import com.example.hucklebucklebuckeye.model.LogBaseHelper;

public class HistoryFragment extends Fragment {

    private static String outputText = "added!\n";
    private static int addedCount = 0;
    private static int stepCount = 15;
    private static int updateCount = 0;
    private static int deleteCount = 0;
    private static String updateText = "updated!\n";
    public static HistoryFragment newInstance() {
        return new HistoryFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        Log.d("HistoryFragment", "onCreateView() method called");

        View view = inflater.inflate(R.layout.history_fragment, container, false);

        History h = new History(getContext());
        final LogBaseHelper logHandler = new LogBaseHelper(getActivity());

        final Button addButton = view.findViewById(R.id.addButton);
        final Button updateButton = view.findViewById(R.id.updateButton);
        final Button deleteButton = view.findViewById(R.id.deleteButton);
        final TextView output = view.findViewById(R.id.output);


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put("STEPS", 15);
                values.put("MAP", "");
                values.put("DISTANCE", 1.3);
                values.put("TIME", "");
                values.put("COMPLETED", false);

                logHandler.insertData(values);
                String out = "";
                addedCount++;
                //output.setText("added!\n");
                for (int i = 0; i < addedCount; i++){
                    out += outputText;
                }
                Log.d("output", out);
                output.setText(out);
                Toast.makeText(getContext(), out, Toast.LENGTH_SHORT).show();
             }
        });


        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                stepCount++;
                values.put("STEPS", stepCount);
                values.put("MAP", "");
                values.put("DISTANCE", 1.3);
                values.put("TIME", "");
                values.put("COMPLETED", false);

                logHandler.updateData(values);
                String out = "";
                updateCount++;
                //output.setText("added!\n");
                for (int i = 0; i < updateCount; i++){
                    out += updateText;
                }
                output.setText(out);
                Toast.makeText(getContext(), out, Toast.LENGTH_SHORT).show();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCount++;
                logHandler.deleteRow(Integer.toString(deleteCount));

                output.setText(deleteCount + " deleted!");

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

//    private String getAllLogs(LogBaseHelper l) {
//        SQLiteDatabase db = l.getReadableDatabase();
//        String[] columns = {
//                l.COLUMN_1,
//                l.COLUMN_2,
//                MyDatabaseHelper.COLUMN_3};
//        String selection = null; // this will select all rows
//        Cursor cursor = db.query(MyDatabaseHelper.MY_TABLE, columns, selection,
//                null, null, null, null, null);
//    }

}