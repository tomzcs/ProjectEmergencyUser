package com.example.tomz4th_chaiyot.projectemergencyuser.dao;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by toMz4th-ChaiYot on 12/6/2016.
 */

public class CommentCollectionDao {
    @SerializedName("success") private boolean success;
    @SerializedName("comment") private List<CommentDao> comment;
    @SerializedName("message") private String message;

    public CommentCollectionDao(){}

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<CommentDao> getComment() {
        return comment;
    }

    public void setComment(List<CommentDao> comment) {
        this.comment = comment;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
