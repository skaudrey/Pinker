package com.example.pinker.chatinfo;

import java.util.LinkedHashMap;
import java.util.Map;

import android.app.Application;
import com.example.pinker.R;

public class Myface extends Application {
	public static final int NUM_PAGE = 6;// �ܹ��ж���ҳ
	public static int NUM = 20;// ÿҳ20������,�������һ��ɾ��button
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
		mFaceMap.put("[����]", R.drawable.f_static_000);
		mFaceMap.put("[��Ƥ]", R.drawable.f_static_001);
		mFaceMap.put("[����]", R.drawable.f_static_002);
		mFaceMap.put("[͵Ц]", R.drawable.f_static_003);
		mFaceMap.put("[�ټ�]", R.drawable.f_static_004);
		mFaceMap.put("[�ô�]", R.drawable.f_static_005);
		mFaceMap.put("[����]", R.drawable.f_static_006);
		mFaceMap.put("[��ͷ]", R.drawable.f_static_007);
		mFaceMap.put("[õ��]", R.drawable.f_static_008);
		mFaceMap.put("[����]", R.drawable.f_static_009);
		mFaceMap.put("[���]", R.drawable.f_static_010);
		mFaceMap.put("[��]", R.drawable.f_static_011);
		mFaceMap.put("[��]", R.drawable.f_static_012);
		mFaceMap.put("[ץ��]", R.drawable.f_static_013);
		mFaceMap.put("[ί��]", R.drawable.f_static_014);
		mFaceMap.put("[���]", R.drawable.f_static_015);
		mFaceMap.put("[ը��]", R.drawable.f_static_016);
		mFaceMap.put("[�˵�]", R.drawable.f_static_017);
		mFaceMap.put("[�ɰ�]", R.drawable.f_static_018);
		mFaceMap.put("[ɫ]", R.drawable.f_static_019);
		mFaceMap.put("[����]", R.drawable.f_static_020);

		mFaceMap.put("[����]", R.drawable.f_static_021);
		mFaceMap.put("[��]", R.drawable.f_static_022);
		mFaceMap.put("[΢Ц]", R.drawable.f_static_023);
		mFaceMap.put("[��ŭ]", R.drawable.f_static_024);
		mFaceMap.put("[����]", R.drawable.f_static_025);
		mFaceMap.put("[����]", R.drawable.f_static_026);
		mFaceMap.put("[�亹]", R.drawable.f_static_027);
		mFaceMap.put("[����]", R.drawable.f_static_028);
		mFaceMap.put("[ʾ��]", R.drawable.f_static_029);
		mFaceMap.put("[����]", R.drawable.f_static_030);
		mFaceMap.put("[����]", R.drawable.f_static_031);
		mFaceMap.put("[�ѹ�]", R.drawable.f_static_032);
		mFaceMap.put("[����]", R.drawable.f_static_033);
		mFaceMap.put("[����]", R.drawable.f_static_034);
		mFaceMap.put("[˯]", R.drawable.f_static_035);
		mFaceMap.put("[����]", R.drawable.f_static_036);
		mFaceMap.put("[��Ц]", R.drawable.f_static_037);
		mFaceMap.put("[����]", R.drawable.f_static_038);
		mFaceMap.put("[˥]", R.drawable.f_static_039);
		mFaceMap.put("[Ʋ��]", R.drawable.f_static_040);
		mFaceMap.put("[����]", R.drawable.f_static_041);

		mFaceMap.put("[�ܶ�]", R.drawable.f_static_042);
		mFaceMap.put("[����]", R.drawable.f_static_043);
		mFaceMap.put("[�Һߺ�]", R.drawable.f_static_044);
		mFaceMap.put("[ӵ��]", R.drawable.f_static_045);
		mFaceMap.put("[��Ц]", R.drawable.f_static_046);
		mFaceMap.put("[����]", R.drawable.f_static_047);
		mFaceMap.put("[����]", R.drawable.f_static_048);
		mFaceMap.put("[��]", R.drawable.f_static_049);
		mFaceMap.put("[���]", R.drawable.f_static_050);
		mFaceMap.put("[����]", R.drawable.f_static_051);
		mFaceMap.put("[ǿ]", R.drawable.f_static_052);
		mFaceMap.put("[��]", R.drawable.f_static_053);
		mFaceMap.put("[����]", R.drawable.f_static_054);
		mFaceMap.put("[ʤ��]", R.drawable.f_static_055);
		mFaceMap.put("[��ȭ]", R.drawable.f_static_056);
		mFaceMap.put("[��л]", R.drawable.f_static_057);
		mFaceMap.put("[��]", R.drawable.f_static_058);
		mFaceMap.put("[����]", R.drawable.f_static_059);
		mFaceMap.put("[����]", R.drawable.f_static_060);
		mFaceMap.put("[ơ��]", R.drawable.f_static_061);
		mFaceMap.put("[Ʈ��]", R.drawable.f_static_062);

		mFaceMap.put("[����]", R.drawable.f_static_063);
		mFaceMap.put("[OK]", R.drawable.f_static_064);
		mFaceMap.put("[����]", R.drawable.f_static_065);
		mFaceMap.put("[����]", R.drawable.f_static_066);
		mFaceMap.put("[Ǯ]", R.drawable.f_static_067);
		mFaceMap.put("[����]", R.drawable.f_static_068);
		mFaceMap.put("[��Ů]", R.drawable.f_static_069);
		mFaceMap.put("[��]", R.drawable.f_static_070);
		mFaceMap.put("[����]", R.drawable.f_static_071);
		mFaceMap.put("[�]", R.drawable.f_static_072);
		mFaceMap.put("[ȭͷ]", R.drawable.f_static_073);
		mFaceMap.put("[����]", R.drawable.f_static_074);
		mFaceMap.put("[̫��]", R.drawable.f_static_075);
		mFaceMap.put("[����]", R.drawable.f_static_076);
		mFaceMap.put("[����]", R.drawable.f_static_077);
		mFaceMap.put("[����]", R.drawable.f_static_078);
		mFaceMap.put("[����]", R.drawable.f_static_079);
		mFaceMap.put("[����]", R.drawable.f_static_080);
		mFaceMap.put("[����]", R.drawable.f_static_081);
		mFaceMap.put("[��]", R.drawable.f_static_082);
		mFaceMap.put("[����]", R.drawable.f_static_083);

		mFaceMap.put("[��ĥ]", R.drawable.f_static_084);
		mFaceMap.put("[�ٱ�]", R.drawable.f_static_085);
		mFaceMap.put("[����]", R.drawable.f_static_086);
		mFaceMap.put("[�ܴ���]", R.drawable.f_static_087);
		mFaceMap.put("[��ߺ�]", R.drawable.f_static_088);
		mFaceMap.put("[��Ƿ]", R.drawable.f_static_089);
		mFaceMap.put("[�����]", R.drawable.f_static_090);
		mFaceMap.put("[��]", R.drawable.f_static_091);
		mFaceMap.put("[����]", R.drawable.f_static_092);
		mFaceMap.put("[ƹ����]", R.drawable.f_static_093);
		mFaceMap.put("[NO]", R.drawable.f_static_094);
		mFaceMap.put("[����]", R.drawable.f_static_095);
		mFaceMap.put("[���]", R.drawable.f_static_096);
		mFaceMap.put("[תȦ]", R.drawable.f_static_097);
		mFaceMap.put("[��ͷ]", R.drawable.f_static_098);
		mFaceMap.put("[��ͷ]", R.drawable.f_static_099);
		mFaceMap.put("[����]", R.drawable.f_static_100);
		mFaceMap.put("[����]", R.drawable.f_static_101);
		mFaceMap.put("[����]", R.drawable.f_static_102);
		mFaceMap.put("[����]", R.drawable.f_static_103);
		mFaceMap.put("[��̫��]", R.drawable.f_static_104);

		mFaceMap.put("[��̫��]", R.drawable.f_static_105);
		mFaceMap.put("[����]", R.drawable.f_static_106);
	}
}