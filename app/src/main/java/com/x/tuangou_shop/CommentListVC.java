package com.x.tuangou_shop;

import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.AbsListView;
import android.widget.ListView;

import com.x.adapter.CommentAdapter;
import com.x.model.CommentModel;
import com.x.net.XActivityindicator;
import com.x.net.XNetUtil;
import com.x.util.BaseActivity;
import com.x.util.Bimp;
import com.x.util.DataCache;
import com.x.util.MyEventBus;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import static com.x.util.ApplicationClass.APPService;

/**
 * Created by Administrator on 2017/6/5 0005.
 */

public class CommentListVC extends BaseActivity {

    SwipeRefreshLayout swipe_refresh;
    ListView mainList;

    CommentAdapter adapter;

    int page = 1;
    boolean end = false;

    List<CommentModel> arrs = new ArrayList<>();

    @Override
    protected void setupUi() {
        setContentView(R.layout.mycollect);
        EventBus.getDefault().register(this);

        swipe_refresh = (SwipeRefreshLayout)findViewById(R.id.swipe_container);
        mainList = (ListView) findViewById(R.id.mycollect_list);

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

        adapter = new CommentAdapter(this,arrs);

        mainList.setAdapter(adapter);

        getData();
    }


    private void getData()
    {

        String sid = DataCache.getInstance().user.getSid()+"";

        XNetUtil.Handle(APPService.dp_list(sid,page+""), new XNetUtil.OnHttpResult<List<CommentModel>>() {
            @Override
            public void onError(Throwable e) {
                swipe_refresh.setRefreshing(false);
            }

            @Override
            public void onSuccess(List<CommentModel> models) {
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


    @Subscribe
    public void getEventmsg(MyEventBus myEventBus) {
        if (myEventBus.getMsg().equals("ReplySuccess")) {
            adapter.notifyDataSetChanged();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        Bimp.clear();
        EventBus.getDefault().unregister(this);

    }
}