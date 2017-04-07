package com.example.pinker.chatinfo;

import java.util.LinkedHashMap;
import java.util.Map;

import android.app.Application;
import com.example.pinker.R;

public class Myface extends Application {
	public static final int NUM_PAGE = 6;// ×Ü¹²ÓÐ¶àÉÙÒ³
	public static int NUM = 20;// Ã¿Ò³20¸ö±íÇé,»¹ÓÐ×îºóÒ»¸öÉ¾³ýbutton
	private static Map<String, Integer> mFaceMap = new LinkedHashMap<String, Integer>();
	private static Myface mApplication;

	public synchronized static Myface getInstance() {
		return mApplication;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mApplication = this;
		initFaceMap();
	}

	public static Map<String, Integer> getFaceMap() {
		initFaceMap();
		if (!mFaceMap.isEmpty())
			return mFaceMap;
		return null;
	}
		
	private static void initFaceMap() {
		// TODO Auto-generated method stub
		mFaceMap.put("[ßÚÑÀ]", R.drawable.f_static_000);
		mFaceMap.put("[µ÷Æ¤]", R.drawable.f_static_001);
		mFaceMap.put("[Á÷º¹]", R.drawable.f_static_002);
		mFaceMap.put("[ÍµÐ¦]", R.drawable.f_static_003);
		mFaceMap.put("[ÔÙ¼û]", R.drawable.f_static_004);
		mFaceMap.put("[ÇÃ´ò]", R.drawable.f_static_005);
		mFaceMap.put("[²Áº¹]", R.drawable.f_static_006);
		mFaceMap.put("[ÖíÍ·]", R.drawable.f_static_007);
		mFaceMap.put("[Ãµ¹å]", R.drawable.f_static_008);
		mFaceMap.put("[Á÷Àá]", R.drawable.f_static_009);
		mFaceMap.put("[´ó¿Þ]", R.drawable.f_static_010);
		mFaceMap.put("[Ðê]", R.drawable.f_static_011);
		mFaceMap.put("[¿á]", R.drawable.f_static_012);
		mFaceMap.put("[×¥¿ñ]", R.drawable.f_static_013);
		mFaceMap.put("[Î¯Çü]", R.drawable.f_static_014);
		mFaceMap.put("[±ã±ã]", R.drawable.f_static_015);
		mFaceMap.put("[Õ¨µ¯]", R.drawable.f_static_016);
		mFaceMap.put("[²Ëµ¶]", R.drawable.f_static_017);
		mFaceMap.put("[¿É°®]", R.drawable.f_static_018);
		mFaceMap.put("[É«]", R.drawable.f_static_019);
		mFaceMap.put("[º¦Ðß]", R.drawable.f_static_020);

		mFaceMap.put("[µÃÒâ]", R.drawable.f_static_021);
		mFaceMap.put("[ÍÂ]", R.drawable.f_static_022);
		mFaceMap.put("[Î¢Ð¦]", R.drawable.f_static_023);
		mFaceMap.put("[·¢Å­]", R.drawable.f_static_024);
		mFaceMap.put("[ÞÏÞÎ]", R.drawable.f_static_025);
		mFaceMap.put("[¾ª¿Ö]", R.drawable.f_static_026);
		mFaceMap.put("[Àäº¹]", R.drawable.f_static_027);
		mFaceMap.put("[°®ÐÄ]", R.drawable.f_static_028);
		mFaceMap.put("[Ê¾°®]", R.drawable.f_static_029);
		mFaceMap.put("[°×ÑÛ]", R.drawable.f_static_030);
		mFaceMap.put("[°ÁÂý]", R.drawable.f_static_031);
		mFaceMap.put("[ÄÑ¹ý]", R.drawable.f_static_032);
		mFaceMap.put("[¾ªÑÈ]", R.drawable.f_static_033);
		mFaceMap.put("[ÒÉÎÊ]", R.drawable.f_static_034);
		mFaceMap.put("[Ë¯]", R.drawable.f_static_035);
		mFaceMap.put("[Ç×Ç×]", R.drawable.f_static_036);
		mFaceMap.put("[º©Ð¦]", R.drawable.f_static_037);
		mFaceMap.put("[°®Çé]", R.drawable.f_static_038);
		mFaceMap.put("[Ë¥]", R.drawable.f_static_039);
		mFaceMap.put("[Æ²×ì]", R.drawable.f_static_040);
		mFaceMap.put("[ÒõÏÕ]", R.drawable.f_static_041);

		mFaceMap.put("[·Ü¶·]", R.drawable.f_static_042);
		mFaceMap.put("[·¢´ô]", R.drawable.f_static_043);
		mFaceMap.put("[ÓÒºßºß]", R.drawable.f_static_044);
		mFaceMap.put("[Óµ±§]", R.drawable.f_static_045);
		mFaceMap.put("[»µÐ¦]", R.drawable.f_static_046);
		mFaceMap.put("[·ÉÎÇ]", R.drawable.f_static_047);
		mFaceMap.put("[±ÉÊÓ]", R.drawable.f_static_048);
		mFaceMap.put("[ÔÎ]", R.drawable.f_static_049);
		mFaceMap.put("[´ó±ø]", R.drawable.f_static_050);
		mFaceMap.put("[¿ÉÁ¯]", R.drawable.f_static_051);
		mFaceMap.put("[Ç¿]", R.drawable.f_static_052);
		mFaceMap.put("[Èõ]", R.drawable.f_static_053);
		mFaceMap.put("[ÎÕÊÖ]", R.drawable.f_static_054);
		mFaceMap.put("[Ê¤Àû]", R.drawable.f_static_055);
		mFaceMap.put("[±§È­]", R.drawable.f_static_056);
		mFaceMap.put("[µòÐ»]", R.drawable.f_static_057);
		mFaceMap.put("[·¹]", R.drawable.f_static_058);
		mFaceMap.put("[µ°¸â]", R.drawable.f_static_059);
		mFaceMap.put("[Î÷¹Ï]", R.drawable.f_static_060);
		mFaceMap.put("[Æ¡¾Æ]", R.drawable.f_static_061);
		mFaceMap.put("[Æ®³æ]", R.drawable.f_static_062);

		mFaceMap.put("[¹´Òý]", R.drawable.f_static_063);
		mFaceMap.put("[OK]", R.drawable.f_static_064);
		mFaceMap.put("[°®Äã]", R.drawable.f_static_065);
		mFaceMap.put("[¿§·È]", R.drawable.f_static_066);
		mFaceMap.put("[Ç®]", R.drawable.f_static_067);
		mFaceMap.put("[ÔÂÁÁ]", R.drawable.f_static_068);
		mFaceMap.put("[ÃÀÅ®]", R.drawable.f_static_069);
		mFaceMap.put("[µ¶]", R.drawable.f_static_070);
		mFaceMap.put("[·¢¶¶]", R.drawable.f_static_071);
		mFaceMap.put("[²î¾¢]", R.drawable.f_static_072);
		mFaceMap.put("[È­Í·]", R.drawable.f_static_073);
		mFaceMap.put("[ÐÄËé]", R.drawable.f_static_074);
		mFaceMap.put("[Ì«Ñô]", R.drawable.f_static_075);
		mFaceMap.put("[ÀñÎï]", R.drawable.f_static_076);
		mFaceMap.put("[×ãÇò]", R.drawable.f_static_077);
		mFaceMap.put("[÷¼÷Ã]", R.drawable.f_static_078);
		mFaceMap.put("[»ÓÊÖ]", R.drawable.f_static_079);
		mFaceMap.put("[ÉÁµç]", R.drawable.f_static_080);
		mFaceMap.put("[¼¢¶ö]", R.drawable.f_static_081);
		mFaceMap.put("[À§]", R.drawable.f_static_082);
		mFaceMap.put("[ÖäÂî]", R.drawable.f_static_083);

		mFaceMap.put("[ÕÛÄ¥]", R.drawable.f_static_084);
		mFaceMap.put("[¿Ù±Ç]", R.drawable.f_static_085);
		mFaceMap.put("[¹ÄÕÆ]", R.drawable.f_static_086);
		mFaceMap.put("[ôÜ´óÁË]", R.drawable.f_static_087);
		mFaceMap.put("[×óºßºß]", R.drawable.f_static_088);
		mFaceMap.put("[¹þÇ·]", R.drawable.f_static_089);
		mFaceMap.put("[¿ì¿ÞÁË]", R.drawable.f_static_090);
		mFaceMap.put("[ÏÅ]", R.drawable.f_static_091);
		mFaceMap.put("[ÀºÇò]", R.drawable.f_static_092);
		mFaceMap.put("[Æ¹ÅÒÇò]", R.drawable.f_static_093);
		mFaceMap.put("[NO]", R.drawable.f_static_094);
		mFaceMap.put("[ÌøÌø]", R.drawable.f_static_095);
		mFaceMap.put("[âæ»ð]", R.drawable.f_static_096);
		mFaceMap.put("[×ªÈ¦]", R.drawable.f_static_097);
		mFaceMap.put("[¿ÄÍ·]", R.drawable.f_static_098);
		mFaceMap.put("[»ØÍ·]", R.drawable.f_static_099);
		mFaceMap.put("[ÌøÉþ]", R.drawable.f_static_100);
		mFaceMap.put("[¼¤¶¯]", R.drawable.f_static_101);
		mFaceMap.put("[½ÖÎè]", R.drawable.f_static_102);
		mFaceMap.put("[Ï×ÎÇ]", R.drawable.f_static_103);
		mFaceMap.put("[×óÌ«¼«]", R.drawable.f_static_104);

		mFaceMap.put("[ÓÒÌ«¼«]", R.drawable.f_static_105);
		mFaceMap.put("[±Õ×ì]", R.drawable.f_static_106);
	}
}