package com.x.tuangou_shop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.x.adapter.OrderListAdapter;
import com.x.model.OrderModel;
import com.x.net.XActivityindicator;
import com.x.net.XNetUtil;
import com.x.util.BaseActivity;
import com.x.util.DataCache;

import java.util.ArrayList;
import java.util.List;

import static com.x.util.ApplicationClass.APPService;

/**
 * Created by Administrator on 2017/6/5 0005.
 */

public class OrderListVC extends BaseActivity {

    SwipeRefreshLayout swipe_refresh;
    ListView mainList;

    OrderListAdapter adapter;

    int page = 1;
    boolean end = false;

    List<OrderModel> arrs = new ArrayList<>();

    @Override
    protected void setupUi() {
        setContentView(R.layout.order_list);

        swipe_refresh = (SwipeRefreshLayout)findViewById(R.id.order_list_refresh);
        mainList = (ListView) findViewById(R.id.order_list_listview);

        swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                end = false;
                getData();
            }
        });

        mainList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        if (view.getLastVisiblePosition() == (view.getCount() - 1)) {
                            if(!end)
                            {
                                getData();
                            }
                        }
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        mainList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                to_info(position);

            }
        });

        adapter = new OrderListAdapter(this,arrs);

        mainList.setAdapter(adapter);

        getData();
    }


    private void getData()
    {

        String sid = DataCache.getInstance().user.getSid()+"";

        XNetUtil.Handle(APPService.order_list(sid,page+""), new XNetUtil.OnHttpResult<List<OrderModel>>() {
            @Override
            public void onError(Throwable e) {
                swipe_refresh.setRefreshing(false);
            }

            @Override
            public void onSuccess(List<OrderModel> models) {
                swipe_refresh.setRefreshing(false);
                if(models != null)
                {
                    if(page == 1)
                    {
                        arrs.clear();
                    }

                    if(models.size() > 0)
                    {
                        page++;
                        arrs.addAll(models);
                    }
                    else
                    {
                        end = true;
                        XActivityindicator.showToast("已全部加载完毕！");
                    }

                    if(adapter != null)
                    {
                        adapter.setList(arrs);
                    }

                }
            }
        });
    }


    @Override
    protected void setupData() {

    }

    private void to_info(int p)
    {
        OrderModel m = arrs.get(p);
        Bundle bundle = new Bundle();
        bundle.putString("url","http://tg01.sssvip.net/wap/index.php?ctl=biz_dealo&act=app_info&id="+m.getId());
        bundle.putString("title","订单详情");

        pushVC(XHtmlVC.class,bundle);

    }


}
