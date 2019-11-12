package com.tsimeon.android.timelineviewlib;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * date：2019/11/11 14:26
 * author：simeon
 * email：simeon@qq.com
 * description：时间线装饰器_rv_addItemDecoration使用
 */
public class TimeLineDecoration extends RecyclerView.ItemDecoration {

    private Context context;
    //是否空心
    private boolean stroke = false;
    //是否显示第一个item_line
    private boolean showFirstLine = false;
    //是否显示最后一个item_line
    private boolean showLastLine = false;
    //背景颜色,默认透明
    private int backgroudColor = Color.parseColor("#00000000");
    //控件大小
    private int width;
    //小点半径
    private int dotRadius;
    //文字大小
    private int titleTextSize;
    //内容大小
    private int contentTextSize;
    //内容距离标题距离
    private int contentTopPadding;
    //文字内容距离边界距离
    private int textPadding;
    //线的粗细
    private int lineWidth;
    //圆圈画笔粗细
    private int circleWidth;
    //bitmap大小
    private int bitmapSize;
    //指示器padding
    private int indicatorPadding;
    private int dotColor = Color.parseColor("#DDDDDD");
    private int lineColor = Color.parseColor("#DDDDDD");
    private int titleColor = Color.parseColor("#666666");
    private int contentColor = Color.parseColor("#666666");

    private Paint circlePaint = new Paint((Paint.ANTI_ALIAS_FLAG));
    private Paint linePaint = new Paint((Paint.ANTI_ALIAS_FLAG));
    private Paint backGroundPaint = new Paint((Paint.ANTI_ALIAS_FLAG));
    private Paint titlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint contentPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Bitmap mBitmap;
    //存放单独设置某一项圆圈颜色
    private HashMap<Integer, Integer> itemCircleColorMap = new HashMap<>();
    //存放单独设置某一项圆圈大小
    private HashMap<Integer, Integer> itemCircleSizeMap = new HashMap<>();
    //存放单独设置某一项图标
    private HashMap<Integer, Integer> itemBitmap = new HashMap<>();
    //存放单独设置某一项图标大小
    private HashMap<Integer, Integer> itemBitmapSize = new HashMap<>();

    //标题数据
    private List<String> titleDatas = new ArrayList<>();
    //内容数据
    private List<String> contentDatas = new ArrayList<>();

    public TimeLineDecoration(Context context) {
        this.context = context;
        //设置默认值
        width = dip2px(100);
        dotRadius = dip2px(2);
        titleTextSize = dip2px(11);
        contentTextSize = dip2px(9);
        contentTopPadding = dip2px(2);
        textPadding = dip2px(15);
        lineWidth = dip2px(1);
        circleWidth = dip2px(1);
        bitmapSize = dip2px(12);

        linePaint.setStrokeWidth(lineWidth);
        linePaint.setColor(lineColor);
        circlePaint.setColor(dotColor);
        circlePaint.setStrokeWidth(circleWidth);
        backGroundPaint.setColor(backgroudColor);
        titlePaint.setColor(titleColor);
        titlePaint.setTextAlign(Paint.Align.RIGHT);
        contentPaint.setColor(contentColor);
        contentPaint.setTextAlign(Paint.Align.RIGHT);
    }

    /**
     * 设置某一项圆圈颜色
     *
     * @param layoutPos
     * @param color
     */
    public void setItemCircleColor(int layoutPos, int color) {
        itemCircleColorMap.put(layoutPos, color);
    }

    /**
     * 设置某一项圆圈半径
     *
     * @param layoutPos
     * @param dotRadius
     */
    public void setItemCircleRaius(int layoutPos, int dotRadius) {
        itemCircleSizeMap.put(layoutPos, dip2px(dotRadius));
    }

    /**
     * 设置某一项图标资源
     *
     * @param layoutPos
     * @param idRes
     */
    public void setItemBitmapRes(int layoutPos, int idRes) {
        itemBitmap.put(layoutPos, idRes);
    }

    /**
     * 设置某一项图标大小
     *
     * @param layoutPos
     * @param size
     */
    public void setItemBitmapSize(int layoutPos, int size) {
        itemBitmapSize.put(layoutPos, dip2px(size));
    }

    /**
     * 图片距离上线线的大小
     *
     * @param indicatorPadding
     */
    public void setIndicatorPadding(int indicatorPadding) {
        this.indicatorPadding = dip2px(indicatorPadding);
    }

    /**
     * 设置drawable图片
     *
     * @param resId
     */
    public void setDrawableResId(int resId) {
        mBitmap = BitmapFactory.decodeResource(context.getResources(), resId);
        if (mBitmap != null) {
            mBitmap = imageScale(mBitmap, bitmapSize, bitmapSize);
        }
    }

    /**
     * 设置bitmap大小
     *
     * @param size
     */
    public void setBitmapSize(int size) {
        bitmapSize = dip2px(size);
        if (mBitmap != null) {
            mBitmap = imageScale(mBitmap, dip2px(size), dip2px(size));
        }
    }

    /**
     * 缩放Bitmap大小
     *
     * @param bitmap
     * @param dst_w
     * @param dst_h
     * @return
     */
    private Bitmap imageScale(Bitmap bitmap, int dst_w, int dst_h) {
        int src_w = bitmap.getWidth();
        int src_h = bitmap.getHeight();
        float scale_w = ((float) dst_w) / src_w;
        float scale_h = ((float) dst_h) / src_h;
        Matrix matrix = new Matrix();
        matrix.postScale(scale_w, scale_h);
        return Bitmap.createBitmap(bitmap, 0, 0, src_w, src_h, matrix,
                true);
    }


    /**
     * 圆圈画笔粗细
     *
     * @param circleWidth
     */
    public void setCircleWidth(int circleWidth) {
        this.circleWidth = dip2px(circleWidth);
        circlePaint.setStrokeWidth(dip2px(circleWidth));
    }

    /**
     * 设置文字距离时间线的padding
     *
     * @param textPadding
     */
    public void setTextRightPadding(int textPadding) {
        this.textPadding = dip2px(textPadding);
    }

    /**
     * 设置content 到标题的padding
     *
     * @param contentTopPadding
     */
    public void setContentTopPadding(int contentTopPadding) {
        this.contentTopPadding = dip2px(contentTopPadding);
    }

    /**
     * 设置标题数据
     *
     * @param titleDatas
     */
    public void setTitleDatas(List<String> titleDatas) {
        this.titleDatas = titleDatas;
    }

    /**
     * 设置内容数据
     *
     * @param contentDatas
     */
    public void setContentDatas(List<String> contentDatas) {
        this.contentDatas = contentDatas;
    }


    /**
     * 设置标题大小
     *
     * @param titleTextSize
     */
    public void setTitleTextSize(int titleTextSize) {
        this.titleTextSize = dip2px(titleTextSize);
        titlePaint.setTextSize(dip2px(titleTextSize));
    }

    /**
     * 设置内容大小
     *
     * @param contentTextSize
     */
    public void setContentTextSize(int contentTextSize) {
        this.contentTextSize = dip2px(contentTextSize);
        contentPaint.setTextSize(dip2px(contentTextSize));
    }

    /**
     * 标题颜色
     *
     * @param titleColor
     */
    public void setTitleColor(int titleColor) {
        this.titleColor = titleColor;
        titlePaint.setColor(titleColor);
    }

    /**
     * 设置宽度,单位是dp
     *
     * @param width dp value
     */
    public void setWidth(int width) {
        this.width = dip2px(width);
    }

    /**
     * 内容颜色
     *
     * @param contentColor
     */
    public void setContentColor(int contentColor) {
        this.contentColor = contentColor;
        contentPaint.setColor(contentColor);
    }


    /**
     * 是否空心圆
     *
     * @param stroke
     */
    public void setStroke(boolean stroke) {
        this.stroke = stroke;
        circlePaint.setStyle(stroke ? Paint.Style.STROKE : Paint.Style.FILL);
    }


    /**
     * 是否显示第一根线
     *
     * @param showFirstLine
     */
    public void showFirstLine(boolean showFirstLine) {
        this.showFirstLine = showFirstLine;
    }

    /**
     * 是否显示最后一根线
     *
     * @param showLastLine
     */
    public void showLastLine(boolean showLastLine) {
        this.showLastLine = showLastLine;
    }

    /**
     * 设置背景颜色
     *
     * @param backgroudColor
     */
    public void setBackgroudColor(int backgroudColor) {
        this.backgroudColor = backgroudColor;
        backGroundPaint.setColor(backgroudColor);
    }

    /**
     * 设置点大小
     *
     * @param dotRadius
     */
    public void setDotRadius(int dotRadius) {
        this.dotRadius = dip2px(dotRadius);
    }

    /**
     * 设置线宽度
     *
     * @param lineWidth
     */
    public void setLineWidth(int lineWidth) {
        this.lineWidth = dip2px(lineWidth);
        linePaint.setStrokeWidth(dip2px(lineWidth));
    }

    /**
     * 设置点颜色
     *
     * @param dotColor
     */
    public void setDotColor(int dotColor) {
        this.dotColor = dotColor;
        circlePaint.setColor(dotColor);
    }

    /**
     * 设置线颜色
     *
     * @param lineColor
     */
    public void setLineColor(int lineColor) {
        this.lineColor = lineColor;
        linePaint.setColor(lineColor);
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent,
                       @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        //暂时只支持垂直的rv
        if (parent.getLayoutManager() instanceof LinearLayoutManager) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
            if (layoutManager.getOrientation() == RecyclerView.VERTICAL) {
                drawBackGround(c, parent);
                drawIndicator(c, parent);
                drawTopLine(c, parent);
                drawBottomLine(c, parent);
                drawTitleText(c, parent);
                drawContentText(c, parent);
            }
        }
    }

    //绘制内容文字-文字居中绘制
    private void drawContentText(Canvas c, RecyclerView parent) {
        contentPaint.setTextSize(contentTextSize);
        titlePaint.setTextSize(titleTextSize);
        if (contentDatas != null && !contentDatas.isEmpty()) {
            float titleTextHeight = 0f;
            if (titleDatas != null && !titleDatas.isEmpty()) {
                Paint.FontMetrics fontMetrics = contentPaint.getFontMetrics();
                titleTextHeight = fontMetrics.bottom - fontMetrics.top;
            }
            int count = parent.getChildCount();
            Paint.FontMetrics fontMetrics = contentPaint.getFontMetrics();
            float distance = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
            for (int i = 0; i < count; i++) {
                View child = parent.getChildAt(i);
                if (contentDatas.get(parent.getChildLayoutPosition(child)) != null) {
                    float x = width - textPadding;
                    float y =
                            child.getTop() + child.getHeight() / 2f + distance + titleTextHeight + contentTopPadding;
                    //这里要获取layoutpos才能正确的设置数据,不然会被复用重复
                    c.drawText(contentDatas.get(parent.getChildLayoutPosition(child)), x, y, contentPaint);
                }
            }
        }
    }

    //绘制标题文字
    private void drawTitleText(Canvas c, RecyclerView parent) {
        titlePaint.setTextSize(titleTextSize);
        if (titleDatas != null && !titleDatas.isEmpty()) {
            int count = parent.getChildCount();
            Paint.FontMetrics fontMetrics = contentPaint.getFontMetrics();
            float distance = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
            for (int i = 0; i < count; i++) {
                View child = parent.getChildAt(i);
                if (titleDatas.get(parent.getChildLayoutPosition(child)) != null) {
                    float x = width - textPadding;
                    float y = child.getTop() + child.getHeight() / 2f + distance;
                    //这里要获取layoutpos才能正确的设置数据,不然会被复用重复
                    c.drawText(titleDatas.get(parent.getChildLayoutPosition(child)), x, y, titlePaint);
                }
            }
        }
    }

    /**
     * 绘制背景颜色
     *
     * @param c
     * @param parent
     */
    private void drawBackGround(Canvas c, RecyclerView parent) {
        int count = parent.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = parent.getChildAt(i);
            c.drawRect(0, child.getTop(), width, child.getBottom(),
                    backGroundPaint);
        }
    }

    /**
     * 画上半部分竖线
     *
     * @param c
     * @param parent
     */
    private void drawTopLine(Canvas c, RecyclerView parent) {
        int count = parent.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = parent.getChildAt(i);
            float startX = width;
            float startY = child.getTop();
            float stopX = startX;
            float stopY;
            int layoutPos = parent.getChildLayoutPosition(child);
            int tDotRadius = getItemRadius(layoutPos);
            if (mBitmap != null) {
                if (getItemBitmap(layoutPos, child) != null) {
                    stopY = child.getTop() + child.getHeight() / 2f - getItemBitmapSize(layoutPos, child) / 2f - indicatorPadding;
                } else {
                    stopY = child.getTop() + child.getHeight() / 2f - bitmapSize / 2f - indicatorPadding;
                }
            } else {
                if (getItemBitmap(layoutPos, child) != null) {
                    stopY = child.getTop() + child.getHeight() / 2f - getItemBitmapSize(layoutPos, child) / 2f - indicatorPadding;
                } else {
                    stopY = child.getTop() + child.getHeight() / 2f - tDotRadius - (stroke ?
                            circlePaint.getStrokeWidth() / 2f : 0f) - indicatorPadding;
                }
            }
            if (i == 0) {
                if (showFirstLine) {
                    c.drawLine(startX, startY, stopX, stopY, linePaint);
                }
            } else {
                c.drawLine(startX, startY, stopX, stopY, linePaint);
            }
        }
    }


    /**
     * 画下部分竖线
     *
     * @param c
     * @param parent
     */
    private void drawBottomLine(Canvas c, RecyclerView parent) {
        int count = parent.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = parent.getChildAt(i);
            float startX = width;
            float startY;
            float stopX = startX;
            float stopY = child.getBottom();
            int layoutPos = parent.getChildLayoutPosition(child);
            int tDotRadius = getItemRadius(layoutPos);
            if (mBitmap != null) {
                if (getItemBitmap(layoutPos, child) != null) {
                    startY = child.getTop() + child.getHeight() / 2f + getItemBitmapSize(layoutPos, child) / 2f + indicatorPadding;
                } else {
                    startY = child.getTop() + child.getHeight() / 2f + bitmapSize / 2f + indicatorPadding;
                }
            } else {
                if (getItemBitmap(layoutPos, child) != null) {
                    startY = child.getTop() + child.getHeight() / 2f + getItemBitmapSize(layoutPos, child) / 2f + indicatorPadding;
                } else {
                    //空心圆的时候应该减去圆圈画笔的1/2
                    startY = child.getTop() + child.getHeight() / 2f + tDotRadius
                            + (stroke ? circlePaint.getStrokeWidth() / 2f : 0f) + indicatorPadding;
                }
            }
            if (i == count - 1) {
                if (showLastLine) {
                    c.drawLine(startX, startY, stopX, stopY, linePaint);
                }
            } else {
                c.drawLine(startX, startY, stopX, stopY, linePaint);
            }
        }
    }

    /**
     * 获取item的半径[检查是否有单独设置样式的item]
     *
     * @param layoutPos
     * @return
     */
    private int getItemRadius(int layoutPos) {
        int tDotRadius;
        if (itemCircleSizeMap != null && !itemCircleSizeMap.isEmpty() && itemCircleSizeMap.get(layoutPos) != null) {
            tDotRadius = itemCircleSizeMap.get(layoutPos);
        } else {
            tDotRadius = dotRadius;
        }
        return tDotRadius;
    }

    /**
     * 获取item图片大小[检查是否有单独设置样式的item]
     *
     * @param layoutPos
     * @return
     */
    private int getItemBitmapSize(int layoutPos, View child) {
        int size;
        if (itemBitmapSize != null && !itemBitmapSize.isEmpty() && itemBitmapSize.get(layoutPos) != null) {
            size = itemBitmapSize.get(layoutPos);
        } else {
            size = bitmapSize;
        }
        if (size > child.getHeight() / 3) {
            size = child.getHeight() / 3;
        }
        return size;
    }

    /**
     * 获取item的bitmap[检查是否有单独设置样式的item]
     *
     * @param layoutPos
     * @return
     */
    private Bitmap getItemBitmap(int layoutPos, View child) {
        int bitmapRes = -1;
        if (itemBitmap != null && !itemBitmap.isEmpty() && itemBitmap.get(layoutPos) != null) {
            bitmapRes = itemBitmap.get(layoutPos);
        }
        if (bitmapRes == -1) {
            return null;
        }
        Bitmap mBitmap = BitmapFactory.decodeResource(context.getResources(), bitmapRes);
        if (mBitmap != null) {
            int mapSize = getItemBitmapSize(layoutPos, child);
            mBitmap = imageScale(mBitmap, mapSize, mapSize);
        }
        return mBitmap;
    }

    /**
     * 获取item的color
     *
     * @param layoutPos
     * @return
     */
    private int getItemColor(int layoutPos) {
        int color;
        if (itemCircleColorMap != null && !itemCircleColorMap.isEmpty() && itemCircleColorMap.get(layoutPos) != null) {
            color = itemCircleColorMap.get(layoutPos);
        } else {
            color = dotColor;
        }
        return color;
    }


    /**
     * 画中间指示器
     *
     * @param c
     * @param parent
     */
    private void drawIndicator(Canvas c, RecyclerView parent) {
        int count = parent.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = parent.getChildAt(i);
            int layoutPos = parent.getChildLayoutPosition(child);
            int color = getItemColor(layoutPos);
            circlePaint.setColor(color);
            int tDotRadius = getItemRadius(layoutPos);
            float x = width;
            float y = child.getTop() + child.getHeight() / 2f;
            if (mBitmap != null) {
                //单独设置的图片
                Bitmap itemBitmap = getItemBitmap(layoutPos, child);
                if (itemBitmap != null) {
                    drawItemBitmap(c, child, layoutPos, x, y, itemBitmap, getItemBitmapSize(layoutPos, child));
                } else {
                    //bitmap设置最大不能超过item的1/3-超过自动设置为1/3大小
                    if (bitmapSize > child.getHeight() / 3) {
                        bitmapSize = child.getHeight() / 3;
                    }
                    drawItemBitmap(c, child, layoutPos, x, y, mBitmap, bitmapSize);
                }
            } else {
                Bitmap bitmap = getItemBitmap(layoutPos, child);
                if (bitmap != null) {
                    drawItemBitmap(c, child, layoutPos, x, y, bitmap, getItemBitmapSize(layoutPos, child));
                } else {
                    c.drawCircle(x, y, tDotRadius, circlePaint);
                }
            }
        }
    }

    /**
     * 画item bitmap
     *
     * @param c
     * @param child
     * @param layoutPos
     * @param x
     * @param y
     * @param itemBitmap
     */
    private void drawItemBitmap(Canvas c, View child, int layoutPos, float x, float y, Bitmap itemBitmap, int bitmapSize) {
        if (bitmapSize > child.getHeight() / 3) {
            itemBitmap = imageScale(itemBitmap, child.getHeight() / 3, child.getHeight() / 3);
            if (itemBitmap != null) {
                bitmapSize = child.getHeight() / 3;
                c.drawBitmap(itemBitmap, x - itemBitmap.getWidth() / 2f, y - bitmapSize / 2f, circlePaint);
            }
        } else {
            c.drawBitmap(itemBitmap, x - itemBitmap.getWidth() / 2f, y - bitmapSize / 2f, circlePaint);
        }
    }


    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                               @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        //暂时只支持垂直的rv
        if (parent.getLayoutManager() instanceof LinearLayoutManager) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
            if (layoutManager.getOrientation() == RecyclerView.VERTICAL) {
                outRect.set(width, 0, 0, 0);
            }
        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    private int dip2px(float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
