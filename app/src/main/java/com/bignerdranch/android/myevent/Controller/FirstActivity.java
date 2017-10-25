package com.bignerdranch.android.myevent.Controller;

import android.Manifest;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

import com.bignerdranch.android.myevent.Helper.GPSService;
import com.bignerdranch.android.myevent.R;
import com.bignerdranch.android.myevent.ControllerView.FristActivity_FirstFragment;
import com.bignerdranch.android.myevent.ControllerView.FristActivity_SecondFragment;

import java.util.UUID;

public class FirstActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        setContentView(R.layout.first_activity_outline);

        FristActivity_FirstFragment ff = new FristActivity_FirstFragment();
        FragmentManager fm=getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.sublayout1, ff,ff.getTag()).commit();

        FristActivity_SecondFragment sf =new FristActivity_SecondFragment();
        fm.beginTransaction().replace(R.id.sublayout2,sf,sf.getTag()).commit();
    }

}
