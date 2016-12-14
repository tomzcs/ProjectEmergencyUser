package com.example.tomz4th_chaiyot.projectemergencyuser.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tomz4th_chaiyot.projectemergencyuser.R;
import com.example.tomz4th_chaiyot.projectemergencyuser.dao.CommentCollectionDao;


/**
 * Created by toMz4th-ChaiYot on 12/5/2016.
 */

public class CommentsListAdapter extends RecyclerView.Adapter<CommentsListAdapter.MyViewHolder> {

    Context mContext;
    CommentCollectionDao dataComment;
    TextView tvName;
    TextView tvDate;
    TextView tvDetail;

    public CommentsListAdapter(Context mContext) {
        this.mContext = mContext;
    }


    @Override
    public int getItemCount() {
        if (dataComment == null) {
            return 0;
        }
        if (dataComment.getComment() == null) {
            return 0;
        }
        return dataComment.getComment().size();
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
            tvName.setText(dataComment.getComment().get(position).getUserName());
            tvDate.setText(dataComment.getComment().get(position).getCommentCreatedAt());
            tvDetail.setText(dataComment.getComment().get(position).getCommentDetail());
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

    public void setDataComment(CommentCollectionDao dataComment) {
        this.dataComment = dataComment;
    }
}
