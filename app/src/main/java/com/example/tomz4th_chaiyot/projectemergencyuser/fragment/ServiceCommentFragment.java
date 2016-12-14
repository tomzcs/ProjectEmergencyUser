package com.example.tomz4th_chaiyot.projectemergencyuser.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tomz4th_chaiyot.projectemergencyuser.R;
import com.example.tomz4th_chaiyot.projectemergencyuser.adapter.CommentsListAdapter;
import com.example.tomz4th_chaiyot.projectemergencyuser.dao.CommentCollectionDao;
import com.example.tomz4th_chaiyot.projectemergencyuser.dao.ServiceCollectionDao;
import com.example.tomz4th_chaiyot.projectemergencyuser.manager.HttpManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by nuuneoi on 11/16/2014.
 */
@SuppressWarnings("unused")
public class ServiceCommentFragment extends Fragment {

    RecyclerView recyclerView;
    CommentsListAdapter listAdapter;
    String id;
    int idd;
    CommentCollectionDao dataComment;
    TextView tvCountComment;

    public ServiceCommentFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static ServiceCommentFragment newInstance(String getId) {
        ServiceCommentFragment fragment = new ServiceCommentFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_service_comment, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
        listAdapter = new CommentsListAdapter(getContext());
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        tvCountComment = (TextView) rootView.findViewById(R.id.tvCountComment);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();

        loadData();
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

    private void loadData() {
        Call<ServiceCollectionDao> call = HttpManager.getInstance().getService().getService(idd);
        call.enqueue(new Callback<ServiceCollectionDao>() {
            @Override
            public void onResponse(Call<ServiceCollectionDao> call, Response<ServiceCollectionDao> response) {

                if (response.isSuccessful()) {
                    ServiceCollectionDao daoService = response.body();
                    if (daoService.isSuccess()) {
                        int getId = daoService.getService().get(0).getUserServiceId();
                        getData(getId);
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

    public void getData(int getId) {
        Call<CommentCollectionDao> call = HttpManager.getInstance().getService().getComment(getId);
        call.enqueue(new Callback<CommentCollectionDao>() {
            @Override
            public void onResponse(Call<CommentCollectionDao> call, Response<CommentCollectionDao> response) {
                if (response.isSuccessful()) {
                    dataComment = response.body();
                    if (dataComment.isSuccess()) {
                        if (dataComment.getComment().size() == 0) {
                            Toast.makeText(getContext(), "111", Toast.LENGTH_SHORT).show();
                        } else {
                            tvCountComment.setText("" + dataComment.getComment().size());
                            listAdapter.setDataComment(dataComment);
                            listAdapter.notifyDataSetChanged();
                        }
                    }
                } else {
                    Toast.makeText(getContext(), "333", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CommentCollectionDao> call, Throwable t) {

            }
        });
    }

}
