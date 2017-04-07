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
	        //����ͼ��  
	        notification.icon=R.drawable.ic_launcher;//����ͼ��  
	        //����ͼ�꼶����������ж��ͼ�꣬��qq������Ϣ�ȣ�ͼ�꼶�������Ϊ�������ݣ�Ĭ��Ϊ0  
	       notification.iconLevel=0;  
	        //������ʾ��Ϣ  
	        notification.tickerText="PINKER��ʾ";  
	        //ʱ���  
	        notification.when=System.currentTimeMillis();  
	        //������Ϣ������δ��������������������ͼ��֮ǰ��ʾ��Ĭ��Ϊ0,0�͸�������ʾ  
	        notification.number=0;  
	        notification.flags = Notification.FLAG_AUTO_CANCEL; // ��������ť�����Ϣ֪ͨ,
	        notification.defaults = Notification.DEFAULT_VIBRATE; // ��
	        //notification.flags |= Notification.FLAG_INSISTENT; // һֱ���У���������һֱ���ţ�֪���û���Ӧ
	        notification.defaults=Notification.DEFAULT_SOUND;  
	        //��������  
	        Context context=this;  
	        String contentTitle="PINKER��ʾ,��ȫ��ʾ,����ȷ��";  
	        String contentText="�����ڵ�һʱ������ϵ�˱�����Ϣ!ף�����!";  
	        //��תҳ��  
	        Intent intent = new Intent(Notify.this,ConfirmStatus.class);  //RegisterActivity.class ����������תҳ�棬������;ʵ�ʵ���ص���ͼ����;
	        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,PendingIntent.FLAG_ONE_SHOT);  
	        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
	        notification.setLatestEventInfo(context, contentTitle, contentText, pendingIntent);
  	        notificationManager.notify(0, notification); 
	 }
	
	//ɾ��֪ͨ    
    private void clearNotification(){
        // ������ɾ��֮ǰ���Ƕ����֪ͨ   
        NotificationManager notificationManager = (NotificationManager) this
                .getSystemService(NOTIFICATION_SERVICE);   
        notificationManager.cancel(0);   
    }

}
