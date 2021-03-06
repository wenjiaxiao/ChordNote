package com.example.chordnote.ui.base;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.example.chordnote.ChordNoteApp;
import com.example.chordnote.R;
import com.example.chordnote.di.component.ActivityComponent;
import com.example.chordnote.di.component.DaggerActivityComponent;
import com.example.chordnote.di.module.ActivityModule;
import com.example.chordnote.utils.NetworkUtils;

import java.io.File;

public class BaseActivity extends AppCompatActivity
        implements MvpView, BaseFragment.Callback {

    private static final String TAG = "BaseActivity";

    private ActivityComponent mActivityComponent;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityComponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .applicationComponent(((ChordNoteApp) getApplication()).getComponent())
                .build();
        progressBar = new ProgressBar(this);

        progressBar.findViewById(R.id.pb_loading);

    }

    public ActivityComponent getActivityComponent() {
        return mActivityComponent;
    }

    @Override
    public void showLoading() {
        hideLoading();
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        if (progressBar != null && progressBar.isShown()) {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void showToastText(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean isConnected() {
        return NetworkUtils.isNetWorkAvailable(this);
    }

    @Override
    public void onError(String message) {
    }

    @Override
    public void onError(@StringRes int resId) {
        onError(getString(resId));
    }

    @Override
    public void onFragmentAttached() {

    }

    @Override
    public void onFragmentDetached(String tag) {

    }

    @Override
    public File getCacheDir() {
        return getExternalCacheDir();
    }


    @Override
    protected void onStop() {
        super.onStop();

        Log.d(TAG, "onStop: " + toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "onDestroy: " + this.toString());
    }
}
