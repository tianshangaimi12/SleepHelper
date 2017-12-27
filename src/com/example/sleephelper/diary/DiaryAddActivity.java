package com.example.sleephelper.diary;

import java.util.Calendar;

import com.example.sleephelper.HorizonExpandMenu;
import com.example.sleephelper.SleepHelperTitle;
import com.example.sleephelper.SleepHelperTitle.BackClickListner;
import com.example.sleephlper.R;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DiaryAddActivity extends Activity implements OnClickListener,OnFocusChangeListener{
	
	private DiaryDatabaseHelper mDiaryDatabaseHelper;
	private SQLiteDatabase mDatabase;
	private int diaryType;
	
	private SleepHelperTitle mHelperTitle;
	private TextView mTextViewDate;
	private EditText mEditTextTitle;
	private DiaryEditText mEditTextContent;
	private Button mBtnAdd;
	private Button mBtnDelete;
	private Button mBtnClose;
	private HorizonExpandMenu hMenu;
	
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
		hMenu = (HorizonExpandMenu)findViewById(R.id.hMenu);
		mEditTextContent = (DiaryEditText)findViewById(R.id.dedt_content);
		mEditTextContent.setOnFocusChangeListener(this);
		mEditTextTitle = (EditText)findViewById(R.id.edt_title);
		mEditTextTitle.setOnFocusChangeListener(this);
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
		mBtnAdd = (Button)findViewById(R.id.btn_menu_add);
		mBtnClose = (Button)findViewById(R.id.btn_menu_close);
		mBtnDelete = (Button)findViewById(R.id.btn_menu_delete);
		mBtnAdd.setOnClickListener(this);
		mBtnClose.setOnClickListener(this);
		mBtnDelete.setOnClickListener(this);
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
		case R.id.btn_menu_add:
			save();
			DiaryAddActivity.this.finish();
			break;
		case R.id.btn_menu_close:
			DiaryAddActivity.this.finish();
			break;
		case R.id.btn_menu_delete:
			mEditTextContent.setText("");
			mEditTextTitle.setText("");
			break;
		default:
			break;
		}
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		Log.d("JokeFragment", ""+hasFocus);
		if(hasFocus && hMenu.isExpanded == true)
		{
			//hMenu.startExpandAnimation();
		}
	}
	

}
