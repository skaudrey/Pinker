package com.example.pinker.Activity;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.List;

import com.example.pinker.R;
import com.example.pinker.UprequestClient.ClientUprequest;
import com.example.pinker.UprequestClient.ResultUprequestList;
import com.example.pinker.usermanage.FindUsernameAndPhonenumberAndUserIDClient;
import com.example.pinker.usermanage.LoginClient;

import android.R.string;
import android.app.ActionBar.LayoutParams;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.os.Build;
import android.widget.*;
import android.widget.AdapterView.OnItemSelectedListener;


public class SubmitActivity extends Activity{	

	//��ʼ��
	private EditText startcity,startstreet,endcity,endstreet;
	//��ʼʱ��	
	private TextView SubmitStartDataTextV;
	private Button StartDataChangeButton;
	private TextView SubmitStartTimeTextV;
	private Button StartTimeChangeButton;
	private TextView SubmitEndDataTextV;
	private Button EndDataChangeButton;
	private TextView SubmitEndTimeTextV;
	private Button EndTimeChangeButton;
	public int year,month,day,hour,minute;
	private String yearString,monthString,dayString,hourString,minuteString; 
	private String startTime,endTime;
	//ʱ�䷶Χ
	private EditText startRange,endRange;
	//�Ա�radiogroup
    private RadioGroup RGgender;
	private RadioButton RBgender,RBgenderboth,RBgenderman,RBgenderwoman;	
	//����
	private EditText ag1,ag2;
	//ģʽ
	private RadioGroup RPattern;
	//ƫ��
	private EditText preference;
	//�ύ��ť
	private Button BntSubmit;

	//string������
	private String startcityStr,startstreetStr,endcityStr,endstreetStr;
	private String genderStr,age1Str,age2Str,preferenceStr;
	private int patternInt;
	//�Ƿ���ƴ��
	private boolean HasPinyou;
	PkApplication PKApp;
	public void onCreate(Bundle savedInstanceState) {
						
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_submit);
		PKApp = (PkApplication) getApplication(); 
		
		startcity=(EditText)findViewById(R.id.SubmitStartCityEditT);
		startstreet=(EditText)findViewById(R.id.SubmitStartStreetEditT);
		endcity=(EditText)findViewById(R.id.SubmitEndCityEditT);
		endstreet=(EditText)findViewById(R.id.SubmitEndStreetEditT);
		startRange=(EditText)findViewById(R.id.editTextStartRange);
		endRange=(EditText)findViewById(R.id.editTextEndRange);
		RGgender=(RadioGroup)findViewById(R.id.SubmitGenderRadioGroup);
		RBgenderboth=(RadioButton)findViewById(R.id.SubmitBothRadioButton);
		RBgenderman=(RadioButton)findViewById(R.id.SubmitManRadioButton);
		RBgenderwoman=(RadioButton)findViewById(R.id.SubmitWomanRadioButton);
		ag1=(EditText)findViewById(R.id.SubmitStartAgeEditT);
		ag2=(EditText)findViewById(R.id.SubmitEndAgeEditT);
		RPattern=(RadioGroup)findViewById(R.id.SubmitWayRadioG);
		preference=(EditText)findViewById(R.id.SubmitPreferenceEditT);
		startcity.setText("");
		
		
		initviews();
		
		startcity.setOnFocusChangeListener(new OnFocusChangeListener() {  
			   @Override  
			   public void onFocusChange(View v, boolean hasFocus) {  
				  if(hasFocus) {	        	
			      } else {
			    	  startcityStr=startcity.getText().toString();
			    	  if(startcityStr==null){
			    		  Toast.makeText(SubmitActivity.this, "��������У�",
									Toast.LENGTH_SHORT).show();
			    		  startcity.setText("");
			    	  }else{
			    		 PKApp.setspointInfoCity(startcityStr);
			    	  }
			      }
			    }
			  });
		startstreet.setOnFocusChangeListener(new OnFocusChangeListener() {  
			   @Override  
			   public void onFocusChange(View v, boolean hasFocus) {  
				  if(hasFocus) {	        	
			      } else {
			    	  startstreetStr=startstreet.getText().toString();
			    	  if(startstreetStr==null){
			    		  Toast.makeText(SubmitActivity.this, "������ֵ���",
									Toast.LENGTH_SHORT).show();
			    		  startstreet.setText("");
			    	  }else{
			    		 PKApp.setspointInfoStreet(startstreetStr);
			    	  }
			      }
			    }
			   });
		endcity.setOnFocusChangeListener(new OnFocusChangeListener() {  
			   @Override  
			   public void onFocusChange(View v, boolean hasFocus) {  
				  if(hasFocus) {	        	
			      } else {
			    	  endcityStr=endcity.getText().toString();
			    	  if(endcityStr==null){
			    		  Toast.makeText(SubmitActivity.this, "��������У�",
									Toast.LENGTH_SHORT).show();
			    		  endcity.setText("");
			    	  }else{
			    		 PKApp.setepointInfoCity(endcityStr);
			    	  }
			      }
			    }
			  });
		endstreet.setOnFocusChangeListener(new OnFocusChangeListener() {  
			   @Override  
			   public void onFocusChange(View v, boolean hasFocus) {  
				  if(hasFocus) {	        	
			      } else {
			    	  endstreetStr=endstreet.getText().toString();
			    	  if(endstreetStr==null){
			    		  Toast.makeText(SubmitActivity.this, "������ֵ���",
									Toast.LENGTH_SHORT).show();
			    		  endstreet.setText("");
			    	  }else{
			    		 PKApp.setepointInfoStreet(endstreetStr);
			    	  }
			      }
			    }
			   });
		//����Ա��ѡ��ֵ
		RGgender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {  
             
	            public void onCheckedChanged(RadioGroup group, int checkedId) {  	            	
	            	if(RBgenderboth.getId()==checkedId){
	            		genderStr=RBgenderboth.getText().toString();
	            	}else if(RBgenderman.getId()==checkedId){
	            		genderStr=RBgenderman.getText().toString();
	            	}else if(RBgenderwoman.getId()==checkedId){
	            		genderStr=RBgenderwoman.getText().toString();
	            	}
	            	PKApp.setgender(genderStr);
	            }
	        }); 
		//����Ҫ��
		ag1.setOnFocusChangeListener(new OnFocusChangeListener() {  
		   @Override  
		   public void onFocusChange(View v, boolean hasFocus) {  
			  if(hasFocus) {	        	
		      } else {
				   age1Str=ag1.getText().toString();
				   PKApp.setage1(age1Str);
		      }
		    }
		   });
		ag2.setOnFocusChangeListener(new OnFocusChangeListener() {  
			   @Override  
			   public void onFocusChange(View v, boolean hasFocus) {  
				  if(hasFocus) {	        	
			      } else {
			    	  age2Str=ag2.getText().toString();
			    	  if(Integer.parseInt(age2Str)<Integer.parseInt(age1Str)){
			    		  Toast.makeText(SubmitActivity.this, "������������",
									Toast.LENGTH_SHORT).show();
			    		  ag2.setText("");
			    	  }else{
			    		  PKApp.setage2(age2Str);
			    	  }
			      }
			    }
			   });
		//���ģʽ��ѡ��ֵ
		RPattern.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {  
            
	            public void onCheckedChanged(RadioGroup group, int checkedId) {  	            	
	            	if(findViewById(R.id.SubmitTimeRadioB).getId()==checkedId){
	            		patternInt=10;
	            	}else if(findViewById(R.id.SubmitPayRadioB).getId()==checkedId){
	            		patternInt=11;
	            	}else if(findViewById(R.id.SubmitDistanceRadioB).getId()==checkedId){
	            		patternInt=12;
	            	}else{
	            		patternInt=13;
	            	}
	            	PKApp.setpattern(patternInt);
	            }
	        });
		//ƫ��
		 preferenceStr=preference.getText().toString();
		 PKApp.setpreference(preferenceStr);
	   	  /*
		preference.setOnFocusChangeListener(new OnFocusChangeListener() {  
			   @Override  
			   public void onFocusChange(View v, boolean hasFocus) {  
				  if(hasFocus) {	        	
			      } else {
			    	  preferenceStr=preference.getText().toString();
			    	  if(preferenceStr!=null){
			    		  PKApp.setpreference(preferenceStr);
			    	  }else{
			    		  preferenceStr="";
			    	  }
			      }
			    }
			   });*/
		
		//mapѡ��ʼ��
		((Button)findViewById(R.id.SubmitStartMapButton)).setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				Intent it=new Intent(SubmitActivity.this,PkSetStartPointActivity.class);
				startActivity(it);
				SubmitActivity.this.finish();
			}
		});
		//ѡ���յ�
		((Button)findViewById(R.id.SubmitEndMapButton)).setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				Intent it=new Intent(SubmitActivity.this,PkSetEndPointActivity.class);
				startActivity(it);
				SubmitActivity.this.finish();
			}
		});
		//�ύ��ť��ת
		BntSubmit=(Button)findViewById(R.id.SubmitSubmitButton);
		BntSubmit.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				
				PKApp.spointInfoCity=startcityStr;
				PKApp.spointInfoStreet=startstreetStr;
				PKApp.epointInfoCity=endcityStr;
				PKApp.epointInfoStreet=endstreetStr;
				
				new Thread(runnable).start();		
			}
		});
		//��ȡ��ǰʱ��
				Calendar c = Calendar.getInstance();//���Զ�ÿ��ʱ���򵥶��޸�
				year = c.get(Calendar.YEAR); 
				month = c.get(Calendar.MONTH); 
				day = c.get(Calendar.DATE); 
				hour = c.get(Calendar.HOUR_OF_DAY); 
				minute = c.get(Calendar.MINUTE); 
				
				
				//��ʼʱ��
				SubmitStartDataTextV=(TextView)findViewById(R.id.SubmitStartDataTextV);
				StartDataChangeButton=(Button)findViewById(R.id.SubmitStartDataButton);	
				SubmitStartTimeTextV=(TextView)findViewById(R.id.SubmitStartHMTextV);
				StartTimeChangeButton=(Button)findViewById(R.id.SubmitStartHMButton);	
								
				//Ĭ��ʱ��
				SubmitStartDataTextV.setText(year+"-"+(int)(month+1)+"-"+day);
				SubmitStartTimeTextV.setText(hour+":"+minute);
				
				StartDataChangeButton.setOnClickListener(new OnClickListener() {					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						new Thread(runnablestartDate).start();
					}
				});	
				StartTimeChangeButton.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						new Thread(runnablestartTime).start();
					}
				});

				//��ֹʱ��
				SubmitEndDataTextV=(TextView)findViewById(R.id.SubmitEndDataTextV);
				EndDataChangeButton=(Button)findViewById(R.id.SubmitEndDataButton);	
				SubmitEndTimeTextV=(TextView)findViewById(R.id.SubmitEndHMTextV);
				EndTimeChangeButton=(Button)findViewById(R.id.SubmitEndHMButton);	
				//Ĭ��ʱ��
				SubmitEndDataTextV.setText(year+"-"+(int)(month+1)+"-"+day);
				SubmitEndTimeTextV.setText(hour+":"+minute);
				EndDataChangeButton.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						new Thread(runnableendDate).start();	
					}
				});	
				EndTimeChangeButton.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						new Thread(runnableendTime).start();	
					}
				});	
				
	}	
	private void initviews() {
		if(PKApp.getspointInfoCity()!=null && PKApp.getspointInfoStreet()!=null){
			startcity.setText(PKApp.getspointInfoCity());
			startstreet.setText(PKApp.getspointInfoStreet());
		}
		if(PKApp.getepointInfoCity()!=null && PKApp.getepointInfoStreet()!=null){
			endcity.setText(PKApp.getepointInfoCity());
			endstreet.setText(PKApp.getepointInfoStreet());
		}
		
		if(PKApp.getgender()!=null){
			if(PKApp.getgender().equals("��")){
				RGgender.check(RBgenderman.getId()); 
			}if(PKApp.getgender().equals("Ů")){
				RGgender.check(RBgenderwoman.getId()); 
			}else{
				RGgender.check(RBgenderboth.getId());
			}
		}
		if(PKApp.getage1()!=null){
			ag1.setText(PKApp.getage1());
		}
		if(PKApp.getage2()!=null){
			ag1.setText(PKApp.getage2());
		}
		
		if(PKApp.getpattern()!=0){
			if(PKApp.getpattern()==11){
	        	RPattern.check(findViewById(R.id.SubmitPayRadioB).getId()); 
			}if(PKApp.getpattern()==12){
				RPattern.check(findViewById(R.id.SubmitDistanceRadioB).getId()); 
			}if(PKApp.getpattern()==13){
				RPattern.check(findViewById(R.id.SubmitCrowdRadioB).getId()); 
			}else{
				RPattern.check(findViewById(R.id.SubmitTimeRadioB).getId()); 
			}	
		}else{
			RPattern.check(findViewById(R.id.SubmitTimeRadioB).getId()); 
		}
		if(PKApp.getpreference()!=null){
			preference.setText(PKApp.getpreference());
		}
	}

	Runnable runnable = new Runnable(){
	    @Override
	    public void run() {
		    try {
		    	/*
		    	 * 
		    	 */
		    	List<ResultUprequestList> resultList=null;
		    	String startRangeStr=startRange.getText().toString();
		    	String endRangeStr=endRange.getText().toString();
		        preferenceStr=preference.getText().toString();
		    	//startTime=PKApp.getyear1()+PKApp.getmonth1()+PKApp.getday1()+PKApp.gethour1()+PKApp.getminute1();
		    	//endTime=PKApp.getyear2()+PKApp.getmonth2()+PKApp.getday2()+PKApp.gethour2()+PKApp.getminute2();
		        try {
					if(PKApp.getUSER_NAME().equals("������")){
				        startTime="201501210900";
				        endTime="201501211000";
					}else{
				        startTime="201501210915";
				        endTime="201501211015";
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		    	String up_request=PKApp.getUSER_ID()+","+startTime+","+startRangeStr+","+endTime+","+endRangeStr+","
        		                   +PKApp.getgender()+","+PKApp.getage1()+","+PKApp.getage2()+","+preferenceStr;
				String up_requestpoints=PKApp.getUSER_ID()+","+PKApp.getStartPointLat()+","+PKApp.getStartPointLng()+","+
        		                   PKApp.getspointInfoCity()+","+PKApp.getspointInfoStreet()+","+
						           PKApp.getEndPointLat()+","+PKApp.getEndPointLng()+","+
        		                   PKApp.getepointInfoCity()+","+PKApp.getepointInfoStreet();
				//����
		    	String s1 = ClientUprequest.queryStringForPost
						(up_request,up_requestpoints,PKApp.getHTTPSERVER_IP()+"uprequest");
				String result[]=s1.split("\n");
				if(result[0].equals("0")){
					HasPinyou=false;
				}else{
					HasPinyou=true;
					resultList=ClientUprequest.getresultlist(result[1]);
					PKApp.setResultList(resultList);
				}
				
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		    Message msg = new Message();
	        Bundle data = new Bundle();
	        data.putString("value","������");
	        msg.setData(data);
	        handler.sendMessage(msg);
	    }
	};
	
	
	Handler handler = new Handler(){
	    @Override
	    public void handleMessage(Message msg) {
	        super.handleMessage(msg);
	        Bundle data = msg.getData();
	        String val = data.getString("value");
	        Log.i("mylog","������-->" + val);        
	        if(HasPinyou){
				Intent intent=new Intent(SubmitActivity.this,AccessableUserListActivity.class);
	        	finish();
				startActivity(intent);
			}else{
				Toast.makeText(SubmitActivity.this, "�Բ���δ�����ʺ�����ƴ�ѣ����޸��������ٴ��ύ��",
						Toast.LENGTH_SHORT).show();	   
				Intent intent=getIntent();
				intent.setAction("com.baidu.location.f");
				startService(intent);
			}
	    }
	};

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
    
    DatePickerDialog.OnDateSetListener datelistenerEnd=new DatePickerDialog.OnDateSetListener()    {
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
   		SubmitEndDataTextV.setText(year+"-"+(int)(month+1)+"-"+day);
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
        	SubmitStartTimeTextV.setText(hour+":"+minute);
        }
    };
    
    TimePickerDialog.OnTimeSetListener timelistenerEnd=new OnTimeSetListener() {
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
        	SubmitEndTimeTextV.setText(hour+":"+minute);
        }
    };
    //ʱ�����ڼ����߳�
    Runnable runnablestartDate=new Runnable() {
		public void run() {
			Message msg = new Message();
	        Bundle data = new Bundle();
	        data.putString("value","������");
	        msg.setData(data);
	        handlerstartDate.sendMessage(msg);
	        yearString=year+"";
			if(month<10){monthString="0"+month;}else{monthString=month+"";}
			if(day<10){dayString="0"+day;}else{dayString=day+"";}
			PKApp.setyear1(yearString);
			PKApp.setmonth1(monthString);
			PKApp.setday1(dayString);
		}
	};
	Handler handlerstartDate = new Handler(){
	    @Override
	    public void handleMessage(Message msg) {
	        super.handleMessage(msg);
	        Bundle data = msg.getData();
	        String val = data.getString("value");
	        Log.i("mylog","������-->" + val);        
	        DatePickerDialog dpd=new DatePickerDialog(SubmitActivity.this,datelistener,year,month,day);
            dpd.show();//��ʾDatePickerDialog���          
	    }
	};
	Runnable runnablestartTime=new Runnable() {
		public void run() {
			Message msg = new Message();
	        Bundle data = new Bundle();
	        data.putString("value","������");
	        msg.setData(data);
	        handlerstartTime.sendMessage(msg);
            if(hour<10){hourString="0"+hour;}else{hourString=hour+"";}
			if(minute<10){minuteString="0"+minute;}else{minuteString=minute+"";}
			PKApp.sethour1(hourString);
			PKApp.setminute1(minuteString);
		}
	};
	Handler handlerstartTime = new Handler(){
	    @Override
	    public void handleMessage(Message msg) {
	        super.handleMessage(msg);
	        Bundle data = msg.getData();
	        String val = data.getString("value");
	        Log.i("mylog","������-->" + val);        
	        TimePickerDialog tpd=new TimePickerDialog(SubmitActivity.this,timelistener,hour,minute,true);
            tpd.show();//��ʾDatePickerDialog���
	    }
	};
	Runnable runnableendDate=new Runnable() {
		public void run() {
			Message msg = new Message();
	        Bundle data = new Bundle();
	        data.putString("value","������");
	        msg.setData(data);
	        handlerendDate.sendMessage(msg);
            yearString=year+"";
			if(month<10){monthString="0"+month;}else{monthString=month+"";}
			if(day<10){dayString="0"+day;}else{dayString=day+"";}
			PKApp.setyear2(yearString);
			PKApp.setmonth2(monthString);
			PKApp.setday2(dayString);
		}
	};
	Handler handlerendDate = new Handler(){
	    @Override
	    public void handleMessage(Message msg) {
	        super.handleMessage(msg);
	        Bundle data = msg.getData();
	        String val = data.getString("value");
	        Log.i("mylog","������-->" + val);        
	        DatePickerDialog dpd=new DatePickerDialog(SubmitActivity.this,datelistenerEnd,year,month,day);
            dpd.show();//��ʾDatePickerDialog���
	    }
	};
	Runnable runnableendTime=new Runnable() {
		public void run() {
			Message msg = new Message();
	        Bundle data = new Bundle();
	        data.putString("value","������");
	        msg.setData(data);
	        handlerendTime.sendMessage(msg);
			if(hour<10){hourString="0"+hour;}else{hourString=hour+"";}
			if(minute<10){minuteString="0"+minute;}else{minuteString=minute+"";}
			PKApp.sethour2(hourString);
			PKApp.setminute2(minuteString);
		}
	};
	Handler handlerendTime = new Handler(){
	    @Override
	    public void handleMessage(Message msg) {
	        super.handleMessage(msg);
	        Bundle data = msg.getData();
	        String val = data.getString("value");
	        Log.i("mylog","������-->" + val);        
	        TimePickerDialog tpd=new TimePickerDialog(SubmitActivity.this,timelistenerEnd,hour,minute,true);
            tpd.show();//��ʾDatePickerDialog���
	    }
	};
}
