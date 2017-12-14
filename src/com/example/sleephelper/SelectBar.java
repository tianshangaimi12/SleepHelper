package com.example.sleephelper;

import com.example.sleephlper.R;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class SelectBar extends View{
	private int barWidth;
	private int barHeight;
	private int barColor;
	private int startPoint;
	
	private Paint mPaint;
	
	public SelectBar(Context context)
	{
		this(context, null);
	}
	
	public SelectBar(Context context,AttributeSet attrs)
	{
		this(context, attrs, 0);
	}

	public SelectBar(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.selectBar, defStyleAttr, 0);
		barWidth = a.getDimensionPixelSize(a.getIndex(R.styleable.selectBar_barWidth), 0);
		barHeight = a.getDimensionPixelSize(a.getIndex(R.styleable.selectBar_barHeight), 0);
		barColor = a.getColor(a.getIndex(R.styleable.selectBar_barColor), Color.BLUE);
		a.recycle();
		startPoint = (getWidth()/3-barWidth)/2;
		mPaint = new Paint();
		mPaint.setColor(barColor);
		mPaint.setStrokeWidth(barHeight);
		mPaint.setStyle(Style.STROKE);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if(startPoint < 0)
			startPoint = (getWidth()/3-barWidth)/2;
		//canvas.drawLine(0, getHeight(), getWidth(), getHeight(), mPaint);
		//canvas.drawPoint(getWidth()/2, getTop(), mPaint);
		canvas.drawLine(startPoint, getHeight()/2, startPoint+barWidth, getHeight()/2, mPaint);
		
	}
	
	public void setStartPoint(float value,int index)
	{
		if(index == 1)
			startPoint = (int) (value*getWidth()/3 + (getWidth()/3-barWidth)/2);
		else if(index == 2)
			startPoint = (int) (value*getWidth()/3 + (getWidth()/3-barWidth)/2)+getWidth()/3;
		else if(index == -1)
			startPoint = (int) (-(1-value)*getWidth()/3 + (getWidth()/3-barWidth)/2)+getWidth()/3;
		else if(index == -2)
			startPoint = (int) (-(1-value)*getWidth()/3 + (getWidth()/3-barWidth)/2)+getWidth()/3*2;
			
		invalidate();
	}
	
	public void setStartPoint()
	{
		startPoint = (getWidth()/2-barWidth)/2;
		invalidate();
	}
	
	public void setIndex(int i)
	{
		if(i == 0)
			startPoint = (getWidth()/3-barWidth)/2;
		else if(i == 1)
			startPoint = getWidth()/3+(getWidth()/3-barWidth)/2;
		else if(i == 2)
			startPoint = getWidth()/3*2+(getWidth()/3-barWidth)/2;
		invalidate();
	}

}
