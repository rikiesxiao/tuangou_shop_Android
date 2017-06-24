package com.x.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.x.model.CommentModel;
import com.x.tuangou_shop.PhotoPreView;
import com.x.tuangou_shop.R;
import com.x.tuangou_shop.ReplyVC;
import com.x.util.BaseActivity;
import com.x.util.Bimp;
import com.x.util.ImageItem;
import com.x.xinterface.XRecyclerViewItemClick;

import java.util.List;

/**
 * Created by Administrator on 2017/5/22 0022.
 */

public class CommentAdapter extends BaseAdapter {

    public static CommentModel ReplyModel;

    private List<CommentModel> list;
    private Context context;

    public List<CommentModel> getList() {
        return list;
    }

    public void setList(List<CommentModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public CommentAdapter(Context context, List<CommentModel> productList) {
        this.list = productList;
        this.context = context;

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

        getItemView getItemView;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.comment_cell, null);
            getItemView = new CommentAdapter.getItemView();

            getItemView.star = (RatingBar) convertView.findViewById(R.id.comment_cell_star);

            getItemView.shopImg = (ImageView) convertView.findViewById(R.id.comment_cell_shopimg);
            getItemView.headImg = (RoundedImageView) convertView.findViewById(R.id.comment_cell_head);

            getItemView.time = (TextView)convertView.findViewById(R.id.comment_cell_time);
            getItemView.content = (TextView)convertView.findViewById(R.id.comment_cell_content);
            getItemView.shopname = (TextView)convertView.findViewById(R.id.comment_cell_shopname);
            getItemView.shoptype = (TextView)convertView.findViewById(R.id.comment_cell_shoptype);
            getItemView.repley = (TextView)convertView.findViewById(R.id.comment_cell_repley);
            getItemView.rbtn = (TextView)convertView.findViewById(R.id.comment_cell_rbtn);

            getItemView.picsRV = (RecyclerView) convertView.findViewById(R.id.comment_cell_pics);

            convertView.setTag(getItemView);
        }
        else
        {
            getItemView = (CommentAdapter.getItemView) convertView.getTag();
        }

        getItemView.star.setIsIndicator(true);

        try {
            int p = Integer.parseInt(list.get(position).getPoint());
            getItemView.star.setNumStars(p);
        }
        catch (Exception e)
        {
            getItemView.star.setNumStars(0);
        }

        getItemView.time.setText(list.get(position).getCreate_time());
        getItemView.content.setText(list.get(position).getContent());

        String url = list.get(position).getIcon();

        if(url.indexOf("http://") < 0)
        {
            url = "http://www.tcbjpt.com/" + url;
        }

        ImageLoader.getInstance().displayImage(url,getItemView.shopImg);
        ImageLoader.getInstance().displayImage(list.get(position).getAvatar(),getItemView.headImg);

        getItemView.shopname.setText(list.get(position).getName());
        getItemView.shoptype.setText(list.get(position).getSub_name());

//设置布局管理器
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        getItemView.picsRV.setLayoutManager(linearLayoutManager);

        List<String> imgs = list.get(position).getImages();

        if(imgs != null)
        {
            getItemView.picsRV.setVisibility(View.VISIBLE);

            CommentPicAdapter adapter = new CommentPicAdapter(imgs,context);

            getItemView.picsRV.setAdapter(adapter);

            adapter.setOnItemClick(new XRecyclerViewItemClick() {
                @Override
                public void ItemClickListener(View view, int aaa) {

                    Bimp.clear();

                    for(String str : list.get(position).getImages())
                    {
                        ImageItem imageItem = new ImageItem();
                        imageItem.setUrl(str);
                        Bimp.tempSelectBitmap.add(imageItem);
                    }

                    Intent intent = new Intent(context,
                            PhotoPreView.class);
                    intent.putExtra("index", aaa);
                    intent.putExtra("hidedel",true);
                    context.startActivity(intent);

                }
            });
        }
        else
        {
            getItemView.picsRV.setVisibility(View.GONE);
        }

        if(list.get(position).getReply_time().equals(""))
        {
            getItemView.repley.setVisibility(View.GONE);
        }
        else
        {
            getItemView.repley.setVisibility(View.VISIBLE);
            getItemView.repley.setText("[掌柜回复]："+list.get(position).getReply_content());
        }


        getItemView.rbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ReplyModel = list.get(position);

                Bundle bundle = new Bundle();
                bundle.putSerializable("model",list.get(position));
                ((BaseActivity)context).pushVC(ReplyVC.class,bundle);

            }
        });

        return convertView;
    }

    private class getItemView {
        RatingBar star;
        ImageView shopImg;
        RoundedImageView headImg;
        TextView time, content,shopname,shoptype,repley,rbtn;
        RecyclerView picsRV;
    }

}