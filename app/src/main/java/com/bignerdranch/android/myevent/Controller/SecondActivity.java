package com.bignerdranch.android.myevent.Controller;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.bignerdranch.android.myevent.ControllerView.SecondActivity_FirstFragment;
import com.bignerdranch.android.myevent.Helper.SingleFragmentActivity;
import com.bignerdranch.android.myevent.Model.Event;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.UUID;

/**
 * Created by linliu on 27/9/17.
 */

public class SecondActivity extends SingleFragmentActivity {
    //define key and value for intent
    public static final String EXTRA_EVENT_ID =
            "extra_event_id";
    private static final int REQUEST_ERROR = 0;



    //using Context because of this method is static to create new intent

    public static Intent newIntent(Context packageContext, UUID eventId) {

            //Intent intent = new Intent(packageContext, SecondActivity.class);
            //return intent;

        Intent intent = new Intent(packageContext, SecondActivity.class);
        intent.putExtra(EXTRA_EVENT_ID,eventId );
        return intent;
    }



    @Override
    protected Fragment createFragment() {
        //return new SecondActivity_FirstFragment();

        UUID eventId = (UUID) getIntent().getSerializableExtra(EXTRA_EVENT_ID);
        return SecondActivity_FirstFragment.newInstance(eventId);

    }








    //GPS thing
    //just verify that Play Services is available
    @Override
    public void onResume() {
        super.onResume();
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int errorCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (errorCode != ConnectionResult.SUCCESS) {
            Dialog errorDialog = apiAvailability.getErrorDialog(this,
                    errorCode,
                    REQUEST_ERROR,
                    new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialogInterface) {
                            // Leave if services are unavailable.

                            //finish() trigger onDestory
                            finish();
                            Log.d("debug","Cancelled delete. Do nothing.");
                        }
                    });
            errorDialog.show();
        }
    }
}
