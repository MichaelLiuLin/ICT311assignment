package com.bignerdranch.android.myevent.Helper;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import com.bignerdranch.android.myevent.R;

/**
 * Created by linliu on 26/8/17.
 */

public abstract class SingleFragmentActivity extends AppCompatActivity {
    // this is the first step.
    protected abstract Fragment createFragment();

    //onCreate method will be executed by system automatically. wrote here is due to we want to override this method.
    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);

        setContentView(R.layout.single_fragment_activity_outline);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if(fragment == null){
            fragment = createFragment();
            fm.beginTransaction().add(R.id.fragment_container,fragment).commit();
        }
    }
}
