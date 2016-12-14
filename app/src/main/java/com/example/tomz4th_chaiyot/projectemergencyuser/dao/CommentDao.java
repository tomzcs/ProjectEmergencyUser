package com.example.tomz4th_chaiyot.projectemergencyuser.dao;

import com.google.gson.annotations.SerializedName;

/**
 * Created by toMz4th-ChaiYot on 12/6/2016.
 */

public class CommentDao {
    @SerializedName("COMMENT_ID") private int commentId;
    @SerializedName("COMMENT_DETAIL") private String commentDetail;
    @SerializedName("USER_ID") private int userId;
    @SerializedName("USER_NAME") private String userName;
    @SerializedName("USER_ID_SERVICE") private int userServiceId;
    @SerializedName("COMMENT_CREATED_AT") private String commentCreatedAt;

    public CommentDao(){}

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getCommentDetail() {
        return commentDetail;
    }

    public void setCommentDetail(String commentDetail) {
        this.commentDetail = commentDetail;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserServiceId() {
        return userServiceId;
    }

    public void setUserServiceId(int userServiceId) {
        this.userServiceId = userServiceId;
    }

    public String getCommentCreatedAt() {
        return commentCreatedAt;
    }

    public void setCommentCreatedAt(String commentCreatedAt) {
        this.commentCreatedAt = commentCreatedAt;
    }
}
