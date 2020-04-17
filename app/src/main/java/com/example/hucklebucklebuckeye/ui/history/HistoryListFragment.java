package com.example.hucklebucklebuckeye.ui.history;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hucklebucklebuckeye.R;
import com.example.hucklebucklebuckeye.model.History;
import com.example.hucklebucklebuckeye.model.LogBaseHelper;

import java.util.List;

public class HistoryListFragment extends Fragment {

    private RecyclerView mHistoryRecyclerView;

    public static HistoryListFragment newInstance() {
        return new HistoryListFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) { View view =
            inflater.inflate(R.layout.history_fragment, container, false);
        mHistoryRecyclerView = view
                .findViewById(R.id.my_recycler_view); mHistoryRecyclerView.setLayoutManager(new
                LinearLayoutManager(getActivity()));
        updateUI();
        return view;
    }

    private void updateUI() {
        LogBaseHelper logBaseHelper =
                LogBaseHelper.get(getActivity());
        List<History> history = logBaseHelper.getUserHistory();
        HistoryAdapter adapter = new HistoryAdapter(history);
        mHistoryRecyclerView.setAdapter(adapter);
    }



    private class HistoryHolder extends RecyclerView.ViewHolder {

        private TextView mDateTextView;
        private TextView mStepsTextView;
        private TextView mMapTextView;
        private TextView mDistanceTextView;
        private TextView mTimeTextView;

        public HistoryHolder(LayoutInflater inflater,
                             ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_history, parent, false));
            mDateTextView = itemView.findViewById(R.id.history_date);
            mStepsTextView = itemView.findViewById(R.id.history_steps);
            mMapTextView = itemView.findViewById(R.id.history_map);
            mDistanceTextView = itemView.findViewById(R.id.history_distance);
            mTimeTextView = itemView.findViewById(R.id.history_time);
        }

        @SuppressLint("SetTextI18n")
        public void bind(History h) {
            mDateTextView.setText(getString(R.string.Date) + h.getDate());
            mStepsTextView.setText(getString(R.string.Steps) + h.getSteps());
            mMapTextView.setText(h.getMap());
            mDistanceTextView.setText(getString(R.string.Distance) + h.getDistance() + getString(R.string.Feet));
            mTimeTextView.setText(getString(R.string.Time) + h.getTime());
            //mCompletedTextView.setText(mHistory.getCompleted());
        }

    }

    private class HistoryAdapter extends RecyclerView.Adapter<HistoryHolder> {
        private List<History> mHistory;
        public HistoryAdapter(List<History> history) {
            mHistory = history;
        }

        @Override
        public HistoryHolder
        onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new HistoryHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(HistoryHolder holder, int position) {
            History currentEntry = mHistory.get(position);
            holder.bind(currentEntry);
        }

        @Override
        public int getItemCount() {
            return mHistory.size(); }
    }
}