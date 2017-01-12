package com.example.tomz4th_chaiyot.projectemergencyuser.activity;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;
import com.crashlytics.android.Crashlytics;
import com.example.tomz4th_chaiyot.projectemergencyuser.R;
import com.example.tomz4th_chaiyot.projectemergencyuser.dao.CarColorCollectionDao;
import com.example.tomz4th_chaiyot.projectemergencyuser.dao.CarNameCollectionDao;
import com.example.tomz4th_chaiyot.projectemergencyuser.dao.CarTypeCollectionDao;
import com.example.tomz4th_chaiyot.projectemergencyuser.dao.CarsCollectionDao;
import com.example.tomz4th_chaiyot.projectemergencyuser.dao.CommentCollectionDao;
import com.example.tomz4th_chaiyot.projectemergencyuser.dao.RateCollectionDao;
import com.example.tomz4th_chaiyot.projectemergencyuser.dao.RequestCollectionDao;
import com.example.tomz4th_chaiyot.projectemergencyuser.dao.ServiceCollectionDao;
import com.example.tomz4th_chaiyot.projectemergencyuser.dao.UsersCollectionDao;
import com.example.tomz4th_chaiyot.projectemergencyuser.fragment.RequestFragment;
import com.example.tomz4th_chaiyot.projectemergencyuser.fragment.ServiceListFragment;
import com.example.tomz4th_chaiyot.projectemergencyuser.manager.HistoryListManager;
import com.example.tomz4th_chaiyot.projectemergencyuser.manager.HttpManager;
import com.example.tomz4th_chaiyot.projectemergencyuser.manager.carManager;
import com.example.tomz4th_chaiyot.projectemergencyuser.manager.userManager;
import com.example.tomz4th_chaiyot.projectemergencyuser.notification.Config;
import com.example.tomz4th_chaiyot.projectemergencyuser.notification.UserUpdateFcmManager;
import com.example.tomz4th_chaiyot.projectemergencyuser.util.NotificationUtils;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.UUID;

import io.fabric.sdk.android.Fabric;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.tomz4th_chaiyot.projectemergencyuser.BaseUrl.BASE_URL_IMG_USER;

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
    private int userId = 0;
    CarsCollectionDao daocar;
    carManager car;
    private ArrayList<String> spTextCarType = new ArrayList<String>();
    private ArrayList<String> spTextCarName = new ArrayList<String>();
    private ArrayList<String> spTextCarColor = new ArrayList<String>();
    ImageView imgPhoto;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private String title;
    private String message;
    private String payload;
    private JSONObject data;
    private String status;

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
        UserUpdateFcmManager fcmManager = new UserUpdateFcmManager(MainActivity.this);
        if (fcmManager.getFcmToken() != null) {
            Log.e("MainActivity", "Token ID=" + fcmManager.getFcmToken());

            Call<UsersCollectionDao> call = HttpManager.getInstance().getService().updateFcmId(fcmManager.getFcmToken(), dao.getUser().get(0).getUserId());
            call.enqueue(new Callback<UsersCollectionDao>() {
                @Override
                public void onResponse(Call<UsersCollectionDao> call, Response<UsersCollectionDao> response) {

                    if (response.isSuccessful()) {
                        UsersCollectionDao data = response.body();
                        int id = data.getUser().get(0).getUserId();
                        String message = data.getMessage();
                        if (data.isSuccess()) {

                        } else {

                        }
                    } else {
                        Log.e("Error", response.errorBody().toString());

                    }
                }

                @Override
                public void onFailure(Call<UsersCollectionDao> call, Throwable t) {
                    Log.e("errorConnection", t.toString());

                }
            });
        }
        getRequestComment(5);
        getRequestCancel(6);
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
        imgPhoto = (ImageView) headerLayout.findViewById(R.id.imgPhoto);
        getCars();


        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    try {
                        title = intent.getStringExtra("title"); //check
                        message = intent.getStringExtra("message");//check
                        payload = intent.getStringExtra("payload");//check
                        data = new JSONObject(payload);//check
                        status = data.getString("status");//check

                    } catch (JSONException e) {
                        Log.e("1", "Json Exception: " + e.getMessage());
                    } catch (Exception e) {
                        Log.e("2", "Exception: " + e.getMessage());
                    }

                    //Handle Code Here!!
                    //loadDataJob();
                    if (title.equals("รับการร้องขอ")) {
                        getRequestSuccess(2);
                    }
                    if (title.equals("ความคิดเห็น")) {
                        getRequestComment(5);
                    }
                    if (title.equals("ไม่รับ")) {
                        getRequestCancel(6);
                    }

                }


            }
        };

    }

    @Override
    public void onResume() {
        super.onResume();
        getUsersShow();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(Contextor.getInstance().getContext());
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
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
        private String tabTitles[] = new String[]{"การร้องขอ", "ร้านที่ให้บริการ"};


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
        } else if (id == R.id.nav_history) {
            startActivity(new Intent(this, HistoryActivity.class));
        } else if (id == R.id.nav_tel) {
            startActivity(new Intent(this, TelActivity.class));
        } else if (id == R.id.nav_logout) {
            AlertDialog.Builder builder =
                    new AlertDialog.Builder(MainActivity.this);

            builder.setTitle("ต้องการต้องการออกระบบ ?");

            builder.setPositiveButton("ใช่", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    user.logOut();
                    Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
            });

            builder.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            builder.show();
        }
        return true;
    }

    private void send(String type, String name, String color, String number) {
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

                            final Dialog dialog = new Dialog(MainActivity.this);
                            dialog.setContentView(R.layout.dialog_car);

                            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                            lp.copyFrom(dialog.getWindow().getAttributes());
                            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                            dialog.getWindow().setAttributes(lp);

                            //Spinner
                            final Spinner spCarType = (Spinner) dialog.findViewById(R.id.spCarType);
                            spTextCarType.clear();
                            spCarType.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_dropdown_item_1line, spTextCarType));
                            createCarType();
                            ArrayAdapter<String> adapterCarType = new ArrayAdapter<String>(getBaseContext(),
                                    R.layout.support_simple_spinner_dropdown_item, spTextCarType);
                            spCarType.setAdapter(adapterCarType);

                            final Spinner spCarName = (Spinner) dialog.findViewById(R.id.spCarName);
                            final Spinner spCarColor = (Spinner) dialog.findViewById(R.id.spCarColor);

                            final TextView tvCarType = (TextView) dialog.findViewById(R.id.tvCarType);
                            final TextView tvCarName = (TextView) dialog.findViewById(R.id.tvCarName);
                            final TextView tvCarColor = (TextView) dialog.findViewById(R.id.tvCarColor);
                            final TextView tvCarNumber = (TextView) dialog.findViewById(R.id.tvCarNumber);

                            final EditText ediCarNumber = (EditText) dialog.findViewById(R.id.edtCarNumber);


                            final Button btnNextName = (Button) dialog.findViewById(R.id.btnNextName);
                            final Button btnExit = (Button) dialog.findViewById(R.id.btnExit);
                            final Button btnBackType = (Button) dialog.findViewById(R.id.btnBackType);
                            final Button btnNextColor = (Button) dialog.findViewById(R.id.btnNextColor);
                            final Button btnBackName = (Button) dialog.findViewById(R.id.btnBackName);
                            final Button btnSuccess = (Button) dialog.findViewById(R.id.btnSuccess);

                            btnNextName.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String text = spCarType.getSelectedItem().toString().substring(0, 1);
                                    if (text.equals("ก")) {
                                        showToast("กรุณาเลือกประเภทรถ");
                                    } else {
                                        int id = Integer.parseInt(text);
                                        tvCarType.setVisibility(View.GONE);
                                        spCarType.setVisibility(View.GONE);
                                        btnNextName.setVisibility(View.GONE);
                                        btnExit.setVisibility(View.GONE);

                                        spTextCarName.clear();
                                        spCarName.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_dropdown_item_1line, spTextCarName));
                                        createCarName(id);
                                        ArrayAdapter<String> adapterCarName = new ArrayAdapter<String>(getBaseContext(),
                                                R.layout.support_simple_spinner_dropdown_item, spTextCarName);
                                        spCarName.setAdapter(adapterCarName);

                                        tvCarName.setVisibility(View.VISIBLE);
                                        spCarName.setVisibility(View.VISIBLE);
                                        btnBackType.setVisibility(View.VISIBLE);
                                        btnNextColor.setVisibility(View.VISIBLE);

                                    }
                                }
                            });

                            btnExit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });

                            btnBackType.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    tvCarType.setVisibility(View.VISIBLE);
                                    spCarType.setVisibility(View.VISIBLE);
                                    btnNextName.setVisibility(View.VISIBLE);
                                    btnExit.setVisibility(View.VISIBLE);

                                    tvCarName.setVisibility(View.GONE);
                                    spCarName.setVisibility(View.GONE);
                                    btnBackType.setVisibility(View.GONE);
                                    btnNextColor.setVisibility(View.GONE);
                                }
                            });

                            btnNextColor.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String text = spCarName.getSelectedItem().toString().substring(0, 1);
                                    if (text.equals("ก")) {
                                        showToast("กรุณาเลือกยี่ห้อ/รุ่น รถ");
                                    } else {
                                        tvCarName.setVisibility(View.GONE);
                                        spCarName.setVisibility(View.GONE);
                                        btnBackType.setVisibility(View.GONE);
                                        btnNextColor.setVisibility(View.GONE);


                                        spTextCarColor.clear();
                                        spCarColor.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_dropdown_item_1line, spTextCarColor));
                                        createCarColor();
                                        ArrayAdapter<String> adapterCarColor = new ArrayAdapter<String>(getBaseContext(),
                                                R.layout.support_simple_spinner_dropdown_item, spTextCarColor);
                                        spCarColor.setAdapter(adapterCarColor);

                                        tvCarColor.setVisibility(View.VISIBLE);
                                        spCarColor.setVisibility(View.VISIBLE);
                                        tvCarNumber.setVisibility(View.VISIBLE);
                                        ediCarNumber.setVisibility(View.VISIBLE);
                                        btnBackName.setVisibility(View.VISIBLE);
                                        btnSuccess.setVisibility(View.VISIBLE);
                                    }

                                }
                            });

                            btnBackName.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    tvCarColor.setVisibility(View.GONE);
                                    spCarColor.setVisibility(View.GONE);
                                    tvCarNumber.setVisibility(View.GONE);
                                    ediCarNumber.setVisibility(View.GONE);
                                    btnBackName.setVisibility(View.GONE);
                                    btnSuccess.setVisibility(View.GONE);

                                    tvCarName.setVisibility(View.VISIBLE);
                                    spCarName.setVisibility(View.VISIBLE);
                                    btnBackType.setVisibility(View.VISIBLE);
                                    btnNextColor.setVisibility(View.VISIBLE);
                                }
                            });

                            btnSuccess.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String text = spCarColor.getSelectedItem().toString().substring(0, 1);
                                    if (text.equals("ก")) {
                                        showToast("กรุณาเลือกสีรถ");
                                    } else if (ediCarNumber.getText().length() == 0) {
                                        ediCarNumber.setError("กรุณากรอกข้อมูลป้ายทะเบียน");
                                    } else {
                                        String type = spCarType.getSelectedItem().toString().substring(2);
                                        String name = spCarName.getSelectedItem().toString().substring(2);
                                        String color = spCarColor.getSelectedItem().toString().substring(2);
                                        String number = ediCarNumber.getText().toString();
                                        send(type, name, color, number);
                                        dialog.dismiss();
                                    }
                                }
                            });

                            dialog.show();
                        }
                    } else {

                    }
                } else {
                    Log.e("Error", response.errorBody().toString());
                    showToast("ลงทะเบียนล้มเหลว");
                }
            }

            @Override
            public void onFailure(Call<CarsCollectionDao> call, Throwable t) {
                Log.e("errorConnection", t.toString());


            }

        });

    }

    private void createCarType() {
        Call<CarTypeCollectionDao> call = HttpManager.getInstance().getService().getCarType();
        call.enqueue(new Callback<CarTypeCollectionDao>() {
            @Override
            public void onResponse(Call<CarTypeCollectionDao> call, Response<CarTypeCollectionDao> response) {

                if (response.isSuccessful()) {
                    CarTypeCollectionDao daoCarType;
                    daoCarType = response.body();
                    if (daoCarType.isSuccess()) {
                        int numRows = daoCarType.getNumRows();
                        for (int i = 0; i < numRows; i++) {
                            spTextCarType.add("" + daoCarType.getCarType().get(i).getCarTypeId() + "." + daoCarType.getCarType().get(i).getCarTypeName());
                        }
                    }
                } else {
                    Log.e("Error", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<CarTypeCollectionDao> call, Throwable t) {
                Log.e("errorConnection", t.toString());


            }

        });
        spTextCarType.add("กรุณาเลือกประเภทรถ");
    }

    private void createCarName(int id) {
        Call<CarNameCollectionDao> call = HttpManager.getInstance().getService().getCarName(id);
        call.enqueue(new Callback<CarNameCollectionDao>() {
            @Override
            public void onResponse(Call<CarNameCollectionDao> call, Response<CarNameCollectionDao> response) {

                if (response.isSuccessful()) {
                    CarNameCollectionDao daoCarName;
                    daoCarName = response.body();

                    if (daoCarName.isSuccess()) {
                        int numRows = daoCarName.getNumRows();
                        for (int i = 0; i < numRows; i++) {
                            int no = i + 1;
                            spTextCarName.add("" + no + "." + daoCarName.getCarName().get(i).getCarNameName());
                        }
                    }

                } else {
                    Log.e("Error", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<CarNameCollectionDao> call, Throwable t) {
                Log.e("errorConnection", t.toString());


            }

        });
        spTextCarName.add("กรุณาเลือกยี่ห้อ/รุ่น รถ");
    }

    private void createCarColor() {
        Call<CarColorCollectionDao> call = HttpManager.getInstance().getService().getCarColor();
        call.enqueue(new Callback<CarColorCollectionDao>() {
            @Override
            public void onResponse(Call<CarColorCollectionDao> call, Response<CarColorCollectionDao> response) {

                if (response.isSuccessful()) {
                    CarColorCollectionDao daoCarColor;
                    daoCarColor = response.body();
                    if (daoCarColor.isSuccess()) {
                        int numRows = daoCarColor.getNumRows();
                        for (int i = 0; i < numRows; i++) {
                            int no = i + 1;
                            spTextCarColor.add("" + no + "." + daoCarColor.getCarColor().get(i).getCarColorName());
                        }
                    }
                } else {
                    Log.e("Error", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<CarColorCollectionDao> call, Throwable t) {
                Log.e("errorConnection", t.toString());


            }

        });
        spTextCarColor.add("กรุณาเลือกสีรถ");
    }

    private void showToast(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    private void getRequestComment(int statusId) {

        Call<RequestCollectionDao> call = HttpManager.getInstance().getService().getRequestAddComment(dao.getUser().get(0).getUserId(), statusId);
        call.enqueue(new Callback<RequestCollectionDao>() {
            @Override
            public void onResponse(Call<RequestCollectionDao> call, Response<RequestCollectionDao> response) {

                if (response.isSuccessful()) {
                    RequestCollectionDao data = response.body();
                    if (data.isSuccess()) {
                        final int idService = data.getRequest().get(0).getUserIdService();
                        final int idRequest = data.getRequest().get(0).getRequestId();

                        final Dialog dialog = new Dialog(MainActivity.this);
                        dialog.setContentView(R.layout.dialog_comment);

                        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                        lp.copyFrom(dialog.getWindow().getAttributes());
                        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                        dialog.getWindow().setAttributes(lp);
                        final RatingBar ratingBar = (RatingBar) dialog.findViewById(R.id.rate);
                        final EditText edtComment = (EditText) dialog.findViewById(R.id.edtComment);

                        Button btnComment = (Button) dialog.findViewById(R.id.btnComment);
                        btnComment.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                sendRate(ratingBar.getRating(), idService);
                                sendComment(edtComment.getText().toString(), idService);
                                UpdateRequestStatus(idRequest, 3);
                                dialog.dismiss();

                            }
                        });
                        dialog.show();
                    } else {

                    }

                } else {
                    Log.e("Error", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<RequestCollectionDao> call, Throwable t) {
                Log.e("errorConnection", t.toString());

            }

        });
    }

    private void getRequestSuccess(int statusId) {

        Call<RequestCollectionDao> call = HttpManager.getInstance().getService().getRequestAddComment(dao.getUser().get(0).getUserId(), statusId);
        call.enqueue(new Callback<RequestCollectionDao>() {
            @Override
            public void onResponse(Call<RequestCollectionDao> call, Response<RequestCollectionDao> response) {

                if (response.isSuccessful()) {
                    RequestCollectionDao data = response.body();
                    if (data.isSuccess()) {
                        getNameService(data.getRequest().get(0).getUserIdService());
                    } else {

                    }

                } else {
                    Log.e("Error", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<RequestCollectionDao> call, Throwable t) {
                Log.e("errorConnection", t.toString());

            }

        });
    }

    private void getNameService(int id) {
        Call<ServiceCollectionDao> call = HttpManager.getInstance().getService().getServices(id);
        call.enqueue(new Callback<ServiceCollectionDao>() {
            @Override
            public void onResponse(Call<ServiceCollectionDao> call, Response<ServiceCollectionDao> response) {
                if (response.isSuccessful()) {
                    ServiceCollectionDao data = response.body();
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("การร้องขอ")
                            .setMessage("ร้าน " + data.getService().get(0).getServiceName() + " รับเรื่องคุณแล้ว")
                            .setPositiveButton("ปิด", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .show();
                }
            }

            @Override
            public void onFailure(Call<ServiceCollectionDao> call, Throwable t) {

            }
        });

    }

    private void getRequestCancel(int statusId) {

        Call<RequestCollectionDao> call = HttpManager.getInstance().getService().getRequestAddComment(dao.getUser().get(0).getUserId(), statusId);
        call.enqueue(new Callback<RequestCollectionDao>() {
            @Override
            public void onResponse(Call<RequestCollectionDao> call, Response<RequestCollectionDao> response) {

                if (response.isSuccessful()) {
                    final RequestCollectionDao data = response.body();
                    if (data.isSuccess()) {
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("การร้องขอ")
                                .setMessage("ผู้ให้บริการร้าน " + data.getRequest().get(0).getServiceName() + " ไม่รับเรื่องของคุณ")
                                .setNegativeButton("ปิด", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        UpdateRequestStatus(data.getRequest().get(0).getRequestId(), 7);
                                        dialog.dismiss();
                                    }
                                })
                                .setPositiveButton("แสดงความคิดเห็น", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(final DialogInterface dialog, int which) {
                                        UpdateRequestStatus(data.getRequest().get(0).getRequestId(), 7);
                                        final int idService = data.getRequest().get(0).getUserIdService();
                                        final Dialog dialogcommwnt = new Dialog(MainActivity.this);
                                        dialogcommwnt.setContentView(R.layout.dialog_comment);

                                        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                                        lp.copyFrom(dialogcommwnt.getWindow().getAttributes());
                                        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                                        dialogcommwnt.getWindow().setAttributes(lp);

                                        final EditText edtComment = (EditText) dialogcommwnt.findViewById(R.id.edtComment);

                                        Button btnComment = (Button) dialogcommwnt.findViewById(R.id.btnComment);
                                        btnComment.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                sendComment(edtComment.getText().toString(), idService);
                                                dialogcommwnt.dismiss();

                                            }
                                        });
                                        dialogcommwnt.show();
                                    }
                                })
                                .show();
                    } else {

                    }

                } else {
                    Log.e("Error", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<RequestCollectionDao> call, Throwable t) {
                Log.e("errorConnection", t.toString());

            }

        });
    }

    private void sendComment(String text, int serviceId) {
        Call<CommentCollectionDao> call = HttpManager.getInstance().getService().insertComment(text, dao.getUser().get(0).getUserId(), serviceId);
        call.enqueue(new Callback<CommentCollectionDao>() {
            @Override
            public void onResponse(Call<CommentCollectionDao> call, Response<CommentCollectionDao> response) {

                if (response.isSuccessful()) {
                    showToast("เพิ่มความคิดเห็นเรียบร้อยแล้ว");

                } else {
                    Log.e("Error", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<CommentCollectionDao> call, Throwable t) {
                Log.e("errorConnection", t.toString());

            }

        });
    }

    private void UpdateRequestStatus(int requestId, int statusId) {
        Call<RequestCollectionDao> call = HttpManager.getInstance().getService().updateStatusRequest(requestId, statusId);
        call.enqueue(new Callback<RequestCollectionDao>() {
            @Override
            public void onResponse(Call<RequestCollectionDao> call, Response<RequestCollectionDao> response) {

            }

            @Override
            public void onFailure(Call<RequestCollectionDao> call, Throwable t) {

            }
        });
    }

    private void getUsersShow() {
        int id = dao.getUser().get(0).getUserId();

        Call<UsersCollectionDao> call = HttpManager.getInstance().getService().getUsersShow(id);
        call.enqueue(new Callback<UsersCollectionDao>() {
            @Override
            public void onResponse(Call<UsersCollectionDao> call, Response<UsersCollectionDao> response) {

                if (response.isSuccessful()) {
                    dao = response.body();
                    int id = dao.getUser().get(0).getUserId();
                    String message = dao.getMessage();
                    if (dao.isSuccess()) {
                        tvName.setText("ชื่อ :" + dao.getUser().get(0).getName());
                        tvEmail.setText("อีเมล์ :" + dao.getUser().get(0).getEmail());
                        tvTel.setText("ประเภท :" + dao.getUser().get(0).getTypeName());
                        String nameImg = dao.getUser().get(0).getImg();

                        Glide.with(MainActivity.this)
                                .load(BASE_URL_IMG_USER + nameImg)
                                .signature(new StringSignature(UUID.randomUUID().toString()))
                                .into(imgPhoto);

                    } else {
                        showToast(message);

                    }
                } else {
                    Log.e("Error", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<UsersCollectionDao> call, Throwable t) {
                Log.e("errorConnection", t.toString());
                showToast("เชื่อมต่อไม่สำเร็จ");
            }
        });
    }

    private void sendRate(float rate, int idService) {

        Call<RateCollectionDao> call = HttpManager.getInstance().getService().insertRating(dao.getUser().get(0).getUserId(), Float.toString(rate), idService);
        call.enqueue(new Callback<RateCollectionDao>() {
            @Override
            public void onResponse(Call<RateCollectionDao> call, Response<RateCollectionDao> response) {

            }

            @Override
            public void onFailure(Call<RateCollectionDao> call, Throwable t) {

            }
        });
    }


}

