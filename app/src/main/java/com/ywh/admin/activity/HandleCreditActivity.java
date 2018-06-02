package com.ywh.admin.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.ywh.admin.R;
import com.ywh.admin.adapter.CreditListAdapter;
import com.ywh.admin.bean.User;
import com.ywh.admin.constant.NetConstant;
import com.ywh.admin.helper.Retrofit2.UserHelper;
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

public class HandleCreditActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private ArrayList<User> userArrayList = new ArrayList<>();
    private CreditListAdapter creditListAdapter;

    @BindView(R.id.lv_handle_credit)
    ListView lvHandleCredit;
    @BindView(R.id.srl_handle_credit)
    SwipeRefreshLayout srlHandleCredit;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handle_credit);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("处理信誉分过低用户");
        actionBar.setDisplayHomeAsUpEnabled(true);

        srlHandleCredit.setOnRefreshListener(this);
        creditListAdapter=new CreditListAdapter(userArrayList,this);
        lvHandleCredit.setAdapter(creditListAdapter);

        initData();
    }
    private void initData(){
        RetrofitUtils.getRetrofit(NetConstant.BASE_URL).create(UserHelper.class).getUserListCall(7).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()){
                    userArrayList.clear();
                    userArrayList.addAll(response.body());
                    creditListAdapter.notifyDataSetChanged();
                }else {
                    ToastUtils.showMsg(getApplicationContext(),"请求失败");
                }
            }
            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
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
    public void onRefresh() {
        initData();
        if (srlHandleCredit.isRefreshing()){
            srlHandleCredit.setRefreshing(false);
        }
    }
}
