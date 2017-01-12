package com.example.tomz4th_chaiyot.projectemergencyuser.activity;

import android.content.Intent;
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
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;
import com.example.tomz4th_chaiyot.projectemergencyuser.R;
import com.example.tomz4th_chaiyot.projectemergencyuser.dao.ServiceCollectionDao;
import com.example.tomz4th_chaiyot.projectemergencyuser.fragment.ServiceCommentFragment;
import com.example.tomz4th_chaiyot.projectemergencyuser.fragment.ServiceProfileFragment;
import com.example.tomz4th_chaiyot.projectemergencyuser.manager.HttpManager;

import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.tomz4th_chaiyot.projectemergencyuser.BaseUrl.BASE_URL_IMG_SERVICE;
import static com.example.tomz4th_chaiyot.projectemergencyuser.BaseUrl.BASE_URL_IMG_USER;


public class ServiceProfileActivity extends AppCompatActivity {

    Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private boolean isShow = true;
    private int scrollRange = -1;
    private String getId;
    private ImageView imgPhotoService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_service_profile);

        Bundle bundle = getIntent().getExtras();
        getId = bundle.getString("id", "0");
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

        //collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);

        // AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);
        // appBarLayout.addOnOffsetChangedListener(this);
        imgPhotoService = (ImageView) findViewById(R.id.imgPhotoService);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);

        int id = Integer.parseInt(getId);
        Call<ServiceCollectionDao> call = HttpManager.getInstance().getService().getService(id);
        call.enqueue(new Callback<ServiceCollectionDao>() {
            @Override
            public void onResponse(Call<ServiceCollectionDao> call, Response<ServiceCollectionDao> response) {

                if (response.isSuccessful()) {
                    ServiceCollectionDao daoService = response.body();

                    if (daoService.isSuccess()) {
                        collapsingToolbarLayout.setTitle("" + daoService.getService().get(0).getServiceName());
                        Glide.with(ServiceProfileActivity.this)
                                .load(BASE_URL_IMG_SERVICE + daoService.getService().get(0).getServiceImg())
                                .signature(new StringSignature(UUID.randomUUID().toString()))
                                .into(imgPhotoService);
                    }

                } else {
                    Log.e("Error", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<ServiceCollectionDao> call, Throwable t) {
                Log.e("errorConnection", t.toString());

            }
        });

    }

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
                return ServiceProfileFragment.newInstance(getId);
            }
            return ServiceCommentFragment.newInstance(getId);
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

