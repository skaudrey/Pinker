package com.example.pinker.Activity;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.app.Application;
import android.app.Service;
import android.os.Vibrator;
import android.util.Log;
import android.widget.TextView;















import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.example.pinker.UprequestClient.ResultUprequestList;
import com.example.pinker.fileservice.FileHelper;
import com.example.pinker.safeProtect.TimeOut;


public class PkApplication extends Application {

	//定位相关
	public LocationClient mLocationClient;
	
	public MyLocationListener mMyLocationListener;
	
		
	public TextView mLocationResult,logMsg;
	public Vibrator mVibrator;
	public boolean isArrivedDestination=false;//判断是否到达目的地--与目的地距离100米以内认为到达
	@Override
	public void onCreate() {
		super.onCreate();
		// 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
		SDKInitializer.initialize(this);
		
		mLocationClient = new LocationClient(this.getApplicationContext());
		mMyLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mMyLocationListener);
				
		
		mVibrator =(Vibrator)getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
				
	}
	
	/**
	 * 实现实位回调监听
	 * 定位信息在主程序中记录
	 */
	public class MyLocationListener implements BDLocationListener {

		public void onReceiveLocation(BDLocation location) {
			/**
			 * 计算当前位置与目标点距离并判断是否到达
			 */
			double distance=DistanceUtil. getDistance(
					new LatLng(location.getLatitude(), 
							location.getLongitude()), 
							new LatLng(30.53400,
									114.363525));
			if(distance<100){
				isArrivedDestination=true;								
			}			
			/**
			 * 到达之后停止实时定位
			 */			
			if(isArrivedDestination){
				mLocationClient.stop();
			}
		}
		/**
		 * 显示请求字符串
		 * @param str
		 */
		public void logMsg(String str) {
			try {
				if (mLocationResult != null)
					mLocationResult.setText(str);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void onReceivePoi(BDLocation arg0) {}
}
    
	public String USER_ID;
	public String getUSER_ID() {  
		 return USER_ID;  
   }  
	public void setUSER_ID(String USER_ID) {  
		 this.USER_ID = USER_ID;  
   }
	public String USER_NAME;
	public String getUSER_NAME() throws Exception {  
		return USER_NAME;
   }  
	public void setUSER_NAME(String USER_NAME) {  
		 this.USER_NAME = USER_NAME;  
   }
	public String USER_AGE="";
	public String getUSER_AGE() {  
		 return USER_AGE;  
   }  
	public void setUSER_AGE(String USER_AGE) {  
		 this.USER_AGE = USER_AGE;  
   }
	public String USER_PHONENO;
	public String getUSER_PHONENO() throws Exception { 
		return USER_PHONENO;  
   }  
	public void setUSER_PHONENO(String USER_PHONENO) {  
		 this.USER_PHONENO = USER_PHONENO;  
   }
	public String USER_PASSWORD;
	public String getUSER_PASSWORD() {  
		 return USER_PASSWORD;  
   }  
	public void setUSER_PASSWORD(String USER_PASSWORD) {  
		 this.USER_PASSWORD = USER_PASSWORD;  
   }
	public String USER_GENDER;
	public String getUSER_GENDER() {  
		 return USER_GENDER;  
   }  
	public void setUSER_GENDER(String USER_GENDER) {  
		 this.USER_GENDER = USER_GENDER;  
   }
	public String USER_CONTACTPHONENO="";
	public String getUSER_CONTACTPHONENO() {  
		 return USER_CONTACTPHONENO;  
   }  
	public void setUSER_CONTACTPHONENO(String USER_CONTACTPHONENO) {  
		 this.USER_CONTACTPHONENO = USER_CONTACTPHONENO;  
   }
	public String USER_SIGNATURE="";
	public String getUSER_SIGNATURE() {  
		 return USER_SIGNATURE;  
   }  
	public void setUSER_SIGNATURE(String USER_SIGNATURE) {  
		 this.USER_SIGNATURE = USER_SIGNATURE;  
   }
	public String currentLocCity;
	public String getcurrentLocCity() {  
		 return currentLocCity;  
   }  
	public void setcurrentLocCity(String currentLocCity) {  
		 this.currentLocCity = currentLocCity;  
   }
	public String currentLocStreet;
	public String getcurrentLocStreet() {  
		 return currentLocStreet;  
   }  
	public void setcurrentLocStreet(String currentLocStreet) {  
		 this.currentLocStreet = currentLocStreet;  
   }
	public double currentLng=500.0;
	public double getcurrentLng() {  
		 return currentLng;  
   }  
	public void setcurrentLng(double currentLng) {  
		 this.currentLng = currentLng;  
   }
	public double currentLat=100.0;
	public double getcurrentLat() {  
		 return currentLat;  
   }  
	public void setcurrentLat(double currentLat) {  
		 this.currentLat = currentLat;  
   }
	//startpoint
    public double StartPointLat=100.0;
	public double getStartPointLat() {  
		 return StartPointLat;  
   }  
	public void setStartPointLat(double StartPointLat) {  
		 this.StartPointLat = StartPointLat;  
   }
	public double StartPointLng=500.0;
	public double getStartPointLng() {  
		 return StartPointLng;  
   }  
	public void setStartPointLng(double StartPointLng) {  
		 this.StartPointLng = StartPointLng;  
   }
	public String spointInfoCity=null;
	public String getspointInfoCity() {  
		 return spointInfoCity;  
   }  
	public void setspointInfoCity(String spointInfoCity) {  
		 this.spointInfoCity = spointInfoCity;  
   }
	public String spointInfoStreet=null;
	public String getspointInfoStreet() {  
		 return spointInfoStreet;  
   }  
	public void setspointInfoStreet(String spointInfoStreet) {  
		 this.spointInfoStreet = spointInfoStreet;  
   }
	
	//endpoint
	public double EndPointLat=100.0;
	public double getEndPointLat() {  
		 return EndPointLat;  
   }  
	public void setEndPointLat(double EndPointLat) {  
		 this.EndPointLat = EndPointLat;  
   }
	public double EndPointLng=500.0;
	public double getEndPointLng() {  
		 return EndPointLng;  
   }  
	public void setEndPointLng(double EndPointLng) {  
		 this.EndPointLng = EndPointLng;  
   }
	public String epointInfoCity=null;
	public String getepointInfoCity() {  
		 return epointInfoCity;  
   }  
	public void setepointInfoCity(String epointInfoCity) {  
		 this.epointInfoCity = epointInfoCity;  
   }
	public String epointInfoStreet=null;
	public String getepointInfoStreet() {  
		 return epointInfoStreet;  
   }  
	public void setepointInfoStreet(String epointInfoStreet) {  
		 this.epointInfoStreet = epointInfoStreet;  
   }
	public String gender=null;
	public String getgender() {  
		 return gender;  
   }  
	public void setgender(String gender) {  
		 this.gender = gender;  
   }
	public String age1=null;
	public String getage1() {  
		 return age1;  
   }  
	public void setage1(String age1) {  
		 this.age1 = age1;  
   }
	public String age2=null;
	public String getage2() {  
		 return age2;  
   }  
	public void setage2(String age2) {  
		 this.age2 = age2;  
   }
	public String preference=null;
	public String getpreference() {  
		 return preference;
   }  
	public void setpreference(String preference) {  
		 this.preference = preference;  
   }
	public int pattern=0;
	public int getpattern() {  
		 return pattern;  
   }  
	public void setpattern(int pattern) {  
		 this.pattern = pattern;  
   }
	//年月日时分
	public String year1=null;
	public String getyear1() {  
		 return year1;
   }  
	public void setyear1(String year1) {  
		 this.year1 = year1;  
   }
	public String month1=null;
	public String getmonth1() {  
		 return month1;
   }  
	public void setmonth1(String month1) {  
		 this.month1 = month1;  
   }
	public String day1=null;
	public String getday1() {  
		 return day1;
   }  
	public void setday1(String day1) {  
		 this.day1 = day1;  
   }
	public String hour1=null;
	public String gethour1() {  
		 return hour1;
   }  
	public void sethour1(String hour1) {  
		 this.hour1 = hour1;  
   }
	public String minute1=null;
	public String getminute1() {  
		 return minute1;
   }  
	public void setminute1(String minute1) {  
		 this.minute1 = minute1;  
   }
	
	public String year2=null;
	public String getyear2() {  
		 return year2;
   }  
	public void setyear2(String year2) {  
		 this.year2 = year2;  
   }
	public String month2=null;
	public String getmonth2() {  
		 return month2;
   }  
	public void setmonth2(String month2) {  
		 this.month2 = month2;  
   }
	public String day2=null;
	public String getday2() {  
		 return day2;
   }  
	public void setday2(String day2) {  
		 this.day2 = day2;  
   }
	public String hour2=null;
	public String gethour2() {  
		 return hour2;
   }  
	public void sethour2(String hour2) {  
		 this.hour2 = hour2;  
   }
	public String minute2=null;
	public String getminute2() {  
		 return minute2;
   }  
	public void setminute2(String minute2) {  
		 this.minute2 = minute2;  
   }
	//拼友列表	
	List <ResultUprequestList> resultlist = new ArrayList<ResultUprequestList>();
	public void setResultList(List<ResultUprequestList> resultList){
		this.resultlist.addAll(resultList);
	}
	public List<ResultUprequestList> getResultList(){
		return resultlist;
	}
	public String fStartPointCity;
	public String getfStartPointCity() {  
		 return fStartPointCity;  
 }  
	public void setfStartPointCity(String fStartPointCity) {  
		 this.fStartPointCity = fStartPointCity;  
 }
	public String fStartPointStreets;
	public String getfStartPointStreets() {  
		 return fStartPointStreets;  
 }  
	public void setfStartPointStreets(String fStartPointStreets) {  
		 this.fStartPointStreets = fStartPointStreets;  
 }
	public String fEndPointCity;
	public String getfEndPointCity() {  
		 return fEndPointCity;  
 }  
	public void setfEndPointCity(String fEndPointCity) {  
		 this.fEndPointCity = fEndPointCity;  
 }
	public String fEndPointStreets;
	public String getfEndPointStreets() {  
		 return fEndPointStreets;  
 }  
	public void setfEndPointStreets(String fEndPointStreets) {  
		 this.fEndPointStreets = fEndPointStreets;  
 }
	//聊天
	public int resultitem;
	public int getresultitem() {  
		 return resultitem;  
  }  
	public void setresultitem(int i) {  
		 resultitem=i;  
  }  
	public String FRIEND;
	public String getFRIEND() {  
		 return FRIEND;  
  }  
	public void setFRIEND(String FRIEND) {  
		 this.FRIEND = FRIEND;  
  }
	public ArrayList<String> FRIENDS=new ArrayList<String>();
	public ArrayList<String> getFRIENDS() {  
		 return FRIENDS;  
  }  
	public void setFRIENDS(ArrayList<String> FRIENDS) {  
		 this.FRIENDS.addAll(FRIENDS);
  }
	public void addFriend(String Friend){
		this.FRIENDS.add(Friend);
	}
	//聊天是否联网成功
	public boolean CHAT_CONNECTBOOL;
	public boolean getCHAT_CONNECTBOOL() {  
		 return CHAT_CONNECTBOOL;  
  }  
	public void setCHAT_CONNECTBOOL(boolean CHAT_CONNECTBOOL) {  
		 this.CHAT_CONNECTBOOL = CHAT_CONNECTBOOL;  
  }
	//将年月日时分分开
	//日期
	public String getdate(String str) {
		String s=str.substring(0, 8);
		s=s.substring(0,4)+"-"+s.substring(4,6)+"-"+s.substring(6,8);
		return s;
	}
	//时间
	public String gettime(String str) {
		String s=str.substring(8, 12);
		s=s.substring(0,2)+"-"+s.substring(2,4);
		return s;
	}
	public boolean pinche;
	public boolean getpinche() {  
		 return pinche;  
  } public void setpinche(boolean b){
	  pinche=b;
  }
    public int timeinterval;
    public int gettimeinteval(){
    	return timeinterval;
    }
    public void settimeinterval(int i){
    	timeinterval=i;
    }
    public String safe_pwd;
    public String getsafe_pwd(){
    	return safe_pwd;
    }
    public void setsafe_pwd(String safe_pwd){
    	this.safe_pwd=safe_pwd;
    }
	public String HTTPSERVER_IP="http://192.168.23.3:8080/PServerWeb/";
	public String getHTTPSERVER_IP() {  
		 return HTTPSERVER_IP;  
  } 
//	public boolean isarrive;
	public boolean getisarrive(){
		return isArrivedDestination;
	}
	public void setisarrive(boolean isArrived){
		this.isArrivedDestination=isArrived;
	}
}





