package com.example.tomz4th_chaiyot.projectemergencyuser.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tomz4th_chaiyot.projectemergencyuser.R;
import com.example.tomz4th_chaiyot.projectemergencyuser.activity.MainActivity;
import com.example.tomz4th_chaiyot.projectemergencyuser.dao.UsersCollectionDao;
import com.example.tomz4th_chaiyot.projectemergencyuser.manager.HttpManager;
import com.example.tomz4th_chaiyot.projectemergencyuser.manager.userManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment implements View.OnClickListener {
    EditText editTextEmail;
    EditText editTextPassword;
    Button btnLogin;
    Button btnRegister;
    private ProgressDialog mProgress;

    public LoginFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("บริการช่วยรถยามฉุกเฉิน");
        editTextEmail = (EditText) rootView.findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) rootView.findViewById(R.id.editTextPassword);
        btnLogin = (Button) rootView.findViewById(R.id.btnLogin);
        btnRegister = (Button) rootView.findViewById(R.id.btnRegister);
        btnLogin.setOnClickListener(this);
        editTextPassword.setOnClickListener(this);
        btnRegister.setOnClickListener(this);

        mProgress = new ProgressDialog(getContext());
        mProgress.setTitle("กำลังลงชื่อเข้าใช้");
        mProgress.setMessage("โปรอรอ...");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);
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

    @Override
    public void onClick(View v) {
        if (v == btnLogin || v == editTextPassword) {
            clickLogin();
        }
        if (v == btnRegister) {
            //startActivity(new Intent(getContext(), RegisterActivity.class));
            getFragmentManager().beginTransaction()
                    .replace(R.id.contentContainer, RegisterFragment.newInstance())
                    .addToBackStack(null)
                    .commit();
        }
    }

    private void clickLogin() {
        if (editTextEmail.getText().length() == 0)
            editTextEmail.setError("กรุณากรอกข้อมูล ชื่อเข้าใช้งาน!");
        else if (editTextPassword.getText().length() == 0)
            editTextPassword.setError("กรุณากรอกข้อมูล รหัสผ่าน!");
        else

            send();
    }

    private void send() {
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        Call<UsersCollectionDao> call = HttpManager.getInstance().getService().login(email, password);
        call.enqueue(new Callback<UsersCollectionDao>() {
            @Override
            public void onResponse(Call<UsersCollectionDao> call, Response<UsersCollectionDao> response) {

                if (response.isSuccessful()) {
                    UsersCollectionDao data = response.body();
                    int id = data.getUser().get(0).getUserId();
                    String message = data.getMessage();
                    if (data.isSuccess()) {
                        userManager user = new userManager();
                        user.setDao(data);
                        user.SaveCache();
                        if (id == 0) {
                            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                            editTextPassword.setText("");
                            editTextPassword.clearFocus();
                            editTextEmail.findFocus();

                        } else if (id == 1) {
                            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                            editTextPassword.setText("");
                        } else {
                            mProgress.show();
                            new Thread(new Runnable() {
                                public void run() {
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                    }

                                    Intent intent = new Intent(getContext(), MainActivity.class);
                                    startActivity(intent);
                                    getActivity().finish();
                                    mProgress.dismiss();
                                }
                            }).start();
                        }
                    }

                } else {
                    Toast.makeText(getContext(), "เชื่อมต่อไม่สำเร็จ Json", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UsersCollectionDao> call, Throwable t) {

                Log.e("errorConnection", t.toString());
                Toast.makeText(getContext(), "เชื่อมต่อไม่สำเร็จ ", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
