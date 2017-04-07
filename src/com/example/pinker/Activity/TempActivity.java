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
	
	//��ʼʱ��	
		
		
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
		
		//��ȡ��ǰʱ��
		Calendar calendar= Calendar.getInstance();//���Զ�ÿ��ʱ���򵥶��޸�
		year = calendar.get(Calendar.YEAR); 
		month = calendar.get(Calendar.MONTH); 
		day = calendar.get(Calendar.DATE); 
		hour = calendar.get(Calendar.HOUR_OF_DAY); 
		minute = calendar.get(Calendar.MINUTE); 
		
		
		//��ʼʱ��
		SubmitStartDataTextV=(TextView)findViewById(R.id.SubmitStartDataTextV);
		StartDataChangeButton=(Button)findViewById(R.id.SubmitStartDataButton);	
		SubmitStartTimeTextV=(TextView)findViewById(R.id.SubmitStartHMTextV);
		StartTimeChangeButton=(Button)findViewById(R.id.SubmitStartHMButton);	
		//Ĭ��ʱ��
		SubmitStartDataTextV.setText(year+"-"+(int)(month+1)+"-"+day);
		SubmitStartTimeTextV.setText(hour+"-"+minute);
		StartDataChangeButton.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DatePickerDialog dpd=new DatePickerDialog(TempActivity.this,datelistener,year,month,day);
                dpd.show();//��ʾDatePickerDialog���
			}
		});	
		StartTimeChangeButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TimePickerDialog dpd=new TimePickerDialog(TempActivity.this,timelistener,hour,minute,true);
                dpd.show();//��ʾDatePickerDialog���
			}
		});
	}
	DatePickerDialog.OnDateSetListener datelistener=new DatePickerDialog.OnDateSetListener()    {
		 @Override
		public void onDateSet(DatePicker view, int myyear, int monthOfYear,int dayOfMonth) {
			year=myyear;
            month=monthOfYear;
            day=dayOfMonth;
            //��������
            updateDate();
		}
		//��DatePickerDialog�ر�ʱ������������ʾ
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
       //��TimePickerDialog�ر�ʱ������������ʾ
        private void updateTime()
        {
        	SubmitStartTimeTextV.setText(hour+"-"+minute);
        }
    };
}
