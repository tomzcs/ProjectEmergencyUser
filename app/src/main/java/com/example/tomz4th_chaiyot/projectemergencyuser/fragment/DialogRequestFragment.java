package com.example.tomz4th_chaiyot.projectemergencyuser.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tomz4th_chaiyot.projectemergencyuser.R;
import com.example.tomz4th_chaiyot.projectemergencyuser.dao.RequestCollectionDao;
import com.example.tomz4th_chaiyot.projectemergencyuser.dao.UsersCollectionDao;
import com.example.tomz4th_chaiyot.projectemergencyuser.manager.HttpManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by nuuneoi on 11/16/2014.
 */
@SuppressWarnings("unused")
public class DialogRequestFragment extends Fragment {

    private UsersCollectionDao dao;
    private TextView tvRequestDetail;
    private TextView tvRequestDetailCar;
    private TextView tvUserIdService;
    private TextView tvDate;
    private TextView tvStatus;

    public DialogRequestFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static DialogRequestFragment newInstance() {
        DialogRequestFragment fragment = new DialogRequestFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_request, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        tvRequestDetail = (TextView) rootView.findViewById(R.id.tvRequestDetail);
        tvRequestDetailCar = (TextView) rootView.findViewById(R.id.tvRequestDetailCar);
        tvUserIdService = (TextView) rootView.findViewById(R.id.tvUserIdService);
        tvDate = (TextView) rootView.findViewById(R.id.tvDate);
        tvStatus = (TextView) rootView.findViewById(R.id.tvStatus);
        getRequestUser();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
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
                        tvRequestDetail.setText("555555555555555555555");
                        tvRequestDetailCar.setText("" + daoRequestUser.getRequest().get(0).getRequestDetailCar());
                        tvUserIdService.setText("" + daoRequestUser.getRequest().get(0).getUserName());
                        tvDate.setText(""+ daoRequestUser.getRequest().get(0).getRequestCreatedAt());
                        tvStatus.setText("" + daoRequestUser.getRequest().get(0).getStatusName());

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

    private void showToast(String text) {
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
    }

}
