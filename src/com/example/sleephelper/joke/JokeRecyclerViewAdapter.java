package com.example.sleephelper.joke;

import java.util.List;

import com.bumptech.glide.Glide;
import com.example.sleephelper.joke.Data.DataBean;
import com.example.sleephlper.R;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class JokeRecyclerViewAdapter extends Adapter{
	private List<DataBean> data;
	private Context context;
	
	public JokeRecyclerViewAdapter(Context context,List<DataBean> data)
	{
		this.context = context;
		this.data = data;
	}

	@Override
	public int getItemCount() {
		return data.size();
	}
	

	@Override
	public void onBindViewHolder(ViewHolder arg0, int position) {
		MyViewHolder viewHolder = (MyViewHolder) arg0;
		DataBean dataBean = data.get(position);
		GroupBean groupBean = dataBean.getGroup();
		UserBean userBean = groupBean.getUser();
		viewHolder.txtPerson.setText(userBean.getName());
		viewHolder.txtContent.setText(groupBean.getText());
		Glide.with(context)
		.load(userBean.getAvatar_url())
		.into(viewHolder.imgPerson);
		
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
		View view = LayoutInflater.from(context).inflate(R.layout.list_item_joke, arg0,false);
		return new MyViewHolder(view);
	}
	
	class MyViewHolder extends ViewHolder
	{
		ImageView imgPerson;
		TextView txtPerson;
		TextView txtContent;

		public MyViewHolder(View view) {
			super(view);
			imgPerson = (ImageView)view.findViewById(R.id.img_joke_person);
			txtPerson = (TextView)view.findViewById(R.id.txt_joke_person);
			txtContent = (TextView)view.findViewById(R.id.txt_joke_content);
		}
		
	}

}
