package com.example.tomz4th_chaiyot.projectemergencyuser.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.tomz4th_chaiyot.projectemergencyuser.R;
import com.inthecheesefactory.thecheeselibrary.view.BaseCustomViewGroup;
import com.inthecheesefactory.thecheeselibrary.view.state.BundleSavedState;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class HistoryListItem extends BaseCustomViewGroup {

    private TextView tvRequestDetail;
    private TextView tvRequestDetailCar;
    private TextView tvUserIdService;
    private TextView tvDate;
    private TextView tvStatus;

    public HistoryListItem(Context context) {
        super(context);
        initInflate();
        initInstances();
    }

    public HistoryListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstances();
        initWithAttrs(attrs, 0, 0);
    }

    public HistoryListItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstances();
        initWithAttrs(attrs, defStyleAttr, 0);
    }

    @TargetApi(21)
    public HistoryListItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initInflate();
        initInstances();
        initWithAttrs(attrs, defStyleAttr, defStyleRes);
    }

    private void initInflate() {
        inflate(getContext(), R.layout.dialog_request, this);
    }

    private void initInstances() {
        // findViewById here
        tvRequestDetail = (TextView) findViewById(R.id.tvRequestDetail);
        tvRequestDetailCar = (TextView) findViewById(R.id.tvRequestDetailCar);
        tvUserIdService = (TextView) findViewById(R.id.tvUserIdService);
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvStatus = (TextView) findViewById(R.id.tvStatus);
    }

    private void initWithAttrs(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        /*
        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.StyleableName,
                defStyleAttr, defStyleRes);

        try {

        } finally {
            a.recycle();
        }
        */
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();

        BundleSavedState savedState = new BundleSavedState(superState);
        // Save Instance State(s) here to the 'savedState.getBundle()'
        // for example,
        // savedState.getBundle().putString("key", value);

        return savedState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        BundleSavedState ss = (BundleSavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());

        Bundle bundle = ss.getBundle();
        // Restore State from bundle here
    }


    public void setTvRequestDetail(String text) {
        tvRequestDetail.setText(text);
    }

    public void setTvRequestDetailCar(String text) {
        tvRequestDetailCar.setText(text);
    }

    public void setTvUserIdService(String text) {
        tvUserIdService.setText(text);
    }

    public void setTvDate(String text) {
        tvDate.setText(text);
    }

    public void setTvStatus(String text) {
        tvStatus.setText(text);
    }

}
