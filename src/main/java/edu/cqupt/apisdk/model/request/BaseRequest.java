package edu.cqupt.apisdk.model.request;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import edu.cqupt.apisdk.model.response.ResultResponse;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseRequest<O, T extends ResultResponse> {

	private Map<String, Object> requestParams = new HashMap<>();

	public abstract String getMethod();

	/**
	 * 获取路径
	 */
	public abstract String getPath();

	/**
	 * 获取响应类
	 */
	public abstract Class<T> getResponseClass();

	@JsonAnyGetter
	public Map<String, Object> getRequestParams() {
		return requestParams;
	}

	public void setRequestParams(O params) {
		this.requestParams = new Gson().fromJson(JSONUtil.toJsonStr(params), new TypeToken<Map<String, Object>>() {
		}.getType());
	}

}