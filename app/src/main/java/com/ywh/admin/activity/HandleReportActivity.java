package com.ywh.admin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.ywh.admin.R;
import com.ywh.admin.adapter.ReportListAdapter;
import com.ywh.admin.constant.NetConstant;
import com.ywh.admin.helper.GsonBean.ReportItem;
import com.ywh.admin.helper.Retrofit2.ReportHelper;
import com.ywh.admin.utils.IntentUtils;
import com.ywh.admin.utils.RetrofitUtils;
import com.ywh.admin.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 李维升 on 2018/6/1.
 */

public class HandleReportActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,SwipeRefreshLayout.OnRefreshListener {
    private ArrayList<ReportItem> reportItemArrayList=new ArrayList<>();
    private ReportListAdapter reportListAdapter;
    @BindView(R.id.lv_handle_report)
    ListView lvHandleReport;
    @BindView(R.id.srl_handle_report)
    SwipeRefreshLayout srlHandleReport;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handle_report);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("处理举报信息");
        actionBar.setDisplayHomeAsUpEnabled(true);

        srlHandleReport.setOnRefreshListener(this);

        reportListAdapter=new ReportListAdapter(this,reportItemArrayList);
        lvHandleReport.setAdapter(reportListAdapter);
        lvHandleReport.setOnItemClickListener(this);
        initData();
    }
    private void initData(){
        RetrofitUtils.getRetrofit(NetConstant.BASE_URL).create(ReportHelper.class).getReportListCall(1).enqueue(new Callback<List<ReportItem>>() {
            @Override
            public void onResponse(Call<List<ReportItem>> call, Response<List<ReportItem>> response) {
                if (response.isSuccessful()){
                    reportItemArrayList.clear();
                    reportItemArrayList.addAll(response.body());
                    if (reportItemArrayList.isEmpty()){
                        ToastUtils.showMsg(getApplicationContext(),"暂无任何举报信息");
                    }
                    reportListAdapter.notifyDataSetChanged();
                }else {
                    ToastUtils.showMsg(getApplicationContext(),"请求失败");
                }
            }

            @Override
            public void onFailure(Call<List<ReportItem>> call, Throwable t) {
                ToastUtils.showMsg(getApplicationContext(),"请求失败");
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (!reportItemArrayList.isEmpty()){
            ReportItem reportItem=reportItemArrayList.get(i);
            Intent intent=new Intent(HandleReportActivity.this,ReportGoodsDetailsActivity.class);
            Bundle bundle=new Bundle();
            bundle.putInt("GOODS_ID",reportItem.getGoodsId());
            bundle.putInt("REPORT_ID",reportItem.getReportId());
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    @Override
    public void onRefresh() {
        initData();
        if (srlHandleReport.isRefreshing()){
            srlHandleReport.setRefreshing(false);
        }
    }
}
