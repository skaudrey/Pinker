package com.example.pinker.Activity;
import com.example.pinker.R;
import com.example.pinker.UprequestClient.ResultUprequestList;
import com.example.pinker.chatservice.ChatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


public class UserFriendMActivity extends Activity{
	TextView friendname,friendstarttime,friendendtime,friendgender,friendage,friendsignature,friendStartCity,friendStartStreet,friendEndCity,friendEndStreet;;
	PkApplication pkApp;
	ResultUprequestList friendresult;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_userfriendm);
		
		pkApp=(PkApplication)getApplication();
		
		friendresult=pkApp.getResultList().get(pkApp.getresultitem());
	    friendname=(TextView)findViewById(R.id.friendname);
	    friendstarttime=(TextView)findViewById(R.id.friendstarttime);
	    friendendtime=(TextView)findViewById(R.id.friendendtime);
	    friendgender=(TextView)findViewById(R.id.friendgender);
	    friendage=(TextView)findViewById(R.id.friendage);
	    friendsignature=(TextView)findViewById(R.id.friendsignature);
	    friendStartCity=(TextView)findViewById(R.id.UserFriendStartCityTextV);
	    friendStartStreet=(TextView)findViewById(R.id.UserFriendStartStreetTextV);
	    friendEndCity=(TextView)findViewById(R.id.UserFriendEndCityTextV);
	    friendEndStreet=(TextView)findViewById(R.id.UserFriendEndStreetEditT);
	    
	    initviews();
	    
	    //Ω¯»Î¡ƒÃÏ
        ((Button)findViewById(R.id.UserFriendMessageButton)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            	pkApp.addFriend(friendname.getText().toString());
            	pkApp.setFRIEND(friendname.getText().toString());
            	Intent intent=new Intent(UserFriendMActivity.this,ChatActivity.class);
            	UserFriendMActivity.this.finish();
        		startActivity(intent);
            }
        });
	    		
	}
	void initviews(){
		friendname.setText(friendresult.rusername);
		friendstarttime.setText(pkApp.getdate(friendresult.rsttime1)+" "+pkApp.gettime(friendresult.rsttime1)
				+"\n"+pkApp.getdate(friendresult.rsttime2)+" "+pkApp.gettime(friendresult.rsttime2));
		friendendtime.setText(pkApp.getdate(friendresult.redtime1)+" "+pkApp.gettime(friendresult.redtime1)
				+"\n"+pkApp.getdate(friendresult.redtime2)+" "+pkApp.gettime(friendresult.redtime2));
		friendage.setText(friendresult.rage+"");
		friendgender.setText(friendresult.rgender);
		friendsignature.setText(friendresult.rsignature);
		friendStartCity.setText(friendresult.rstartcity);
		friendStartStreet.setText(friendresult.rstartstreet);
		friendEndCity.setText(friendresult.rendcity);
		friendEndStreet.setText(friendresult.rendstreet);
	}
}
