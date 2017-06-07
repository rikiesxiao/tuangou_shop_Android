package com.x.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.x.model.TixianModel;
import com.x.tuangou_shop.R;

import java.util.List;

/**
 * Created by x on 2017/6/7.
 */

public class TixianAdapter extends BaseAdapter {
    Context context;

    private List<TixianModel> list;

    public List<TixianModel> getList() {
        return list;
    }

    public void setList(List<TixianModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public TixianAdapter(Context context, List<TixianModel> arr) {
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
        TixianAdapter.getItemView bundle ;

        if(convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.money_cell, null);
            bundle = new TixianAdapter.getItemView();
            bundle.time=(TextView)convertView.findViewById(R.id.money_cell_time);
            bundle.money=(TextView)convertView.findViewById(R.id.money_cell_money);
            bundle.info=(TextView)convertView.findViewById(R.id.money_cell_content);

            bundle.img=(ImageView)convertView.findViewById(R.id.money_cell_icon);

            convertView.setTag(bundle);
        }
        else
        {
            bundle = (TixianAdapter.getItemView) convertView.getTag();
        }

        final TixianModel item = list.get(position);

        String[] strs = item.getF_create_time().split(" ");

        bundle.time.setText(strs[0]+"\r\n"+strs[1]);
        bundle.money.setText(item.getMoney()+"");
        bundle.info.setText(item.getStatus());

        if(item.getStatus().equals("已拒绝"))
        {
            bundle.info.setTextColor(Color.parseColor("#ff4948"));

            bundle.info.setClickable(true);
            bundle.info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertView alert = new AlertView("拒绝原因", item.getReason(), null, null,
                            new String[]{"确定"},
                            context, AlertView.Style.Alert, null);

                    alert.show();

                }
            });

        }
        else
        {
            bundle.info.setClickable(false);
            bundle.info.setOnClickListener(null);
            bundle.info.setTextColor(Color.parseColor("#888888"));
        }

        return convertView;
    }

    private class getItemView {
        TextView time,money,info;
        ImageView img;
    }
}
