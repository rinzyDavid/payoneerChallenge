package com.payoneerchallange.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.support.v4.media.session.PlaybackStateCompat;

import com.payoneerchallange.R;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * Following JetPack 1 activity guidelines, this single activity will host the
 * FragmentContainer and  NavHostFragment
 */
@AndroidEntryPoint
public class RootHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root_home);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}