package com.example.sleephelper;

import com.example.sleephlper.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SleepHelperTitle extends LinearLayout{
	private ImageView mImageView;
	private TextView mTextViewTitle;
	private TextView mTextViewCenter;
	private ImageView mImageViewGrid;
	private BackClickListner mBackClickListner;
	private BackClickListner mBackClickListnerGrid;
	
	public SleepHelperTitle(Context context)
	{
		super(context);
	}
	

	public SleepHelperTitle(Context context, AttributeSet attrs) {
		super(context, attrs);
		View view = LayoutInflater.from(context).inflate(R.layout.sleephelper_title, this);
		mImageView = (ImageView)view.findViewById(R.id.imgbtn_back);
		mImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(mBackClickListner != null)
					mBackClickListner.onClick(arg0);
			}
		});
		mTextViewTitle = (TextView)view.findViewById(R.id.txt_gradetrack_title);
		mTextViewCenter = (TextView)view.findViewById(R.id.txt_gradetrack_title_center);
		mImageViewGrid = (ImageView)view.findViewById(R.id.img_show_grid);
		mImageViewGrid.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(mBackClickListnerGrid != null)
					mBackClickListnerGrid.onClick(arg0);
			}
		});
	}
	
	public void setTitle(String title)
	{
		mTextViewTitle.setText(title);
	}
	
	public void setCenterTitle(String title)
	{
		mTextViewCenter.setText(title);
	}
	
	public void setTitleImgVisible()
	{
		mTextViewCenter.setVisibility(View.VISIBLE);
		mImageViewGrid.setVisibility(View.VISIBLE);
	}
	
	public void setBackClickListner(BackClickListner backClickListner)
	{
		mBackClickListner = backClickListner;
	}
	
	public void setBackClickGridListner(BackClickListner backClickListner)
	{
		mBackClickListnerGrid = backClickListner;
	}
	
	public interface BackClickListner
	{
		void onClick(View view);
	}
	
	public void setMenuImg(int resId)
	{
		mImageViewGrid.setImageResource(resId);
	}
	
	public void setBackImg(int resId)
	{
		mImageView.setImageResource(resId);
	}
}
