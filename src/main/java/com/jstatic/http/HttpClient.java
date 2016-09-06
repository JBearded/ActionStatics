package com.jstatic.http;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.util.*;

/**
 * Http客户端服务
 *
 * @author 谢俊权
 * @create 2016/1/30 15:24
 */
public class HttpClient {

	private final PoolingHttpClientConnectionManager manager = new PoolingHttpClientConnectionManager();
	private final Object lock = new Object();

	private int MAX_TOTAL = 4;

	private int KEEP_ALIVE_MILSECONDS = 1000 * 60;

	private int SOCKET_TIMEOUT_MSECONDS = 1000 * 10;
	private int CONNECTION_TIMOUT_MSECONDS = 1000 * 10;
	private int REQUEST_TIMEOUT_MSECONDS = 1000 * 10;

	private int HTTP_RETRY_TIMES = 3;

	private static class HttpClientServiceHolder{
		public static HttpClient httpClientService = new HttpClient();
	}
	private HttpClient(){

	}
	public static HttpClient getInstance(){
		return HttpClientServiceHolder.httpClientService;
	}


	public String post(String uri) throws Exception {
		return post(uri, null, REQUEST_TIMEOUT_MSECONDS);
	}

	public String post(String uri, int timeoutMs) throws Exception {
		return post(uri, null, timeoutMs);
	}

	public String post(String uri, Map<String, String> params) throws Exception {
		return post(uri, params, REQUEST_TIMEOUT_MSECONDS);
	}

	public String post(String uri, Map<String, String> params, int timeoutMs) throws Exception{

		HttpPost post = new HttpPost(uri);
		setRequestTimeoutMs(post, timeoutMs);
		if(params != null && !params.isEmpty())
			setParams(post, params);
		return execute(post);
	}

	public String get(String uri) throws Exception {
		return get(uri, null, REQUEST_TIMEOUT_MSECONDS);
	}

	public String get(String uri, int timeoutMs) throws Exception {
		return get(uri, null, timeoutMs);
	}

	public String get(String uri, Map<String, String> params) throws Exception {
		return post(uri, params, REQUEST_TIMEOUT_MSECONDS);
	}

	public String get(String uri, Map<String, String> params, int timeout) throws Exception{

		HttpGet get = new HttpGet(uri);
		setRequestTimeoutMs(get, timeout);
		if(params != null && !params.isEmpty())
			setParams(get, params);
		return execute(get);
	}
	
	private String execute(HttpUriRequest request) throws Exception{
		
		CloseableHttpClient client = HttpClients.custom()
					.setConnectionManager(manager)
					.setRetryHandler(new DefaultHttpRetryHandler())
					.setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy())
					.build();
		if(client == null)
			return null;
		CloseableHttpResponse response = client.execute(request);
		String entityString = null;
		try{
			int statusCode = response.getStatusLine().getStatusCode();
			if(statusCode == HttpStatus.SC_OK){
				HttpEntity entity = response.getEntity();
				if(entity != null){
					entityString = EntityUtils.toString(entity, "UTF-8");
				}
			}
			
		}finally{
			response.close();
		}

		return entityString;
	}
	
	private class DefaultHttpRetryHandler implements HttpRequestRetryHandler{

		@Override
		public boolean retryRequest(IOException exception, int executionCount,
				HttpContext context) {
			
			if(executionCount <= HTTP_RETRY_TIMES){	//如果重连次数不超过三次, 则重连
				return true;
			}
			return false;
		}
	}
	
	private class DefaultConnectionKeepAliveStrategy implements ConnectionKeepAliveStrategy{

		@Override
		public long getKeepAliveDuration(HttpResponse arg0, HttpContext arg1) {
			
			return KEEP_ALIVE_MILSECONDS;
		}
	}
	
	private void setParams(HttpRequestBase request, Map<String, String> params) throws Exception{
		
		String method = request.getMethod();
		String getMethod = String.valueOf("GET");
		String postMethod = String.valueOf("POST");
		List<NameValuePair> list = toNameValuePairList(params);
		
		if(getMethod.equalsIgnoreCase(method)){
			HttpGet get = (HttpGet) request;
			URIBuilder uriBuilder = new URIBuilder(get.getURI());
			uriBuilder.addParameters(list);
			request.setURI(new URI(uriBuilder.toString()));
		}else if(postMethod.equalsIgnoreCase(method)){
			HttpPost post = (HttpPost) request;
			HttpEntity entity = new UrlEncodedFormEntity(list, "UTF-8");
			post.setEntity(entity);
		}
	}
	
	private List<NameValuePair> toNameValuePairList(Map<String, String> params){
		
		List<NameValuePair> list = new ArrayList<>();
		Map<String, String> paramsMap = new HashMap<>();
		paramsMap.putAll(params);
		Iterator<String> it = paramsMap.keySet().iterator();
		while(it.hasNext()){
			String name = it.next();
			String value = paramsMap.get(name);
			list.add(new BasicNameValuePair(name, value));
		}
		return list;
	}
	
	
	private void setRequestTimeoutMs(HttpRequestBase request, int timeoutMs) {

		RequestConfig config = RequestConfig.custom()
				.setSocketTimeout(SOCKET_TIMEOUT_MSECONDS)
				.setConnectTimeout(CONNECTION_TIMOUT_MSECONDS)
				.setConnectionRequestTimeout(timeoutMs)
				.setCookieSpec(CookieSpecs.IGNORE_COOKIES)
				.build();
		request.setConfig(config);
	}

	public void setMaxTotal(int maxTotal){
		MAX_TOTAL = maxTotal;
		manager.setMaxTotal(MAX_TOTAL);
	}

	public void setHttpRetryTimes(int httpRetryTimes) {
		HTTP_RETRY_TIMES = httpRetryTimes;
	}

	public void setConnectionTimoutMseconds(int connectionTimeoutMs) {
		CONNECTION_TIMOUT_MSECONDS = connectionTimeoutMs;
	}

	public void setKeepAliveMilseconds(int keepAliveMs) {
		KEEP_ALIVE_MILSECONDS = keepAliveMs;
	}

	public void setRequestTimeoutMseconds(int requestTimeoutMs) {
		REQUEST_TIMEOUT_MSECONDS = requestTimeoutMs;
	}
}
