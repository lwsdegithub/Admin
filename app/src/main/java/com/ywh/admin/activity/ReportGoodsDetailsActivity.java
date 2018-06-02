package com.ywh.admin.activity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ywh.admin.R;
import com.ywh.admin.bean.Goods;
import com.ywh.admin.bean.User;
import com.ywh.admin.constant.NetConstant;
import com.ywh.admin.helper.GsonBean.Common;
import com.ywh.admin.helper.GsonBean.GoodsDetails;
import com.ywh.admin.helper.Retrofit2.GoodsHelper;
import com.ywh.admin.helper.Retrofit2.ReportHelper;
import com.ywh.admin.utils.BundleUtils;
import com.ywh.admin.utils.GlideUtils;
import com.ywh.admin.utils.RetrofitUtils;
import com.ywh.admin.utils.ToastUtils;
import com.ywh.admin.widget.ImageDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 李维升 on 2018/5/1.
 */

public class ReportGoodsDetailsActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    private int GOODS_ID;
    private int USER_ID;
    private int REPORT_ID;

    private String url1;
    private String url2;
    private String url3;

    @BindView(R.id.tv_goods_details_goods_name)
    TextView tvGoodsDetailsGoodsName;
    @BindView(R.id.civ_user_head_image)
    CircleImageView civUserHeadImage;
    @BindView(R.id.tv_goods_details_user_name)
    TextView tvGoodsDetailsUserName;
    @BindView(R.id.tv_goods_details_description)
    TextView tvGoodsDetailsDescription;
    @BindView(R.id.iv_goods_details_goods_photo_1)
    ImageView ivGoodsDetailsGoodsPhoto1;
    @BindView(R.id.iv_goods_details_goods_photo_2)
    ImageView ivGoodsDetailsGoodsPhoto2;
    @BindView(R.id.iv_goods_details_goods_photo_3)
    ImageView ivGoodsDetailsGoodsPhoto3;
    @BindView(R.id.srl_good_details)
    SwipeRefreshLayout srlGoodDetails;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GOODS_ID= BundleUtils.getInt(this,"GOODS_ID");
        REPORT_ID=BundleUtils.getInt(this,"REPORT_ID");
        setContentView(R.layout.activity_report_goods_details);
        ButterKnife.bind(this);
        this.initView();
    }

    private void initView() {
        //
        srlGoodDetails.setOnRefreshListener(this);
        //设置ActionBar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("物品详情");
        this.initData();

    }
    //初始化数据
    private void initData(){
        RetrofitUtils.getRetrofit(NetConstant.BASE_URL).create(GoodsHelper.class).getGoodsDetailsCall(GOODS_ID).enqueue(new Callback<GoodsDetails>() {
            @Override
            public void onResponse(Call<GoodsDetails> call, Response<GoodsDetails> response) {
                if (response.isSuccessful()) {
                    GoodsDetails goodsDetails = response.body();
                    Goods goods = goodsDetails.goods;
                    User user = goodsDetails.user;
                    //填充数据
                    USER_ID=user.getId();
                    tvGoodsDetailsUserName.setText(user.getUserName());
                    tvGoodsDetailsGoodsName.setText(goods.getGoods_name());
                    tvGoodsDetailsDescription.setText(goods.getGoods_description());
                    GlideUtils.loadImage(getApplicationContext(), NetConstant.BASE_HEAD_ICON_URL + user.getHeadIcon(), civUserHeadImage);
                    String[] photos = goods.getGoods_photo().split(",");
                    //加载物品图片
                    if (!photos[0].isEmpty()) {
                        if (photos.length==1){
                            ivGoodsDetailsGoodsPhoto1.setVisibility(View.VISIBLE);
                            url1 = photos[0];
                            GlideUtils.loadImage(getApplicationContext(), NetConstant.BASE_GOODS_PHOTOS_URL + url1, ivGoodsDetailsGoodsPhoto1);
                        }else if (photos.length==2){
                            ivGoodsDetailsGoodsPhoto1.setVisibility(View.VISIBLE);
                            ivGoodsDetailsGoodsPhoto2.setVisibility(View.VISIBLE);
                            url1 = photos[0];
                            url2 = photos[1];
                            GlideUtils.loadImage(getApplicationContext(), NetConstant.BASE_GOODS_PHOTOS_URL + url1, ivGoodsDetailsGoodsPhoto1);
                            GlideUtils.loadImage(getApplicationContext(), NetConstant.BASE_GOODS_PHOTOS_URL + url2, ivGoodsDetailsGoodsPhoto2);

                        }else if (photos.length==3){
                            ivGoodsDetailsGoodsPhoto1.setVisibility(View.VISIBLE);
                            ivGoodsDetailsGoodsPhoto2.setVisibility(View.VISIBLE);
                            ivGoodsDetailsGoodsPhoto3.setVisibility(View.VISIBLE);
                            url1 = photos[0];
                            url2 = photos[1];
                            url3 = photos[2];
                            GlideUtils.loadImage(getApplicationContext(), NetConstant.BASE_GOODS_PHOTOS_URL + url1, ivGoodsDetailsGoodsPhoto1);
                            GlideUtils.loadImage(getApplicationContext(), NetConstant.BASE_GOODS_PHOTOS_URL + url2, ivGoodsDetailsGoodsPhoto2);
                            GlideUtils.loadImage(getApplicationContext(), NetConstant.BASE_GOODS_PHOTOS_URL + url3, ivGoodsDetailsGoodsPhoto3);
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<GoodsDetails> call, Throwable t) {
                ToastUtils.showMsg(getApplicationContext(),"加载失败！");
            }
        });
    }

    @OnClick({ R.id.iv_goods_details_goods_photo_1, R.id.iv_goods_details_goods_photo_2, R.id.iv_goods_details_goods_photo_3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_goods_details_goods_photo_1:
                new ImageDialog(this,url1).show();
                break;
            case R.id.iv_goods_details_goods_photo_2:
                new ImageDialog(this,url2).show();
                break;
            case R.id.iv_goods_details_goods_photo_3:
                new ImageDialog(this,url3).show();
                break;
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.report_good_details_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id){
            case R.id.item_true:
            new AlertDialog.Builder(this).setTitle("举报属实").setMessage("将删除这个帖子，请确认！").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //删除这个物品,由于数据库为CASCADE，级联删除，所以删除物品时所有的举报信息都会被删除
                    RetrofitUtils.getRetrofit(NetConstant.BASE_URL).create(GoodsHelper.class).getDeleteGoodsCall(0,GOODS_ID).enqueue(new Callback<Common>() {
                        @Override
                        public void onResponse(Call<Common> call, Response<Common> response) {
                            if (response.isSuccessful()){
                                if (response.body().getStatus()==NetConstant.OK){
                                    ToastUtils.showMsg(getApplicationContext(),"删除成功");
                                    ReportGoodsDetailsActivity.this.finish();
                                }
                            }else {
                                ToastUtils.showMsg(getApplicationContext(),"请求失败");
                            }
                        }
                        @Override
                        public void onFailure(Call<Common> call, Throwable t) {
                            ToastUtils.showMsg(getApplicationContext(),"请求失败");
                        }
                    });
                }
            }).setNegativeButton("取消",null).show();
            break;
            case R.id.item_false:
                new AlertDialog.Builder(this).setTitle("举报有误").setMessage("将不做任何处理！").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //设置已经处理
                        RetrofitUtils.getRetrofit(NetConstant.BASE_URL).create(ReportHelper.class).getReportCall(2,REPORT_ID).enqueue(new Callback<Common>() {
                            @Override
                            public void onResponse(Call<Common> call, Response<Common> response) {
                                if (response.isSuccessful()){
                                    if (response.body().getStatus()==NetConstant.OK){
                                        ToastUtils.showMsg(getApplicationContext(),"处理成功");
                                        ReportGoodsDetailsActivity.this.finish();
                                    }
                                }else {
                                    ToastUtils.showMsg(getApplicationContext(),"请求失败");
                                }
                            }
                            @Override
                            public void onFailure(Call<Common> call, Throwable t) {
                                ToastUtils.showMsg(getApplicationContext(),"请求失败");
                            }
                        });
                    }
                }).setNegativeButton("取消",null).show();
            break;
        }
        return super.onOptionsItemSelected(item);
    }
    //返回键
    @Override
    public boolean onSupportNavigateUp() {
        this.finish();
        return super.onSupportNavigateUp();
    }



    @Override
    public void onRefresh() {
        this.initData();
        if (srlGoodDetails.isRefreshing()){
            srlGoodDetails.setRefreshing(false);
        }
    }
}
