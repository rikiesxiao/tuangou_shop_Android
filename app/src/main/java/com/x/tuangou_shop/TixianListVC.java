package com.x.tuangou_shop;

import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.x.adapter.MoneyCellAdapter;
import com.x.adapter.TixianAdapter;
import com.x.model.TixianModel;
import com.x.net.XActivityindicator;
import com.x.net.XNetUtil;
import com.x.util.BaseActivity;
import com.x.util.DataCache;

import java.util.ArrayList;
import java.util.List;

import static com.x.util.ApplicationClass.APPService;

/**
 * Created by x on 2017/6/7.
 */

public class TixianListVC extends BaseActivity {

    SwipeRefreshLayout swipe_refresh;
    ListView mainList;

    TextView titleTV;

    TixianAdapter adapter;

    int page = 1;
    boolean end = false;

    List<TixianModel> arrs = new ArrayList<>();

    @Override
    protected void setupUi() {
        setContentView(R.layout.order_list);
        titleTV = (TextView) findViewById(R.id.title);
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

        titleTV.setText("提现历史");

        adapter = new TixianAdapter(this,arrs);

        mainList.setAdapter(adapter);

        getData();
    }


    private void getData()
    {

        String sid = DataCache.getInstance().user.getSid()+"";

        XNetUtil.Handle(APPService.tixian_list(sid,page+""), new XNetUtil.OnHttpResult<List<TixianModel>>() {
            @Override
            public void onError(Throwable e) {
                swipe_refresh.setRefreshing(false);
            }

            @Override
            public void onSuccess(List<TixianModel> models) {
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



}
