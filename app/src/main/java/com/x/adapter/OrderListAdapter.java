package com.x.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.x.model.OrderModel;
import com.x.tuangou_shop.R;

import java.util.List;

/**
 * Created by Administrator on 2017/6/5 0005.
 */

public class OrderListAdapter extends BaseAdapter {
    Context context;

    private List<OrderModel> list;

    public List<OrderModel> getList() {
        return list;
    }

    public void setList(List<OrderModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public OrderListAdapter(Context context, List<OrderModel> arr) {
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
        getItemView bundle ;

        if(convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.order_cell, null);
            bundle = new getItemView();
            bundle.name=(TextView)convertView.findViewById(R.id.order_cell_name);
            bundle.status=(TextView)convertView.findViewById(R.id.order_cell_status);
            bundle.time=(TextView)convertView.findViewById(R.id.order_cell_time);
            bundle.price=(TextView)convertView.findViewById(R.id.order_cell_price);

            bundle.img=(ImageView)convertView.findViewById(R.id.order_cell_img);

            convertView.setTag(bundle);
        }
        else
        {
            bundle = (getItemView) convertView.getTag();
        }

        OrderModel item = list.get(position);

        bundle.name.setText(item.getName());
        bundle.time.setText("下单时间："+item.getCreate_time());
        bundle.price.setText("￥"+item.getTotal_price());

        ImageLoader.getInstance().displayImage(item.getIcon(),bundle.img);

        if(!item.getPay_status().equals("2"))
        {
            bundle.status.setText("未付款");
        }
        else
        {
            if(item.getOrder_status().equals("1"))
            {

                if(item.getDp_id() > 0)
                {
                    bundle.status.setText("已点评");
                }
                else
                {
                    bundle.status.setText("已消费");
                }

            }
            else
            {
                bundle.status.setText("未使用");
            }

        }

        if(item.getRefund_status() == 1)
        {
            bundle.status.setText("退款中");
        }
        else if(item.getRefund_status() == 2)
        {
            bundle.status.setText("已退款");
        }
        else if(item.getRefund_status() == 3)
        {
            bundle.status.setText("拒绝退款");
        }


        return convertView;
    }

    private class getItemView {
        TextView name,status,time,price;
        ImageView img;
    }
}
