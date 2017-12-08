package com.example.sleephelper.diary;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

public class DiaryLine extends View{
	private Paint mPaint;
	private RectF mRectFFirst;
	private RectF mRectFSecond;
	private RectF mRectFThird;
	private float height;
	private float width;
	private float rectWidth;
	private float rectDistance;
	private List<Integer> pointsY;
	
	public DiaryLine(Context context, AttributeSet attrs) {
		super(context, attrs);
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		rectWidth = 15;
		rectDistance = 60;
		pointsY = new ArrayList<Integer>();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		height = getHeight();
		width = getWidth();
		//画竖线
		mPaint.setStyle(Style.STROKE);
		mPaint.setColor(Color.parseColor("#bbbbbb"));
		mPaint.setStrokeWidth(6);
		canvas.drawLine((float)width, 0f, (float)width, height, mPaint);
		
		//画线圈
		mPaint.setStyle(Style.STROKE);
		mPaint.setColor(Color.parseColor("#1296db"));
		mRectFSecond = new RectF(0, height/2-rectWidth, width*2, height/2+rectWidth);
		mRectFFirst = new RectF(0,mRectFSecond.top-rectDistance,width*2,mRectFSecond.bottom-rectDistance);
		mRectFThird = new RectF(0,mRectFSecond.top+rectDistance,width*2,mRectFSecond.bottom+rectDistance);
		canvas.drawArc(mRectFFirst, 90, 270, false, mPaint);
		canvas.drawArc(mRectFSecond, 90, 270, false, mPaint);
		canvas.drawArc(mRectFThird, 90, 270, false, mPaint);
		
		//画点
		/*mPaint.setStyle(Style.STROKE);
		mPaint.setColor(Color.BLUE);
		for(int i=0;i<pointsY.size();i++)
		{
			canvas.drawCircle(width, pointsY.get(i), 10, mPaint);
		}*/
		
	}

	public void setPointsY(List<Integer> pointsY)
	{
		this.pointsY = pointsY;
		invalidate();
	}
}
