package com.example.tomz4th_chaiyot.projectemergencyuser.fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tomz4th_chaiyot.projectemergencyuser.R;
import com.example.tomz4th_chaiyot.projectemergencyuser.activity.ServiceProfileActivity;
import com.example.tomz4th_chaiyot.projectemergencyuser.adapter.ServiceListAdapter;
import com.example.tomz4th_chaiyot.projectemergencyuser.adapter.ServiceListAllAdapter;
import com.example.tomz4th_chaiyot.projectemergencyuser.dao.CarColorCollectionDao;
import com.example.tomz4th_chaiyot.projectemergencyuser.dao.ServiceCollectionDao;
import com.example.tomz4th_chaiyot.projectemergencyuser.manager.HttpManager;
import com.example.tomz4th_chaiyot.projectemergencyuser.manager.ServiceListManager;
import com.example.tomz4th_chaiyot.projectemergencyuser.manager.ServiceListsManager;
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

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class ServiceListFragment extends Fragment implements AdapterView.OnItemClickListener,
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    private GoogleApiClient googleApiClient;
    private GoogleMap mMap;
    public double latitude = 0;
    public double longitude = 0;
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 123;
    private String description;
    ListView listView;
    ServiceListAllAdapter listAdapter;
    ServiceCollectionDao daoService;
    private boolean isData;

    public ServiceListFragment() {
        super();
    }

    public static ServiceListFragment newInstance() {
        ServiceListFragment fragment = new ServiceListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);
        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
    }


    private void onRestoreInstanceState(Bundle savedInstanceState) {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_service_list, container, false);
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

    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.mapContainer);
        mapFragment.getMapAsync(this);

        listView = (ListView) rootView.findViewById(R.id.listView);
        listAdapter = new ServiceListAllAdapter();
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(listViewItemClickListener);


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
                String pro = addresses.get(0).getAdminArea();
                strAdd = pro;

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
        description = getCompleteAddressString(latitude, longitude);
        if (!isData) {

            loadata();
        }
        LatLng latlng = new LatLng(latitude, longitude);
        MarkerOptions markFrom = new MarkerOptions().position(new LatLng(latitude, longitude)).title("ตำแหน่งปัจจุบัน");
        mMap.addMarker(markFrom);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom((latlng), 12));

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startActivity(new Intent(getContext(), ServiceProfileActivity.class));
    }

    AdapterView.OnItemClickListener listViewItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            String serviceId = daoService.getService().get(position).getServiceId() + "";
            Intent intent = new Intent(getContext(), ServiceProfileActivity.class);
            intent.putExtra("id", serviceId);
            startActivity(intent);

        }
    };

    public void loadata() {

        String getlat = Double.toString(latitude);
        String getlon = Double.toString(longitude);
        Call<ServiceCollectionDao> call = HttpManager.getInstance().getService().getServiceAllNear(getlat, getlon,  100 + "");
        call.enqueue(new Callback<ServiceCollectionDao>() {
            @Override
            public void onResponse(Call<ServiceCollectionDao> call, Response<ServiceCollectionDao> response) {
                if (response.isSuccessful()) {
                    daoService = response.body();
                    ServiceListsManager.getInstance().setDao(daoService);
                    listAdapter.notifyDataSetChanged();
                    isData = true;
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
}
