package com.example.tomz4th_chaiyot.projectemergencyuser.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tomz4th_chaiyot.projectemergencyuser.R;
import com.example.tomz4th_chaiyot.projectemergencyuser.dao.CommentCollectionDao;
import com.example.tomz4th_chaiyot.projectemergencyuser.dao.ComplaintCollectionDao;


/**
 * Created by toMz4th-ChaiYot on 12/5/2016.
 */

public class ComplaintListAdapter extends RecyclerView.Adapter<ComplaintListAdapter.MyViewHolder> {

    Context mContext;
    ComplaintCollectionDao dataCommplaint;
    TextView tvName;
    TextView tvDate;
    TextView tvDetail;

    public ComplaintListAdapter(Context mContext) {
        this.mContext = mContext;
    }


    @Override
    public int getItemCount() {
        if (dataCommplaint == null) {
            return 0;
        }
        if (dataCommplaint.getComplaint() == null) {
            return 0;
        }
        return dataCommplaint.getComplaint().size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;


        itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.fragment_comment_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (position<getItemCount()){
            tvName.setText(dataCommplaint.getComplaint().get(position).getServiceName());
            tvDate.setText(dataCommplaint.getComplaint().get(position).getCreatedAt());
            tvDetail.setText(dataCommplaint.getComplaint().get(position).getDetail());
        }

    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        MyViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            tvDetail = (TextView) itemView.findViewById(R.id.tvDetail);

        }
    }

    public void setDataCommplaint(ComplaintCollectionDao dataCommplaint) {
        this.dataCommplaint = dataCommplaint;
    }
}
