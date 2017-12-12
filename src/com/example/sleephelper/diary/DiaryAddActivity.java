package com.example.sleephelper.diary;

import java.util.Calendar;

import com.example.sleephelper.SleepHelperTitle;
import com.example.sleephelper.SleepHelperTitle.BackClickListner;
import com.example.sleephlper.R;

import android.R.integer;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.TextureView;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

public class DiaryAddActivity extends Activity implements OnClickListener{
	
	private DiaryDatabaseHelper mDiaryDatabaseHelper;
	private SQLiteDatabase mDatabase;
	private int diaryType;
	
	private SleepHelperTitle mHelperTitle;
	private TextView mTextViewDate;
	private EditText mEditTextTitle;
	private DiaryEditText mEditTextContent;
	private ImageButton mImageButtonDiaryOptions;
	private PopupWindow mPopupWindow;
	private ImageView mImageViewAdd;
	private ImageView mImageViewClose;
	private ImageView mImageViewDelete;
	
	private final int DIARY_ADD = 1;
	private final int DIARY_EDIT = 2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_add_diary);
		initView();
		initData();
	}
	
	public void initView()
	{
		mImageButtonDiaryOptions = (ImageButton)findViewById(R.id.imgbtn_check_diary);
		mImageButtonDiaryOptions.setOnClickListener(this);
		mEditTextContent = (DiaryEditText)findViewById(R.id.dedt_content);
		mEditTextTitle = (EditText)findViewById(R.id.edt_title);
		mHelperTitle = (SleepHelperTitle)findViewById(R.id.title_diary_add);
		mHelperTitle.setCenterTitle("添加日记");
		mHelperTitle.setBackImg(R.drawable.back);
		mHelperTitle.setBackClickListner(new BackClickListner() {
			
			@Override
			public void onClick(View view) {
				finish();
			}
		});
		mTextViewDate = (TextView)findViewById(R.id.txt_date);
		Calendar c = Calendar.getInstance();
		mTextViewDate.setText(getDateString(c));
	}
	
	public void initData()
	{
		Intent intent = getIntent();
		if(intent.getStringExtra("date") != null)
		{
			diaryType = DIARY_EDIT;
			String date = intent.getStringExtra("date");
			String title = intent.getStringExtra("title");
			String content = intent.getStringExtra("content");
			mTextViewDate.setText("今天,"+date);
			mEditTextTitle.setText(title);
			mEditTextContent.setText(content);
			mHelperTitle.setCenterTitle("修改日记");
		}
		else diaryType = DIARY_ADD;
		mDiaryDatabaseHelper = new DiaryDatabaseHelper(this, "SavedDiarys.db", null, 1);
		mDatabase = mDiaryDatabaseHelper.getWritableDatabase();
	}
	
	public String getDateString(Calendar c)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("今天,"+c.get(Calendar.YEAR)+"年"+(c.get(Calendar.MONTH)+1)+"月"
		+c.get(Calendar.DAY_OF_MONTH)+"日,");
		switch (c.get(Calendar.DAY_OF_WEEK)) {
		case 1:
			sb.append("星期天");
			break;
		case 2:
			sb.append("星期一");
			break;
		case 3:
			sb.append("星期二");
			break;
		case 4:
			sb.append("星期三");
			break;
		case 5:
			sb.append("星期四");
			break;
		case 6:
			sb.append("星期五");
			break;
		case 7:
			sb.append("星期六");
			break;
		default:
			break;
		}
		return sb.toString();
	}
	
	public String getDateString(String date)
	{
		String year = date.split("-")[0];
		String month = date.split("-")[1];
		String day = date.split("-")[2];
		char[] days = day.toCharArray();
		if(days[0] == '0')
			day = String.valueOf(days[1]);
		Calendar c = Calendar.getInstance();
		c.set(Integer.valueOf(year), Integer.valueOf(month)-1, Integer.valueOf(day));
		return getDateString(c);
	}
	
	public void showPopupWindow()
	{
		if(mPopupWindow != null)
		{
			if(mPopupWindow.isShowing())
				return;
		}
		View root = getLayoutInflater().inflate(R.layout.pwindow_diary_options, null);
		mImageViewAdd =  (ImageView)root.findViewById(R.id.img_diary_check);
		mImageViewClose = (ImageView)root.findViewById(R.id.img_diary_close);
		mImageViewDelete = (ImageView)root.findViewById(R.id.img_diary_delete);
		mImageViewAdd.setOnClickListener(this);
		mImageViewClose.setOnClickListener(this);
		mImageViewDelete.setOnClickListener(this);
		mPopupWindow = new PopupWindow(root,LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
		mPopupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
		mPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		mPopupWindow.showAtLocation(mImageButtonDiaryOptions, Gravity.LEFT|Gravity.TOP, 
				mImageButtonDiaryOptions.getLeft()+mImageButtonDiaryOptions.getWidth()/3-10,
				mImageButtonDiaryOptions.getTop()+mImageButtonDiaryOptions.getHeight()/2+10);
	}
	
	public void save()
	{
		String date = mTextViewDate.getText().toString().substring(3);
		String title = mEditTextTitle.getText().toString();
		String content = mEditTextContent.getText().toString();
		if(!TextUtils.isEmpty(title)&&!TextUtils.isEmpty(content))
		{
			if(diaryType == DIARY_ADD)
			{
				ContentValues values = new ContentValues();
				values.put("date", date);
				values.put("title", title);
				values.put("content", content);
				mDatabase.insert("diary", null, values);
			}
			else {
				ContentValues values = new ContentValues();
				values.put("title", title);
				values.put("content", content);
				mDatabase.update("diary", values, "title=? and content=? and date=?", 
						new String[]{getIntent().getStringExtra("title"),getIntent().getStringExtra("content"),date});
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imgbtn_check_diary:
			showPopupWindow();
			break;
		case R.id.img_diary_check:
			save();
			DiaryAddActivity.this.finish();
			break;
		case R.id.img_diary_close:
			mPopupWindow.dismiss();
			break;
		case R.id.img_diary_delete:
			mEditTextContent.setText("");
			mEditTextTitle.setText("");
			break;
		default:
			break;
		}
	}

}
