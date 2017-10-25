package com.bignerdranch.android.myevent.ControllerView;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.android.myevent.Controller.SecondActivity;
import com.bignerdranch.android.myevent.Model.Event;
import com.bignerdranch.android.myevent.Model.EventGenerater;
import com.bignerdranch.android.myevent.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

/**
 * Created by linliu on 23/9/17.
 */

public class FristActivity_SecondFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private EventAdapter mAdapter;
    private TextView   mEmptyTextView;
    private TextView   mEmptyTextView2;
    private List<Event> mEvents;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.first_activity_second_fragment, container, false);
        mEmptyTextView=(TextView)view.findViewById(R.id.empty);
        mEmptyTextView2=(TextView)view.findViewById(R.id.empty2);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventGenerater eventGenerater =EventGenerater.get(getActivity());
        List<Event> events = eventGenerater.getEvents();
        if(events.isEmpty()){
            mEmptyTextView.setVisibility(View.VISIBLE);
            mEmptyTextView2.setVisibility(View.VISIBLE);
        }else if(events.isEmpty()==false){
            mEmptyTextView.setVisibility(View.GONE);
            mEmptyTextView2.setVisibility(View.GONE);
        }

    }

    private void updateUI() {
        EventGenerater eventGenerater =EventGenerater.get(getActivity());
        List<Event> events = eventGenerater.getEvents();
        if (mAdapter == null) {
            mAdapter = new EventAdapter(events);
            mRecyclerView.setAdapter(mAdapter);
            //Log.e("autosave",""+"mAdapter is null");
        }
        else  {
            mAdapter.setCrimes(events);
            mAdapter.notifyDataSetChanged();
            //Log.e("autosave",""+"mAdapter is changed");
        }

    }

    private class EventHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mTitleTextView;
        private TextView mPlaceTextView;
        private TextView   mTimeTextView;

        private Event mEvent;



        public void bindEvent(Event event) {
            mEvent = event;
            mTitleTextView.setText(mEvent.getTitle());
            mPlaceTextView.setText(mEvent.getPlace());
            mTimeTextView.setText(mEvent.getProperTime());


        }
        public EventHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTitleTextView = (TextView) itemView.findViewById(R.id.title_textView);
            mPlaceTextView=(TextView) itemView.findViewById(R.id.place_textView);
            mTimeTextView=(TextView) itemView.findViewById(R.id.time_textView);
        }

        @Override
        public void onClick(View view) {
            Intent intent = SecondActivity.newIntent(getActivity(), mEvent.getId());
            startActivity(intent);
            //Toast.makeText(getActivity(),mEvent.getTitle() + " clicked!", Toast.LENGTH_SHORT).show();
        }
    }

    private class EventAdapter extends RecyclerView.Adapter<EventHolder> {


        public EventAdapter(List<Event> events) {
            mEvents = events;
        }

        @Override
        public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.first_activity_first_fragment_recycler_view, parent, false);  //!!!!!! very important
            return new EventHolder(view);
        }

        @Override
        public void onBindViewHolder(EventHolder holder, int position) {
            Event event = mEvents.get(position);
            //holder.mTitleTextView.setText(crime.getTitle());
            holder.bindEvent(event);
        }

        @Override
        public int getItemCount() {
            return mEvents.size();
        }

        public void setCrimes(List<Event> crimes) {
            mEvents = crimes;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

}
