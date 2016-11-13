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


}
