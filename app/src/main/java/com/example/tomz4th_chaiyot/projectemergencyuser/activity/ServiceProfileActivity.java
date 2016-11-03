package com.example.tomz4th_chaiyot.projectemergencyuser.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import com.example.tomz4th_chaiyot.projectemergencyuser.R;
import com.example.tomz4th_chaiyot.projectemergencyuser.fragment.RequestFragment;
import com.example.tomz4th_chaiyot.projectemergencyuser.fragment.ServiceCommentFragment;
import com.example.tomz4th_chaiyot.projectemergencyuser.fragment.ServiceListFragment;
import com.example.tomz4th_chaiyot.projectemergencyuser.fragment.ServiceProfileFragment;

import static com.example.tomz4th_chaiyot.projectemergencyuser.R.id.activity_main;
import static com.example.tomz4th_chaiyot.projectemergencyuser.R.id.tabLayout;

public class ServiceProfileActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener{

    Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_service_profile);
        initInstances();
    }

    private void initInstances() {
        //collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);
        //collapsingToolbarLayout.setTitle("ชื่อร้าน");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        //tabLayout
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        
 NestedScrollView nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);
        nestedScrollView.setFillViewport(true);
        
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);
        
         AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);
        appBarLayout.addOnOffsetChangedListener(this);
    }
 @Override
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


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
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

