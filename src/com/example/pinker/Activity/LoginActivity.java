package com.example.pinker.Activity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.example.pinker.R;
import com.example.pinker.chatservice.ChatActivity;
import com.example.pinker.fileservice.FileHelper;
import com.example.pinker.usermanage.FindUsernameAndPhonenumberAndUserIDClient;
import com.example.pinker.usermanage.LoginClient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity{

	//控件
	public EditText etusername;
	public EditText etpassword;
	public Button btlog,btreg;
	public CheckBox cbremped,cbautolog;
	//是否记住密码、自动登录
	boolean isautolog;
	boolean isrempwd;
	//用户信息
	String userid;
	String username;
	String password;
	String phoneno;
	FileHelper filehelper;

	String logresult=null;
	String result=null;
	boolean IsLogin;
	//accessStartEditT=(EditText)findViewById(R.id.accessStartEditT);
	private PkApplication PKApp;  
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		PKApp = (PkApplication) getApplication(); 
		try {
			filehelper = new FileHelper(LoginActivity.this);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			isautolog=filehelper.getCheckCondition();
			isrempwd =filehelper.getPwdCondition();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		btlog=(Button)findViewById(R.id.loginLoginButton);
		btreg=(Button)findViewById(R.id.loginRegisterButton);
		etusername=(EditText)findViewById(R.id.loginUsernameOrTelEditT);
		etpassword=(EditText)findViewById(R.id.loginPasswordEditT);
		cbremped = (CheckBox)findViewById(R.id.logrememberpwd);
		cbautolog= (CheckBox)findViewById(R.id.logautolog);
		try {
			username=filehelper.getUserItem(1);
			password=filehelper.getUserItem(2);
			phoneno =filehelper.getUserItem(3);
			if(username.equals("无")){
				username="";}
			etusername.setText(username);
			if(password.equals("无")){
				password="";
			}
			if(phoneno.equals("无")){
				phoneno="";
			}
			if(isrempwd){
				etpassword.setText(password);
				cbremped.setChecked(true);
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(isautolog){
			cbautolog.setChecked(true);
			new Thread(runnable).start();
		}
		//edittext监听
		//用户名
		etusername.setOnFocusChangeListener(new OnFocusChangeListener() {  
						    @Override  
						    public void onFocusChange(View v, boolean hasFocus) {  
						        if(hasFocus) {	        	
						    } else {
							    username=etusername.getText().toString();
							    if(username.equals("")){
								   Toast.makeText(LoginActivity.this, "用户名不能为空！",
										Toast.LENGTH_SHORT).show();
							       }
						        }
						    }
						});
				//密码
		etpassword.setOnFocusChangeListener(new OnFocusChangeListener() {  
								    @Override  
								    public void onFocusChange(View v, boolean hasFocus) {  
								        if(hasFocus) {	        	
								    } else {
									    password=etpassword.getText().toString();
									    if(password.equals("")){
										   Toast.makeText(LoginActivity.this, "密码不能为空！",
												Toast.LENGTH_SHORT).show();
									       }
								        }
								    }
								});
		//按钮响应函数
		btlog.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
			   new Thread(runnable).start();
		  }
		});
		btreg.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
				startActivity(intent);
				LoginActivity.this.finish();
			}		
		});
		((Button)findViewById(R.id.loginForgetPassWButton)).setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				Intent intent=new Intent(LoginActivity.this,ResetPasswordActivity.class);
				startActivity(intent);
				LoginActivity.this.finish();
			}		
		});
	}
	Runnable runnable = new Runnable(){
	    @Override
	    public void run() {
		    try {
		    	username=etusername.getText().toString();
		    	password=etpassword.getText().toString();
				String s1 = LoginClient.queryStringForPost(username,password,PKApp.getHTTPSERVER_IP()+"login");
				String[] st1=s1.split(",");//用","隔开
				logresult = st1[0];
				String isLogin=st1[1];//输出0,1
				if(isLogin.equals("1")){IsLogin=true;}else{IsLogin=false;}
				//获得ID
				if(IsLogin){
					String s2 = FindUsernameAndPhonenumberAndUserIDClient.queryStringForPost(username,PKApp.getHTTPSERVER_IP()+"findUsernameAndPhonenumberAndUserID");
					String[] st2=s2.split(",");//用","隔开
					username=st2[0];
					phoneno=st2[1];
					userid=st2[2];
					PKApp.setUSER_NAME(username);
					PKApp.setUSER_PASSWORD(password);
		        	PKApp.setUSER_PHONENO(phoneno);
		        	PKApp.setUSER_ID(userid);
		        	try {
						filehelper.saveUserFile(userid,username,password,
								phoneno,"","","","","","");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		    Message msg = new Message();
	        Bundle data = new Bundle();
	        data.putString("value","请求结果");
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
	        Log.i("mylog","请求结果-->" + val);	        
	        Toast.makeText(LoginActivity.this, logresult,
					Toast.LENGTH_SHORT).show();	        
	        if(IsLogin){
	        	//记住密码
				if(cbremped.isChecked()){
					try {
						filehelper.savePwdCondition("1");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				//自动登录
				if(cbautolog.isChecked()){
					try {
						filehelper.saveCheckCondition("1");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				Intent intent=new Intent(LoginActivity.this,PkLocationMapActivity.class);
	        	finish();
				startActivity(intent);
			}
	    }
	};
	
}
