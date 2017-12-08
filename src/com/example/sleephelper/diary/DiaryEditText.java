package com.example.sleephelper.diary;

import android.R.integer;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.widget.EditText;

public class DiaryEditText extends EditText{
	
	private int lastLine;
	
	private Paint mPaint;
	private Rect mRect;

	public DiaryEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		mRect = new Rect();
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setColor(Color.parseColor("#bbbbbb"));
		mPaint.setStyle(Style.FILL_AND_STROKE);
		mPaint.setStrokeWidth(2);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int count = getLineCount();
		//根据已有的文本画下划线
		for(int i =0;i<count;i++)
		{
			int baseLine = getLineBounds(i, mRect);
			lastLine = baseLine;
			canvas.drawLine(mRect.left, baseLine+8, mRect.right, baseLine+8, mPaint);
		}
		int lineDistance = lastLine/(count);
		//文本没有充满EditText,下划线也不会满,使下划线充满EditText
		for(int i = 1;i<=20;i++)
		{
			int currentLine = lastLine + i*lineDistance;
			canvas.drawLine(mRect.left, currentLine+8, mRect.right, currentLine+8, mPaint);
		}
		
	}

}
