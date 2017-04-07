package com.example.pinker.systemservice;

import java.util.ArrayList;
import java.util.List;

import com.example.pinker.R;

import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.os.Bundle;

public class SystemPeopleActivity extends ActionBarActivity {


	public List<SystemPeople> listpeople = new ArrayList<SystemPeople>();
	private SystemPeopleAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.system_people);
        initviews();
	
		findViewById(R.id.bt_back).setOnClickListener(new OnClickListener(){
        	@Override
    		public void onClick(View v) {
        		finish();
        	}
        });
		
		
	}
	
	  
	  public void initviews(){
		  ListView listview = (ListView) findViewById(R.id.listview);
		  adapter = new SystemPeopleAdapter(getApplicationContext(), listpeople);
		  listview.setAdapter(adapter);
	  }
	  @Override
	  public boolean onKeyDown(int keyCode, KeyEvent event) {
	   // TODO Auto-generated method stub
	   
	    if(keyCode == KeyEvent.KEYCODE_BACK){
		   finish();
	    }
	    return true;
	  }
}
