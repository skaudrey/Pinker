package com.example.pinker.Activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baidu.mapapi.map.ArcOptions;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.DrivingRouteOverlay;
import com.baidu.mapapi.overlayutil.OverlayManager;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.core.RouteNode;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteLine;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteLine;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption.DrivingPolicy;
import com.example.pinker.R;
import com.example.pinker.safeProtect.StartProtect;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
/**
 * @author Skory
 * 由拼友列表页面跳转过来
 * 拼友列表传参--
 * 
 */

/**
 * 两人路径加载，获取重叠路径
 * @author Skory
 *
 */
public class PkRouteActivity extends Activity implements 
OnGetRoutePlanResultListener {
	
    RouteLine route1 = null;
    RouteLine route2=null;
    OverlayManager routeOverlay = null;
    boolean useDefaultIcon = false;
    /**
     * @author Skory
     * 记录路线节点信息
     */
    List<LatLng> points1=new ArrayList<LatLng>();
    List<LatLng> points2=new ArrayList<LatLng>();  
    
    //路线距离
    int distance;
    
    int price;
    
  //Intent
  	Intent intent;
    
    MapView mMapView = null;    // 地图View
    BaiduMap mBaidumap = null;
    //搜索相关
    RoutePlanSearch mSearch1 = null;    // 搜索模块，也可去掉地图模块独立使用
    RoutePlanSearch mSearch2=null;
  //搜索policy--以时间最短为默认搜索模式
    DrivingPolicy policy=DrivingPolicy.ECAR_TIME_FIRST;
    
  //常用policy常量
    public static final int MINFEE=10;
    public static final int MINTIME=11;
    public static final int MINDISTANCE=12;
    public static final int MINAVOID=13;
   //
    PkApplication Pkapp;
       
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        //初始化地图
        mMapView = (MapView) findViewById(R.id.map);
        mBaidumap = mMapView.getMap();
        Pkapp=(PkApplication)getApplication();
      //获取intent
        intent = getIntent();
       
        if(intent.hasExtra("policy")){
        	Bundle b = intent.getExtras();        	
        	setPolicy(b.getInt("policy"));
        }
        
        // 初始化搜索模块，注册事件监听
        mSearch1 = RoutePlanSearch.newInstance();
        mSearch1.setOnGetRoutePlanResultListener(this);
        
        mSearch2=RoutePlanSearch.newInstance();
        mSearch2.setOnGetRoutePlanResultListener(new OnGetRoutePlanResultListener() {
			
			public void onGetWalkingRouteResult(WalkingRouteResult arg0) {}
			
			public void onGetTransitRouteResult(TransitRouteResult arg0) {}
			
			public void onGetDrivingRouteResult(
					DrivingRouteResult result) {
	            route2 = result.getRouteLines().get(0);            
	            DrivingRouteOverlay overlay = new MyDrivingRouteOverlay(mBaidumap);
	            routeOverlay = overlay;
	            mBaidumap.setOnMarkerClickListener(overlay);
	            overlay.setData(result.getRouteLines().get(0));
	            overlay.addToMap();
	            overlay.zoomToSpan();
	            
	            GetRoute(route2, points2);
			}
		});
        if (Pkapp.getStartPointLat()!=100.0 && Pkapp.getEndPointLat()!=100.0) {
        	Bundle b=intent.getExtras();
        	search(new LatLng(Pkapp.getStartPointLat(),
        			Pkapp.getStartPointLng()),
        			new LatLng(Pkapp.getEndPointLat(),
        					Pkapp.getEndPointLng()),
        			mSearch1);	
		}
        else if(Pkapp.getspointInfoCity()!=null && Pkapp.getepointInfoCity()!=null) {
        	Bundle b=intent.getExtras();
        	search(Pkapp.getspointInfoCity(),
        			Pkapp.getspointInfoStreet(),
        			Pkapp.getepointInfoCity(),
        			Pkapp.getepointInfoStreet(),
        			mSearch1);
        }        
        search(Pkapp.getfStartPointCity(),
        		Pkapp.getfStartPointStreets(),
        		Pkapp.getfEndPointCity(),
        		Pkapp.getfEndPointStreets(),
    			mSearch2);
        
        
        ((Button)findViewById(R.id.routesettings)).setOnClickListener(new OnClickListener(){
			public void onClick(View v){ 
			      Intent intent=new Intent(PkRouteActivity.this,StartProtect.class);
			      PkRouteActivity.this.finish();
			      startActivity(intent);
		    }
		});
        
    }
    /**
     * 获取路径关键点，将关键点坐标存在arraylist中
     * @param route	规划路径
     * @param points 记录路径关键点坐标
     */
    public void GetRoute(RouteLine route,List<LatLng> points){
    	for(int i=0;i<route.getAllStep().size();i++){
    	Object step = route.getAllStep().get(i);
    	
        if (step instanceof DrivingRouteLine.DrivingStep) {
            points.addAll(((DrivingRouteLine.DrivingStep) step).getWayPoints());
        }
       }
   }
    /**
     * 通过重叠路径获取距离
     * @param overlapRoute 重叠路径关键点
     * @return 重叠路径距离
     */
    public int getOverlapRouteDistance(List<LatLng> overlapRoute) {
    	RoutePlanSearch mSearch=RoutePlanSearch.newInstance();
    	mSearch.setOnGetRoutePlanResultListener(new OnGetRoutePlanResultListener() {
			
			public void onGetWalkingRouteResult(WalkingRouteResult arg0) {}
			
			public void onGetTransitRouteResult(TransitRouteResult arg0) {}
			
			public void onGetDrivingRouteResult(
					DrivingRouteResult result) {
				distance=result.getRouteLines().get(0).
						getDistance();
				
          
			}
		});
    	
        PlanNode stNode = PlanNode.withLocation(overlapRoute.get(0));
    	PlanNode enNode=PlanNode.withLocation(
    			overlapRoute.get(overlapRoute.size()-1));
    	mSearch.drivingSearch((new DrivingRoutePlanOption())
                .from(stNode)
                .to(enNode));
    	/**
    	 * 传参--距离
    	 */
    	
        intent.putExtra("distance", distance);
        
        return distance;
	}
    /**
     * 根据路径计算经费
     * @param distance 重叠路径距离
     * @return	价钱
     */
    public int getPrice(int distance) {
    	    	
    	/**
    	 * 传参--经费
    	 */    	
        intent.putExtra("price", price);
    	
		return price;
	}
    
    
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    
    public void onGetWalkingRouteResult(WalkingRouteResult result) {}

    
    public void onGetTransitRouteResult(TransitRouteResult result) {}

  
    public void onGetDrivingRouteResult(DrivingRouteResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(PkRouteActivity.this,
            		"抱歉，未找到结果", Toast.LENGTH_SHORT).show();
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            //起终点或途经点地址有岐义，通过以下接口获取建议查询信息
            return;
        }
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            route1 = result.getRouteLines().get(0);            
            DrivingRouteOverlay overlay = new MyDrivingRouteOverlay(mBaidumap);
            routeOverlay = overlay;
            mBaidumap.setOnMarkerClickListener(overlay);
            overlay.setData(result.getRouteLines().get(0));
            overlay.addToMap();
            overlay.zoomToSpan();
            
            GetRoute(route1, points1);
            OverlayOptions ooPolyline = new PolylineOptions().
            		width(10).color(0xFFFF00FF).points(points1);
            mBaidumap.addOverlay(ooPolyline);
            
        }
        
    }
    //定制RouteOverly
    private class MyDrivingRouteOverlay extends DrivingRouteOverlay {

        public MyDrivingRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        
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
        mSearch1.destroy();
        mSearch2.destroy();
        mMapView.onDestroy();
        super.onDestroy();
    }
    
    /**
     * 设置检索模式
     */
    public void setPolicy(int policyMode) {
    	
		if(policyMode==MINTIME){
			policy=DrivingPolicy.ECAR_TIME_FIRST;}
		if (policyMode==MINFEE) {
			policy=DrivingPolicy.ECAR_FEE_FIRST;
		}
		if (policyMode==MINDISTANCE) {
			policy=DrivingPolicy.ECAR_DIS_FIRST;
		}
		if (policyMode==MINAVOID) {
			policy=DrivingPolicy.ECAR_AVOID_JAM;
		}
	}
    
    public void search(LatLng stLatLng,LatLng enLatLng,
    		RoutePlanSearch mSearch) {
        //获取起终点节点
        PlanNode stNode = PlanNode.withLocation(stLatLng);
        PlanNode enNode = PlanNode.withLocation(enLatLng);
        //发起搜索
        mSearch.drivingSearch((new DrivingRoutePlanOption())
                    .from(stNode)
                    .to(enNode));
    }
    public void search(String stPointCity,String stPointPlace,
    		String enPointCity,String enPointPlace,
    		RoutePlanSearch mSearch) {
       PlanNode stNode = PlanNode.withCityNameAndPlaceName(
        		stPointCity, stPointPlace);
        PlanNode enNode = PlanNode.withCityNameAndPlaceName(
        		enPointCity, enPointPlace);
      //发起搜索
        mSearch.drivingSearch((new DrivingRoutePlanOption())
                    .from(stNode)
                    .to(enNode));
    }
}
