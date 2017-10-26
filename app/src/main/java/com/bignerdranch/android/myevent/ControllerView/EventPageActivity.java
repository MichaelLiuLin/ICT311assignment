package com.bignerdranch.android.myevent.ControllerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.bignerdranch.android.myevent.Model.Event;
import com.bignerdranch.android.myevent.Model.EventGenerater;
import com.bignerdranch.android.myevent.R;

import java.util.List;
import java.util.UUID;

/**
 * Created by linliu on 29/9/17.
 */

public class EventPageActivity extends FragmentActivity {
    private static final String EXTRA_CRIME_ID =
            "extra_event_id";
    private ViewPager mViewPager;
    private List<Event> mEvents;

    public static Intent newIntent(Context packageContext, UUID crimeId) {
        Intent intent = new Intent(packageContext, EventPageActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_pager);
        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);



        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mEvents = EventGenerater.get(this).getEvents();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Event event = mEvents.get(position);
                return SecondActivity_FirstFragment.newInstance(event.getId());
            }
            @Override
            public int getCount() {
                return mEvents.size();
            }
        });
    }
}
