package com.example.sleephelper.girl;

import com.google.gson.annotations.SerializedName;

public class GirlBean {
	@SerializedName("_id")
	private String id;
	
	private String createAt;
	
	private String url;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreateAt() {
		return createAt;
	}

	public void setCreateAt(String createAt) {
		this.createAt = createAt;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
