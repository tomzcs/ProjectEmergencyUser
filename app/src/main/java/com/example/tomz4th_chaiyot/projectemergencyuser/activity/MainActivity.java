package com.example.tomz4th_chaiyot.projectemergencyuser.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.example.tomz4th_chaiyot.projectemergencyuser.R;
import com.example.tomz4th_chaiyot.projectemergencyuser.dao.CarsCollectionDao;
import com.example.tomz4th_chaiyot.projectemergencyuser.dao.UsersCollectionDao;
import com.example.tomz4th_chaiyot.projectemergencyuser.fragment.RequestFragment;
import com.example.tomz4th_chaiyot.projectemergencyuser.fragment.ServiceListFragment;
import com.example.tomz4th_chaiyot.projectemergencyuser.manager.HttpManager;
import com.example.tomz4th_chaiyot.projectemergencyuser.manager.carManager;
import com.example.tomz4th_chaiyot.projectemergencyuser.manager.userManager;

import io.fabric.sdk.android.Fabric;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    private userManager user;
    private UsersCollectionDao dao;
    private TextView tvName;
    private TextView tvEmail;
    private TextView tvTel;
    private boolean doubleBackToExitPressedOnce;
    private NavigationView navigationView;
    private EditText carType;
    private EditText carName;
    private EditText carColor;
    private EditText carNumber;
    private int userId = 0;
    CarsCollectionDao daocar;
    carManager car;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Fabric.with(this, new Crashlytics());
        user = new userManager();
        dao = user.getDao();
        if (dao == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }
        setContentView(R.layout.activity_main);

        initInstances();


    }

    private void initInstances() {
        car = new carManager();
        //toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //viewPager
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        //tabLayout
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        //Drawer
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                MainActivity.this,
                drawerLayout,
                R.string.open_drawer,
                R.string.close_drawer
        );
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerLayout = navigationView.getHeaderView(0);

        tvName = (TextView) headerLayout.findViewById(R.id.tvName);
        tvEmail = (TextView) headerLayout.findViewById(R.id.tvEmail);
        tvTel = (TextView) headerLayout.findViewById(R.id.tvTel);
        tvName.setText("ชื่อ :" + dao.getUser().get(0).getName());
        tvEmail.setText("อีเมล์ :" + dao.getUser().get(0).getEmail());
        tvTel.setText("เบอร์โทร :" + dao.getUser().get(0).getTel());

        getCars();


    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private String tabTitles[] = new String[]{"การ้องขอ", "ร้านที่ให้บริการ"};


        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);

        }

        @Override
        public Fragment getItem(int position) {

            if (position == 0) {
                return RequestFragment.newInstance();
            }
            return ServiceListFragment.newInstance();
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

    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "กดอีกครั้งเพื่อ ออก", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_edit_profile) {
            startActivity(new Intent(this, UserProfileActivity.class));
            //overridePendingTransition(R.anim.from_right, R.anim.to_left);
        } else if (id == R.id.nav_history) {
            startActivity(new Intent(this, HistoryActivity.class));

        } else if (id == R.id.nav_logout) {
            user.logOut();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
        return true;
    }

    private void send() {
        String type = carType.getText().toString();
        String name = carName.getText().toString();
        String color = carColor.getText().toString();
        String number = carNumber.getText().toString();
        userId = dao.getUser().get(0).getUserId();

        Call<CarsCollectionDao> call = HttpManager.getInstance().getService().insertCar(type, name, color, number, userId);
        call.enqueue(new Callback<CarsCollectionDao>() {
            @Override
            public void onResponse(Call<CarsCollectionDao> call, Response<CarsCollectionDao> response) {

                if (response.isSuccessful()) {
                    CarsCollectionDao data = response.body();
                    String message = data.getMessage();
                    if (data.isSuccess()) {
                        showToast(message);
                    } else {
                        showToast(message);
                    }
                } else {
                    Log.e("Error", response.errorBody().toString());
                    showToast("ลงทะเบียนล้มเหลว");
                }
            }

            @Override
            public void onFailure(Call<CarsCollectionDao> call, Throwable t) {
                Log.e("errorConnection", t.toString());
                showToast("เชื่อมต่อไม่สำเร็จ");

            }

        });

    }

    private void getCars() {
        userId = dao.getUser().get(0).getUserId();

        Call<CarsCollectionDao> call = HttpManager.getInstance().getService().getCar(userId);
        call.enqueue(new Callback<CarsCollectionDao>() {
            @Override
            public void onResponse(Call<CarsCollectionDao> call, Response<CarsCollectionDao> response) {

                if (response.isSuccessful()) {
                    daocar = response.body();
                    if (daocar.isSuccess()) {
                        if (daocar.getCar().get(0).getCarName() == null) {
                            AlertDialog.Builder builder =
                                    new AlertDialog.Builder(MainActivity.this);
                            LayoutInflater inflater = getLayoutInflater();

                            View view = inflater.inflate(R.layout.dialog_car, null);
                            builder.setView(view);

                            carType = (EditText) view.findViewById(R.id.edtCarType);
                            carName = (EditText) view.findViewById(R.id.edtCarName);
                            carColor = (EditText) view.findViewById(R.id.edtCarColor);
                            carNumber = (EditText) view.findViewById(R.id.edtCarNumber);


                            builder.setPositiveButton("เพิ่มข้อมูล", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    send();
                                }
                            });
                            builder.setNegativeButton("ข้าม", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });

                            builder.show();
                        }
                    } else {
                        Log.e("error", "555555555555555555");
                    }
                } else {
                    Log.e("Error", response.errorBody().toString());
                    showToast("ลงทะเบียนล้มเหลว");
                }
            }

            @Override
            public void onFailure(Call<CarsCollectionDao> call, Throwable t) {
                Log.e("errorConnection", t.toString());
                showToast("เชื่อมต่อไม่สำเร็จ");

            }

        });

    }

    private void showToast(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

}

