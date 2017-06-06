package com.x.tuangou_shop;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.DisplayMetrics;
import android.view.View;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.x.model.AccountsModel;
import com.x.net.XNetUtil;
import com.x.util.BaseActivity;
import com.x.util.DataCache;
import com.x.util.DensityUtil;

import java.math.BigDecimal;
import java.util.ArrayList;

import static com.x.util.ApplicationClass.APPService;

/**
 * Created by x on 2017/6/6.
 */

public class AccountsCountVC extends BaseActivity {

    private PieChart mChart;
    AccountsModel accountsModel;

    @Override
    protected void setupUi() {
        setContentView(R.layout.accounts_count);
        mChart = (PieChart) findViewById(R.id.piechart);

    }

    @Override
    protected void setupData() {

        String sid = DataCache.getInstance().user.getSid();

        XNetUtil.Handle(APPService.accounts_count(sid), new XNetUtil.OnHttpResult<AccountsModel>() {
            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onSuccess(AccountsModel model) {
                if(model != null)
                {
                    accountsModel = model;
                    PieData mPieData = getPieData();
                    showChart(mChart, mPieData);
                }

            }
        });

    }


    private void showChart(PieChart pieChart, PieData pieData) {

        //pieChart.setBottom(DensityUtil.dip2px(this,50.0f));
        //pieChart.setExtraTopOffset(-50.0f);
        pieChart.setExtraBottomOffset(80.0f);
        pieChart.setExtraLeftOffset(8);
        pieChart.setExtraRightOffset(8);



        pieChart.setTransparentCircleColor(Color.WHITE);
        // 设置环形图和中间空心圆之间的圆环的透明度
        pieChart.setTransparentCircleAlpha(110);

        pieChart.setHoleRadius(50f);  //半径
        pieChart.setTransparentCircleRadius(54f); // 半透明圈
        //pieChart.setHoleRadius(0)  //实心圆

        // mChart.setDrawYValues(true);
        pieChart.setDrawCenterText(true);  //饼状图中间可以添加文字

        pieChart.setDrawHoleEnabled(true);

        pieChart.setRotationAngle(90); // 初始旋转角度

        Description dis = new Description();
        dis.setText("");
        pieChart.setDescription(dis);

        // draws the corresponding description value into the slice
        // mChart.setDrawXValues(true);

        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(true); // 可以手动旋转

        // display percentage values
        pieChart.setUsePercentValues(true);  //显示成百分比
        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        // add a selection listener
//      mChart.setOnChartValueSelectedListener(this);
        // mChart.setTouchEnabled(false);

//      mChart.setOnAnimationListener(this);

        pieChart.setCenterText("总销售额\r\n\r\n￥"+accountsModel.getSale_money());  //饼状图中间的文字

        //设置数据
        pieChart.setData(pieData);

        // undo all highlights
//      pieChart.highlightValues(null);
//      pieChart.invalidate();

        Legend mLegend = pieChart.getLegend();  //设置比例图
        mLegend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        mLegend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        mLegend.setOrientation(Legend.LegendOrientation.VERTICAL);
        //mLegend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);  //最右边显示
//      mLegend.setForm(LegendForm.LINE);  //设置比例图的形状，默认是方形

        mLegend.setFormSize(20.0f);
        mLegend.setYOffset(-10);
        mLegend.setXOffset(30);

        mLegend.setXEntrySpace(7f);
        mLegend.setYEntrySpace(16f);

        pieChart.animateXY(1000, 1000);  //设置动画
        // mChart.spin(2000, 0, 360);
    }



    private PieData getPieData() {

        ArrayList<PieEntry> yValues = new ArrayList<>();  //yVals用来表示封装每个饼块的实际数据

        // 饼图数据
        /**
         * 将一个饼形图分成四部分， 四部分的数值比例为14:14:34:38
         * 所以 14代表的百分比就是14%
         */
        float quarterly1 = (float) ((accountsModel.getMoney()*(1.0-accountsModel.getBili())) / accountsModel.getSale_money() * 100.0f);
        float quarterly2 = (float) (accountsModel.getMoney()*accountsModel.getBili() / accountsModel.getSale_money() * 100.0f);
        float quarterly3 = (float) (accountsModel.getWd_money() / accountsModel.getSale_money() * 100.0f);
        float quarterly4 = (float) (accountsModel.getRefund_money() / accountsModel.getSale_money() * 100.0f);
        float quarterly5 = (float) (accountsModel.getLock_money() / accountsModel.getSale_money() * 100.0f);


        double v1 = accountsModel.getMoney()*(1.0-accountsModel.getBili());
        BigDecimal bd = new BigDecimal(v1);
        bd = bd.setScale(2,BigDecimal.ROUND_HALF_UP);

        double v2 = accountsModel.getMoney()*accountsModel.getBili();
        BigDecimal bd2 = new BigDecimal(v2);
        bd2 = bd2.setScale(2,BigDecimal.ROUND_HALF_UP);

        double v3 = accountsModel.getWd_money();
        BigDecimal bd3 = new BigDecimal(v3);
        bd3 = bd3.setScale(2,BigDecimal.ROUND_HALF_UP);

        double v4 = accountsModel.getRefund_money();
        BigDecimal bd4 = new BigDecimal(v4);
        bd4 = bd4.setScale(2,BigDecimal.ROUND_HALF_UP);

        double v5 = accountsModel.getLock_money();
        BigDecimal bd5 = new BigDecimal(v5);
        bd5 = bd5.setScale(2,BigDecimal.ROUND_HALF_UP);

        yValues.add(new PieEntry(quarterly1,"可用余额: "+bd.doubleValue(), 0));
        yValues.add(new PieEntry(quarterly2, "扣点金额: "+bd2.doubleValue(),1));
        yValues.add(new PieEntry(quarterly3, "提现金额: "+bd3.doubleValue(),2));
        yValues.add(new PieEntry(quarterly4, "退款金额: "+bd4.doubleValue(),3));
        yValues.add(new PieEntry(quarterly5, "待消费金额: "+bd5.doubleValue(),4));
        //y轴的集合
        PieDataSet pieDataSet = new PieDataSet(yValues, ""/*显示在比例图上*/);



        pieDataSet.setSliceSpace(0f); //设置个饼状图之间的距离

        ArrayList<Integer> colors = new ArrayList<Integer>();

        // 饼图颜色

        colors.add(Color.parseColor("#1abc9c"));
        colors.add(Color.rgb(255, 123, 124));
        colors.add(Color.rgb(114, 188, 223));
        colors.add(Color.parseColor("#e67e22"));
        colors.add(Color.rgb(205, 205, 205));

        pieDataSet.setColors(colors);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float px = 5 * (metrics.densityDpi / 160f);
        pieDataSet.setSelectionShift(px); // 选中态多出的长度

        PieData pieData = new PieData(pieDataSet);

        return pieData;
    }

    public void to_info(View v)
    {
        pushVC(MoneyListVC.class);
    }

}
