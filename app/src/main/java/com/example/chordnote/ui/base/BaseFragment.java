package com.example.chordnote.ui.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.example.chordnote.ChordNoteApp;
import com.example.chordnote.R;
import com.example.chordnote.di.component.ActivityComponent;
import com.example.chordnote.di.module.ActivityModule;
import com.example.chordnote.utils.NetworkUtils;

import java.io.File;

import butterknife.Unbinder;

public class BaseFragment extends Fragment implements MvpView {

    private static final String TAG = "BaseFragment";

    private BaseActivity mActivity;
    protected Unbinder unbinder;
    private ProgressBar progressBar;

    public ActivityComponent getActivityComponent() {
        if (mActivity != null) {

            Log.d(TAG, "getActivityComponent: ");
            return mActivity.getActivityComponent();
        }
        return null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressBar = new ProgressBar(getContext());

        progressBar.findViewById(R.id.pb_loading);


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            BaseActivity activity = (BaseActivity) context;
            this.mActivity = activity;
            activity.onFragmentAttached();
        }
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
        Toast.makeText(getContext(), string, Toast.LENGTH_LONG).show();

    }

    @Override
    public boolean isConnected() {
        return NetworkUtils.isNetWorkAvailable((Context)getActivity());
    }

    @Override
    public void onError(String message) {
        if (mActivity != null) {
            mActivity.onError(message);
        }
    }

    @Override
    public void onError(@StringRes int resId) {
        if (mActivity != null) {
            mActivity.onError(resId);
        }
    }

    @Override
    public void onDetach() {
        mActivity = null;
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    public interface Callback {

        void onFragmentAttached();

        void onFragmentDetached(String tag);
    }

    @Override
    public File getCacheDir() {
        return mActivity.getCacheDir();
    }
}
