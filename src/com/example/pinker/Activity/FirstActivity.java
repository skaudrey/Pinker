package com.example.pinker.Activity;

import java.util.*;

import com.example.pinker.R;
import com.example.pinker.safeProtect.StartProtect;
import com.example.pinker.safeProtect.TimeTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

public class FirstActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_first);
		
		//��������2�룬���û�е�¼�����¼���棻����Ѿ���¼�������ύƴ��Ҫ�����
		Timer timer = new Timer();
		TimerTask tt=new TimerTask(){
			public void run(){//LoginActivity
				Intent intent=new Intent(FirstActivity.this,LoginActivity.class);
				startActivity(intent);
				FirstActivity.this.finish();			
			}
		};
		timer.schedule(tt, 2000);
	}
}
