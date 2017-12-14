package com.example.sleephelper.joke;

import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.example.sleephlper.R;
import com.google.gson.Gson;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class JokeFragment extends Fragment{
	private Data mData;
	private JokeRecyclerViewAdapter mAdapter;
	
	private RecyclerView mRecyclerView;
	
	private final String TAG = "JokeFragment";
	private final String URL = "http://is.snssdk.com/neihan/stream/mix/v1/?" +
			"mpic=1&webp=1&essence=1&content_type=-102&message_cursor=-1&am_longitude=110&am_latitude=120&" +
			"am_city=%E5%8C%97%E4%BA%AC%E5%B8%82&am_loc_time=1489226058493&count=30&min_time=1489205901&" +
			"screen_width=1450&do00le_col_mode=0&iid=3216590132&device_id=32613520945&ac=wifi&channel=360&" +
			"aid=7&app_name=joke_essay&version_code=612&version_name=6.1.2&device_platform=android&ssmix=a&" +
			"device_type=sansung&device_brand=xiaomi&os_api=28&os_version=6.10.1&uuid=326135942187625&" +
			"openudid=3dg6s95rhg2a3dg5&" +
			"manifest_version_code=612&resolution=1450*2800&dpi=620&update_version_code=6120";
	
	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_joke, null,false);
		mRecyclerView = (RecyclerView)view.findViewById(R.id.recyclerview_joke);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		getJoke();
		return view;
	}
	
	public void getJoke()
	{
		JsonObjectRequest request = new JsonObjectRequest(URL, null, 
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						Log.d(TAG, "response="+response);
						String msg = response.optString("message");
						if(msg.equals("success"))
						{
							Log.d(TAG, "data="+response.optJSONObject("data").toString());
							Gson gson = new Gson();
							mData = gson.fromJson(response.optJSONObject("data").toString(), Data.class);
							setAdapter();
						}
					}
				}, 
				new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						Log.d(TAG, "error="+error);
					}
				});
		RequestQueue queue = Volley.newRequestQueue(getActivity());
		queue.add(request);
	}
	
	public void setAdapter()
	{
		mAdapter = new JokeRecyclerViewAdapter(getActivity(), mData.getData());
		mRecyclerView.setAdapter(mAdapter);
	}

}
