package com.feng.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
//commons-httpclient-3.1.jar
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
//fastjson-1.1.40.jar
import com.alibaba.fastjson.JSONObject;

/**
 * HTTP CLIENT UTIL <BR/>
 * <BR/>
 * commons-codec-1.10.jar <BR/>
 * commons-httpclient-3.1.jar <BR/>
 * commons-logging-1.2.jar <BR/>
 * 
 * @author zte.gcj
 *
 */
public class HttpUtil {
	public static final String GET = "get";
	public static final String POST = "post";
	
	private static final int TIMEOUT = 60 * 1000;
	
	/**
	 * http GET 请求
	 * @param host
	 * @param callMethod
	 * @param params
	 * @return
	 */
	public static String httpGet(String host, String callMethod, Map<String, String> params) {
		return httpCall(host, callMethod, params, GET);
	}
	
	/**
	 * http POST 请求
	 * @param host
	 * @param callMethod
	 * @param params
	 * @return
	 */
	public static String httpPost(String host, String callMethod, Map<String, String> params) {
		return httpCall(host, callMethod, params, POST);
	}
	
	/**
	 * http 请求
	 * 
	 * @param host
	 * @param callMethod
	 * @param params
	 * @param handleType
	 * @return
	 */
	public static String httpCall(String host, String callMethod, Map<String, String> params, String handleType) {
		return httpCall(host, callMethod, -1, params, handleType);
	}
	
	/**
	 * http 请求
	 * 
	 * @param host
	 * @param callMethod
	 * @param port
	 * @param params
	 * @param handleType
	 * @return
	 */
	public static String httpCall(String host, String callMethod, int port, Map<String, String> params, String handleType) {
		try {
			HttpClient httpClient = null;
			if(port == -1){
				httpClient = getHttpClient(host);
			} else {
				httpClient = getHttpClient(host, port);
			}
			//
			if(POST.equals(handleType)){
				//post 请求
				String uri = host + callMethod;
				return callHttpPostJson(httpClient, uri, params);
			} else {
				//get 请求
				StringBuffer param_buf = new StringBuffer();
				if(params != null && params.isEmpty() == false && params.size() > 0){
					Set<String> set = params.keySet();
					Iterator<String> it = set.iterator();
					String key = null;
					if(it.hasNext()){
						key = it.next();
						param_buf.append("?"+key+"="+params.get(key));
					}
					while (it.hasNext()) {
						key = it.next();
						param_buf.append("&"+key+"="+params.get(key));
					}
				}
				String uri = host + callMethod + param_buf.toString();
				return callHttpGet(httpClient, uri);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/** get 请求 **/
	protected static String callHttpGet(HttpClient httpClient, String uri) {
		try {
			GetMethod method = new GetMethod(uri);
			method.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
			//
			method.releaseConnection();
			httpClient.executeMethod(method);
			int code = method.getStatusCode();
			if(code != 200){
				return null;
			}
			String responseFromApi = method.getResponseBodyAsString();
			return responseFromApi;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/** post 请求，KEY,VALUE参数 **/
	protected static String callHttpPost(HttpClient httpClient, String uri, Map<String, String> params) {
		try {
			PostMethod method = new PostMethod(uri);
			//
			method.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
			List<NameValuePair> param = new ArrayList<NameValuePair>();
			if(params != null && params.isEmpty() == false){
				Set<String> set = params.keySet();
				Iterator<String> it = set.iterator();
				while (it.hasNext()) {
					String key = it.next();
					NameValuePair currentNameValuePair =  new NameValuePair(key, params.get(key));
					param.add(currentNameValuePair);
				}
			}
			//
			final int size = param.size();
			NameValuePair[] _NameValuePairArray = param.toArray(new NameValuePair[size]);
			method.setRequestBody(_NameValuePairArray);
			method.releaseConnection();
			//设置超时的时间
			method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, TIMEOUT);
			//
			httpClient.executeMethod(method);
			int code = method.getStatusCode();
			if(code != 200){
				return null;
			}
			String responseFromApi = method.getResponseBodyAsString();
			return responseFromApi;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/** post 请求，JSON 参数 **/
	protected static String callHttpPostJson(HttpClient httpClient, String uri, Map<String, String> params) {
		try {
			PostMethod method = new PostMethod(uri);
			//
			JSONObject jsonObject = new JSONObject();
			if(params != null && params.isEmpty() == false){
				Set<String> set = params.keySet();
				Iterator<String> it = set.iterator();
				while (it.hasNext()) {
					String key = it.next();
					jsonObject.put(key, params.get(key));
				}
			}
			String transJson = jsonObject.toString();
			RequestEntity se = new StringRequestEntity(transJson, "application/json", "UTF-8");
			method.setRequestEntity(se);
			//使用系统提供的默认的恢复策略
			method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
			//设置超时的时间
			method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, TIMEOUT);
			//
			httpClient.executeMethod(method);
			int code = method.getStatusCode();
			if(code != 200){
				return null;
			}
			String responseFromApi = method.getResponseBodyAsString();
			return responseFromApi;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static HttpClient getHttpClient(String host){
		HttpClient httpClient = new HttpClient();
		httpClient.getHostConfiguration().setHost(host);
		return httpClient;
	}
	private static HttpClient getHttpClient(String host, int port){
		HttpClient httpClient = new HttpClient();
		httpClient.getHostConfiguration().setHost(host, port, "http");
		return httpClient;
	}
}