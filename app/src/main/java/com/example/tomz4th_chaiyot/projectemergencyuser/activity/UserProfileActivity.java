package com.example.tomz4th_chaiyot.projectemergencyuser.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.tomz4th_chaiyot.projectemergencyuser.R;
import com.example.tomz4th_chaiyot.projectemergencyuser.fragment.UserProfileFragment;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

/**
 * Created by toMz4th-ChaiYot on 11/5/2016.
 */

public class UserProfileActivity extends AppCompatActivity {

    Toolbar toolbar;
    private boolean doubleBackToExitPressedOnce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainer, UserProfileFragment.newInstance())
                    .commit();
        }

        initInstances();

    }

    private void initInstances() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("ข้อมูลส่วนตัว");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
