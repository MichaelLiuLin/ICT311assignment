package com.bignerdranch.android.myevent.Controller;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.bignerdranch.android.myevent.ControllerView.FourthActivity_FirstFragment;
import com.bignerdranch.android.myevent.Helper.SingleFragmentActivity;

/**
 * Created by linliu on 25/10/17.
 */

public class FourthActivity extends SingleFragmentActivity {
    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, FourthActivity.class);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        return  FourthActivity_FirstFragment.newInstance();
    }
}
