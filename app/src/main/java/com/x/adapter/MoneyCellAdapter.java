package com.x.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.x.model.MoneyInfoModel;
import com.x.model.OrderModel;
import com.x.tuangou_shop.R;

import java.util.List;

/**
 * Created by x on 2017/6/6.
 */

public class MoneyCellAdapter extends BaseAdapter {
    Context context;

    private List<MoneyInfoModel> list;

    public List<MoneyInfoModel> getList() {
        return list;
    }

    public void setList(List<MoneyInfoModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public MoneyCellAdapter(Context context, List<MoneyInfoModel> arr) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.money_cell, null);
            bundle = new getItemView();
            bundle.time=(TextView)convertView.findViewById(R.id.money_cell_time);
            bundle.money=(TextView)convertView.findViewById(R.id.money_cell_money);
            bundle.info=(TextView)convertView.findViewById(R.id.money_cell_content);

            bundle.img=(ImageView)convertView.findViewById(R.id.money_cell_icon);

            convertView.setTag(bundle);
        }
        else
        {
            bundle = (getItemView) convertView.getTag();
        }

        MoneyInfoModel item = list.get(position);

        String[] strs = item.getCreate_time().split(" ");

        String type = "+";

        if(item.getType() > 3)
        {
            type = "-";
        }

        bundle.time.setText(strs[0]+"\r\n"+strs[1]);
        bundle.money.setText(type+item.getMoney());
        bundle.info.setText(item.getLog_info());

        if(type.equals("+"))
        {
            bundle.img.setImageResource(R.mipmap.money_add);
        }
        else
        {
            bundle.img.setImageResource(R.mipmap.money_clip);
        }

        return convertView;
    }

    private class getItemView {
        TextView time,money,info;
        ImageView img;
    }
}
