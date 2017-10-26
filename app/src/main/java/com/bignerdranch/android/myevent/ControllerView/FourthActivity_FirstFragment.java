package com.bignerdranch.android.myevent.ControllerView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.bignerdranch.android.myevent.Controller.FourthActivity;
import com.bignerdranch.android.myevent.Model.SettingGenerater;
import com.bignerdranch.android.myevent.Model.Settings;
import com.bignerdranch.android.myevent.R;

/**
 * Created by linliu on 25/10/17.
 */

public class FourthActivity_FirstFragment extends Fragment {
    private EditText mNameField;
    private EditText mIdNum;
    private EditText mEmail;
    private EditText mGender;
    private EditText mComment;

    private Settings mSetting;

    public static FourthActivity_FirstFragment newInstance() {
        FourthActivity_FirstFragment settingsFragment = new FourthActivity_FirstFragment();
        return settingsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSetting = SettingGenerater.get(getActivity()).getSetting("1");

        if (mSetting == null) {
            mSetting = new Settings();
            mSetting.setName("");
            mSetting.setIdNum("");
            mSetting.setEmail("");
            mSetting.setGender("");
            mSetting.setComment("");
            SettingGenerater settingLab = SettingGenerater.get(getActivity());
            settingLab.addSetting(mSetting);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fourth_activity_first_fragment, container, false);
        mNameField = (EditText) v.findViewById(R.id.settings_name_edit_text);
        mIdNum = (EditText) v.findViewById(R.id.settings_id_edit_text);
        mEmail = (EditText) v.findViewById(R.id.settings_email_edit_text);
        mGender = (EditText) v.findViewById(R.id.settings_gender_edit_text);
        mComment = (EditText) v.findViewById(R.id.settings_comment_edit_text);

        mNameField.setText(mSetting.getName());
        mIdNum.setText(mSetting.getIdNum());
        mEmail.setText((mSetting.getEmail()));
        mGender.setText(mSetting.getGender());
        mComment.setText(mSetting.getComment());

        mNameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //intentionally blank
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mSetting.setName(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //intentionally blank
            }
        });

        mIdNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //intentionally blank
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mSetting.setIdNum(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //intentionally blank
            }
        });


        mEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //intentionally blank
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mSetting.setEmail(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //intentionally blank
            }
        });

        mGender.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //intentionally blank
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mSetting.setGender(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //intentionally blank
            }
        });

        mComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //intentionally blank
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mSetting.setComment(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //intentionally blank
            }
        });


        return v;
    }

  //updating in onPause
    @Override
    public void onPause() {
        super.onPause();
        SettingGenerater settingGenerater = SettingGenerater.get(getActivity());
        settingGenerater.updateSetting(mSetting);
    }
}
