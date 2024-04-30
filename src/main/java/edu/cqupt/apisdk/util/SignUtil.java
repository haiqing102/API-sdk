package edu.cqupt.apisdk.util;

import cn.hutool.crypto.digest.MD5;
import cn.hutool.json.JSONUtil;

public class SignUtil {

	public static String getSign(String body, String secretKey) {
		return MD5.create().digestHex(JSONUtil.toJsonStr(body) + '.' + secretKey);
	}

}
