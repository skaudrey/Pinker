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
		//���ı����
		phonenumber=editTextPhonenumber.getText().toString();
		password=editTextPassword.getText().toString();
		password2=editTextPassword2.getText().toString();
		//��ȡ��֤��
		bnSecCode.setOnClickListener(new OnClickListener(){
			public void onClick(View v){	
				phonenumber=editTextPhonenumber.getText().toString();
				 Random random = new Random();
				 rand=random.nextInt(899999)+100000;
				 SmsManager smsManager=SmsManager.getDefault();
				 smsManager.sendTextMessage(phonenumber, null, rand+"",null,null);				
			}
		});
		//����
		editTextPassword.setOnFocusChangeListener(new OnFocusChangeListener() {  
			public void onFocusChange(View v, boolean hasFocus) {  
				if(hasFocus) {} else {
					 password=editTextPassword.getText().toString();
						 if(password.equals("")){
							 Toast.makeText(ResetPasswordActivity.this, "���벻��Ϊ�գ�", Toast.LENGTH_SHORT).show();
							 }
						}
				}
		});
				//����2
		editTextPassword2.setOnFocusChangeListener(new OnFocusChangeListener() {  
				@Override  
					 public void onFocusChange(View v, boolean hasFocus) {  
						 if(hasFocus) { } else {
							 password2=editTextPassword2.getText().toString();
								  if(!password2.equals(password)){
								      Toast.makeText(ResetPasswordActivity.this, "�����������벻һ�£�", Toast.LENGTH_SHORT).show();
									 editTextPassword2.setText("");
							}
			    }
	    }
	 });
				//��֤��
		editTextSecCode.setOnFocusChangeListener(new OnFocusChangeListener() {  
						 @Override  
						public void onFocusChange(View v, boolean hasFocus) {  
							if(hasFocus) { } else {
								SecCode=editTextSecCode.getText().toString();
								 if(SecCode.equals("")){
								 Toast.makeText(ResetPasswordActivity.this, "��֤���������",Toast.LENGTH_SHORT).show();
		 }}}});
		//ȷ�ϰ�ť����¼�			
		bnOK.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				SecCode=editTextSecCode.getText().toString();
				if(SecCode.equals(rand+"")){
					new Thread(runnable).start();
				}else{
					 Toast.makeText(ResetPasswordActivity.this, "��֤���������!", Toast.LENGTH_LONG).show();
				}
			}
		});	
		
	}
	Runnable runnable = new Runnable(){
	    @Override
	    public void run() {
		    try {
				result =ResetPasswordClient.queryStringForPost(phonenumber,password,PKApp.getHTTPSERVER_IP()+"resetPassword");
				String[] results=result.split(",");//��","����
				result = results[0];
				String isLogin=results[1];//���0,1
				if(isLogin.equals("1")){IsReset=true;}else{IsReset=false;}					
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    Message msg = new Message();
	        Bundle data = new Bundle();
	        data.putString("value","������");
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
	        Log.i("mylog","������-->" + val);
	        Toast.makeText(ResetPasswordActivity.this, result, Toast.LENGTH_LONG).show();
	        if(IsReset==true){
	        	Intent intent=new Intent(ResetPasswordActivity.this,LoginActivity.class);
				startActivity(intent);
				ResetPasswordActivity.this.finish();
	        }else{}
	    }
	};

}
