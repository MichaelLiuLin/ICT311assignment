package com.bignerdranch.android.myevent.ControllerView;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.android.myevent.Controller.SecondActivity;
import com.bignerdranch.android.myevent.Controller.ThirdActivity;
import com.bignerdranch.android.myevent.Helper.GPSService;
import com.bignerdranch.android.myevent.Helper.PictureUtils;
import com.bignerdranch.android.myevent.Model.Event;
import com.bignerdranch.android.myevent.Model.EventGenerater;
import com.bignerdranch.android.myevent.R;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.File;
import java.util.List;
import java.util.UUID;

import static com.bignerdranch.android.myevent.ControllerView.FristActivity_FirstFragment.logButton;

/**
 * Created by linliu on 27/9/17.
 */

public class SecondActivity_FirstFragment extends Fragment  {
    private Event mEvent;
    private EventGenerater mEvents;
    private Button mDateButton;
    private Button mGPSButton;
    private EditText mTitleField;
    private EditText mTitlePlace;
    private ImageButton mPhotoButton;
    private Spinner mSpinner;
    private TextView mEventType;
    private ImageView mPhotoView;
    private File mPhotoFile;
    private static final int REQUEST_PHOTO= 2;
    private static final String ARG_CRIME_ID = "event_id";
    private GoogleApiClient mClient;
    private String mGPS="null";
    private BroadcastReceiver mBroadcastReceiver;
    private String mLatitude;
    private String mLongitude;
    private Button mSaveButton;
    private Button mCancelButton;
    private Button mDeleteButton;
    private boolean cancelButton=false;
    private boolean saveButton=false;
    private boolean deleteButton=false;
    private EditText mTitleComments;
    private EditText mTitleDuration;
    private String mGPS2;


//comments duration type


    public static SecondActivity_FirstFragment newInstance(UUID eventId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID, eventId);

        SecondActivity_FirstFragment fragment = new SecondActivity_FirstFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onPause() {
        super.onPause();
        Log.e("mGPS","onPause");

        Log.e("mGPS"," "+mGPS2);



        if (cancelButton==false){
            EventGenerater.get(getActivity())
                    .updateEvent(mEvent);
            Log.e("mGPS","onPause cancel button is false");
        }
        if(cancelButton==true&&logButton==true){
            EventGenerater.get(getActivity()).deleteEvent(mEvent);
            getActivity().finish();
            Log.e("mGPS","onPause cancelbutton & logbutton is true ");
            logButton=false;
            cancelButton=false;
        }
        //
        if(logButton==true){
            getActivity().unregisterReceiver(mBroadcastReceiver);
            Log.e("mGPS","onPause unregister");

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("mGPS","onResume");
        if(logButton==true){
            getActivity().registerReceiver(mBroadcastReceiver, new IntentFilter("1"));
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("mGPS","onStart");

        try{
            getActivity().startService(new Intent(getActivity(), GPSService.class));
            Log.e("GPS","startGPSService");
        }catch(Exception e){
            Toast.makeText(getContext(), "turn on location first.", Toast.LENGTH_SHORT).show();
        }

        //refresh
//        if(logButton==true){
//            fn_receiveGPSServiceData();
//        }else if (logButton==false){
//            mGPSButton.setText(mEvent.getGPS());
//        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("mGPS","onDestroy");
        Log.e("autosave","onDestroy"+cancelButton);
        logButton=false;


    }

    //stop service needs to stop the activity first,
    // stop the service from the activity then the stop method in service class will be called.
    @Override
    public void onStop() {
        super.onStop();
        Log.e("mGPS","onStop");
        getActivity().stopService(new Intent(getActivity(), GPSService.class));
        Log.e("GPS","stopGPSService");
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cancelButton=false;
        Log.e("mGPS","onPause");




        //generate related events

       // UUID eventId = (UUID) getActivity().getIntent().getSerializableExtra(SecondActivity.EXTRA_EVENT_ID);
        UUID eventId = (UUID) getArguments().getSerializable(ARG_CRIME_ID);

        mEvent = EventGenerater.get(getActivity()).getEvent(eventId);//??

        //mEvent=new Event();

        mPhotoFile = EventGenerater.get(getActivity()).getPhotoFile(mEvent);

        int REQUEST_PERMISSION= 100;
        int cameraPermission = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            cameraPermission = getActivity().checkSelfPermission(Manifest.permission.CAMERA);
        }
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            if (cameraPermission != PackageManager.PERMISSION_GRANTED  ) {


                this.requestPermissions(
                        new String[]{Manifest.permission.CAMERA },
                        REQUEST_PERMISSION
                );
            }
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle saveInstanceState){
        Log.e("mGPS","onCreateView");
        View view = inflater.inflate(R.layout.second_activity_first_fragment, parent,false);
        mGPSButton = (Button)view.findViewById(R.id.gps);
        mSpinner=(Spinner) view.findViewById(R.id.spinner);
        ArrayAdapter adapter= ArrayAdapter.createFromResource(getActivity(),R.array.event_type,android.R.layout.simple_spinner_item);
        mSpinner.setAdapter(adapter);
        mSpinner.setSelection(mEvent.getType());
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                 mEventType= (TextView) view;
                mEvent.setType(mSpinner.getSelectedItemPosition());
                //Toast.makeText(getActivity(),"You selected "+mEventType.getText(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        if(logButton==true){
            fn_receiveGPSServiceData();
        }else if (logButton==false){
            if(mEvent.getGPS()!=null){
                Log.e("mGPS","getGPS is"+mEvent.getGPS());
            }else{
                mGPSButton.setText(mEvent.getGPS());
            }
                                                                                                                                                                                                                                                                                                                                            mGPSButton.setText(mEvent.getGPS());
        }


        //title
        mTitleField = (EditText)view.findViewById(R.id.type_title);
        mTitleField.setText(mEvent.getTitle());
        //place
        mTitlePlace =(EditText)view.findViewById(R.id.type_place);
        mTitlePlace.setText(mEvent.getPlace());
        //comments
        mTitleComments =(EditText)view.findViewById(R.id.comments);
        mTitleComments.setText(mEvent.getComments());
        //duration
        mTitleDuration =(EditText)view.findViewById(R.id.duration);
        mTitleDuration.setText(mEvent.getDuration());

        mTitleComments.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                mEvent.setComments(s.toString());  // can be used to set the title for the event
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mTitleDuration.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                mEvent.setDuration(s.toString());  // can be used to set the title for the event
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                mEvent.setTitle(s.toString());  // can be used to set the title for the event
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        mTitlePlace.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                mEvent.setPlace(s.toString());  // can be used to set the place for the event
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        //fn_receiveGPSServiceData();
        //this button is used to show the map

        mGPSButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //showing the map.
                if(logButton==true){
                    Intent myIntent = new Intent(getActivity(), ThirdActivity.class);
                    myIntent.putExtra("latitude",mLatitude ); //send GPS
                    myIntent.putExtra("longitude",mLongitude );
                    startActivity(myIntent);
                    //Log.e("GPS","sending la and lo from GPS button"+mLatitude+"    "+mLongitude);
                }
                else if(logButton==false){
                    String[] gpsLocation = mEvent.getGPS().split(";");
                    Intent myIntent = new Intent(getActivity(), ThirdActivity.class);
                    myIntent.putExtra("latitude",gpsLocation[0]); //send GPS
                    myIntent.putExtra("longitude", gpsLocation[1]);
                    startActivity(myIntent);
                }

            }
        });

        mSaveButton= (Button)view.findViewById(R.id.save);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               saveButton =true;
               //Toast.makeText(getActivity(), "Saved" , Toast.LENGTH_SHORT).show();
               Log.e("save","testing save button");
               EventGenerater.get(getActivity()).updateEvent(mEvent);
               getActivity().finish();



           }
        });

        mCancelButton= (Button)view.findViewById(R.id.cancel);
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelButton =true;
                getActivity().finish();
            }
        });

        mDeleteButton= (Button)view.findViewById(R.id.delete);
        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Deleted" , Toast.LENGTH_SHORT).show();
                EventGenerater.get(getActivity()).deleteEvent(mEvent);
                getActivity().finish();
                deleteButton =true;
            }
        });

        mDateButton = (Button) view.findViewById(R.id.date);
        mDateButton.setText(mEvent.getProperTime());//show time and saved time have different format.

        mDateButton.setEnabled(false);
        mEvent.setDate(mEvent.getDate());
        Log.e("date",mEvent.getDate().toString());
        //Log.e("date",mEvent.getProperTime().toString());
        //camera thing
        mPhotoButton = (ImageButton) view.findViewById(R.id.camera);


        PackageManager packageManager = getActivity().getPackageManager();

        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        boolean canTakePhoto = mPhotoFile != null &&
                captureImage.resolveActivity(packageManager) != null;
        mPhotoButton.setEnabled(canTakePhoto);


        mPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = FileProvider.getUriForFile(getActivity(),
                        "myevent.fileprovider",
                        mPhotoFile);
                captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);

                List<ResolveInfo> cameraActivities = getActivity()
                        .getPackageManager().queryIntentActivities(captureImage,
                                PackageManager.MATCH_DEFAULT_ONLY);

                for (ResolveInfo activity : cameraActivities) {
                    getActivity().grantUriPermission(activity.activityInfo.packageName,
                            uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                }

                startActivityForResult(captureImage, REQUEST_PHOTO);
            }
        });
        mPhotoView = (ImageView) view.findViewById(R.id.photo);
        updatePhotoView();
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_PHOTO) {
            Uri uri = FileProvider.getUriForFile(getActivity(),
                    "myevent.fileprovider",
                    mPhotoFile);

            getActivity().revokeUriPermission(uri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

            updatePhotoView();
        }
    }


    private void updatePhotoView() {
        if (mPhotoFile == null || !mPhotoFile.exists()) {
            mPhotoView.setImageDrawable(null);
        } else {
            Bitmap bitmap = PictureUtils.getScaledBitmap(
                    mPhotoFile.getPath(), getActivity());
            mPhotoView.setImageBitmap(bitmap);
        }
    }


    private void fn_receiveGPSServiceData(){
        mBroadcastReceiver = new BroadcastReceiver(){

            @Override
            public void onReceive(Context context, Intent intent) {
                mLatitude=intent.getStringExtra("latitude");
                mLongitude=intent.getStringExtra("longitude");

                mGPS=("Latitude: " +mLatitude+"                       Longitude: "+mLongitude);
                mGPSButton.setText(mGPS);
                mGPS2=(mLatitude+";"+mLongitude);
                //send gps data to database
               mEvent.setGPS(mGPS2);
            }
        };
    }

}

