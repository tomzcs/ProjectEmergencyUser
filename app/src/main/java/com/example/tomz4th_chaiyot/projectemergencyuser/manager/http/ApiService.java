package com.example.tomz4th_chaiyot.projectemergencyuser.manager.http;

import com.example.tomz4th_chaiyot.projectemergencyuser.dao.UsersCollectionDao;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by toMz4th-ChaiYot on 10/9/2016.
 */

public interface ApiService {

    @FormUrlEncoded
    @POST("user/register")
    Call<UsersCollectionDao> register(@Field("user_name") String name,
                                      @Field("user_tel") String tel,
                                      @Field("user_email") String email,
                                      @Field("user_password") String password,
                                      @Field("user_type") String type
    );

    @FormUrlEncoded
    @POST("user/login")
    Call<UsersCollectionDao> login(@Field("user_email") String email,
                                   @Field("user_password") String password);


}
