package com.example.jarvis_tv_client;

import android.content.Intent;
import android.os.Bundle;
import androidx.leanback.app.BrowseSupportFragment;
import androidx.leanback.widget.OnItemViewClickedListener;
import androidx.leanback.widget.OnItemViewSelectedListener;
import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.Row;
import androidx.leanback.widget.RowPresenter;
import androidx.core.content.ContextCompat;

import android.util.Log;
import android.widget.Toast;


import java.util.Objects;

public class MainFragment extends BrowseSupportFragment {
    private static final String TAG = "MainFragment";

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onActivityCreated(savedInstanceState);

        setupUIElements();

        setupEventListeners();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    private void setupUIElements() {
        setTitle(getString(R.string.browse_title)); // Badge, when set, takes precedent
        // over title
        setHeadersState(HEADERS_ENABLED);
        setHeadersTransitionOnBackEnabled(true);

        // set fastLane (or headers) background color
        setBrandColor(ContextCompat.getColor(requireContext(), R.color.fastlane_background));
        // set search icon color
        setSearchAffordanceColor(ContextCompat.getColor(requireContext(), R.color.search_opaque));
    }

    private void setupEventListeners() {
        setOnSearchClickedListener(view -> Toast.makeText(getActivity(), "Implement your own in-app search", Toast.LENGTH_LONG)
                .show());
    }
}