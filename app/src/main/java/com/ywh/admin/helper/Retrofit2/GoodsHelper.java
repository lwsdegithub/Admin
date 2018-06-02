package com.ywh.admin.helper.Retrofit2;

import com.ywh.admin.helper.GsonBean.Common;
import com.ywh.admin.helper.GsonBean.GoodsDetails;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by 李维升 on 2018/6/2.
 */

public interface GoodsHelper {
    //根据物品ID获取
    @GET("GoodsDetailsSevlet")
    Call<GoodsDetails> getGoodsDetailsCall(@Query("GOODS_ID") int GOODS_ID);

    @POST("GoodsServlet")
    @FormUrlEncoded
    Call<Common> getDeleteGoodsCall(@Field("TYPE") int type, @Field("GOODS_ID") int goodsId);
}
