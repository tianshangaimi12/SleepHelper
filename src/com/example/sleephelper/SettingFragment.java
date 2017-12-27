package com.example.sleephelper;

import java.util.zip.Inflater;

import com.bumptech.glide.Glide;
import com.example.sleephlper.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.drawable.BitmapDrawable;
import android.nfc.NfcAdapter.CreateBeamUrisCallback;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SettingFragment extends Fragment implements OnClickListener{
	private MainActivity activity;
	
	private ImageView mImageViewUser;
	private LinearLayout mLLDiary;
	private LinearLayout mLLGirl;
	private LinearLayout mLLJoke;
	private LinearLayout mLLAbout;
	
	private final String IMG_URL = "http://imgsrc.baidu.com/forum/w=580/sign=" +
			"032f70505766d0167e199e20a729d498/4c4e0cf3d7ca7bcb30b234b2bb096b63f724a826.jpg";
	private final String ABOUT_IMG_URL = "http://www.enterdesk.com/uploadfile/2014/0227/20140227113508372.jpg";
	
	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_new_setting, null,false);
		initView(view);
		activity = (MainActivity) getActivity();
		return view;
	}
	
	public void initView(View view)
	{
		mImageViewUser = (ImageView)view.findViewById(R.id.img_user);
		Glide.with(getActivity())
		.load(IMG_URL)
		.transform(new CircleCrop(getActivity()))
		.into(mImageViewUser);
		mLLDiary = (LinearLayout)view.findViewById(R.id.ll_menu_diary);
		mLLGirl = (LinearLayout)view.findViewById(R.id.ll_menu_girl);
		mLLJoke = (LinearLayout)view.findViewById(R.id.ll_menu_joke);
		mLLAbout = (LinearLayout)view.findViewById(R.id.ll_menu_about);
		mLLDiary.setOnClickListener(this);
		mLLGirl.setOnClickListener(this);
		mLLJoke.setOnClickListener(this);
		mLLAbout.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_menu_diary:
			activity.setCurrentPage(0);
			break;
		case R.id.ll_menu_girl:
			activity.setCurrentPage(1);
			break;
		case R.id.ll_menu_joke:
			activity.setCurrentPage(2);
			break;
		case R.id.ll_menu_about:
			showAboutWindow();
			break;
		default:
			break;
		}
	}
	
	public void showAboutWindow()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_about, null,false);
		builder.create();
		final Dialog dialog = builder.show();
		dialog.getWindow().setContentView(view);
		dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		WindowManager.LayoutParams params = dialog.getWindow().getAttributes();  
		params.width = 700;  
		params.height = 600 ;  
		//dialog.getWindow().setAttributes(params); 
		ImageView imageView = (ImageView)view.findViewById(R.id.img_dialog_about);
		Glide.with(getActivity())
		.load(ABOUT_IMG_URL)
		.placeholder(R.drawable.menu_joke)
		.fitCenter()
		.transform(new CircleCrop(getActivity()))
		.into(imageView);
		TextView textView = (TextView)view.findViewById(R.id.txt_dialog_about);
		textView.setText("CREATED"+"\n"+"BY"+"\n"+"Z&C");
		TextView button = (TextView)view.findViewById(R.id.btn_dialog_about);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
	}

}
