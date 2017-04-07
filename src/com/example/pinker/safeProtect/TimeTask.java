package com.example.pinker.safeProtect;

import java.util.Timer;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.pinker.Activity.PkApplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.StaticLayout;


public class TimeTask extends Activity{
	
	 PkApplication pkapp;
	 private Timer timer;  
	
	 private boolean isEndPoint;//test
	 private int time;
	 String phoneno;
	 
	 //定位
	 private LocationClient mLocationClient;
	    @Override  
	    protected void onCreate(Bundle savedInstanceState) {  
	        super.onCreate(savedInstanceState);
	        
	        timer = new Timer();
	        
	        pkapp=(PkApplication)getApplication();

	        phoneno=pkapp.getUSER_CONTACTPHONENO();
	        
	        time=pkapp.gettimeinteval();
	        isEndPoint=pkapp.getisarrive();
	        //start timer task：开启定时器
	        contorl(isEndPoint, time);
	        
	        //定位相关
//	        mLocationClient = ((PkApplication)getApplication()).mLocationClient;
//			InitLocation();
//			mLocationClient.start();
	    }  
	    
	   @Override  
	    protected void onDestroy() {
	        super.onDestroy();  
	        // cancel timer  //关闭定时器
	        timer.cancel();  
	        //停止实时定位
//	        mLocationClient.stop();
	    }  
	
	    java.util.TimerTask timerTask = new  java.util.TimerTask(){public void run() {  
         Message message = new Message();  
         message.what = 1;  
         doActionHandler.sendMessage(message);  
     }  } ; 

		
	 // 执行动作  
	    private Handler doActionHandler = new Handler() {  
	        @Override  
	        public void handleMessage(Message msg) {  
	            super.handleMessage(msg);  
	            int msgId = msg.what;  
	            switch (msgId) {  
	                case 1:  
	                	if(isEndPoint==false){
	                		Intent intent=new Intent(TimeTask.this,Notify.class);
		                	intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		    				startActivity(intent);
	                	}
	                    break;  
	                default:  
	                    break;  
	            }  
	        }  
	    }; 
	 
	
     public void contorl(boolean isEndPoint,int time){
    	 //isEndPoint:是否开启安全监测的功能，由用户控制；time：由用户选择间隔多长时间推送，单位是分钟
    	 int timems=time*60*1000;
    	 if(isEndPoint==false){
    			 timer.schedule(timerTask, 0, timems); //循环控制
    			//停止定时器  			
    			 //TimeOut timeOut=new TimeOut();
    			// timeOut.timeout();
    			 //SendMessage sendMessage=new SendMessage("13554003132","危险!");
    			 }
    	 else{
    		 //停止定时器
    		 timer.cancel();  
    	 }
    	 }

     public String getPhoneno(){
    	 return phoneno;
     }
     
     private void InitLocation(){
 		LocationClientOption option = new LocationClientOption();
 		option.setOpenGps(true);
 		option.setAddrType("all");//返回的定位结果包含地址信息
 		option.setCoorType("bd09ll");//返回的定位信息是百度经纬度，默认值是gcj02
 		
 		option.setScanSpan(5000);//设置发起定位请求的时间间隔为5000ms
 		option.disableCache(true);//禁止启用缓存定位
 		option.setPoiNumber(5);	//最多返回poi个数
 		option.setPoiDistance(1000); //poi查询距离	
 		mLocationClient.setLocOption(option);
 	}
}
