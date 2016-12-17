package com.example.tomz4th_chaiyot.projectemergencyuser.manager.http;

import com.example.tomz4th_chaiyot.projectemergencyuser.dao.CarColorCollectionDao;
import com.example.tomz4th_chaiyot.projectemergencyuser.dao.CarNameCollectionDao;
import com.example.tomz4th_chaiyot.projectemergencyuser.dao.CarTypeCollectionDao;
import com.example.tomz4th_chaiyot.projectemergencyuser.dao.CarsCollectionDao;
import com.example.tomz4th_chaiyot.projectemergencyuser.dao.CommentCollectionDao;
import com.example.tomz4th_chaiyot.projectemergencyuser.dao.ComplaintCollectionDao;
import com.example.tomz4th_chaiyot.projectemergencyuser.dao.RequestCollectionDao;
import com.example.tomz4th_chaiyot.projectemergencyuser.dao.ServiceCollectionDao;
import com.example.tomz4th_chaiyot.projectemergencyuser.dao.UserSendNotification;
import com.example.tomz4th_chaiyot.projectemergencyuser.dao.UsersCollectionDao;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by toMz4th-ChaiYot on 10/9/2016.
 */

public interface ApiService {

    @FormUrlEncoded
    @POST("user/register")
    Call<UsersCollectionDao> register(@Field("USER_NAME") String name,
                                      @Field("USER_TEL") String tel,
                                      @Field("USER_EMAIL") String email,
                                      @Field("USER_PASSWORD") String password,
                                      @Field("USER_IMG") String img,
                                      @Field("USER_TYPE_ID") String type);

    @FormUrlEncoded
    @POST("user/login")
    Call<UsersCollectionDao> login(@Field("USER_EMAIL") String email,
                                   @Field("USER_PASSWORD") String password);

    @FormUrlEncoded
    @POST("user/updateUser")
    Call<UsersCollectionDao> updateUser(@Field("USER_ID") int id,
                                        @Field("USER_NAME") String name,
                                        @Field("USER_TEL") String tel,
                                        @Field("USER_PASSWORD") String password,
                                        @Field("USER_PASSWORD_NEW") String passwordNew
    );

    @FormUrlEncoded
    @POST("user/insertCar")
    Call<CarsCollectionDao> insertCar(@Field("CAR_TYPE") String carType,
                                      @Field("CAR_NAME") String carName,
                                      @Field("CAR_COLOR") String carColor,
                                      @Field("CAR_NUMBER") String carNumber,
                                      @Field("USER_ID") int userId
    );

    @FormUrlEncoded
    @POST("user/insertRequest")
    Call<RequestCollectionDao> insertRequest(@Field("REQUEST_DETAIL") String requestDetail,
                                             @Field("REQUEST_DETAIL_CAR") String requestDetailCar,
                                             @Field("REQUEST_LAT") String requestLat,
                                             @Field("REQUEST_LON") String requestLon,
                                             @Field("STATUS_ID") int statusId,
                                             @Field("USER_ID") int userId,
                                             @Field("USER_ID_SERVICE") int userIdService
    );

    @GET("user/getCar/{id}")
    Call<CarsCollectionDao> getCar(@Path("id") int userId);

    @GET("user/getUsersShow/{id}")
    Call<UsersCollectionDao> getUsersShow(@Path("id") int userId);

    @GET("user/getRequestUser/{id}")
    Call<RequestCollectionDao> getRequestUser(@Path("id") int userId);

    @GET("user/getCarType")
    Call<CarTypeCollectionDao> getCarType();

    @GET("user/getCarName/{id}")
    Call<CarNameCollectionDao> getCarName(@Path("id") int userId);

    @GET("user/getCarColor")
    Call<CarColorCollectionDao> getCarColor();

    @GET("user/getServiceAll")
    Call<ServiceCollectionDao> getServiceAll();

    @GET("user/getServiceAllShow")
    Call<ServiceCollectionDao> getServiceAllShow();

    @GET("user/getServiceId/{id}")
    Call<ServiceCollectionDao> getService(@Path("id") int serviceId);

    @GET("user/getComment/{id}")
    Call<CommentCollectionDao> getComment(@Path("id") int serviceId);

    @FormUrlEncoded
    @POST("user/updateFcmId")
    Call<UsersCollectionDao> updateFcmId(@Field("USER_FCM_ID") String userFcmId,
                                         @Field("USER_ID") int userId

    );

    @FormUrlEncoded
    @POST("user/notification/{id}")
    Call<UserSendNotification> notification(
            @Path("id") int serviceId,
            @Field("title") String title,
            @Field("body") String body

    );

    @FormUrlEncoded
    @POST("user/updateservicestatus")
    Call<ServiceCollectionDao> updateservicestatus(
            @Field("SERVICE_ID") int serviceId,
            @Field("SERVICE_STATUS") int serviceStatus
    );

    @FormUrlEncoded
    @POST("user/getRequestAddComment/{id}")
    Call<RequestCollectionDao> getRequestAddComment(@Path("id") int userId, @Field("STATUS_ID") int statusId);

    @FormUrlEncoded
    @POST("user/insertComment")
    Call<CommentCollectionDao> insertComment(
            @Field("COMMENT_DETAIL") String complaintDetail,
            @Field("USER_ID") int userId,
            @Field("USER_ID_SERVICE") int serviceId

    );

    @FormUrlEncoded
    @POST("user/updateStatusRequest")
    Call<RequestCollectionDao> updateStatusRequest(@Field("REQUEST_ID") int id,
                                                   @Field("STATUS_ID") int name

    );

    @GET("user/getComplaint/{id}")
    Call<ComplaintCollectionDao> getComplaint(@Path("id") int userId);

    @FormUrlEncoded
    @POST("user/AddImgUser")
    Call<UsersCollectionDao> AddImgUser(@Field("USER_ID") int userId,
                                        @Field("ENCODE") String encode);

}
