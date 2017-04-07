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
 * ��ƴ���б�ҳ����ת����
 * ƴ���б���--
 * 
 */

/**
 * ����·�����أ���ȡ�ص�·��
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
     * ��¼·�߽ڵ���Ϣ
     */
    List<LatLng> points1=new ArrayList<LatLng>();
    List<LatLng> points2=new ArrayList<LatLng>();  
    
    //·�߾���
    int distance;
    
    int price;
    
  //Intent
  	Intent intent;
    
    MapView mMapView = null;    // ��ͼView
    BaiduMap mBaidumap = null;
    //�������
    RoutePlanSearch mSearch1 = null;    // ����ģ�飬Ҳ��ȥ����ͼģ�����ʹ��
    RoutePlanSearch mSearch2=null;
  //����policy--��ʱ�����ΪĬ������ģʽ
    DrivingPolicy policy=DrivingPolicy.ECAR_TIME_FIRST;
    
  //����policy����
    public static final int MINFEE=10;
    public static final int MINTIME=11;
    public static final int MINDISTANCE=12;
    public static final int MINAVOID=13;
   //
    PkApplication Pkapp;
       
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        //��ʼ����ͼ
        mMapView = (MapView) findViewById(R.id.map);
        mBaidumap = mMapView.getMap();
        Pkapp=(PkApplication)getApplication();
      //��ȡintent
        intent = getIntent();
       
        if(intent.hasExtra("policy")){
        	Bundle b = intent.getExtras();        	
        	setPolicy(b.getInt("policy"));
        }
        
        // ��ʼ������ģ�飬ע���¼�����
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
     * ��ȡ·���ؼ��㣬���ؼ����������arraylist��
     * @param route	�滮·��
     * @param points ��¼·���ؼ�������
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
     * ͨ���ص�·����ȡ����
     * @param overlapRoute �ص�·���ؼ���
     * @return �ص�·������
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
    	 * ����--����
    	 */
    	
        intent.putExtra("distance", distance);
        
        return distance;
	}
    /**
     * ����·�����㾭��
     * @param distance �ص�·������
     * @return	��Ǯ
     */
    public int getPrice(int distance) {
    	    	
    	/**
    	 * ����--����
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
            		"��Ǹ��δ�ҵ����", Toast.LENGTH_SHORT).show();
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            //���յ��;�����ַ����壬ͨ�����½ӿڻ�ȡ�����ѯ��Ϣ
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
    //����RouteOverly
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
     * ���ü���ģʽ
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
        //��ȡ���յ�ڵ�
        PlanNode stNode = PlanNode.withLocation(stLatLng);
        PlanNode enNode = PlanNode.withLocation(enLatLng);
        //��������
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
      //��������
        mSearch.drivingSearch((new DrivingRoutePlanOption())
                    .from(stNode)
                    .to(enNode));
    }
}
