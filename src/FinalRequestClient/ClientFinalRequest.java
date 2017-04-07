package FinalRequestClient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class ClientFinalRequest {
		
		// 获得Post请求对象request
		public static HttpPost getHttpPost(String url){
			 HttpPost request = new HttpPost(url);
			 return request;
		}
		
		// 根据请求获得响应对象response
		public static HttpResponse getHttpResponse(HttpPost request) throws ClientProtocolException, IOException{
			HttpResponse response = new DefaultHttpClient().execute(request);
			return response;
		}
		
		// 发送Post请求，获得响应查询结果
		
		@SuppressWarnings("deprecation")
		public static String queryStringForPost(String finalrequest,String url) throws UnsupportedEncodingException {
			// 根据url获得HttpPost对象
			HttpPost request = ClientFinalRequest.getHttpPost(url);
			String result = null;
			//对传递参数进行封装
			List<NameValuePair> params=new ArrayList<NameValuePair>();
			//防止中文乱码
			finalrequest=URLEncoder.encode(URLEncoder.encode(finalrequest, "UTF-8"), "UTF-8");
			params.add(new BasicNameValuePair("finalrequest",finalrequest));
			try {
				//设置请求参数项
				request.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
				// 获得响应对象
				HttpResponse response = ClientFinalRequest.getHttpResponse(request);
				// 判断是否请求成功
				if(response.getStatusLine().getStatusCode()==200){
					// 获得响应
					result = EntityUtils.toString(response.getEntity(),"UTF_8");
					
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
				
			} catch (IOException e) {
				e.printStackTrace();
				
			}
			return result;
	    }
}
