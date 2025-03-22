package com.example.cameradetector;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import java.util.ArrayList;
import java.util.Random;
public class SonarRadarView extends View{

    private Paint circlePaint, linePaint, blipPaint, sweepPaint;
    private RectF rectF;
    private float sweepAngle = 0;
    private ArrayList<Blip> blips = new ArrayList<>();
    private Random random = new Random();

    public SonarRadarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    private void init() {
        circlePaint = new Paint();
        circlePaint.setColor(Color.GREEN);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(5);
        circlePaint.setAntiAlias(true);

        linePaint = new Paint();
        linePaint.setColor(Color.GREEN);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(3);
        linePaint.setAntiAlias(true);

        sweepPaint = new Paint();
        sweepPaint.setColor(Color.argb(150, 0, 255, 0));
        sweepPaint.setStyle(Paint.Style.FILL);
        sweepPaint.setAntiAlias(true);

        blipPaint = new Paint();
        blipPaint.setColor(Color.RED);
        blipPaint.setStyle(Paint.Style.FILL);
        blipPaint.setAntiAlias(true);

        rectF = new RectF();

        startAnimation();
        generateBlips();
    }
    private void startAnimation() {
        ValueAnimator animator = ValueAnimator.ofFloat(0, 360);
        animator.setDuration(2000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.addUpdateListener(animation -> {
            sweepAngle = (float) animation.getAnimatedValue();
            updateBlipAlphas();
            invalidate();
        });
        animator.start();
    }

    private void generateBlips() {
        blips.clear();
        for (int i = 0; i < 10; i++) {
            float angle = random.nextFloat() * 360;
            float distance = random.nextFloat();
            blips.add(new Blip(angle, distance));
        }
    }

    private void updateBlipAlphas() {
        for (Blip blip : blips) {
            float diff = Math.abs(sweepAngle - blip.angle);
            if (diff < 20) {
                blip.isDetected = true; // Mark as detected
                blip.holdTime = 30; // Hold brightness for 30 frames (~1.5 sec)

                // Gradual fade-in effect
                blip.alpha = Math.min(255, blip.alpha + 15);
            } else if (blip.holdTime > 0) {
                blip.holdTime--; // Keep visible for a while
            } else {
                // Gradual fade-out effect
                blip.alpha = Math.max(0, blip.alpha - 5);
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();
        int radius = Math.min(width, height) / 2;
        int centerX = width / 2;
        int centerY = height / 2;

        rectF.set(centerX - radius, centerY - radius, centerX + radius, centerY + radius);

        // Draw radar circles
        for (int i = 1; i <= 4; i++) {
            canvas.drawCircle(centerX, centerY, (radius / 4) * i, circlePaint);
        }

        // Draw cross lines
        canvas.drawLine(centerX, centerY - radius, centerX, centerY + radius, linePaint);
        canvas.drawLine(centerX - radius, centerY, centerX + radius, centerY, linePaint);

        // Draw sweeping effect
        canvas.drawArc(rectF, sweepAngle, 45, true, sweepPaint);

        // Draw blips
        for (Blip blip : blips) {
            float angleRad = (float) Math.toRadians(blip.angle);
            float distance = blip.distance * radius;
            float blipX = centerX + (float) Math.cos(angleRad) * distance;
            float blipY = centerY + (float) Math.sin(angleRad) * distance;

            blipPaint.setAlpha(blip.alpha);
            canvas.drawCircle(blipX, blipY, 10, blipPaint);
        }
    }
    private static class Blip {
        float angle;
        float distance;
        int alpha = 0; // Initially invisible
        int holdTime = 0;
        boolean isDetected = false;
        Blip(float angle, float distance) {
            this.angle = angle;
            this.distance = distance;
        }
    }
}
