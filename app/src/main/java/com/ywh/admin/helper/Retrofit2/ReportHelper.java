package com.ywh.admin.helper.Retrofit2;

import com.ywh.admin.helper.GsonBean.Common;
import com.ywh.admin.helper.GsonBean.ReportItem;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by 李维升 on 2018/6/1.
 */

public interface ReportHelper {
    @POST("ReportSevlet")
    @FormUrlEncoded
    Call<List<ReportItem>> getReportListCall(@Field("TYPE") int type);

    @POST("ReportSevlet")
    @FormUrlEncoded
    Call<Common> getReportCall(@Field("TYPE") int type,@Field("REPORT_ID") int reportId);

}
