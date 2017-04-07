package com.example.pinker.Activity;
import java.util.*;

import javax.crypto.spec.IvParameterSpec;

import com.example.pinker.R;
import com.example.pinker.UprequestClient.ResultUprequestList;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.os.Build;
import android.widget.*;
import android.widget.AdapterView.OnItemSelectedListener;

public class AccessableUserListActivity extends Activity{
	public ListView userslistview;
    //	PkApplication Pkapp;
	
	PkApplication pkApplication=null;
	private EditText ownStartpoint,ownEndPoint;
	public void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_accessableuserlist);
		pkApplication=(PkApplication)getApplication();
		final List<ResultUprequestList> resultList=pkApplication.getResultList();
		userslistview = (ListView) findViewById(R.id.AccessUserListV); 	
		int[] AccessUserImage=new int[]{R.drawable.title1,R.drawable.title2,R.drawable.title3,R.drawable.title4};
		/*String [] AccessUserName=new String[]{"iahgfioeo","B1","C1"};
		String [] AccessUserStartP=new String[]{"八一路","珞珈山路","广八路"};
		String [] AccessUserEndP= new String[]{"东湖","梨园","桂圆"};*/
		ownStartpoint=(EditText)findViewById(R.id.AccessOwnStartPEditT);
		ownEndPoint=(EditText)findViewById(R.id.AccessOwnEndPEditT);
		ownStartpoint.setText(pkApplication.getspointInfoCity()+pkApplication.getspointInfoStreet());
		ownEndPoint.setText(pkApplication.getepointInfoCity()+pkApplication.getepointInfoStreet());
		List<Map<String,Object>>listItems=new ArrayList<Map<String,Object>>(); 
		
		//通过for循环将图片id和列表项文字放到Map中，并添加到List集合中  
        for(int i=0;i<resultList.size();i++){  
            Map<String,Object>map=new HashMap<String,Object>(); //实例化map对象  
            map.put("AccessUserImage",AccessUserImage[i]);  
            map.put("AccessUserName",resultList.get(i).rusername);  
            map.put("AccessUserStartP","起点："+resultList.get(i).rstartcity+resultList.get(i).rstartstreet);
            map.put("AccessUserEndP", "终点："+resultList.get(i).rendcity+resultList.get(i).rendstreet);
            listItems.add(map);     //将map对象添加到List集合中  
        }  
        SimpleAdapter adapter=new SimpleAdapter(this,listItems,R.layout.activity_listviewitem,
        		new String[]{"AccessUserImage","AccessUserName","AccessUserStartP","AccessUserEndP"},
        		new int[]{R.id.AccessUserImageV,R.id.AccessUserNameTextV,R.id.AccessUserStartPTextV,
        		R.id.AccessUserEndPTextV});//创建SimpleAdapter  
        userslistview.setAdapter(adapter);   //将适配器与ListView关联  
        userslistview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
        	 public void onItemClick(AdapterView<?> parent, View view,int position,long id) {
        		 pkApplication.setresultitem((int)id);
        		 
        		 pkApplication.fStartPointCity=resultList.get((int)id).rstartcity;
        		 pkApplication.fStartPointStreets=resultList.get((int)id).rstartstreet;
        		 pkApplication.fEndPointCity=resultList.get((int)id).rendcity;
        		 pkApplication.fEndPointStreets=resultList.get((int)id).rendstreet;
        		 
        		 Intent intent=new Intent(AccessableUserListActivity.this,UserFriendMActivity.class);
        		 startActivity(intent);
        		 
        		
        	 }
        });
    
		
	}
}
