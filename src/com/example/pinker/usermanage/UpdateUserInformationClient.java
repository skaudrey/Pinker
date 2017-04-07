package com.example.pinker.usermanage;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class UpdateUserInformationClient {
	      // ���Post�������request
			public static HttpPost getHttpPost(String url){
				 HttpPost request = new HttpPost(url);
				 return request;
			}
			
			// ������������Ӧ����response
			public static HttpResponse getHttpResponse(HttpPost request) throws ClientProtocolException, IOException{
				HttpResponse response = new DefaultHttpClient().execute(request);
				return response;
			}
			
			
			// ����Post���󣬻����Ӧ��ѯ���
			
			@SuppressWarnings("deprecation")
			public static String queryStringForPost(String username,String phonenumber,String gender,String birth,String contactphonenumber,String signature,String url) throws UnsupportedEncodingException {
				// ����url���HttpPost����
				HttpPost request = LoginClient.getHttpPost(url);
				String result = null;
				//�Դ��ݲ������з�װ
				List<NameValuePair> params=new ArrayList<NameValuePair>();
				//��ֹ��������
				username=URLEncoder.encode(URLEncoder.encode(username, "UTF-8"), "UTF-8");
				gender=URLEncoder.encode(URLEncoder.encode(gender, "UTF-8"), "UTF-8");
				signature=URLEncoder.encode(URLEncoder.encode(signature, "UTF-8"), "UTF-8");
				//����û���������
				params.add(new BasicNameValuePair("username",username));
				params.add(new BasicNameValuePair("phonenumber",phonenumber));
				params.add(new BasicNameValuePair("gender",gender));
				params.add(new BasicNameValuePair("birth", birth));
				params.add(new BasicNameValuePair("contactphonenumber",contactphonenumber));
				params.add(new BasicNameValuePair("signature",signature));
				try {
					//�������������
					request.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
					// �����Ӧ����
					HttpResponse response = LoginClient.getHttpResponse(request);
					// �ж��Ƿ�����ɹ�
					if(response.getStatusLine().getStatusCode()==200){
						// �����Ӧ
						result = EntityUtils.toString(response.getEntity(),"UTF_8");
						//result=new  String(result.getBytes("8859_1"),"GB2312"); // ����Ҫ�ɲ�Ҫ�����㲻��������Ϊ׼
						
						return result;
					}
				} catch (ClientProtocolException e) {
					e.printStackTrace();
					result = "�����쳣!"+","+"0";
					return result;
				} catch (IOException e) {
					e.printStackTrace();
					result = "�����쳣!"+","+"0";
					return result;
				}
				result = "δ���ӵ�������!"+","+"0";
				return result;
		    }
			
}
