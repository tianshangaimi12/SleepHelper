package com.example.sleephelper.joke;

import java.util.ArrayList;
import java.util.List;


public class Data {
	
	private List<DataBean> data;

	public List<DataBean> getData() {
		return data;
	}

	public void setData(List<DataBean> data) {
		this.data = data;
	}


	class DataBean
	{
		private GroupBean group;

		public GroupBean getGroup() {
			return group;
		}

		public void setGroup(GroupBean group) {
			this.group = group;
		}

	}
}
