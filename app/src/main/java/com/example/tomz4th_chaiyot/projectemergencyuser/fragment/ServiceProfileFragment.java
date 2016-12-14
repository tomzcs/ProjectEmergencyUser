package com.example.tomz4th_chaiyot.projectemergencyuser.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tomz4th_chaiyot.projectemergencyuser.R;
import com.example.tomz4th_chaiyot.projectemergencyuser.dao.ServiceCollectionDao;
import com.example.tomz4th_chaiyot.projectemergencyuser.manager.HttpManager;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@SuppressWarnings("unused")
public class ServiceProfileFragment extends Fragment implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleApiClient googleApiClient;
    private GoogleMap mMap;
    private double latitude = 0;
    private double longitude = 0;
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 123;
    String id;
    int idd;
    TextView tvName;
    TextView tvDetail;
    TextView tvAdd;
    TextView tvTel;
    ServiceCollectionDao daoService;

    public ServiceProfileFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static ServiceProfileFragment newInstance(String getId) {
        ServiceProfileFragment fragment = new ServiceProfileFragment();
        Bundle args = new Bundle();
        args.putString("id", getId);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getArguments().getString("id");
        idd = Integer.parseInt(id);
        init(savedInstanceState);

        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_service_profile, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
        googleApiClient = new GoogleApiClient.Builder(getContext())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this) // alt+enter ออกมา เลือกอันที่ 2 คำว่า make
                .addOnConnectionFailedListener(this) //alt+enter ออกมา เลือกอันที่ 2 คำว่า make
                .build();
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        NestedScrollView nestedScrollView = (NestedScrollView) rootView.findViewById(R.id.nestedScrollView);
        nestedScrollView.setFillViewport(true);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.mapContainer);
        mapFragment.getMapAsync(this);
        tvName = (TextView) rootView.findViewById(R.id.tvName);
        tvDetail = (TextView) rootView.findViewById(R.id.tvDetail);
        tvAdd = (TextView) rootView.findViewById(R.id.tvAdd);
        tvTel = (TextView) rootView.findViewById(R.id.tvTel);

        Call<ServiceCollectionDao> call = HttpManager.getInstance().getService().getService(idd);
        call.enqueue(new Callback<ServiceCollectionDao>() {
            @Override
            public void onResponse(Call<ServiceCollectionDao> call, Response<ServiceCollectionDao> response) {

                if (response.isSuccessful()) {
                    daoService = response.body();

                    if (daoService.isSuccess()) {
                        tvName.setText(daoService.getService().get(0).getServiceName());
                        tvDetail.setText(daoService.getService().get(0).getServiceDetail());
                        tvAdd.setText(daoService.getService().get(0).getServiceAdd());
                        tvTel.setText(daoService.getService().get(0).getServiceTel());
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Call<ServiceCollectionDao> call = HttpManager.getInstance().getService().getService(idd);
        call.enqueue(new Callback<ServiceCollectionDao>() {
            @Override
            public void onResponse(Call<ServiceCollectionDao> call, Response<ServiceCollectionDao> response) {

                if (response.isSuccessful()) {
                    daoService = response.body();

                    if (daoService.isSuccess()) {
                        double lat = Double.parseDouble(daoService.getService().get(0).getServiceLat());
                        double lon = Double.parseDouble(daoService.getService().get(0).getServiceLon());
                        LatLng latlng = new LatLng(lat, lon);
                        mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(lat, lon))
                                .title("ตำแหน่งร้าน"));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom((latlng), 12));
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
    public void onConnected(@Nullable Bundle bundle) {
        //getCurrentLocation();

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
    @SuppressWarnings("UnusedParameters")
    private void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore Instance State here
    }

    @Override
    public void onLocationChanged(Location location) {
        mMap.clear();
        latitude = location.getLatitude();
        longitude = location.getLongitude();

        LatLng latlng = new LatLng(latitude, longitude);
        MarkerOptions markFrom = new MarkerOptions().position(new LatLng(latitude, longitude)).title("ตำแหน่งของร้านคุณ");
        mMap.addMarker(markFrom);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom((latlng), 12));
    }

    private void getService() {
        Call<ServiceCollectionDao> call = HttpManager.getInstance().getService().getService(idd);
        call.enqueue(new Callback<ServiceCollectionDao>() {
            @Override
            public void onResponse(Call<ServiceCollectionDao> call, Response<ServiceCollectionDao> response) {

                if (response.isSuccessful()) {
                    daoService = response.body();

                    if (daoService.isSuccess()) {
                        Toast.makeText(getContext(), daoService.getService().get(0).getServiceName(), Toast.LENGTH_SHORT).show();
                        tvName.setText(daoService.getService().get(0).getServiceName());
                        tvDetail.setText(daoService.getService().get(0).getServiceDetail());
                        tvAdd.setText(daoService.getService().get(0).getServiceAdd());
                        tvTel.setText(daoService.getService().get(0).getServiceTel());
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

    private void toast(String text) {
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
    }

}
