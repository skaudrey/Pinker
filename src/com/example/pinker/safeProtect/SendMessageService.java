package com.example.pinker.safeProtect;

import com.baidu.mapapi.search.route.PlanNode;
import com.example.pinker.Activity.PkApplication;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class SendMessageService extends Service{
	PkApplication pkApplication;
	TimeOut timeOut;
	 @Override
	    public void onCreate() {
	        // TODO Auto-generated method stub
	        super.onCreate();
	        pkApplication=(PkApplication)getApplication();
	    }

	    @SuppressWarnings("deprecation")
		@Override
	    public void onStart(Intent intent, int startId) {
	        // TODO Auto-generated method stub
	        super.onStart(intent, startId);
	        timeOut=new TimeOut(pkApplication.getUSER_CONTACTPHONENO());
	        
//	        if(pkApplication.USER_NAME.equals("º«ÔÂç÷")){
//	        	timeOut=new TimeOut("15171458982");
//	        }
//	        else if(pkApplication.USER_NAME.equals("·ëíµ")){
//	        	timeOut=new TimeOut("13554003132");
//	        }
	    }

	    @Override
	    public void onDestroy() {
	        // TODO Auto-generated method stub
	    	timeOut.cancel();
	        super.onDestroy();
	    }

		@Override
		public IBinder onBind(Intent intent) {
			// TODO Auto-generated method stub
			return null;
		}
}
