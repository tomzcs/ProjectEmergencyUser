package com.example.tomz4th_chaiyot.projectemergencyuser.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tomz4th_chaiyot.projectemergencyuser.R;
import com.example.tomz4th_chaiyot.projectemergencyuser.dao.UsersCollectionDao;
import com.example.tomz4th_chaiyot.projectemergencyuser.manager.userManager;


/**
 * Created by nuuneoi on 11/16/2014.
 */
@SuppressWarnings("unused")
public class UserProfileFragment extends Fragment implements View.OnClickListener {

    EditText editTextName;
    EditText editTextTel;
    TextView tvEmail;
    private userManager user;
    private UsersCollectionDao dao;
    Button btnEdit;
    Button btnOpenPass;

    TextView tvPass;
    TextView tvPassNew;
    TextView tvPassNewConfirm;
    EditText editTextPassword;
    EditText editTextPasswordNew;
    EditText editTextPasswordNewConfirm;

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
        btnOpenPass = (Button) rootView.findViewById(R.id.btnOpenPass);
        btnEdit.setOnClickListener(this);
        btnOpenPass.setOnClickListener(this);
        editTextName.setText("" + dao.getUser().get(0).getName());
        editTextTel.setText("" + dao.getUser().get(0).getTel());
        tvEmail.setText("" + dao.getUser().get(0).getEmail());
        tvPass = (TextView) rootView.findViewById(R.id.tvPass);
        editTextPassword = (EditText) rootView.findViewById(R.id.editTextPassword);
        tvPassNew = (TextView) rootView.findViewById(R.id.tvPassNew);
        editTextPasswordNew = (EditText) rootView.findViewById(R.id.editTextPasswordNew);
        tvPassNewConfirm = (TextView) rootView.findViewById(R.id.tvPassNewConfirm);
        editTextPasswordNewConfirm = (EditText) rootView.findViewById(R.id.editTextPasswordNewConfirm);
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

    }
}
