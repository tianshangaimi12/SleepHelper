package com.example.sleephelper.girl;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import android.R.bool;

public class GirlsBean {
	
	private boolean error;
	
	@SerializedName("results")
	private List<GirlBean> girls;

	public boolean getError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public List<GirlBean> getGirls() {
		return girls;
	}

	public void setGirls(List<GirlBean> girls) {
		this.girls = girls;
	}
	
	

}
