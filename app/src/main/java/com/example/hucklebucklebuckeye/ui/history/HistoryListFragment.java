package com.example.hucklebucklebuckeye.ui.history;

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

    private HistoryAdapter mAdapter;

    public static HistoryListFragment newInstance() {
        return new HistoryListFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) { View view =
            inflater.inflate(R.layout.history_fragment, container, false);
        mHistoryRecyclerView = (RecyclerView) view
                .findViewById(R.id.my_recycler_view); mHistoryRecyclerView.setLayoutManager(new
                LinearLayoutManager(getActivity()));
        updateUI();
        return view;
    }

    private void updateUI() {
        LogBaseHelper logBaseHelper =
                LogBaseHelper.get(getActivity());
        List<History> history = logBaseHelper.getHistorys();
        mAdapter = new HistoryAdapter(history);
        mHistoryRecyclerView.setAdapter(mAdapter);
    }



    private class HistoryHolder extends RecyclerView.ViewHolder {

        private TextView mTitleTextView;
        private TextView mDateTextView;
        private History mHistory;

        public HistoryHolder(LayoutInflater inflater,
                             ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_history, parent, false));

            mTitleTextView = (TextView) itemView.findViewById(R.id.history_title);
            mDateTextView = (TextView) itemView.findViewById(R.id.history_date);


        }

        public void bind(History crime) {
            mHistory = crime;
            mTitleTextView.setText(mHistory.getACID());
            mDateTextView.setText(mHistory.getDate()); }

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
            History crime = mHistory.get(position);
            holder.bind(crime);
        }

        @Override
        public int getItemCount() {
            return mHistory.size(); }

    }


}

