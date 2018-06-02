package com.ywh.admin.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.ywh.admin.R;
import com.ywh.admin.constant.NetConstant;
import com.ywh.admin.helper.GsonBean.ReportItem;
import com.ywh.admin.utils.GlideUtils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 李维升 on 2018/5/15.
 */

public class ReportListAdapter extends BaseAdapter {
    private Context context;
    private List<ReportItem> reportItemList;

    public ReportListAdapter(Context context, List<ReportItem> reportItemList) {
        this.context = context;
        this.reportItemList = reportItemList;
    }

    @Override
    public int getCount() {
        return reportItemList.size();
    }

    @Override
    public Object getItem(int i) {
        return reportItemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder=null;
        if (view==null){
            view=View.inflate(context, R.layout.item_list_report,null);
            viewHolder=new ViewHolder();
            viewHolder.headIcon=view.findViewById(R.id.civ_item_reporter_head_icon);
            viewHolder.reporterName=view.findViewById(R.id.tv_item_reporter_name);
            viewHolder.content=view.findViewById(R.id.tv_item_report_content);
            view.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) view.getTag();
        }
        if (!reportItemList.isEmpty()){
            ReportItem item=reportItemList.get(i);
            //加载
            GlideUtils.loadImage(context, NetConstant.BASE_HEAD_ICON_URL+item.getHeadIcon(),viewHolder.headIcon);
            viewHolder.reporterName.setText(item.getReporterName());
            viewHolder.content.setText(item.getReportContent());
        }
        return view;
    }

    private static class ViewHolder{
        CircleImageView headIcon;
        TextView reporterName;
        TextView content;
    }
}
