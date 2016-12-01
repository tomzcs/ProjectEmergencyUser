package com.example.tomz4th_chaiyot.projectemergencyuser.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tomz4th_chaiyot.projectemergencyuser.R;
import com.example.tomz4th_chaiyot.projectemergencyuser.adapter.HistoryListAdapter;
import com.example.tomz4th_chaiyot.projectemergencyuser.dao.RequestCollectionDao;
import com.example.tomz4th_chaiyot.projectemergencyuser.dao.UsersCollectionDao;
import com.example.tomz4th_chaiyot.projectemergencyuser.manager.HistoryListManager;
import com.example.tomz4th_chaiyot.projectemergencyuser.manager.HttpManager;
import com.example.tomz4th_chaiyot.projectemergencyuser.manager.userManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nuuneoi on 11/16/2014.
 */
@SuppressWarnings("unused")
public class HistoryFragment extends Fragment {

    ListView listView;
    HistoryListAdapter listAdapter;
    private UsersCollectionDao dao;
    private userManager user;
    TextView tvData;

    public HistoryFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static HistoryFragment newInstance() {
        HistoryFragment fragment = new HistoryFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_history, container, false);
        user = new userManager();
        dao = user.getDao();

        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("ประวัติการใช้งาน");

        tvData = (TextView) rootView.findViewById(R.id.tvData);
        listView = (ListView) rootView.findViewById(R.id.listView);
        listAdapter = new HistoryListAdapter();
        listView.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();

        Call<RequestCollectionDao> call = HttpManager.getInstance().getService().getRequestUser(dao.getUser().get(0).getUserId());
        call.enqueue(new Callback<RequestCollectionDao>() {
            @Override
            public void onResponse(Call<RequestCollectionDao> call, Response<RequestCollectionDao> response) {

                if (response.isSuccessful()) {
                    RequestCollectionDao daoRequestUser = response.body();
                    String message = daoRequestUser.getMessage();
                    if (message == "0"){
                        tvData.setVisibility(View.VISIBLE);
                    }else{
                        tvData.setVisibility(View.GONE);
                        HistoryListManager.getInstance().setDao(daoRequestUser);
                        listAdapter.notifyDataSetChanged();
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

}
