package com.example.tomz4th_chaiyot.projectemergencyuser.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;
import com.example.tomz4th_chaiyot.projectemergencyuser.R;
import com.example.tomz4th_chaiyot.projectemergencyuser.activity.MainActivity;
import com.example.tomz4th_chaiyot.projectemergencyuser.dao.CommentCollectionDao;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import java.util.UUID;

import static com.example.tomz4th_chaiyot.projectemergencyuser.BaseUrl.BASE_URL_IMG_USER;


/**
 * Created by toMz4th-ChaiYot on 12/5/2016.
 */

public class CommentsListAdapter extends RecyclerView.Adapter<CommentsListAdapter.MyViewHolder> {

    Context mContext;
    CommentCollectionDao dataComment;
    TextView tvName;
    TextView tvDate;
    TextView tvDetail;
    ImageView imgPhoto;

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
            Glide.with(mContext)
                    .load(BASE_URL_IMG_USER + dataComment.getComment().get(position).getUserImg())
                    .signature(new StringSignature(UUID.randomUUID().toString()))
                    .into(imgPhoto);

        }

    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        MyViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            tvDetail = (TextView) itemView.findViewById(R.id.tvDetail);
            imgPhoto = (ImageView) itemView.findViewById(R.id.imgPhoto);


        }
    }

    public void setDataComment(CommentCollectionDao dataComment) {
        this.dataComment = dataComment;
    }
}
