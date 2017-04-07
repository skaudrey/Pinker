package com.example.pinker.Activity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;

import com.example.pinker.R;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.*;
public class TempActivity extends Activity{
	
	//起始时间	
		
		
	private TextView SubmitStartDataTextV;
	private Button StartDataChangeButton;
	private TextView SubmitStartTimeTextV;
	private Button StartTimeChangeButton;
	private int StartDataYear;
	private int StartDataMonth;
	private int StartDataDay;
	public int year;
	public int month;
	public int day;
	private int hour;
	private int minute;
	
	static final int DATE_DIALOG_ID = 0;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_temp);
		
		//获取当前时间
		Calendar calendar= Calendar.getInstance();//可以对每个时间域单独修改
		year = calendar.get(Calendar.YEAR); 
		month = calendar.get(Calendar.MONTH); 
		day = calendar.get(Calendar.DATE); 
		hour = calendar.get(Calendar.HOUR_OF_DAY); 
		minute = calendar.get(Calendar.MINUTE); 
		
		
		//开始时间
		SubmitStartDataTextV=(TextView)findViewById(R.id.SubmitStartDataTextV);
		StartDataChangeButton=(Button)findViewById(R.id.SubmitStartDataButton);	
		SubmitStartTimeTextV=(TextView)findViewById(R.id.SubmitStartHMTextV);
		StartTimeChangeButton=(Button)findViewById(R.id.SubmitStartHMButton);	
		//默认时间
		SubmitStartDataTextV.setText(year+"-"+(int)(month+1)+"-"+day);
		SubmitStartTimeTextV.setText(hour+"-"+minute);
		StartDataChangeButton.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DatePickerDialog dpd=new DatePickerDialog(TempActivity.this,datelistener,year,month,day);
                dpd.show();//显示DatePickerDialog组件
			}
		});	
		StartTimeChangeButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TimePickerDialog dpd=new TimePickerDialog(TempActivity.this,timelistener,hour,minute,true);
                dpd.show();//显示DatePickerDialog组件
			}
		});
	}
	DatePickerDialog.OnDateSetListener datelistener=new DatePickerDialog.OnDateSetListener()    {
		 @Override
		public void onDateSet(DatePicker view, int myyear, int monthOfYear,int dayOfMonth) {
			year=myyear;
            month=monthOfYear;
            day=dayOfMonth;
            //更新日期
            updateDate();
		}
		//当DatePickerDialog关闭时，更新日期显示
        private void updateDate()
        {
    		SubmitStartDataTextV.setText(year+"-"+(int)(month+1)+"-"+day);
        }
    };
    
    TimePickerDialog.OnTimeSetListener timelistener=new OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view,int hourOfDay,int minuteofDay) 
       {
           hour=hourOfDay;
           minute=minuteofDay;
           updateTime();
       } 
       //当TimePickerDialog关闭时，更新日期显示
        private void updateTime()
        {
        	SubmitStartTimeTextV.setText(hour+"-"+minute);
        }
    };
}
