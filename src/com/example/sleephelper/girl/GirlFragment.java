package com.example.sleephelper.girl;

import java.util.ArrayList;

import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sleephelper.girl.GirlRecycleviewAdapter.OnImgClickListener;
import com.example.sleephlper.R;
import com.google.gson.Gson;

import android.app.ProgressDialog;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class GirlFragment extends Fragment{
	
	private ArrayList<String> urls = new ArrayList<String>();
	private GirlsBean mGirlsBean;
	private int index = 1;
	private GirlRecycleviewAdapter adapter;

	private RecyclerView mRecyclerView;
	private SwipeRefreshLayout mSwipeRefreshLayout;
	
	private final String URL = "http://gank.io/api/data/%E7%A6%8F%E5%88%A9/24/";
	private final String TAG = "GirlFragment";
	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_girl, null,false);
		mRecyclerView = (RecyclerView)view.findViewById(R.id.recyclerview_girl);
		initAdapter();
		getGirlsPic(index);
		mSwipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipe_fragment_girl);
		mSwipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				getGirlsPic(++index);
			}
		});
		return view;
	}
	
	public void initAdapter()
	{
		adapter = new GirlRecycleviewAdapter(getActivity(),urls);
		adapter.setOnImgClickListener(new OnImgClickListener() {
			
			@Override
			public void onclick(View view, int position) {
				Intent intent = new Intent(getActivity(),GirlShowActivity.class);
				intent.putStringArrayListExtra("urls", urls);
				intent.putExtra("position", position);
				startActivity(intent);
			}
		});
		mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
		mRecyclerView.setAdapter(adapter);
	}
	
	public void getGirlsPic(int page)
	{
		urls.clear();
		final String url = URL+page;
		JsonObjectRequest request = new JsonObjectRequest(url, null, 
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						if(mSwipeRefreshLayout.isRefreshing())
							mSwipeRefreshLayout.setRefreshing(false);
						Log.d(TAG, url);
						Log.d(TAG, response.toString());
						Gson gson = new Gson();
						mGirlsBean = gson.fromJson(response.toString(), GirlsBean.class);
						if(mGirlsBean.getError() == false)
						{
							if(mGirlsBean.getGirls().size() == 0)
							{
								Toast.makeText(getActivity(), "没有图片", Toast.LENGTH_LONG).show();
								return;
							}
							for(int i=0;i<mGirlsBean.getGirls().size();i++)
							{
								urls.add(mGirlsBean.getGirls().get(i).getUrl());
							}
							adapter.notifyDataSetChanged();
							//mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
						}
						else {
							Toast.makeText(getActivity(), "查询失败", Toast.LENGTH_LONG).show();
						}
					}
				}, 
				new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						if(mSwipeRefreshLayout.isRefreshing())
							mSwipeRefreshLayout.setRefreshing(false);
					}
				});
		
		RequestQueue queue = Volley.newRequestQueue(getActivity());
		queue.add(request);
	}
	

}
