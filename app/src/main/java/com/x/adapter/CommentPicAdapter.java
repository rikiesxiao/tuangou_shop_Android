package com.x.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.x.tuangou_shop.R;
import com.x.xinterface.XRecyclerViewItemClick;

import java.util.List;

/**
 * Created by Administrator on 2017/5/22 0022.
 */

public class CommentPicAdapter extends RecyclerView.Adapter {

    private List<String> productList;
    private Context context;
    XRecyclerViewItemClick itemClickLinstener;

    public void setOnItemClick(XRecyclerViewItemClick listener)
    {
        itemClickLinstener = listener;
    }

    public CommentPicAdapter(List<String> productList,
                             Context context) {
        this.productList = productList;
        this.context = context;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View convertView = LayoutInflater.from(context).inflate(R.layout.comment_cell_pics, null);

        CommentPicAdapter.getItemView getItemView = new CommentPicAdapter.getItemView(convertView);

        getItemView.img = (ImageView) convertView.findViewById(R.id.comment_cell_pics_img);

        return getItemView;

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        CommentPicAdapter.getItemView getItemView = (CommentPicAdapter.getItemView) holder;
        String url = productList.get(position);
        ImageLoader.getInstance().displayImage(url, getItemView.img);

        if(itemClickLinstener != null)
        {
            getItemView.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickLinstener.ItemClickListener(holder.itemView,position);
                }
            });
        }


    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


    private class getItemView extends RecyclerView.ViewHolder {
        ImageView img;

        public getItemView(View itemView) {
            super(itemView);
        }
    }

}

