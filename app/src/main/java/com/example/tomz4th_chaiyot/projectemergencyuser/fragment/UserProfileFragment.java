package com.example.tomz4th_chaiyot.projectemergencyuser.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.tomz4th_chaiyot.projectemergencyuser.R;
import com.example.tomz4th_chaiyot.projectemergencyuser.dao.CarsCollectionDao;
import com.example.tomz4th_chaiyot.projectemergencyuser.dao.UsersCollectionDao;
import com.example.tomz4th_chaiyot.projectemergencyuser.manager.HttpManager;
import com.example.tomz4th_chaiyot.projectemergencyuser.manager.userManager;

import com.kosalgeek.android.photoutil.ImageBase64;
import com.kosalgeek.android.photoutil.ImageLoader;
import com.nguyenhoanglam.imagepicker.activity.ImagePickerActivity;
import com.nguyenhoanglam.imagepicker.model.Image;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


/**
 * Created by nuuneoi on 11/16/2014.
 */
@SuppressWarnings("unused")
public class UserProfileFragment extends Fragment implements View.OnClickListener {

    EditText editTextName;
    EditText editTextTel;
    TextView tvEmail;
    private userManager user;
    UsersCollectionDao dao;
    CarsCollectionDao daocar;
    Button btnEdit;
    Button btnOpenPass;

    TextView tvPass;
    TextView tvPassNew;
    TextView tvPassNewConfirm;
    EditText editTextPassword;
    EditText editTextPasswordNew;
    EditText editTextPasswordNewConfirm;

    EditText edtCarType;
    EditText edtCarName;
    EditText edtCarColor;
    EditText edtCarNumber;
    Button btnEditCar;
    private int userId = 0;

    TextView btnAddPhoto;
    private int REQUEST_CODE_PICKER = 2000;
    private ArrayList<Image> images = new ArrayList<>();
    ImageView imgPhoto;
    Bitmap bitmap;

    public UserProfileFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static UserProfileFragment newInstance() {
        UserProfileFragment fragment = new UserProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = new userManager();
        dao = user.getDao();
        init(savedInstanceState);

        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user_profile, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        editTextName = (EditText) rootView.findViewById(R.id.editTextName);
        editTextTel = (EditText) rootView.findViewById(R.id.editTextTel);
        tvEmail = (TextView) rootView.findViewById(R.id.tvEmail);
        btnEdit = (Button) rootView.findViewById(R.id.btnEdit);
        edtCarType = (EditText) rootView.findViewById(R.id.edtCarType);
        edtCarName = (EditText) rootView.findViewById(R.id.edtCarName);
        edtCarColor = (EditText) rootView.findViewById(R.id.edtCarColor);
        edtCarNumber = (EditText) rootView.findViewById(R.id.edtCarNumber);
        btnOpenPass = (Button) rootView.findViewById(R.id.btnOpenPass);
        btnEditCar = (Button) rootView.findViewById(R.id.btnEditCar);
        imgPhoto = (ImageView) rootView.findViewById(R.id.imgPhoto);
        btnEdit.setOnClickListener(this);
        btnOpenPass.setOnClickListener(this);
        btnEditCar.setOnClickListener(this);
        getUsersShow();


        tvPass = (TextView) rootView.findViewById(R.id.tvPass);
        editTextPassword = (EditText) rootView.findViewById(R.id.editTextPassword);
        tvPassNew = (TextView) rootView.findViewById(R.id.tvPassNew);
        editTextPasswordNew = (EditText) rootView.findViewById(R.id.editTextPasswordNew);
        tvPassNewConfirm = (TextView) rootView.findViewById(R.id.tvPassNewConfirm);
        editTextPasswordNewConfirm = (EditText) rootView.findViewById(R.id.editTextPasswordNewConfirm);

        btnAddPhoto = (TextView) rootView.findViewById(R.id.tvAddPhoto);
        btnAddPhoto.setOnClickListener(this);

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
        if (v == btnOpenPass) {
            tvPass.setVisibility(View.VISIBLE);
            editTextPassword.setVisibility(View.VISIBLE);

            tvPassNew.setVisibility(View.VISIBLE);
            editTextPasswordNew.setVisibility(View.VISIBLE);

            tvPassNewConfirm.setVisibility(View.VISIBLE);
            editTextPasswordNewConfirm.setVisibility(View.VISIBLE);

            btnOpenPass.setVisibility(View.GONE);
        }
        if (v == btnEdit) {
            if (editTextPassword.getText().toString().length() == 0) {
                String password = "null";
                String passwordNew = "null";
                send(password, passwordNew);
            } else {
                if (confirmPassword(editTextPasswordNew.getText().toString(), editTextPasswordNewConfirm.getText().toString())) {
                    String password = editTextPassword.getText().toString();
                    String passwordNew = editTextPasswordNewConfirm.getText().toString();
                    send(password, passwordNew);
                    tvPass.setVisibility(View.GONE);
                    editTextPassword.setVisibility(View.GONE);

                    tvPassNew.setVisibility(View.GONE);
                    editTextPasswordNew.setVisibility(View.GONE);

                    tvPassNewConfirm.setVisibility(View.GONE);
                    editTextPasswordNewConfirm.setVisibility(View.GONE);

                    btnOpenPass.setVisibility(View.VISIBLE);
                } else {
                    showToast("รหัสใหม่ไม่ตรงกัน");
                    editTextPasswordNew.setText("");
                    editTextPasswordNewConfirm.setText("");
                    editTextPasswordNew.findFocus();
                }
            }

        }
        if (v == btnEditCar) {
            sendCar();
        }
        if (v == btnAddPhoto) {
            Intent intent = new Intent(getContext(), ImagePickerActivity.class);

            intent.putExtra(ImagePickerActivity.INTENT_EXTRA_FOLDER_MODE, true);
            intent.putExtra(ImagePickerActivity.INTENT_EXTRA_MODE, ImagePickerActivity.MODE_SINGLE);
            //intent.putExtra(ImagePickerActivity.INTENT_EXTRA_LIMIT, 1);
            intent.putExtra(ImagePickerActivity.INTENT_EXTRA_SHOW_CAMERA, true);
            intent.putExtra(ImagePickerActivity.INTENT_EXTRA_SELECTED_IMAGES, images);
            intent.putExtra(ImagePickerActivity.INTENT_EXTRA_FOLDER_TITLE, "อัลบั้ม");
            intent.putExtra(ImagePickerActivity.INTENT_EXTRA_IMAGE_TITLE, "เลือกรูปภาพ");
            intent.putExtra(ImagePickerActivity.INTENT_EXTRA_IMAGE_DIRECTORY, "กล้อง");
            startActivityForResult(intent, REQUEST_CODE_PICKER);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PICKER && resultCode == RESULT_OK && data != null) {
            images = data.getParcelableArrayListExtra(ImagePickerActivity.INTENT_EXTRA_SELECTED_IMAGES);
            StringBuilder sb = new StringBuilder();

            sb.append(images.get(0).getPath());
            try {
                bitmap = ImageLoader.init().from(sb.toString()).requestSize(1024, 1024).getBitmap();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Log.e("bitmap", "can't bitmap");
                Log.e("bitmap", e.toString());
            }
            Glide.with(getContext())
                    .load(images.get(0).getPath())
                    .into(imgPhoto);

            String encoded = ImageBase64.encode(bitmap);

            Log.e("bitmap", bitmap.toString());
            Log.e("base", encoded);
            Log.e("name ", sb.toString());
        }

    }

    public boolean confirmPassword(String password, String confirm) {
        if (password.equals(confirm)) return true;
        return false;
    }

    private void send(String password, String passwordNew) {
        int id = dao.getUser().get(0).getUserId();
        String name = editTextName.getText().toString();
        String tel = editTextTel.getText().toString();

        Call<UsersCollectionDao> call = HttpManager.getInstance().getService().updateUser(id, name, tel, password, passwordNew);
        call.enqueue(new Callback<UsersCollectionDao>() {
            @Override
            public void onResponse(Call<UsersCollectionDao> call, Response<UsersCollectionDao> response) {

                if (response.isSuccessful()) {
                    UsersCollectionDao data = response.body();
                    int id = data.getUser().get(0).getUserId();
                    String message = data.getMessage();
                    if (data.isSuccess()) {
                        showToast(message);
                    } else {
                        showToast(message);
                        editTextPassword.setText("");
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

    private void showToast(String text) {
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
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
                        editTextName.setText("" + dao.getUser().get(0).getName());
                        editTextTel.setText("" + dao.getUser().get(0).getTel());
                        tvEmail.setText("" + dao.getUser().get(0).getEmail());
                        edtCarType.setText("" + dao.getUser().get(0).getCarType());
                        edtCarName.setText("" + dao.getUser().get(0).getCarName());
                        edtCarColor.setText("" + dao.getUser().get(0).getCarColor());
                        edtCarNumber.setText("" + dao.getUser().get(0).getCarNumber());
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

    private void sendCar() {
        String type = edtCarType.getText().toString();
        String name = edtCarName.getText().toString();
        String color = edtCarColor.getText().toString();
        String number = edtCarNumber.getText().toString();
        int id = dao.getUser().get(0).getUserId();

        Call<CarsCollectionDao> call = HttpManager.getInstance().getService().insertCar(type, name, color, number, id);
        call.enqueue(new Callback<CarsCollectionDao>() {
            @Override
            public void onResponse(Call<CarsCollectionDao> call, Response<CarsCollectionDao> response) {

                if (response.isSuccessful()) {
                    daocar = response.body();
                    String message = daocar.getMessage();
                    if (daocar.isSuccess()) {
                        showToast(message);
                    } else {
                        showToast(message);
                    }
                } else {
                    Log.e("Error", response.errorBody().toString());
                    showToast("แก้ไขล้มเหลว");
                }
            }

            @Override
            public void onFailure(Call<CarsCollectionDao> call, Throwable t) {
                Log.e("errorConnection", t.toString());
                showToast(t.toString());


            }

        });

    }

}
