package com.example.pinker.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager.LayoutParams;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.example.pinker.R;
import com.example.pinker.chatconnect.ClientConServer;
import com.example.pinker.chatservice.ChatActivity;
import com.example.pinker.systemservice.SystemAdviceActivity;
import com.example.pinker.systemservice.SystemPeopleActivity;
import com.example.pinker.systemservice.UpdateUserInformationActivity;

/**
 * 此activity用来定位，并使用MyLocationOverlay绘制定位位置 
 * 对于附近检测到的人的位置进行绘制，在点击标志后后显示详细信息
 * 
 */
public class PkLocationMapActivity extends Activity implements
OnGetGeoCoderResultListener{
		
		// 定位相关
		LocationClient mLocClient;
		public MyLocationListenner myListener = new MyLocationListenner();
		
		// 记录用户当前位置
		LatLng currentLatLng;
		String currentLocCity;
		String currentLocProvince;
		String currentLocDistrict;
		String currentLocStreet;
		String currentLocStreetNumber;
		
		MapView mMapView;
		BaiduMap mBaiduMap;

		// UI相关
		boolean isFirstLoc = true;// 是否首次定位
		Button individualSetting=null;
		ImageButton btsystem;
		//intent
		Intent intent;
		PkApplication PKApp;
		//地理编码搜索
		GeoCoder mSearch = null;
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.activity_location);
			PKApp=(PkApplication)getApplication();
			//UI
			individualSetting=(Button)findViewById(R.id.individualSetting);	
			btsystem=(ImageButton)findViewById(R.id.btsystem);
			//intent--一定要写，不然会抛出
			intent = getIntent();
			// 地图初始化
			mMapView = (MapView) findViewById(R.id.bmapView);
			mBaiduMap = mMapView.getMap();
			// 开启定位图层
			mBaiduMap.setMyLocationEnabled(true);
			// 定位初始化
			mLocClient = new LocationClient(this);
			mLocClient.registerLocationListener(myListener);
			LocationClientOption option = new LocationClientOption();
			option.setOpenGps(true);// 打开gps
			option.setCoorType("bd09ll"); // 设置坐标类型
			option.setScanSpan(1000);
			mLocClient.setLocOption(option);
			mLocClient.start();
			//搜索引擎初始化
			mSearch = GeoCoder.newInstance();
			//注册搜索监听
			mSearch.setOnGetGeoCodeResultListener(this);
			
			//私人订制按钮注册
			individualSetting.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
							Intent intent=new Intent(PkLocationMapActivity.this,SubmitActivity.class);
							PKApp.setcurrentLocCity(currentLocCity);
							PKApp.setcurrentLocStreet(currentLocStreet);
				        	PKApp.setcurrentLat(currentLatLng.latitude);
				        	PKApp.setcurrentLng(currentLatLng.longitude);
							/*intent.putExtra(AllInfo.currentLocCity, currentLocCity);
							intent.putExtra(AllInfo.currentLocStreet, currentLocStreet);
							intent.putExtra(AllInfo.currentLat, currentLatLng.latitude);
							intent.putExtra(AllInfo.currentLng, currentLatLng.longitude);*/
							startActivity(intent);
					}
				});
			//进入系统按钮事件
			btsystem.setOnClickListener(new OnClickListener() {
		    	@Override
		    	public void onClick(View v) {
		    		showPopupWindow(v);
		    	}
		    });
		}

	    private void showPopupWindow(View view) {

	        // 一个自定义的布局，作为显示的内容
	        View contentView = LayoutInflater.from(this).inflate(
	                R.layout.system_items, null);
	        PopupWindow mPop = new PopupWindow(contentView, LayoutParams.WRAP_CONTENT,
	        	                    LayoutParams.WRAP_CONTENT, true);
	        mPop.setWidth(250);
	        mPop.setHeight(210);//设置弹出框大小
	        mPop.setBackgroundDrawable(getResources().getDrawable(R.drawable.systembg));
	        mPop.setOutsideTouchable(true);
	        mPop.showAsDropDown(view);
	        // 设置按钮的点击事件
	        //个人管理
	        ((Button)contentView.findViewById(R.id.bt_user)).setOnClickListener(new OnClickListener() {
	            @Override
	            public void onClick(View v) {
	            	Intent it=new Intent(PkLocationMapActivity.this,UpdateUserInformationActivity.class);		
	            	startActivity(it);
	            }
	        });
	        //收藏路径
	        /*((Button)contentView.findViewById(R.id.bt_routes)).setOnClickListener(new OnClickListener() {
	            @Override
	            public void onClick(View v) {
						
	            }
	        });
	        //好友管理
	        ((Button)contentView.findViewById(R.id.bt_people)).setOnClickListener(new OnClickListener() {
	            @Override
	            public void onClick(View v) {
	            	Intent it=new Intent(PkLocationMapActivity.this,SystemPeopleActivity.class);		
	            	startActivity(it); 
	            }
	        });*/
	        //帮助与反馈
	        ((Button)contentView.findViewById(R.id.bt_advice)).setOnClickListener(new OnClickListener() {

	            @Override
	            public void onClick(View v) {
	            	Intent it=new Intent(PkLocationMapActivity.this,SystemAdviceActivity.class);
					startActivity(it); 
	            }
	        });
	        //退出
	        ((Button)contentView.findViewById(R.id.bt_exit)).setOnClickListener(new OnClickListener() {

	            @Override
	            public void onClick(View v) {
	            	android.os.Process.killProcess(android.os.Process.myPid()); //获取PID
	            	System.exit(0);
	            }
	        });
	    }  
		/**
		 * 定位SDK监听函数
		 * 当前位置添加标记
		 * 当前位置反向编码
		 */
		public class MyLocationListenner implements BDLocationListener {

			
			public void onReceiveLocation(BDLocation location) {
				// map view 销毁后不在处理新接收的位置
				if (location == null || mMapView == null)
					return;
				MyLocationData locData = new MyLocationData.Builder()
						.accuracy(location.getRadius())
						// 此处设置开发者获取到的方向信息，顺时针0-360
						.direction(100).latitude(location.getLatitude())
						.longitude(location.getLongitude()).build();
				mBaiduMap.setMyLocationData(locData);
				/**
				 * @author Skory
				 * 记录用户当前位置
				 */
				currentLatLng=new LatLng(locData.latitude, locData.longitude);
				
				if (isFirstLoc) {
					isFirstLoc = false;
					LatLng ll = new LatLng(location.getLatitude(),
							location.getLongitude());
					MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
					/**
					 * @author Skory
					 * 记录用户当前位置
					 */
					currentLatLng=ll;
					// 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）  
					BitmapDescriptor mCurrentMarker = 
							BitmapDescriptorFactory  
					    .fromResource(R.drawable.icon_gcoding);
					LocationMode mCurrentMode=LocationMode.NORMAL;
					MyLocationConfiguration config = 
							new MyLocationConfiguration(
									mCurrentMode, false, mCurrentMarker);  
					mBaiduMap.setMyLocationConfigeration(config);
					
					mBaiduMap.animateMapStatus(u);

				}
				//对当前坐标进行反向编码
				mSearch.reverseGeoCode(new ReverseGeoCodeOption()
					.location(currentLatLng));
			}
			
			public void onReceivePoi(BDLocation poiLocation) {}
		}
			
		@Override
		protected void onPause() {
			mMapView.onPause();
			super.onPause();
		}

		@Override
		protected void onResume() {
			mMapView.onResume();
			super.onResume();
		}

		@Override
		protected void onDestroy() {			
			// 退出时销毁定位
			mLocClient.stop();
			// 关闭定位图层
			mBaiduMap.setMyLocationEnabled(false);
			mBaiduMap.clear();
			mMapView.onDestroy();
			mMapView = null;
			//销毁搜索引擎
			mSearch.destroy();
			super.onDestroy();
		}
		
		public void onGetGeoCodeResult(GeoCodeResult result) {}


		public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
				if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
				Toast.makeText(PkLocationMapActivity.this, "抱歉，未能找到结果", Toast.LENGTH_LONG)
						.show();
				return;
			}
				/**
				 * @author Skory
				 * intent记录当前位置
				 */
				currentLocProvince=result.getAddressDetail().province;
				currentLocCity=result.getAddressDetail().city;
				currentLocDistrict=result.getAddressDetail().district;
				currentLocStreet=result.getAddressDetail().street;
				currentLocStreetNumber=result.getAddressDetail().streetNumber;
				
		}

}
