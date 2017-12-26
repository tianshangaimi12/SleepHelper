package com.example.sleephelper;

import com.example.sleephlper.R;

import android.R.integer;
import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class HorizonExpandMenu extends LinearLayout{
	private int defaultHeight;
	private int defaultWidth;
	private double defaultAngle;
	private int width;
	private int height;
	private int range;
	private int cicleRadius;
	private boolean isExpanded = true;
	private boolean isAnimatorRunning = false;
	
	private int backColor;
	private int strockColor;
	private int strockWidth;
	private int cornerRadius;
	
	private int symbolColor;
	private int menuStyle;
	
	private Paint sPaint;
	
	private final int LEFT = 0;
	private final int RIGHT = 1;
	
	public HorizonExpandMenu(Context context)
	{
		this(context, null);
	}
	
	public HorizonExpandMenu(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);
	}

	public HorizonExpandMenu(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		defaultHeight = dip2px(context, 40);
		defaultWidth = dip2px(context, 200);
		defaultAngle = 0;
		TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs,R.styleable.horizonExpandMenu, defStyleAttr, 0);
		backColor = typedArray.getColor(typedArray.getIndex(R.styleable.horizonExpandMenu_back_color), Color.WHITE);
		strockColor = typedArray.getColor(typedArray.getIndex(R.styleable.horizonExpandMenu_strock_color), Color.GRAY);
		strockWidth = typedArray.getDimensionPixelOffset(R.styleable.horizonExpandMenu_strock_width, 
				dip2px(context, 1));
		cornerRadius = typedArray.getDimensionPixelOffset(R.styleable.horizonExpandMenu_corner_radius, 
				dip2px(context, 20));
		symbolColor = typedArray.getColor(R.styleable.horizonExpandMenu_symbol_color, Color.GRAY);
		menuStyle = typedArray.getInteger(R.styleable.horizonExpandMenu_menu_style, LEFT);
		typedArray.recycle();
		sPaint = new Paint();
		sPaint.setColor(symbolColor);
		sPaint.setStrokeWidth(9);
		sPaint.setAntiAlias(true);
		sPaint.setStyle(Style.FILL_AND_STROKE);
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		width = measureLength(defaultWidth, widthMeasureSpec);
		height = measureLength(defaultHeight, heightMeasureSpec);
		cicleRadius = getHeight()/2 - getPaddingLeft();
		setMeasuredDimension(width, height);
	}
	
	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
		if(getBackground() == null)
			setMenuBackground();
		cicleRadius = getHeight()/2 - getPaddingLeft();
		range = width - 2*cicleRadius - 2*getPaddingLeft();
		if(menuStyle == LEFT)
		{
			//横线
			canvas.drawLine(getPaddingLeft(), getHeight()/2, getPaddingLeft()+2*cicleRadius, getHeight()/2, sPaint);
			//竖线
			canvas.drawLine((float) (getPaddingLeft()+cicleRadius+Math.cos(defaultAngle)*cicleRadius), 
					(float)(getPaddingLeft()+cicleRadius-cicleRadius*Math.sin(defaultAngle)), 
					(float)(getPaddingLeft()+cicleRadius-cicleRadius*Math.cos(defaultAngle)), 
					(float) (getPaddingLeft()+cicleRadius+cicleRadius*Math.sin(defaultAngle)), sPaint);
		}
		else 
		{
			//横线
			canvas.drawLine(getRight()-getPaddingRight()-2*cicleRadius, getHeight()/2, getRight()-getPaddingRight(), getHeight()/2, sPaint);
			//竖线
			canvas.drawLine((float) (getPaddingLeft()+cicleRadius+Math.cos(defaultAngle)*cicleRadius), 
					(float)(getPaddingLeft()+cicleRadius-cicleRadius*Math.sin(defaultAngle)), 
					(float)(getPaddingLeft()+cicleRadius-cicleRadius*Math.cos(defaultAngle)), 
					(float) (getPaddingLeft()+cicleRadius+cicleRadius*Math.sin(defaultAngle)), sPaint);
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			int x = (int) event.getX();
			if(menuStyle == LEFT)
			{
				if(x < 2*cicleRadius + 2*getPaddingLeft() && isAnimatorRunning == false)
				{
					startExpandAnimation();
					return true;
				}
			}
			else {
				if(x> getRight()-2*cicleRadius-2*getPaddingRight() && isAnimatorRunning == false)
				{
					startExpandAnimation();
					return true;
				}
			}
			break;

		default:
			break;
		}
		return true;
	}
	
	private int measureLength(int length, int measureSpec)
	{
		int result = length;
		int measureMode = MeasureSpec.getMode(measureSpec);
		int measureSize = MeasureSpec.getSize(measureSpec);
		if(measureMode == MeasureSpec.EXACTLY)
			result = measureSize;
		else if(measureMode == MeasureSpec.AT_MOST)
			result = Math.min(length, measureSize);
		return result;
	}
	
	@SuppressLint("NewApi") 
	private void setMenuBackground()
	{
		GradientDrawable drawable = new GradientDrawable();
		drawable.setColor(Color.WHITE);
		drawable.setStroke(strockWidth, strockColor);
		drawable.setCornerRadius(cornerRadius);
		setBackground(drawable);
	}
	
	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f); // +0.5是为了向上取整
	}

	private void startExpandAnimation()
	{
		ValueAnimator animator = ValueAnimator.ofFloat(0,1);
		animator.setDuration(1000);
		animator.addListener(new AnimatorListener() {
			
			@Override
			public void onAnimationStart(Animator animation) {
				isAnimatorRunning = true;
			}
			
			@Override
			public void onAnimationRepeat(Animator animation) {
			}
			
			@Override
			public void onAnimationEnd(Animator animation) {
				isAnimatorRunning = false;
				isExpanded = isExpanded == true ? false : true;
			}
			
			@Override
			public void onAnimationCancel(Animator animation) {
			}
		});
		
		animator.addUpdateListener(new AnimatorUpdateListener() {
			
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				float value = (Float) animation.getAnimatedValue();
				if(isExpanded)
				{
					defaultAngle = Math.PI/2*value;
					if(menuStyle == LEFT)
					{
						layout(getLeft(), getTop(), (int) (width-value*range), getBottom());
					}
					else {
						layout((int) (value*range), getTop(), getRight(), getBottom());
					}
					Log.d("MainActivity", getLeft()+"   "+getRight());
					invalidate();
				}
				else 
				{
					defaultAngle = Math.PI/2-Math.PI/2*value;
					if(menuStyle == LEFT)
					{
						layout(getLeft(), getTop(), (int) (2*cicleRadius+2*getPaddingLeft()+value*range), getBottom());
					}
					else {
						layout((int) (getRight()-value*range-2*cicleRadius-2*getPaddingLeft()), getTop(), getRight(), getBottom());
					}
					Log.d("MainActivity", getLeft()+"   "+getRight());
					invalidate();
				}
			}
		});
		animator.start();
	}
}
