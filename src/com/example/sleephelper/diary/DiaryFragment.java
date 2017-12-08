package com.example.sleephelper.diary;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.example.sleephelper.diary.DiaryRecyclerViewAdapter.EditClickListener;
import com.example.sleephlper.R;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class DiaryFragment extends Fragment{
	private RecyclerView mRecyclerView;
	private DiaryRecyclerViewAdapter mAdapter;
	private List<String> mDates;
	private List<String> mTitles;
	private List<String> mContents;
	
	private DiaryLine mDiaryLine;
	private ImageButton mButtonAddDiary;
	
	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_diary, null,false);
		mRecyclerView = (RecyclerView)view.findViewById(R.id.recyclerview_diary);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		mDiaryLine = (DiaryLine)view.findViewById(R.id.diaryline);
		mButtonAddDiary = (ImageButton)view.findViewById(R.id.ibtn_add_diary);
		mButtonAddDiary.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),DiaryAddActivity.class);
				startActivity(intent);
			}
		});
		initData();
		mAdapter.setEditClickListener(new EditClickListener() {
			
			@Override
			public void onClick(View view, int position) {
				final Intent intent = new Intent(getActivity(),DiaryAddActivity.class);
				intent.putExtra("date", mDates.get(position));
				intent.putExtra("title", mTitles.get(position));
				intent.putExtra("content", mContents.get(position));
				ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotation", 0,360);
				animator.setDuration(500);
				animator.start();
				animator.addListener(new AnimatorListenerAdapter() {
					@Override
					public void onAnimationEnd(Animator animation) {
						super.onAnimationEnd(animation);
						startActivity(intent);
						getActivity().overridePendingTransition(0, 0);
					}
				});
			}
		});
		return view;
	}
	
	public void initData()
	{
		mDates = new ArrayList<String>();
		mTitles = new ArrayList<String>();
		mContents = new ArrayList<String>();
		long nowData = System.currentTimeMillis();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date(nowData);
		String nowDate = format.format(date);
		mDates.add(nowDate);
		mTitles.add("12345");
		mContents.add("222222222222222234994919959911111111111111");
		mAdapter = new DiaryRecyclerViewAdapter(getActivity(), mDates, mTitles, mContents);
		mRecyclerView.setAdapter(mAdapter);
		mDiaryLine.setPointsY(mAdapter.getPointsY());
	}

}
