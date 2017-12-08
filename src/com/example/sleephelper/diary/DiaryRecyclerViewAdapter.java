package com.example.sleephelper.diary;

import java.util.ArrayList;
import java.util.List;

import com.example.sleephlper.R;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.TextPaint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class DiaryRecyclerViewAdapter extends Adapter{
	
	private Context mContext;
	private List<String> mDates;
	private List<String> mTitles;
	private List<String> mContents;
	private List<Integer> pointsY = new ArrayList<Integer>();
	private final String TAG = "DiaryRecyclerViewAdapter";
	
	public DiaryRecyclerViewAdapter(Context context,List<String> dates,List<String> titles,List<String> contents)
	{
		mContext = context;
		mDates = dates;
		mTitles = titles;
		mContents = contents;
	}
	

	@Override
	public int getItemCount() {
		return mDates.size();
	}

	@Override
	public void onBindViewHolder(ViewHolder arg0, int position) {
		DiaryViewHolder viewHolder = (DiaryViewHolder) arg0;
		TextPaint paint = viewHolder.txtTitle.getPaint();
		paint.setFakeBoldText(true); 
		viewHolder.txtDate.setText(mDates.get(position));
		pointsY.add(viewHolder.txtDate.getBottom()-viewHolder.txtDate.getHeight()/2);
		viewHolder.txtTitle.setText(mTitles.get(position));
		viewHolder.txtContent.setText(mContents.get(position));
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
		View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_diary, arg0,false);
		DiaryViewHolder viewHolder = new DiaryViewHolder(view);
		return viewHolder;
	}
	
	class DiaryViewHolder extends ViewHolder
	{
		TextView txtDate;
		TextView txtTitle;
		TextView txtContent;
		ImageView imgEdit;

		public DiaryViewHolder(View view) {
			super(view);
			txtDate = (TextView)view.findViewById(R.id.txt_item_diary_date);
			txtTitle = (TextView)view.findViewById(R.id.txt_item_diary_title);
			txtContent = (TextView)view.findViewById(R.id.txt_item_diary_content);
			imgEdit = (ImageView)view.findViewById(R.id.img_item_diary_edit);
		}
		
	}
	
	/**
	 * @return 所有日期所对应点的纵坐标
	 */
	
	public List<Integer> getPointsY()
	{
		return pointsY;
	}

}
