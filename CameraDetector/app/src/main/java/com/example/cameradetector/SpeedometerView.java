package com.example.cameradetector;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.cameradetector.R;

public class SpeedometerView extends View {
    private Paint backgroundPaint;
    private Paint progressPaint;
    Paint textPaintMin;
    Paint textPaintMax;
    private Bitmap needleBitmap;
    private float speed = 0;  // Current speed (0 to 120 for example)

    // Constants for the speedometer size and range
    private static final int MAX_SPEED = 300;
    private static int RADIUS = 200;    // Radius of the speedometer dial
    Context context;
    public SpeedometerView(Context context) {
        super(context);
        init(context);
    }

    public SpeedometerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        post(() -> Log.i("getWidth", "getWidth: " + getWidth()));
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        backgroundPaint = new Paint();
        backgroundPaint.setColor(getResources().getColor(R.color.gray_soft));
        backgroundPaint.setStyle(Paint.Style.STROKE);
        backgroundPaint.setStrokeWidth(45f);
//        backgroundPaint.setStrokeCap(Paint.Cap.ROUND);

        progressPaint = new Paint();
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeWidth(45f); // Thickness of the arc
        progressPaint.setAntiAlias(true);
//        progressPaint.setStrokeCap(Paint.Cap.ROUND);


//        needlePaint = new Paint();
//        needlePaint.setColor(Color.RED);
//        needlePaint.setStrokeWidth(10);

//        textPaintMin = new Paint();
//        textPaintMin.setColor(Color.BLACK);
//        textPaintMin.setTextSize(40f);
//        textPaintMin.setTextAlign(Paint.Align.RIGHT);
//
//        textPaintMax = new Paint();
//        textPaintMax.setColor(Color.BLACK);
//        textPaintMax.setTextSize(40f);
//        textPaintMax.setTextAlign(Paint.Align.LEFT);
        post(() -> {
            Bitmap originalBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.needle);
            int desiredHeight = getWidth() / 4; // Set your desired height in pixels
            Log.i("getWidth","getWidth: " + getWidth());
            Log.i("desiredHeight","desiredHeight: " + desiredHeight);
            int desiredWidth = (int) (desiredHeight/2.72f);
            needleBitmap = Bitmap.createScaledBitmap(originalBitmap, desiredWidth, desiredHeight, true);
            Shader shader = new SweepGradient(getHeight() / 2,getWidth() / 2,  new int[]{ Color.RED, Color.GREEN,Color.YELLOW,Color.RED}, null);
            progressPaint.setShader(shader);
        });
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Get the center of the canvas (automatically calculates width/height)
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        RADIUS = getWidth()/3-25;

        // Define the rectangle for the arc
        RectF rect = new RectF(centerX - RADIUS, centerY - RADIUS, centerX + RADIUS, centerY + RADIUS);

        // Draw the background arc (half circle)
        canvas.drawArc(rect, 178, 184, false, backgroundPaint);

        // Calculate the needle angle based on the speed (0 to 180 degrees)
        float needleAngle = (speed * 1.0f / MAX_SPEED) * 184;
        canvas.drawArc(rect, 178, needleAngle, false, progressPaint);

        if (needleBitmap != null) {
            // Calculate the X and Y position for the needle bitmap based on the center and rotation
            float needleX = centerX - needleBitmap.getWidth() / 2f;
            float needleY = centerY - needleBitmap.getHeight() +(int)(needleBitmap.getHeight()/6.6);

            // Save canvas state and rotate it to position the needle correctly
            canvas.save();
            canvas.rotate(needleAngle - 90, centerX, centerY);  // Rotate the canvas around the center to place the needle
            canvas.drawBitmap(needleBitmap, needleX, needleY, null);
            canvas.restore();
        }

        // Calculate the min and max positions for the text (0 and max speed)
//        float minX = (float) (centerX + (RADIUS + 120) * Math.cos(Math.toRadians(180)));
//        float minY = (float) (centerY + (RADIUS + 120) * Math.sin(Math.toRadians(180)));
//
//        float maxX = (float) (centerX + (RADIUS + 120) * Math.cos(Math.toRadians(0)));
//        float maxY = (float) (centerY + (RADIUS + 120) * Math.sin(Math.toRadians(0)));

//        canvas.drawText("0", minX - 40, minY, textPaintMin);
//        canvas.drawText(String.valueOf(MAX_SPEED), maxX + 40, maxY, textPaintMax);
    }

    // Set the current speed (you can use this method to update the speed)
    public void setSpeed(float speed) {
        speed = Math.max(0, Math.min(speed, MAX_SPEED));
        this.speed = speed;
        invalidate();  // Redraw the view
    }
}
