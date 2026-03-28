package com.socmonitor.ui.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class BarChartView extends View {

    private int[] values;
    private String[] labels;
    private int[] colors;

    private final Paint barPaint  = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint valPaint  = new Paint(Paint.ANTI_ALIAS_FLAG);

    public BarChartView(Context ctx) { super(ctx); init(); }
    public BarChartView(Context ctx, AttributeSet a) { super(ctx, a); init(); }
    public BarChartView(Context ctx, AttributeSet a, int d) { super(ctx, a, d); init(); }

    private void init() {
        textPaint.setColor(Color.parseColor("#B0BEC5"));
        textPaint.setTextSize(26f);
        textPaint.setTextAlign(Paint.Align.CENTER);
        valPaint.setColor(Color.WHITE);
        valPaint.setTextSize(30f);
        valPaint.setTextAlign(Paint.Align.CENTER);
        valPaint.setFakeBoldText(true);
    }

    public void setData(int[] values, String[] labels, int[] colors) {
        this.values = values;
        this.labels = labels;
        this.colors = colors;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (values == null || values.length == 0) return;
        int w = getWidth(), h = getHeight();
        int n = values.length;
        int max = 1;
        for (int v : values) if (v > max) max = v;

        float barW   = (w - 60f) / n - 20f;
        float maxBarH = h - 80f;
        float startX = 30f;

        for (int i = 0; i < n; i++) {
            float barH  = maxBarH * values[i] / max;
            float left  = startX + i * (barW + 20f);
            float top   = h - 60f - barH;
            float right = left + barW;

            barPaint.setColor(colors[i]);
            canvas.drawRoundRect(new RectF(left, top, right, h - 60f), 8, 8, barPaint);

            valPaint.setColor(colors[i]);
            canvas.drawText(String.valueOf(values[i]), left + barW / 2f, top - 8, valPaint);
            canvas.drawText(labels[i], left + barW / 2f, h - 20f, textPaint);
        }
    }
}
