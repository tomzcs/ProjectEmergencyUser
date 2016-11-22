package com.example.tomz4th_chaiyot.projectemergencyuser.manager.http;

import com.example.tomz4th_chaiyot.projectemergencyuser.dao.CarsCollectionDao;
import com.example.tomz4th_chaiyot.projectemergencyuser.dao.UsersCollectionDao;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
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

    @GET("user/getCar/{id}")
    Call<CarsCollectionDao> getCar(@Path("id") int userId  );

    @GET("user/getUsersShow/{id}")
    Call<UsersCollectionDao> getUsersShow(@Path("id") int userId  );



}
