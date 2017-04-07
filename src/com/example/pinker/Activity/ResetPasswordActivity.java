package com.example.pinker.Activity;

import java.io.UnsupportedEncodingException;
import java.util.*;

import com.example.pinker.R;
import com.example.pinker.usermanage.ResetPasswordClient;

import android.telephony.SmsManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.os.Build;
import android.widget.*;

public class ResetPasswordActivity extends Activity{
	public Button bnSecCode,bnOK;
	private EditText editTextPhonenumber,editTextPassword,editTextPassword2,editTextSecCode;
	String phonenumber,password,password2,SecCode,result;
	int rand=0;
	boolean IsReset;
	private PkApplication PKApp; 
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_resetpassword);
		PKApp = (PkApplication) getApplication(); 
		editTextPhonenumber=(EditText)findViewById(R.id.ForgetPWTelEditT);
		editTextPassword=(EditText)findViewById(R.id.ForgetPWNewPWEditT);
		editTextPassword2=(EditText)findViewById(R.id.ForgetPWReInputEditT);
		editTextSecCode=(EditText)findViewById(R.id.ForgetPWSecCodeEditT);
		bnOK=(Button)findViewById(R.id.ForgetPWSubmitButton);
		bnSecCode=(Button)findViewById(R.id.ForgetPWGetSecCodeButton);
		//从文本框读
		phonenumber=editTextPhonenumber.getText().toString();
		password=editTextPassword.getText().toString();
		password2=editTextPassword2.getText().toString();
		//获取验证码
		bnSecCode.setOnClickListener(new OnClickListener(){
			public void onClick(View v){	
				phonenumber=editTextPhonenumber.getText().toString();
				 Random random = new Random();
				 rand=random.nextInt(899999)+100000;
				 SmsManager smsManager=SmsManager.getDefault();
				 smsManager.sendTextMessage(phonenumber, null, rand+"",null,null);				
			}
		});
		//密码
		editTextPassword.setOnFocusChangeListener(new OnFocusChangeListener() {  
			public void onFocusChange(View v, boolean hasFocus) {  
				if(hasFocus) {} else {
					 password=editTextPassword.getText().toString();
						 if(password.equals("")){
							 Toast.makeText(ResetPasswordActivity.this, "密码不能为空！", Toast.LENGTH_SHORT).show();
							 }
						}
				}
		});
				//密码2
		editTextPassword2.setOnFocusChangeListener(new OnFocusChangeListener() {  
				@Override  
					 public void onFocusChange(View v, boolean hasFocus) {  
						 if(hasFocus) { } else {
							 password2=editTextPassword2.getText().toString();
								  if(!password2.equals(password)){
								      Toast.makeText(ResetPasswordActivity.this, "两次输入密码不一致！", Toast.LENGTH_SHORT).show();
									 editTextPassword2.setText("");
							}
			    }
	    }
	 });
				//验证码
		editTextSecCode.setOnFocusChangeListener(new OnFocusChangeListener() {  
						 @Override  
						public void onFocusChange(View v, boolean hasFocus) {  
							if(hasFocus) { } else {
								SecCode=editTextSecCode.getText().toString();
								 if(SecCode.equals("")){
								 Toast.makeText(ResetPasswordActivity.this, "验证码输入错误！",Toast.LENGTH_SHORT).show();
		 }}}});
		//确认按钮点击事件			
		bnOK.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				SecCode=editTextSecCode.getText().toString();
				if(SecCode.equals(rand+"")){
					new Thread(runnable).start();
				}else{
					 Toast.makeText(ResetPasswordActivity.this, "验证码输入错误!", Toast.LENGTH_LONG).show();
				}
			}
		});	
		
	}
	Runnable runnable = new Runnable(){
	    @Override
	    public void run() {
		    try {
				result =ResetPasswordClient.queryStringForPost(phonenumber,password,PKApp.getHTTPSERVER_IP()+"resetPassword");
				String[] results=result.split(",");//用","隔开
				result = results[0];
				String isLogin=results[1];//输出0,1
				if(isLogin.equals("1")){IsReset=true;}else{IsReset=false;}					
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
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
	        Toast.makeText(ResetPasswordActivity.this, result, Toast.LENGTH_LONG).show();
	        if(IsReset==true){
	        	Intent intent=new Intent(ResetPasswordActivity.this,LoginActivity.class);
				startActivity(intent);
				ResetPasswordActivity.this.finish();
	        }else{}
	    }
	};

}
