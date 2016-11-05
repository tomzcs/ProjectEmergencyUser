package com.example.tomz4th_chaiyot.projectemergencyuser.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.tomz4th_chaiyot.projectemergencyuser.R;
import com.example.tomz4th_chaiyot.projectemergencyuser.fragment.ServiceCommentFragment;
import com.example.tomz4th_chaiyot.projectemergencyuser.fragment.ServiceProfileFragment;


public class ServiceProfileActivity extends AppCompatActivity {

    Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private boolean isShow = true;
    private int scrollRange = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_service_profile);
        initInstances();
    }

    private void initInstances() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        //tabLayout
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        NestedScrollView nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);
        nestedScrollView.setFillViewport(true);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);

       // AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);
       // appBarLayout.addOnOffsetChangedListener(this);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);
        collapsingToolbarLayout.setTitle("ข้อมูลร้านผู้ให้บริการ");
    }

    /*@Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {


        if (scrollRange == -1) {
            scrollRange = appBarLayout.getTotalScrollRange();
        }
        if (scrollRange + verticalOffset == 0) {
            collapsingToolbarLayout.setTitle("ข้อมูลผู้ให้บริการ");
            isShow = true;
        } else if (isShow) {
            collapsingToolbarLayout.setTitle(" ");
            isShow = false;
        }

    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private String tabTitles[] = new String[]{"รายละเอียด", "ความคิดเห็น"};


        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);

        }

        @Override
        public Fragment getItem(int position) {

            if (position == 0) {
                return ServiceProfileFragment.newInstance();
            }
            return ServiceCommentFragment.newInstance();
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }
    }

}

