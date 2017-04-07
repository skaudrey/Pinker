package com.example.pinker.safeProtect;

import com.example.pinker.R;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.NotificationManager;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class Notify extends Activity {
	
	public Button buttonSafe;
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.safe_notify);
	        buttonSafe=(Button)findViewById(R.id.buttonSafe);
	        buttonSafe.setOnClickListener(new OnClickListener(){
				public void onClick(View v){
					Intent intent=new Intent(Notify.this,ConfirmStatus.class);
					startActivity(intent);
					Notify.this.finish();
				}		
			});	
	        
	        clearNotification();
	        startnotification();
	    }

	@SuppressWarnings("deprecation")
	public void startnotification(){
		  NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
	      Notification notification=new Notification();  
	      Intent intentSafeStatus = new Intent();  
	      intentSafeStatus.setAction("com.example.pinker.safeProtect.SendMessageService");
	      startService(intentSafeStatus);
	        //设置图标  
	        notification.icon=R.drawable.ic_launcher;//设置图标  
	        //设置图标级别，如标题栏有多个图标，如qq、短信息等，图标级别可以作为排序依据，默认为0  
	       notification.iconLevel=0;  
	        //设置提示信息  
	        notification.tickerText="PINKER提示";  
	        //时间戳  
	        notification.when=System.currentTimeMillis();  
	        //设置消息数，如未读短信数量，数量会在图标之前显示，默认为0,0和负数不显示  
	        notification.number=0;  
	        notification.flags = Notification.FLAG_AUTO_CANCEL; // 点击清除按钮清除消息通知,
	        notification.defaults = Notification.DEFAULT_VIBRATE; // 震动
	        //notification.flags |= Notification.FLAG_INSISTENT; // 一直进行，比如音乐一直播放，知道用户响应
	        notification.defaults=Notification.DEFAULT_SOUND;  
	        //其他属性  
	        Context context=this;  
	        String contentTitle="PINKER提示,安全提示,请点击确认";  
	        String contentText="否则将在第一时间向联系人报告消息!祝您愉快!";  
	        //跳转页面  
	        Intent intent = new Intent(Notify.this,ConfirmStatus.class);  //RegisterActivity.class 点击进入的跳转页面，测试用;实际点击回到地图界面;
	        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,PendingIntent.FLAG_ONE_SHOT);  
	        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
	        notification.setLatestEventInfo(context, contentTitle, contentText, pendingIntent);
  	        notificationManager.notify(0, notification); 
	 }
	
	//删除通知    
    private void clearNotification(){
        // 启动后删除之前我们定义的通知   
        NotificationManager notificationManager = (NotificationManager) this
                .getSystemService(NOTIFICATION_SERVICE);   
        notificationManager.cancel(0);   
    }

}
