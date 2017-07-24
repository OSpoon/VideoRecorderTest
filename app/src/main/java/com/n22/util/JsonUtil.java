package com.n22.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.n22.util.encoder.Config;
import com.n22.util.encoder.ThreeDES;


/**
 * @author yang
 */
public class JsonUtil {
	
	/**
	 * 投保规则校验&核保出单请求
	 */
	public static final String POLICY = "policy";
	
	/**
	 * 检查更新
	 * {@link}UpgradeReq
	 */
	public static final String UPGRADE = "upgrade";

	
	/**
	 * 支付账户信息修改接口
	 */
	public static final String ORDER = "order";

	private static Gson gson;

	/**
	 * Json转对象
	 */
	public static <T> T jsonToObject(String json, Class<T> classOfT) {
		initGson();
		if(json.charAt(0) != '{'){
			try {
				json = ThreeDES.decode(json, Config.ORG_VALIDATE_CODE);
			} catch (Exception e) {
			}
		}
		return gson.fromJson(json, classOfT);
	}

	/**
	 * 对象转Json
	 */
	public static String objectToJson(Object object) {
		initGson();
		String json = gson.toJson(object);
		return json;
	}

	/**
	 * json解析回ArrayList,参数为new TypeToken<ArrayList<T>>() {},必须加泛型
	 */
	public static List<?> jsonToList(String json, TypeToken<?> token) {
		return (List<?>) jsonToObject(json, token.getType());
	}
	

	/**
	 * Json转对象
	 */
	public static Object jsonToObject(String json, Type type) {
		initGson();
		return gson.fromJson(json, type);
	}

	private static void initGson() {
		if (gson == null) {
			GsonBuilder builder = new GsonBuilder();
			gson = builder.serializeNulls().disableHtmlEscaping().setDateFormat("yyyy-MM-ddHH:mm:ss").create();
		}
	}
	
	public static Gson getGson(){
		initGson();
		return gson;
	}

	/**
	 * 解析网络响应报文体
	 * 根据key值从json中解析出的map对象中获取type类型对应的对象
	 * @param key
	 * @return
	 * @throws Exception 
	 */
	public static <T> T jsonToObject(Class<T> type, String encoded, String key) throws Exception{
		initGson();
		String jsonStr = ThreeDES.decode(encoded, Config.ORG_VALIDATE_CODE);
		HashMap<String, T> map = gson.fromJson(jsonStr , new TypeToken<HashMap<String,T>>(){}.getType());
		if(map != null){
			return map.get(key);
		}
		return null;
	}
	
	/**
	 * 将请求对象根据对应的key值存入map， 转换为json字符串，加密并返回
	 * @param obj
	 * @param key
	 * @return
	 * @throws Exception 
	 */
	public static String objectToJson(Object obj, String key) throws Exception{
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put(key, obj);
		String jsonStr = objectToJson(map);
		String encodedStr = ThreeDES.encode(jsonStr, Config.ORG_VALIDATE_CODE);
		return encodedStr;
	}

}
