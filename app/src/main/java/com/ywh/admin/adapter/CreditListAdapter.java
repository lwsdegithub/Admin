package com.ywh.admin.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.ywh.admin.R;
import com.ywh.admin.bean.User;
import com.ywh.admin.constant.NetConstant;
import com.ywh.admin.helper.GsonBean.Common;
import com.ywh.admin.helper.Retrofit2.UserHelper;
import com.ywh.admin.utils.GlideUtils;
import com.ywh.admin.utils.RetrofitUtils;
import com.ywh.admin.utils.ToastUtils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 李维升 on 2018/6/2.
 */

public class CreditListAdapter extends BaseAdapter {
    private List<User> userList;
    private Context context;

    public CreditListAdapter(List<User> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int i) {
        return userList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder=null;
        if (view==null){
            view=View.inflate(context, R.layout.item_list_user,null);
            viewHolder=new ViewHolder();
            viewHolder.headIcon=view.findViewById(R.id.civ_item_user_head_icon);
            viewHolder.userName=view.findViewById(R.id.tv_item_user_name);
            viewHolder.credit=view.findViewById(R.id.tv_item_credit);
            viewHolder.button=view.findViewById(R.id.btn_item);
            view.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) view.getTag();
        }
        if (!userList.isEmpty()){
            final User user=userList.get(position);
            GlideUtils.loadImage(context, NetConstant.BASE_HEAD_ICON_URL+user.getHeadIcon(),viewHolder.headIcon);
            viewHolder.userName.setText(user.getUserName());
            viewHolder.credit.setText("信誉分"+user.getCreditScore());
            viewHolder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(context).setTitle("停用该用户").setMessage("将删除该用户及其一切记录，是否继续？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, final int i) {
                            RetrofitUtils.getRetrofit(NetConstant.BASE_URL).create(UserHelper.class).getDeleteUserCall(8,user.getId()).enqueue(new Callback<Common>() {
                                @Override
                                public void onResponse(Call<Common> call, Response<Common> response) {
                                    if (response.isSuccessful()){
                                        if (response.body().getStatus()==NetConstant.OK){
                                            ToastUtils.showMsg(context,"删除成功");
                                            userList.remove(position);
                                            CreditListAdapter.this.notifyDataSetChanged();
                                        }
                                    }else {
                                        ToastUtils.showMsg(context,"请求失败");
                                    }
                                }

                                @Override
                                public void onFailure(Call<Common> call, Throwable t) {
                                    ToastUtils.showMsg(context,"请求失败");
                                }
                            });
                        }
                    }).setNegativeButton("取消",null).show();
                }
            });
        }
        return view;
    }
    static class ViewHolder{
        CircleImageView headIcon;
        TextView userName;
        TextView credit;
        Button button;
    }
}
