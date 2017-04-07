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
	 
	 //��λ
	 private LocationClient mLocationClient;
	    @Override  
	    protected void onCreate(Bundle savedInstanceState) {  
	        super.onCreate(savedInstanceState);
	        
	        timer = new Timer();
	        
	        pkapp=(PkApplication)getApplication();

	        phoneno=pkapp.getUSER_CONTACTPHONENO();
	        
	        time=pkapp.gettimeinteval();
	        isEndPoint=pkapp.getisarrive();
	        //start timer task��������ʱ��
	        contorl(isEndPoint, time);
	        
	        //��λ���
//	        mLocationClient = ((PkApplication)getApplication()).mLocationClient;
//			InitLocation();
//			mLocationClient.start();
	    }  
	    
	   @Override  
	    protected void onDestroy() {
	        super.onDestroy();  
	        // cancel timer  //�رն�ʱ��
	        timer.cancel();  
	        //ֹͣʵʱ��λ
//	        mLocationClient.stop();
	    }  
	
	    java.util.TimerTask timerTask = new  java.util.TimerTask(){public void run() {  
         Message message = new Message();  
         message.what = 1;  
         doActionHandler.sendMessage(message);  
     }  } ; 

		
	 // ִ�ж���  
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
    	 //isEndPoint:�Ƿ�����ȫ���Ĺ��ܣ����û����ƣ�time�����û�ѡ�����೤ʱ�����ͣ���λ�Ƿ���
    	 int timems=time*60*1000;
    	 if(isEndPoint==false){
    			 timer.schedule(timerTask, 0, timems); //ѭ������
    			//ֹͣ��ʱ��  			
    			 //TimeOut timeOut=new TimeOut();
    			// timeOut.timeout();
    			 //SendMessage sendMessage=new SendMessage("13554003132","Σ��!");
    			 }
    	 else{
    		 //ֹͣ��ʱ��
    		 timer.cancel();  
    	 }
    	 }

     public String getPhoneno(){
    	 return phoneno;
     }
     
     private void InitLocation(){
 		LocationClientOption option = new LocationClientOption();
 		option.setOpenGps(true);
 		option.setAddrType("all");//���صĶ�λ���������ַ��Ϣ
 		option.setCoorType("bd09ll");//���صĶ�λ��Ϣ�ǰٶȾ�γ�ȣ�Ĭ��ֵ��gcj02
 		
 		option.setScanSpan(5000);//���÷���λ�����ʱ����Ϊ5000ms
 		option.disableCache(true);//��ֹ���û��涨λ
 		option.setPoiNumber(5);	//��෵��poi����
 		option.setPoiDistance(1000); //poi��ѯ���릻	
 		mLocationClient.setLocOption(option);
 	}
}
