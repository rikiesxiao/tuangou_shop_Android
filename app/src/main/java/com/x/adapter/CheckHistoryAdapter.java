package com.x.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.x.model.CheckHistoryModel;
import com.x.tuangou_shop.R;

import java.util.List;

/**
 * Created by x on 2017/6/7.
 */

public class CheckHistoryAdapter extends BaseAdapter {

    Context context;

    private List<CheckHistoryModel> list;

    public List<CheckHistoryModel> getList() {
        return list;
    }

    public void setList(List<CheckHistoryModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public CheckHistoryAdapter(Context context, List<CheckHistoryModel> arr) {
        this.context = context;
        list = arr;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        CheckHistoryAdapter.getItemView bundle ;

        if(convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.check_history_cell, null);
            bundle = new CheckHistoryAdapter.getItemView();
            bundle.name=(TextView)convertView.findViewById(R.id.check_cell_name);
            bundle.pass=(TextView)convertView.findViewById(R.id.check_cell_pass);
            bundle.time=(TextView)convertView.findViewById(R.id.check_cell_time);
            bundle.price=(TextView)convertView.findViewById(R.id.check_cell_price);

            bundle.img=(ImageView)convertView.findViewById(R.id.check_cell_img);

            convertView.setTag(bundle);
        }
        else
        {
            bundle = (CheckHistoryAdapter.getItemView) convertView.getTag();
        }

        CheckHistoryModel item = list.get(position);

        bundle.name.setText(item.getSub_name());
        bundle.time.setText("验证时间："+item.getConfirm_time());
        bundle.price.setText("￥"+item.getCoupon_price());
        bundle.pass.setText("序列号："+item.getPassword());

        ImageLoader.getInstance().displayImage(item.getIcon(),bundle.img);

        
        return convertView;
    }

    private class getItemView {
        TextView name,pass,time,price;
        ImageView img;
    }
}
