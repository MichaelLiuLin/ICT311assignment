package com.bignerdranch.android.myevent.ControllerView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bignerdranch.android.myevent.Controller.FirstActivity;
import com.bignerdranch.android.myevent.Controller.FourthActivity;
import com.bignerdranch.android.myevent.Model.Event;
import com.bignerdranch.android.myevent.Model.EventGenerater;
import com.bignerdranch.android.myevent.Model.Settings;
import com.bignerdranch.android.myevent.R;
import com.bignerdranch.android.myevent.Controller.SecondActivity;

/**
 * Created by linliu on 23/9/17.
 */

public class FristActivity_FirstFragment extends Fragment {
    private Event mEvent;
    private EditText mTitleField;
    private Button mButtonLog;
    private Button mButtonSetting;
    public static boolean logButton=false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mEvent = new Event();
        logButton=false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.first_activity_first_fragment, container, false);





        mButtonLog=(Button)v.findViewById(R.id.button_log);
        mButtonSetting=(Button)v.findViewById(R.id.button_setting);
        mButtonLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logButton =true;
                Event event= new Event();
                EventGenerater.get(getActivity()).addEvent(event);
                Intent intent = SecondActivity.newIntent(getActivity(), event.getId());
                startActivity(intent);
                //startActivity(new Intent(getActivity(), SecondActivity.class));
            }
        });

        mButtonSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"this is the setting button ", Toast.LENGTH_SHORT).show();
                Intent intent = FourthActivity.newIntent(getActivity());
                startActivity(intent);
            }
        });
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu,inflater);
        inflater.inflate(R.menu.menu, menu);
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.menu_item_new_crime:
//                Event event = new Event();
//                EventGenerater.get(getActivity()).addEvent(event);
//                Intent intent = CrimePagerActivity
//                        .newIntent(getActivity(), event.getId());
//                startActivity(intent);
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }


}
