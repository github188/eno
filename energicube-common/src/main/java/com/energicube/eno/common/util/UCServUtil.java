package com.energicube.eno.common.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

/**
 * UCService服务连接类
 * */
public class UCServUtil<T extends java.io.Serializable> {

	private static final Log logger = LogFactory.getLog(UCServUtil.class);

	/**
	 * 获取服务查询结果
	 * 
	 * @param wsurl
	 *            服务地址
	 * 
	 * @return 结果列表
	 * */
	public List<T> getUCServResult(Class<T> entityClass, String wsurl) {

		List<T> result = new ArrayList<T>();
		if (wsurl == null || "".equals(wsurl))
			throw new NullPointerException("服务地址不能为空");
		String resultString = "";
		try {
			URL url = new URL(wsurl);
			URLConnection URLconnection = url.openConnection();

			HttpURLConnection httpConnection = (HttpURLConnection) URLconnection;
			int responseCode = httpConnection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {

				// 获取查询结果
				InputStream urlStream = httpConnection.getInputStream();
				BufferedReader bufferedReader = new BufferedReader(
						new InputStreamReader(urlStream));
				String currentLine = "";
				while ((currentLine = bufferedReader.readLine()) != null) {
					resultString += currentLine;
				}
				// 转换JSON字符为LIST列表
				ObjectMapper mapper = new ObjectMapper();
				TypeFactory typeFactory = TypeFactory.defaultInstance();
				result = mapper.readValue(resultString, typeFactory
						.constructCollectionType(List.class, entityClass));
			} else {
				logger.warn("HttpURLConnection StatusCode:" + responseCode);
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * post提交数据至UCService
	 * 
	 * @param wsurl
	 *            提交地址
	 * 
	 * @param content
	 *            提交内容
	 * 
	 * */
	public void postUCServ(String wsurl, String content) {

		if (wsurl == null || "".equals(wsurl))
			throw new NullPointerException("服务地址不能为空");

		URL url = null;
		try {
			url = new URL(wsurl);
		} catch (MalformedURLException e) {
			logger.error(e);
		}
		HttpURLConnection urlConn = null;
		try {
			urlConn = (HttpURLConnection) url.openConnection();
		} catch (IOException e) {
			logger.error(e);
		}

		urlConn.setDoInput(true);
		urlConn.setDoOutput(true);
		urlConn.setUseCaches(false);

		try {
			urlConn.setRequestMethod("POST");
		} catch (ProtocolException e) {
			logger.error(e);
		}

		DataOutputStream output = null;
		DataInputStream input = null;

		try {
			output = new DataOutputStream(urlConn.getOutputStream());
		} catch (IOException e) {
			logger.error(e);
		}

		try {
			output.writeBytes(content);
			output.flush();
			output.close();
		} catch (IOException e) {
			logger.error(e);
		}

		String str = null;

		try {
			input = new DataInputStream(urlConn.getInputStream());
			while (null != ((str = input.readUTF()))) {
				logger.info(str);
			}
			input.close();
		} catch (IOException e) {
			logger.error(e);
		}

	}

}
