package com.ywh.admin.helper.Retrofit2;

import com.ywh.admin.bean.User;
import com.ywh.admin.helper.GsonBean.Common;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by 李维升 on 2018/6/3.
 */

public interface UserHelper {
    @GET("UserSevlet")
    Call<List<User>> getUserListCall(@Query("TYPE") int type);

    @POST("UserSevlet")
    @FormUrlEncoded
    Call<Common> getDeleteUserCall(@Field("TYPE") int type, @Field("USER_ID") int userId);
}
