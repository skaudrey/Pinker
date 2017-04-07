package com.example.pinker.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
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
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.DrivingRouteOverlay;
import com.baidu.mapapi.overlayutil.OverlayManager;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.example.pinker.R;

public class PkNotificationActivity extends Activity implements
OnGetGeoCoderResultListener,OnGetRoutePlanResultListener{
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
			
			OverlayManager routeOverlay = null;
		    boolean useDefaultIcon = false;
		    
		  //搜索相关
		    RoutePlanSearch mSearch = null; 
			
			MapView mMapView;
			BaiduMap mBaiduMap;

			// UI相关
			Button confirmArrived;
			
			//intent
			Intent intent;
			
			boolean isFirstLoc=true;
			
			//地理编码搜索
			GeoCoder mSearchGeo = null;
			
			PkApplication Pkapp;
			@Override
			public void onCreate(Bundle savedInstanceState) {
				super.onCreate(savedInstanceState);
				setContentView(R.layout.activity_notifymap);
				//intent--一定要写，不然会抛出
				intent = getIntent();
				//Pkapp初始化
				Pkapp=(PkApplication)getApplication();
				// 地图初始化
				mMapView = (MapView) findViewById(R.id.bmapView);
				mBaiduMap = mMapView.getMap();
				//UI初始化
				confirmArrived=(Button)findViewById(R.id.confirmArrived);
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
				mSearchGeo = GeoCoder.newInstance();
				//注册搜索监听--地理编码
				mSearchGeo.setOnGetGeoCodeResultListener(this);
				
				//注册搜索监听--路径规划
				mSearch = RoutePlanSearch.newInstance();
		        mSearch.setOnGetRoutePlanResultListener(this);
		        PlanNode stNode=null;
		        PlanNode enNode=null;
		        
//		        //获取起终点
//		        if(intent.hasExtra("StartPointLat") &&
//		        		intent.hasExtra("StartPointLng")&&
//		        		intent.hasExtra("EndPointLat") && 
//		        		intent.hasExtra("EndPointLng")){
//		        	stNode = PlanNode.withLocation
//		        			(new LatLng(Pkapp.StartPointLat,Pkapp.StartPointLng));
//		            enNode = PlanNode.withLocation
//		        			(new LatLng(Pkapp.EndPointLat,Pkapp.EndPointLng));
//		        	
//		          //发起搜索--路径规划
//			        mSearch.drivingSearch((new DrivingRoutePlanOption())
//		                    .from(stNode)
//		                    .to(enNode));
//		        }
//		        else if (intent.hasExtra("StartPointCity") &&
//		        		intent.hasExtra("StartPointStreets")&&
//		        		intent.hasExtra("EndPointCity") && 
//		        		intent.hasExtra("EndPointStreets")) {
//					// 获得起终点信息
//		        	stNode = PlanNode.withCityNameAndPlaceName(
//		            		Pkapp.spointInfoCity, 
//		            		Pkapp.spointInfoStreet);
//		            enNode = PlanNode.withCityNameAndPlaceName(
//		            		Pkapp.epointInfoCity, 
//		            		Pkapp.epointInfoStreet);
//		        	
//		          //发起搜索--路径规划
//			        mSearch.drivingSearch((new DrivingRoutePlanOption())
//		                    .from(stNode)
//		                    .to(enNode));
//		        }
		        
		        //test
		        if(Pkapp.USER_NAME.equals("韩月琪")){
		        	stNode = PlanNode.withCityNameAndPlaceName(
		            		"武汉", 
		            		"武汉大学");
		            enNode = PlanNode.withCityNameAndPlaceName(
		            		"武汉", 
		            		"光谷");
		        }
		        else if(Pkapp.USER_NAME.equals("冯淼")){
		        	stNode = PlanNode.withCityNameAndPlaceName(
		            		"武汉", 
		            		"武汉大学");
		            enNode = PlanNode.withCityNameAndPlaceName(
		            		"武汉", 
		            		"上马庄");
		        }
		        
		            	
	          //发起搜索--路径规划
		        mSearch.drivingSearch((new DrivingRoutePlanOption())
	                    .from(stNode)
	                    .to(enNode));
		        
		        confirmArrived.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View view) {
						Intent intent=new Intent(PkNotificationActivity.this,PkLocationMapActivity.class);
						
						Pkapp.currentLat=currentLatLng.latitude;
						Pkapp.currentLng=currentLatLng.longitude;
						Pkapp.currentLocCity=currentLocCity;
						Pkapp.currentLocStreet=currentLocDistrict+
								currentLocStreet+currentLocStreetNumber;
						
						Pkapp.setisarrive(true);
						startActivity(intent);
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
					mSearchGeo.reverseGeoCode(new ReverseGeoCodeOption()
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
				mSearchGeo.destroy();
				mSearch.destroy();
				super.onDestroy();
			}
			
			public void onGetGeoCodeResult(GeoCodeResult result) {}


			public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
					if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
					Toast.makeText(PkNotificationActivity.this, "抱歉，未能找到结果", Toast.LENGTH_LONG)
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
			
			@Override
		    protected void onRestoreInstanceState(Bundle savedInstanceState) {
		        super.onRestoreInstanceState(savedInstanceState);
		    }

		    
		    public void onGetWalkingRouteResult(WalkingRouteResult result) {}

		    
		    public void onGetTransitRouteResult(TransitRouteResult result) {}

		  
		    public void onGetDrivingRouteResult(DrivingRouteResult result) {
		        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
		            Toast.makeText(PkNotificationActivity.this,
		            		"抱歉，未找到结果", Toast.LENGTH_SHORT).show();
		        }
		        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
		            //起终点或途经点地址有岐义，通过以下接口获取建议查询信息
		            return;
		        }
		        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
		            DrivingRouteOverlay overlay = new MyDrivingRouteOverlay(mBaiduMap);
		            routeOverlay = overlay;
		            mBaiduMap.setOnMarkerClickListener(overlay);
		            overlay.setData(result.getRouteLines().get(0));
		            
		            overlay.addToMap();
		            overlay.zoomToSpan();     
		            
		            
		        }
		    }

		    //定制RouteOverly
		    private class MyDrivingRouteOverlay extends DrivingRouteOverlay {

		        public MyDrivingRouteOverlay(BaiduMap baiduMap) {
		            super(baiduMap);
		        }

		        @Override
		        public BitmapDescriptor getStartMarker() {
		            if (useDefaultIcon) {
		                return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
		            }
		            return null;
		        }

		        @Override
		        public BitmapDescriptor getTerminalMarker() {
		            if (useDefaultIcon) {
		                return BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
		            }
		            return null;
		        }
		    }

}
