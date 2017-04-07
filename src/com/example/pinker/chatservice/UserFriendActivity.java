package com.example.pinker.chatservice;

import com.example.pinker.R;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.content.Intent;
import android.os.Bundle;

public class UserFriendActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_friend);
		//∫√”—π‹¿Ì
        ((Button)findViewById(R.id.bt_back)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            	Intent it=new Intent(UserFriendActivity.this,ChatActivity.class);		
            	finish();
            	startActivity(it); 
            }
        });
	}

}
