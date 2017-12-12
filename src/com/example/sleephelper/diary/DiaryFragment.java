package com.example.sleephelper.diary;

import java.util.ArrayList;
import java.util.List;

import com.example.sleephelper.diary.DiaryRecyclerViewAdapter.EditClickListener;
import com.example.sleephlper.R;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class DiaryFragment extends Fragment{
	private RecyclerView mRecyclerView;
	private DiaryRecyclerViewAdapter mAdapter;
	private List<String> mDates;
	private List<String> mTitles;
	private List<String> mContents;
	private DiaryDatabaseHelper mDatabaseHelper;
	private SQLiteDatabase mDatabase;
	
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
		return view;
	}
	
	public void initData()
	{
		mDatabaseHelper = new DiaryDatabaseHelper(getActivity(), "SavedDiarys.db", null, 1);
		mDatabase = mDatabaseHelper.getWritableDatabase();
		initListView();
	}
	
	public void initListView()
	{
		mDates = new ArrayList<String>();
		mTitles = new ArrayList<String>();
		mContents = new ArrayList<String>();
		Cursor cursor = mDatabase.query("diary", null, null, null, null, null, null);
		if(cursor.moveToFirst())
		{
			do {
				String date = cursor.getString(cursor.getColumnIndex("date"));
				String title = cursor.getString(cursor.getColumnIndex("title"));
				String content = cursor.getString(cursor.getColumnIndex("content"));
				mDates.add(date);
				mTitles.add(title);
				mContents.add(content);
				
			} while (cursor.moveToNext());
		}
		cursor.close();
		mAdapter = new DiaryRecyclerViewAdapter(getActivity(), mDates, mTitles, mContents);
		mAdapter.setEditClickListener(new EditClickListener() {

			@Override
			public void onImgClick(View view, int position) {
				mDatabase.delete("diary", "date=? and title=? and content=?", new String[]{
						mDates.get(position),mTitles.get(position),mContents.get(position)
				});
				initListView();
			}

			@Override
			public void onItemClick(View view, int position) {
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
		mRecyclerView.setAdapter(mAdapter);
		mDiaryLine.setPointsY(mAdapter.getPointsY());
	}
	
	public void reStart()
	{
		initListView();
	}

}
