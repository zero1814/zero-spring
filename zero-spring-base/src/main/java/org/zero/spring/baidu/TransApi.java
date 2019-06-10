package org.zero.spring.baidu;

import java.util.HashMap;
import java.util.Map;

import zero.commons.basics.HttpConnect;
import zero.commons.basics.MD5Util;

/**
 * 
 * 类: TransApi <br>
 * 描述: 百度翻译开发平台开发接口 <br>
 * 作者: zhy<br>
 * 时间: 2019年6月3日 下午2:02:15
 */
public class TransApi {

	private static final String TRANS_API_HOST = "http://api.fanyi.baidu.com/api/trans/vip/translate";

	/**
	 * APP ID
	 */
	private String appid;
	private String securityKey;

	public TransApi(String appid, String securityKey) {
		this.appid = appid;
		this.securityKey = securityKey;
	}

	public String getTransResult(String query, String from, String to) {
		Map<String, String> params = buildParams(query, from, to);
		return HttpConnect.doGet(TRANS_API_HOST, params);
	}

	private Map<String, String> buildParams(String query, String from, String to) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("q", query);
		params.put("from", from);
		params.put("to", to);
		params.put("appid", appid);
		// 随机数
		String salt = String.valueOf(System.currentTimeMillis());
		params.put("salt", salt);

		// 签名
		String src = appid + query + salt + securityKey; // 加密前的原文
		params.put("sign", MD5Util.md5Hex(src));
		return params;
	}

	public static void main(String[] args) {
		TransApi trans = new TransApi("20190603000304418", "SKuRUqrpbQl6en5BmXgw");
		String result = trans.getTransResult("\r\n" + "\r\n" + "源语言语种不确定时可设置为 auto，目标语言语种不可设置为 auto。\r\n" + "", "zh",
				"en");

		System.out.println(result);
	}

}
