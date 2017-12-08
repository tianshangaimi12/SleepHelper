package com.example.sleephelper;

import java.util.ArrayList;

import com.example.sleephelper.diary.DiaryFragment;
import com.example.sleephelper.girl.GirlFragment;
import com.example.sleephlper.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends FragmentActivity implements OnClickListener{
	private int currentPage;
	private MainFragmentAdapter adapter;
	private ArrayList<Fragment> fragments;
	
	private ViewPager mViewPager;
	private SelectBar mSelectBar;
	private GirlFragment mGirlFragment;
	private DiaryFragment mDiaryFragment;
	private SleepHelperTitle mSleepHelperTitle;
	private LinearLayout mLayoutDiary;
	private LinearLayout mLayoutGirl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }
    
    public void initData()
    {
    	mGirlFragment = new GirlFragment();
        mDiaryFragment = new DiaryFragment();
        fragments = new ArrayList<Fragment>();
        fragments.add(mDiaryFragment);
        fragments.add(mGirlFragment);
        adapter = new MainFragmentAdapter(getSupportFragmentManager(), fragments);
    }
    
    public void initView()
    {
    	mViewPager = (ViewPager)findViewById(R.id.viewpager_main);
    	mViewPager.setAdapter(adapter);
    	mSleepHelperTitle = (SleepHelperTitle)findViewById(R.id.title_main);
    	mSleepHelperTitle.setCenterTitle("SleepHelper");
    	mSleepHelperTitle.setBackImg(R.drawable.menu);
    	mSelectBar = (SelectBar)findViewById(R.id.selectbar);
    	mSelectBar.setStartPoint();
    	mLayoutDiary = (LinearLayout)findViewById(R.id.linearlayout_diary);
    	mLayoutGirl = (LinearLayout)findViewById(R.id.linearlayout_girl);
    	mLayoutDiary.setOnClickListener(this);
    	mLayoutGirl.setOnClickListener(this);
    	mViewPager.addOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				//mSelectBar.setIndex(arg0);
				currentPage = arg0;
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				Log.d("MainActivity","arg1="+arg1+"   arg2="+arg2);//arg2始终为正，值为与初始位置的偏移量
				//0-->1
				if(currentPage == 0 && arg0 == 0)
					mSelectBar.setStartPoint(arg1);
				//1--0
				else if(currentPage == 1 && arg0 == 0)
					mSelectBar.setStartPoint(arg1);
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
			
			}
		});
    }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.linearlayout_diary:
			mViewPager.setCurrentItem(0);
			//mSelectBar.setIndex(0);
			break;
		case R.id.linearlayout_girl:
			mViewPager.setCurrentItem(1);
			//mSelectBar.setIndex(1);
		default:
			break;
		}
	}
}
