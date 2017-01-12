package com.example.tomz4th_chaiyot.projectemergencyuser.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.tomz4th_chaiyot.projectemergencyuser.R;

public class TelActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    android.support.v7.widget.CardView cv191;
    android.support.v7.widget.CardView cv1137;
    android.support.v7.widget.CardView cv1155;
    android.support.v7.widget.CardView cv1192;
    android.support.v7.widget.CardView cv1543;
    android.support.v7.widget.CardView cv1586;
    android.support.v7.widget.CardView cv1669;
    android.support.v7.widget.CardView cv199;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tel);
        initInstances();
    }

    private void initInstances() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Drawer
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                TelActivity.this,
                drawerLayout,
                R.string.open_drawer,
                R.string.close_drawer
        );
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("เบอร์โทรฉุกเฉิน");

        cv191 = (android.support.v7.widget.CardView) findViewById(R.id.cv191);
        cv1137 = (android.support.v7.widget.CardView) findViewById(R.id.cv1137);
        cv1155 = (android.support.v7.widget.CardView) findViewById(R.id.cv1155);
        cv1192 = (android.support.v7.widget.CardView) findViewById(R.id.cv1192);
        cv1543 = (android.support.v7.widget.CardView) findViewById(R.id.cv1543);
        cv1586 = (android.support.v7.widget.CardView) findViewById(R.id.cv1586);
        cv1669 = (android.support.v7.widget.CardView) findViewById(R.id.cv1669);
        cv199 = (android.support.v7.widget.CardView) findViewById(R.id.cv199);
        cv191.setOnClickListener(this);
        cv1137.setOnClickListener(this);
        cv1155.setOnClickListener(this);
        cv1192.setOnClickListener(this);
        cv1543.setOnClickListener(this);
        cv1586.setOnClickListener(this);
        cv1669.setOnClickListener(this);
        cv199.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onClick(View v) {
        if (v == cv191){
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" +191));
            startActivity(intent);
        }if (v == cv1137){
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" +1137));
            startActivity(intent);
        }if (v == cv1155){
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" +1155));
            startActivity(intent);
        }if (v == cv1192){
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" +1192));
            startActivity(intent);
        }if (v == cv1543){
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" +1543));
            startActivity(intent);
        }if (v == cv1586){
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" +1586));
            startActivity(intent);
        }if (v == cv1669){
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" +1669));
            startActivity(intent);
        }if (v == cv199){
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" +199));
            startActivity(intent);
        }
    }
}
