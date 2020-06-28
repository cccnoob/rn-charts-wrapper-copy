package com.github.wuxudong.rncharts.markers;

import android.annotation.SuppressLint;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.res.ResourcesCompat;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
import com.github.wuxudong.rncharts.R;
import com.github.wuxudong.rncharts.data.TradeEntry;


/**
 * @author by benny
 * @date 2020/6/28 11:18
 * @description
 */
@SuppressLint("ViewConstructor")
public class RNMarkerView extends MarkerView {

    private Drawable backgroundLeft = ResourcesCompat.getDrawable(getResources(), R.drawable.mp_marker_left, null);
    private Drawable background = ResourcesCompat.getDrawable(getResources(), R.drawable.mp_marker, null);
    private Drawable backgroundRight = ResourcesCompat.getDrawable(getResources(), R.drawable.mp_marker_right, null);

    private Drawable backgroundTopLeft = ResourcesCompat.getDrawable(getResources(), R.drawable.mp_marker_top_left, null);
    private Drawable backgroundTop = ResourcesCompat.getDrawable(getResources(), R.drawable.mp_marker_top, null);
    private Drawable backgroundTopRight = ResourcesCompat.getDrawable(getResources(), R.drawable.mp_marker_top_right, null);
    private TextView tvContent;
    private LinearLayout llRoot;
    private View viewTop;
    private View viewBottom;
    private int index;
    private boolean checked;

    public RNMarkerView(Context context, boolean checked) {
        super(context, R.layout.my_marker_view);
        llRoot = findViewById(R.id.ll_root);
        tvContent = findViewById(R.id.tvContent);
        viewTop = findViewById(R.id.view_top);
        viewBottom = findViewById(R.id.view_bottom);
        this.checked = checked;
    }

    /**
     * 这里就设置你想显示到makerview上的数据，Entry可以得到X、Y轴坐标，也可以e.getData()获取其他你设置的数据
     */
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        // super.refreshContent(e, highlight);
        if (e instanceof CandleEntry) {
            CandleEntry ce = (CandleEntry) e;
            tvContent.setText(Utils.formatNumber(ce.getHigh(), 0, true));
        } else if (e instanceof TradeEntry) {
            TradeEntry bean = (TradeEntry) e;
            String content;
            if (checked) {
                String rate = bean.getTradeRate();
                rate = rate + "%";
                content = "收益率:" + rate;
            } else {
                String amount = bean.getTradeAmount();
                String unit = bean.getTradeAmountUnit();
                content = "总收益:" + " " + amount + " " + unit;
            }
            String time = bean.getTradeDateTime();
            tvContent.setText(time + "\n" + content);

        } else {
            tvContent.setText(Utils.formatNumber(e.getY(), 0, true));
        }
        super.refreshContent(e, highlight);
    }

    /**
     * offset 是以點到的那個點作為 (0,0) 中心然後往右下角畫出來 该方法是让markerview现实到坐标的上方
     * 所以如果要顯示在點的上方
     * X=寬度的一半，負數
     * Y=高度的負數
     */
    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }

    @Override
    public MPPointF getOffsetForDrawingAtPoint(float posX, float posY) {
        MPPointF offset = getOffset();

        MPPointF offset2 = new MPPointF();

        offset2.x = offset.x;
        offset2.y = offset.y;

        Chart chart = getChartView();

        float width = getWidth();

        if (posX + offset2.x < 0) {
            offset2.x = 0;
            if (posY + offset2.y < 0) {
                offset2.y = posY + offset2.y;
                isTop = true;
                llRoot.setBackground(backgroundTopLeft);
            } else {
                isTop = false;
                llRoot.setBackground(backgroundLeft);

            }

        } else if (chart != null && posX + width + offset2.x > chart.getWidth()) {
            offset2.x = -width;
            if (posY + offset2.y < 0) {
                offset2.y = 0;
                isTop = true;
                llRoot.setBackground(backgroundTopRight);

            } else {
                isTop = false;
                llRoot.setBackground(backgroundRight);

            }

        } else {
            //底部 中间
            if (posY + offset2.y < 0) {
                offset2.y = 0;
                isTop = true;
                llRoot.setBackground(backgroundTop);

            } else {
                isTop = false;
                llRoot.setBackground(background);

            }
        }
        showTopView();
        return offset2;
    }

    boolean isTop;

    private void showTopView() {
        if (isTop) {
            viewTop.setVisibility(GONE);
            viewBottom.setVisibility(GONE);
        } else {
            viewTop.setVisibility(GONE);
            viewBottom.setVisibility(GONE);

        }
    }

    @Override
    public void draw(Canvas canvas, float posX, float posY) {
        super.draw(canvas, posX, posY);
        Paint circleP = new Paint();
        circleP.setStyle(Paint.Style.STROKE);
        circleP.setAntiAlias(true);
        circleP.setStrokeWidth(4f);
        circleP.setColor(Color.argb(255, 93, 131, 255));
        canvas.drawCircle(posX, posY, 4, circleP);

        Paint circleP2 = new Paint();
        circleP2.setStyle(Paint.Style.STROKE);
        circleP2.setAntiAlias(true);
        circleP2.setStrokeWidth(8f);
        circleP2.setColor(Color.argb(25, 93, 131, 255));

        canvas.drawCircle(posX, posY, 8, circleP2);

    }

    public TextView getTvContent() {
        return tvContent;
    }
}


