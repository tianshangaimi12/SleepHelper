package com.example.sleephelper.girl;

import java.util.ArrayList;
import java.util.List;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.sleephlper.R;

import android.app.Activity;
import android.app.Notification.Action;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.ImageView;

public class GirlShowActivity extends Activity{
	
	private float startX = 0;
	private float startY = 0;
	private float endX = 0;
	private float endY = 0;
	private List<String> urls = new ArrayList<String>();
	private int position;
	
	private ImageView mImageView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_show_girl);
		mImageView = (ImageView)findViewById(R.id.img_show_girl);
		initView();
	}
	
	public void initView()
	{
		Intent intent = getIntent();
		urls = intent.getStringArrayListExtra("urls");
		position = intent.getIntExtra("position", 0);
		showGirl(position);
		mImageView.setOnTouchListener(new OnTouchListener() {
			
			public boolean onTouch(View v, MotionEvent event) {
			
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					startX = event.getX();
					startY = event.getY();
					break;
				case MotionEvent.ACTION_MOVE:
					
					break;
				case MotionEvent.ACTION_UP:
					endX = event.getX();
					endY = event.getY();
					if(endY - startY > 200)
					{
						GirlShowActivity.this.finish();
						break;
					}
					if(endX - startX > 200)
					{
						position--;
						showGirl(position);
					}
					else if(endX - startX < -200)
					{
						position++;
						showGirl(position);
					}
					break;
				default:
					break;
				}
				return true;
			}
		});
	}
	
	public void showGirl(int position)
	{
		if(position > urls.size()-1)
			position = urls.size()-1;
		else if(position < 0)
			position = 0;
		Glide.with(this)
		.load(urls.get(position))
		.fitCenter()
		.dontAnimate()
		.diskCacheStrategy(DiskCacheStrategy.ALL)
		.into(mImageView);
	}

}
