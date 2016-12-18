package com.example.tomz4th_chaiyot.projectemergencyuser.fragment;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tomz4th_chaiyot.projectemergencyuser.R;
import com.example.tomz4th_chaiyot.projectemergencyuser.activity.MainActivity;
import com.example.tomz4th_chaiyot.projectemergencyuser.adapter.CommentsListAdapter;
import com.example.tomz4th_chaiyot.projectemergencyuser.adapter.ServiceListAdapter;
import com.example.tomz4th_chaiyot.projectemergencyuser.dao.CarColorCollectionDao;
import com.example.tomz4th_chaiyot.projectemergencyuser.dao.CarNameCollectionDao;
import com.example.tomz4th_chaiyot.projectemergencyuser.dao.CarTypeCollectionDao;
import com.example.tomz4th_chaiyot.projectemergencyuser.dao.CommentCollectionDao;
import com.example.tomz4th_chaiyot.projectemergencyuser.dao.RequestCollectionDao;
import com.example.tomz4th_chaiyot.projectemergencyuser.dao.ServiceCollectionDao;
import com.example.tomz4th_chaiyot.projectemergencyuser.dao.UserSendNotification;
import com.example.tomz4th_chaiyot.projectemergencyuser.dao.UsersCollectionDao;
import com.example.tomz4th_chaiyot.projectemergencyuser.manager.HttpManager;
import com.example.tomz4th_chaiyot.projectemergencyuser.manager.ServiceListManager;
import com.example.tomz4th_chaiyot.projectemergencyuser.manager.userManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RequestFragment extends Fragment implements
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        View.OnClickListener {

    private GoogleApiClient googleApiClient;
    private GoogleMap mMap;
    public double latitude = 0;
    public double longitude = 0;
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 123;
    EditText editTextRequestDetail;
    TextView editTextRequestDetailCar;
    TextView edtUserIdService;
    EditText edtLat;
    EditText edtLon;
    EditText edtServiceId;
    Spinner spRequestDetail;
    private ArrayList<String> text = new ArrayList<String>();
    ImageView btnPlus;
    Button btnSendRequest;
    ImageView btnAddCar;
    ImageView btnDelete;
    ImageView btnDown;
    android.support.design.widget.FloatingActionButton btnOpenRequest;
    ImageView btnAddService;
    android.support.v7.widget.CardView cardView;
    private UsersCollectionDao dao;
    private RequestCollectionDao daoRequest;
    private userManager user;
    private ArrayList<String> spTextCarType = new ArrayList<String>();
    private ArrayList<String> spTextCarName = new ArrayList<String>();
    private ArrayList<String> spTextCarColor = new ArrayList<String>();
    ListView listView;
    ServiceListAdapter listAdapter;
    ServiceCollectionDao daoService;
    Dialog dialog;
    RecyclerView recyclerView;
    CommentsListAdapter listAdapterComment;
    public String description;

    public RequestFragment() {
        super();
    }

    public static RequestFragment newInstance() {
        RequestFragment fragment = new RequestFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = new userManager();
        dao = user.getDao();
        getUsersShow();
        init(savedInstanceState);

        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_request, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;

    }

    private void onRestoreInstanceState(Bundle savedInstanceState) {
    }

    private void init(Bundle savedInstanceState) {
        googleApiClient = new GoogleApiClient.Builder(getContext())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this) // alt+enter ออกมา เลือกอันที่ 2 คำว่า make
                .addOnConnectionFailedListener(this) //alt+enter ออกมา เลือกอันที่ 2 คำว่า make
                .build();

        listAdapterComment = new CommentsListAdapter(getContext());
    }

    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById her
        //Show Maps
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.mapContainer);
        mapFragment.getMapAsync(this);

        editTextRequestDetail = (EditText) rootView.findViewById(R.id.editTextRequestDetail);
        edtServiceId = (EditText) rootView.findViewById(R.id.edtServiceId);

        editTextRequestDetailCar = (TextView) rootView.findViewById(R.id.editTextRequestDetailCar);
        edtUserIdService = (TextView) rootView.findViewById(R.id.edtUserIdService);
        edtLat = (EditText) rootView.findViewById(R.id.edtLat);
        edtLon = (EditText) rootView.findViewById(R.id.edtLon);
        btnSendRequest = (Button) rootView.findViewById(R.id.btnSendRequest);
        btnSendRequest.setOnClickListener(this);

        //Spinner
        spRequestDetail = (Spinner) rootView.findViewById(R.id.spRequestDetail);
        createTextData();
        ArrayAdapter<String> adapterText = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_dropdown_item_1line, text);
        spRequestDetail.setAdapter(adapterText);


        //End

        btnPlus = (ImageView) rootView.findViewById(R.id.btn_plus);
        btnPlus.setOnClickListener(this);
        btnAddCar = (ImageView) rootView.findViewById(R.id.btn_add_car);
        btnAddCar.setOnClickListener(this);
        btnDelete = (ImageView) rootView.findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener(this);
        btnDown = (ImageView) rootView.findViewById(R.id.btnDown);
        btnDown.setOnClickListener(this);
        btnOpenRequest = (android.support.design.widget.FloatingActionButton) rootView.findViewById(R.id.btnOpenRequest);
        btnOpenRequest.setOnClickListener(this);
        btnAddService = (ImageView) rootView.findViewById(R.id.btn_add_service);
        btnAddService.setOnClickListener(this);

        cardView = (android.support.v7.widget.CardView) rootView.findViewById(R.id.cardView);

    }

    private void createTextData() {
        text.add("รถเสีย");
        text.add("รถยางแบน");
        text.add("แบตเตอรี่หมด");
        text.add("หม้อน้ำรั่ว");
        text.add("น้ำมันรั่ว");
    }

    @Override
    public void onStart() {
        super.onStart();
        googleApiClient.connect();

    }

    @Override
    public void onStop() {
        super.onStop();
        if (googleApiClient != null && googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
    }

    /*
     * Save Instance State Here
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance State here
    }

    /*
     * Restore Instance State Here
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore Instance State here
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        getCurrentLocation();

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void getCurrentLocation() {
        //CHECK PERMISSION ACCESS_FINE_LOCATION
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //Request permission ACCESS_FINE_LOCATION
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE_ASK_PERMISSIONS);
            return;
        }

        //Get current location
        LocationAvailability locationAvailability = LocationServices.FusedLocationApi.getLocationAvailability(googleApiClient);
        if (locationAvailability.isLocationAvailable()) {
            LocationRequest locationRequest = new LocationRequest()
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        } else {
            // Do something when location provider not available
        }
    }
    public String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                String aum = addresses.get(0).getLocality();
                String pro = addresses.get(0).getAdminArea();

                strAdd = aum+pro;

            } else {
                Log.w("address", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("address", "Canont get Address!");
        }
        return strAdd;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    getCurrentLocation();
                } else {
                    // Permission Denied
                    Toast.makeText(getContext(), "ACCESS_FINE_LOCATION Denied", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        mMap.clear();
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        description = getCompleteAddressString(latitude,longitude);
        edtLat.setText(latitude + "");
        edtLon.setText(longitude + "");

        LatLng latlng = new LatLng(latitude, longitude);
        MarkerOptions markFrom = new MarkerOptions().position(new LatLng(latitude, longitude)).title("ตำแหน่งปัจจุบัน");
        mMap.addMarker(markFrom);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom((latlng), 12));

    }


    @Override
    public void onClick(View v) {
        if (v == btnPlus) {
            editTextRequestDetail.setVisibility(View.VISIBLE);
            btnPlus.setVisibility(View.GONE);
            btnDelete.setVisibility(View.VISIBLE);
        }
        if (v == btnAddCar) {
            dialogCar();

        }
        if (v == btnAddService) {
            dialogService();
        }
        if (v == btnSendRequest) {
            if (editTextRequestDetailCar.getText().toString().length() == 0) {
                showToast("กรุณาระบุลักษณะรถของคุณ!");
            } else if (edtUserIdService.getText().toString().length() == 0) {
                showToast("กรุณาเลือกร้านให้บริการ!");
            } else {
                AlertDialog.Builder builder =
                        new AlertDialog.Builder(getContext());

                builder.setTitle("ต้องการส่งคำร้องขอ ?");

                builder.setPositiveButton("ใช่", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendRequest();
                        updateStatusService();
                    }
                });

                builder.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.show();

            }
        }
        if (v == btnDelete) {
            editTextRequestDetail.setVisibility(View.GONE);
            editTextRequestDetail.setText("");
            btnPlus.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.GONE);

        }
        if (v == btnDown) {
            cardView.setVisibility(View.GONE);
            btnOpenRequest.setVisibility(View.VISIBLE);
        }
        if (v == btnOpenRequest) {
            getUsersShow();
            cardView.setVisibility(View.VISIBLE);
            btnOpenRequest.setVisibility(View.GONE);
        }
    }

    private void getUsersShow() {
        int id = dao.getUser().get(0).getUserId();

        Call<UsersCollectionDao> call = HttpManager.getInstance().getService().getUsersShow(id);
        call.enqueue(new Callback<UsersCollectionDao>() {
            @Override
            public void onResponse(Call<UsersCollectionDao> call, Response<UsersCollectionDao> response) {

                if (response.isSuccessful()) {
                    dao = response.body();
                    String message = dao.getMessage();
                    if (dao.isSuccess()) {
                        if (dao.getUser().get(0).getCarType() != null) {
                            editTextRequestDetailCar.setText("" + dao.getUser().get(0).getCarType() + "/" +
                                    dao.getUser().get(0).getCarName() + "/" +
                                    dao.getUser().get(0).getCarColor() + "/" +
                                    dao.getUser().get(0).getCarNumber()
                            );
                        }

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

    private void getRequestUser() {
        int id = dao.getUser().get(0).getUserId();

        Call<RequestCollectionDao> call = HttpManager.getInstance().getService().getRequestUser(id);
        call.enqueue(new Callback<RequestCollectionDao>() {
            @Override
            public void onResponse(Call<RequestCollectionDao> call, Response<RequestCollectionDao> response) {

                if (response.isSuccessful()) {
                    RequestCollectionDao daoRequestUser = response.body();
                    String message = daoRequestUser.getMessage();
                    if (daoRequestUser.isSuccess()) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        LayoutInflater inflater = getLayoutInflater(Bundle.EMPTY);

                        View view = inflater.inflate(R.layout.dialog_request, null);
                        builder.setView(view);

                        TextView tvRequestDetail = (TextView) view.findViewById(R.id.tvRequestDetail);
                        TextView tvRequestDetailCar = (TextView) view.findViewById(R.id.tvRequestDetailCar);
                        TextView tvUserIdService = (TextView) view.findViewById(R.id.tvUserIdService);
                        TextView tvDate = (TextView) view.findViewById(R.id.tvDate);
                        TextView tvStatus = (TextView) view.findViewById(R.id.tvStatus);

                        tvRequestDetail.setText("" + daoRequestUser.getRequest().get(0).getRequestDetail());
                        tvRequestDetailCar.setText("" + daoRequestUser.getRequest().get(0).getRequestDetailCar());
                        tvUserIdService.setText("" + daoRequestUser.getRequest().get(0).getServiceName());
                        tvDate.setText("" + daoRequestUser.getRequest().get(0).getRequestCreatedAt());
                        tvStatus.setText("" + daoRequestUser.getRequest().get(0).getStatusName());

                        builder.setNegativeButton("ปิด", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                        builder.show();

                    } else {
                        showToast(message);

                    }
                } else {
                    Log.e("Error", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<RequestCollectionDao> call, Throwable t) {
                Log.e("errorConnection", t.toString());
                showToast("เชื่อมต่อไม่สำเร็จ/เรียกข้อมูลคำร้องขอ");
            }
        });
    }

    private void updateStatusService() {
        String serviceId = edtServiceId.getText().toString();
        int serviceIdd = Integer.parseInt(serviceId);
        Call<ServiceCollectionDao> call = HttpManager.getInstance().getService().updateservicestatus(serviceIdd, 1);
        call.enqueue(new Callback<ServiceCollectionDao>() {
            @Override
            public void onResponse(Call<ServiceCollectionDao> call, Response<ServiceCollectionDao> response) {


            }

            @Override
            public void onFailure(Call<ServiceCollectionDao> call, Throwable t) {
                Log.e("errorConnection", t.toString());
            }
        });
    }

    private void sendRequest() {
        int idUser = dao.getUser().get(0).getUserId();

        String request = spRequestDetail.getSelectedItem().toString();
        String requestDetail = "  " + editTextRequestDetail.getText().toString();
        String requestDetailCar = editTextRequestDetailCar.getText().toString();
        String requestLat = edtLat.getText().toString();
        String requestLon = edtLon.getText().toString();
        String description = getCompleteAddressString(latitude,longitude);

        String serviceId = edtServiceId.getText().toString();
        final int serviceIdd = Integer.parseInt(serviceId);

        Call<RequestCollectionDao> call = HttpManager.getInstance().getService().insertRequest(request + requestDetail, requestDetailCar, requestLat, requestLon, 1, idUser, serviceIdd);
        call.enqueue(new Callback<RequestCollectionDao>() {
            @Override
            public void onResponse(Call<RequestCollectionDao> call, Response<RequestCollectionDao> response) {

                if (response.isSuccessful()) {
                    daoRequest = response.body();
                    String message = daoRequest.getMessage();
                    if (daoRequest.isSuccess()) {
                        getRequestUser();
                        sendNotification();
                    } else {
                        //showToast(message);
                    }
                } else {
                    Log.e("Error", response.errorBody().toString());
                    showToast(response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<RequestCollectionDao> call, Throwable t) {
                Log.e("errorConnection", t.toString());
                showToast("เชื่อมต่อไม่สำเร็จ");
            }
        });
    }

    private void sendNotification() {
        String serviceId = edtServiceId.getText().toString();
        int serviceIdd = Integer.parseInt(serviceId);
        String request = spRequestDetail.getSelectedItem().toString();
        String requestDetail = "  " + editTextRequestDetail.getText().toString();
        String requestDetailCar = editTextRequestDetailCar.getText().toString();


        Call<UserSendNotification> call = HttpManager.getInstance().getService().notification(serviceIdd, request, requestDetail + requestDetailCar);
        call.enqueue(new Callback<UserSendNotification>() {
            @Override
            public void onResponse(Call<UserSendNotification> call, Response<UserSendNotification> response) {
                //showToast("notification success");
            }

            @Override
            public void onFailure(Call<UserSendNotification> call, Throwable t) {
                // showToast("notification not success");
            }
        });

    }

    private void showToast(String text) {
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
    }

    private void dialogCar() {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_car);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(lp);

        //Spinner
        final Spinner spCarType = (Spinner) dialog.findViewById(R.id.spCarType);
        spTextCarType.clear();
        spCarType.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, spTextCarType));
        createCarType();
        ArrayAdapter<String> adapterCarType = new ArrayAdapter<String>(getContext(),
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
                    spCarName.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, spTextCarName));
                    createCarName(id);
                    ArrayAdapter<String> adapterCarName = new ArrayAdapter<String>(getContext(),
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
                    spCarColor.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, spTextCarColor));
                    createCarColor();
                    ArrayAdapter<String> adapterCarColor = new ArrayAdapter<String>(getContext(),
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
                    //send(type, name, color, number);
                    editTextRequestDetailCar.setText("" + type + "/" +
                            name + "/" +
                            color + "/" +
                            number);
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }

    private void dialogService() {
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_service);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(lp);

        listView = (ListView) dialog.findViewById(R.id.listView);
        listAdapter = new ServiceListAdapter();
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(listViewItemClickListener);


        Call<ServiceCollectionDao> call = HttpManager.getInstance().getService().getServiceAll(description);
        call.enqueue(new Callback<ServiceCollectionDao>() {
            @Override
            public void onResponse(Call<ServiceCollectionDao> call, Response<ServiceCollectionDao> response) {
                if (response.isSuccessful()) {
                    daoService = response.body();
                    ServiceListManager.getInstance().setDao(daoService);
                    listAdapter.notifyDataSetChanged();

                } else {
                    Log.e("Error", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<ServiceCollectionDao> call, Throwable t) {
                Log.e("errorConnection", t.toString());

            }
        });

        dialog.show();

    }

    AdapterView.OnItemClickListener listViewItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

            //edtUserIdService.setText(daoService.getService().get(position).getServiceName() + "/" + daoService.getService().get(position).getServiceAdd());
            // edtServiceId.setText(daoService.getService().get(position).getUserServiceId() + "");
            final Dialog dialog1 = new Dialog(getContext());
            dialog1.setContentView(R.layout.dialog_comment_list);

            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(dialog1.getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            dialog1.getWindow().setAttributes(lp);

            Button btnExit = (Button) dialog1.findViewById(R.id.btnExit);
            Button btnSelect = (Button) dialog1.findViewById(R.id.btnSelect);
            TextView tvNotData = (TextView) dialog1.findViewById(R.id.tv_not_data);
            btnExit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog1.dismiss();
                    listAdapterComment.setDataComment(null);
                    listAdapterComment.notifyDataSetChanged();
                }
            });
            btnSelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    edtUserIdService.setText(daoService.getService().get(position).getServiceName() + "/" + daoService.getService().get(position).getServiceAdd());
                    edtServiceId.setText(daoService.getService().get(position).getUserServiceId() + "");
                    dialog1.dismiss();
                    dialog.dismiss();
                }
            });

            recyclerView = (RecyclerView) dialog1.findViewById(R.id.recyclerView);
            listAdapterComment.setDataComment(null);
            listAdapterComment.notifyDataSetChanged();
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(listAdapterComment);
            listAdapterComment.notifyDataSetChanged();
            int idservice = daoService.getService().get(position).getUserServiceId();

            loadData(idservice, tvNotData);
            dialog1.show();


        }
    };

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
                showToast("เชื่อมต่อไม่สำเร็จ");

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
                showToast("เชื่อมต่อไม่สำเร็จ");

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
                showToast("เชื่อมต่อไม่สำเร็จ");

            }

        });
        spTextCarColor.add("กรุณาเลือกสีรถ");
    }


    public void loadData(final int id, final TextView tvNotData) {
        Call<CommentCollectionDao> call = HttpManager.getInstance().getService().getComment(id);
        call.enqueue(new Callback<CommentCollectionDao>() {
            @Override
            public void onResponse(Call<CommentCollectionDao> call, Response<CommentCollectionDao> response) {
                if (response.isSuccessful()) {
                    CommentCollectionDao dataComment = response.body();

                    if (dataComment.getComment() != null) {
                        Log.e("id toat1", id + "");
                        listAdapterComment.setDataComment(dataComment);
                        listAdapterComment.notifyDataSetChanged();
                    } else {
                        Log.e("id toat2", id + "");
                        tvNotData.setVisibility(View.VISIBLE);
                    }

                } else {
                    tvNotData.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<CommentCollectionDao> call, Throwable t) {
                tvNotData.setVisibility(View.VISIBLE);
            }
        });
    }
}
