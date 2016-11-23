package com.example.tomz4th_chaiyot.projectemergencyuser.fragment;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tomz4th_chaiyot.projectemergencyuser.R;
import com.example.tomz4th_chaiyot.projectemergencyuser.activity.MainActivity;
import com.example.tomz4th_chaiyot.projectemergencyuser.dao.RequestCollectionDao;
import com.example.tomz4th_chaiyot.projectemergencyuser.dao.UsersCollectionDao;
import com.example.tomz4th_chaiyot.projectemergencyuser.manager.HttpManager;
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

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestFragment extends Fragment implements
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener, View.OnClickListener {

    private GoogleApiClient googleApiClient;
    private GoogleMap mMap;
    private double latitude = 0;
    private double longitude = 0;
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 123;
    EditText editTextRequestDetail;
    EditText editTextRequestDetailCar;
    EditText edtUserIdService;
    EditText edtLat;
    EditText edtLon;
    Spinner spRequestDetail;
    private ArrayList<String> text = new ArrayList<String>();
    ImageView btnPlus;
    Button btnSendRequest;
    private UsersCollectionDao dao;
    private RequestCollectionDao daoRequest;
    private userManager user;


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
    }

    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById her
        //Show Maps
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.mapContainer);
        mapFragment.getMapAsync(this);

        editTextRequestDetail = (EditText) rootView.findViewById(R.id.editTextRequestDetail);

        editTextRequestDetailCar = (EditText) rootView.findViewById(R.id.editTextRequestDetailCar);
        edtUserIdService = (EditText) rootView.findViewById(R.id.edtUserIdService);
        edtLat = (EditText) rootView.findViewById(R.id.edtLat);
        edtLon = (EditText) rootView.findViewById(R.id.edtLon);
        btnSendRequest = (Button) rootView.findViewById(R.id.btnSendRequest);
        btnSendRequest.setOnClickListener(this);

        //Spinner
        spRequestDetail = (Spinner) rootView.findViewById(R.id.spRequestDetail);
        createThaiClubData();
        ArrayAdapter<String> adapterText = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_dropdown_item_1line, text);
        spRequestDetail.setAdapter(adapterText);


        //End
        btnPlus = (ImageView) rootView.findViewById(R.id.btn_plus);
        btnPlus.setOnClickListener(this);


    }

    private void createThaiClubData() {

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
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                    .setInterval(5000);
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        } else {
            // Do something when location provider not available
        }
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
        }
        if (v == btnSendRequest) {
            sendRequest();
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

                        tvRequestDetail.setText("รายละเอียดการร้องขอ :"+daoRequestUser.getRequest().get(0).getRequestDetail());
                        tvRequestDetailCar.setText("ลักษณะของรถ :"+daoRequestUser.getRequest().get(0).getRequestDetailCar());
                        tvUserIdService.setText("ร้านที่ให้บริการ :"+daoRequestUser.getRequest().get(0).getUserName());
                        tvDate.setText("วัน/เวลา :"+daoRequestUser.getRequest().get(0).getRequestCreatedAt());
                        tvStatus.setText("สถานะ :"+daoRequestUser.getRequest().get(0).getStatusName());

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

    private void sendRequest() {
        int idUser = dao.getUser().get(0).getUserId();

        String requestDetail = editTextRequestDetail.getText().toString();

        String requestDetailCar = editTextRequestDetailCar.getText().toString();
        String requestLat = edtLat.getText().toString();
        String requestLon = edtLon.getText().toString();

        Call<RequestCollectionDao> call = HttpManager.getInstance().getService().insertRequest("10", requestDetailCar, requestLat, requestLon, 1, idUser, 15);
        call.enqueue(new Callback<RequestCollectionDao>() {
            @Override
            public void onResponse(Call<RequestCollectionDao> call, Response<RequestCollectionDao> response) {

                if (response.isSuccessful()) {
                    daoRequest = response.body();
                    String message = daoRequest.getMessage();
                    if (daoRequest.isSuccess()) {
                        showToast(message);
                        getRequestUser();
                    } else {
                        showToast(message);

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

    private void showToast(String text) {
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
    }
}
