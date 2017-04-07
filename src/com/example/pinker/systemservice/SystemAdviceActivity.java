package com.example.pinker.systemservice;

import com.example.pinker.R;
import com.example.pinker.Activity.PkLocationMapActivity;
import com.example.pinker.chatservice.ChatActivity;

import android.support.v7.app.ActionBarActivity;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;
import android.widget.RatingBar.OnRatingBarChangeListener; 
import android.widget.Button;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.os.Bundle;

public class SystemAdviceActivity extends ActionBarActivity {

	private int grade;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.system_advice);
        RatingBar ratingBar = (RatingBar)findViewById(R.id.rb_rating); 
        ratingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener()  
        {  
            //当拖动条的滑块位置发生改变时触发该方法  
            @Override  
            public void onRatingChanged(RatingBar arg0  
                , float rating, boolean fromUser)  
            {  
                grade=(int)rating;
                //((EditText)findViewById(R.id.et_advice)).setText(grade);         
            }  
        });
        findViewById(R.id.bt_submit).setOnClickListener(new OnClickListener(){
        	@Override
    		public void onClick(View v) {
        		Toast.makeText(SystemAdviceActivity.this, "感谢您的评价！",
						Toast.LENGTH_SHORT).show();
        		finish();
        		//Intent it=new Intent(SystemAdviceActivity.this,MainActivity.class);
				//startActivity(it);
        	}
        });
        findViewById(R.id.bt_back).setOnClickListener(new OnClickListener(){
        	@Override
    		public void onClick(View v) {
        		Intent it=new Intent(SystemAdviceActivity.this,PkLocationMapActivity.class);
				startActivity(it);
				finish();
        	}
        });
	}
	//返回按钮重载 
	@Override
	  public boolean onKeyDown(int keyCode, KeyEvent event) {
	   // TODO Auto-generated method stub
	   
	    if(keyCode == KeyEvent.KEYCODE_BACK){
		   Intent it=new Intent(SystemAdviceActivity.this,PkLocationMapActivity.class);
		   startActivity(it);
		   finish();
	    }
	    return true;
	  }
}
