package com.ywh.admin.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;

import com.ywh.admin.R;
import com.ywh.admin.constant.NetConstant;
import com.ywh.admin.utils.GlideUtils;

/**
 * Created by 李维升 on 2018/5/14.
 * 自定义弹出图片Dialog
 */

public class ImageDialog extends AlertDialog{
    private String url;
    private Context context;
    public ImageDialog(Context context, String url) {
        super(context);
        this.context=context;
        this.url=url;

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_image);
        ImageView imageView=findViewById(R.id.iv_dialog);
        GlideUtils.loadImage(context, NetConstant.BASE_GOODS_PHOTOS_URL+url,imageView);
    }

}
