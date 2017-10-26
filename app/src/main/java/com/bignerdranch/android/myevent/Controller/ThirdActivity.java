package com.bignerdranch.android.myevent.Controller;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.bignerdranch.android.myevent.ControllerView.ThirdActivity_FirstFragment;
import com.bignerdranch.android.myevent.Helper.GPSService;
import com.bignerdranch.android.myevent.Helper.SingleFragmentActivity;



public class ThirdActivity extends SingleFragmentActivity{

    @Override
    protected Fragment createFragment() {
        return new ThirdActivity_FirstFragment();
    }
}


