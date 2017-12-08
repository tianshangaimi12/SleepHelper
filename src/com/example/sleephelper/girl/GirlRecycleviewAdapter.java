package com.example.sleephelper.girl;

import java.util.ArrayList;
import java.util.Random;

import com.example.sleephlper.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
/**
 * i love the girl
 * @author zhangchong
 * 
 */

public class GirlRecycleviewAdapter extends RecyclerView.Adapter{
	private Context context;
	private ArrayList<String> urls;
	
	
	public GirlRecycleviewAdapter(Context context,ArrayList<String> urls)
	{
		this.context = context;
		this.urls = urls;
	}

	@Override
	public int getItemCount() {
		return urls.size();
	}

	@Override
	public void onBindViewHolder(ViewHolder arg0, int arg1) {
		MyViewHolder viewHolder = (MyViewHolder) arg0;
		int height = dip2px(context, 150+getRandomH());
		int width = (context.getResources().getDisplayMetrics().widthPixels-dip2px(context, 20))/2;
		viewHolder.imageView.setLayoutParams(new RelativeLayout.LayoutParams(width, height));
		Picasso.with(context)
		.load(urls.get(arg1))
		//.placeholder(R.drawable.ic_launcher)
		//.memoryPolicy(MemoryPolicy.NO_CACHE)
        //.networkPolicy(NetworkPolicy.NO_CACHE)
        .fit()
        //.resize(height, width)
        .into(viewHolder.imageView);
		
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
		View view = LayoutInflater.from(context).inflate(R.layout.list_item_girl, arg0,false);
		MyViewHolder viewHolder = new MyViewHolder(view);
		return viewHolder;
	}
	
	class MyViewHolder extends ViewHolder
	{
		ImageView imageView;

		public MyViewHolder(View view) {
			super(view);
			imageView = (ImageView)view.findViewById(R.id.img_girl);
		}
		
	}
	
	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f); // +0.5是为了向上取整
	}

	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);// +0.5是为了向上取整

	}

	/**
	 * @author zhangchong
	 * @return 100内的正整数
	 */
	public int getRandomH()
	{
		Random random = new Random();
		return random.nextInt(100);
	}
}
