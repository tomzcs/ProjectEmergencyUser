package com.example.tomz4th_chaiyot.projectemergencyuser.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tomz4th_chaiyot.projectemergencyuser.R;
import com.example.tomz4th_chaiyot.projectemergencyuser.dao.UsersCollectionDao;
import com.example.tomz4th_chaiyot.projectemergencyuser.manager.HttpManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterFragment extends Fragment implements View.OnClickListener {

    EditText editTextName;
    EditText editTextTel;
    EditText editTextEmail;
    EditText editTextPassword;
    Button btnRegister;

    public RegisterFragment() {
        super();
    }

    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();
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

    private void onRestoreInstanceState(Bundle savedInstanceState) {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_register, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    private void initInstances(View rootView, Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("ลงทะเบียน");

        editTextName = (EditText) rootView.findViewById(R.id.editTextName);
        editTextTel = (EditText) rootView.findViewById(R.id.editTextTel);
        editTextEmail = (EditText) rootView.findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) rootView.findViewById(R.id.editTextPassword);
        btnRegister = (Button) rootView.findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(this);
        editTextPassword.setOnClickListener(this);


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
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore Instance State here
        }
    }

    @Override
    public void onClick(View v) {
        if (v == btnRegister || v == editTextPassword) {
            if (editTextPassword.getText().toString().length() == 0) {
                editTextPassword.setError("กรุณากรอกข้อมูล รหัสผ่าน!");
            }
            if (editTextName.getText().toString().length() == 0) {
                editTextName.setError("กรุณากรอกข้อมูล ชื่อ-สกุล!");
            }
            if (editTextEmail.getText().toString().length() == 0) {
                editTextEmail.setError("กรุณากรอกข้อมูล อีเมล์!");
            }
            if (editTextTel.getText().toString().length() == 0) {
                editTextTel.setError("กรุณากรอกข้อมูล เบอร์โทรศัพท์!");
            } else {
                send();
            }

        }

    }

    private void send() {
        String name = editTextName.getText().toString();
        String tel = editTextTel.getText().toString();
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        if (!validateEmail()) {
            return;
        }
        Call<UsersCollectionDao> call = HttpManager.getInstance().getService().register(name, tel, email, password, "1");
        call.enqueue(new Callback<UsersCollectionDao>() {
            @Override
            public void onResponse(Call<UsersCollectionDao> call, Response<UsersCollectionDao> response) {

                if (response.isSuccessful()) {
                    UsersCollectionDao data = response.body();
                    int id = data.getUser().get(0).getUserId();
                    String message = data.getMessage();
                    if (data.isSuccess()) {
                        if (id == 0) {
                            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                            editTextEmail.setText("");

                        } else {
                            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                            getFragmentManager().popBackStack();

                        }
                    } else {
                        showToast(message);
                    }
                } else {
                    Log.e("Error", response.errorBody().toString());
                    showToast("ลงทะเบียนล้มเหลว");
                }
            }

            @Override
            public void onFailure(Call<UsersCollectionDao> call, Throwable t) {
                Log.e("errorConnection", t.toString());
                showToast("เชื่อมต่อไม่สำเร็จ");
            }
        });
    }

    // Validating email
    private boolean validateEmail() {
        String email = editTextEmail.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            requestFocus(editTextEmail);
            showToast("อีเมล์ไม่ถูกต้อง");
            return false;
        }

        return true;
    }

    private void showToast(String text) {
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
