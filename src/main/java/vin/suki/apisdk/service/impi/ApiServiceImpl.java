package vin.suki.apisdk.service.impi;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import vin.suki.apisdk.client.ApiClient;
import vin.suki.apisdk.exception.BusinessException;
import vin.suki.apisdk.exception.ErrorResponse;
import vin.suki.apisdk.exception.ResponseCode;
import vin.suki.apisdk.model.request.*;
import vin.suki.apisdk.model.request.*;
import vin.suki.apisdk.model.response.LoveResponse;
import vin.suki.apisdk.model.response.PoisonousChickenSoupResponse;
import vin.suki.apisdk.model.response.RandomWallpaperResponse;
import vin.suki.apisdk.model.response.ResultResponse;
import vin.suki.apisdk.service.ApiService;
import vin.suki.apisdk.util.SignUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Data
public class ApiServiceImpl implements ApiService {

	@Resource
	private ApiClient apiClient;

	/**
	 * 网关地址
	 */
	private String gatewayHost;

	/**
	 * 初始化gatewayHost
	 */
	@PostConstruct
	public void initHost() {
		this.gatewayHost = apiClient.getGatewayHost();
	}

	/**
	 * 通用请求
	 */
	@Override
	public <O, T extends ResultResponse> T request(BaseRequest<O, T> request) throws BusinessException {
		try {
			return res(request);
		} catch (Exception e) {
			throw new BusinessException(ResponseCode.OPERATION_ERROR, e.getMessage());
		}
	}

	/**
	 * 通用请求
	 */
	@Override
	public <O, T extends ResultResponse> T request(ApiClient apiClient, BaseRequest<O, T> request) throws BusinessException {
		checkConfig(apiClient);
		return request(request);
	}

	/**
	 * 检查配置
	 */
	public void checkConfig(ApiClient apiClient) throws BusinessException {
		if (apiClient == null && this.getApiClient() == null) {
			throw new BusinessException(ResponseCode.NO_AUTH_ERROR, "请先配置密钥AccessKey/SecretKey");
		}
		if (apiClient != null && !StringUtils.isAnyBlank(apiClient.getAccessKey(), apiClient.getSecretKey())) {
			this.setApiClient(apiClient);
		}
	}

	/**
	 * 执行请求
	 */
	private <O, T extends ResultResponse> HttpResponse doRequest(BaseRequest<O, T> request) throws BusinessException {
		try (HttpResponse httpResponse = getHttpRequestByRequestMethod(request).execute()) {
			return httpResponse;
		} catch (Exception e) {
			throw new BusinessException(ResponseCode.OPERATION_ERROR, e.getMessage());
		}
	}

	/**
	 * 通过请求方法获取http响应
	 */
	private <O, T extends ResultResponse> HttpRequest getHttpRequestByRequestMethod(BaseRequest<O, T> request) throws BusinessException {
		if (ObjectUtils.isEmpty(request)) {
			throw new BusinessException(ResponseCode.OPERATION_ERROR, "请求参数错误");
		}
		String path = request.getPath().trim();
		String method = request.getMethod().trim().toUpperCase();

		if (ObjectUtils.isEmpty(method)) {
			throw new BusinessException(ResponseCode.OPERATION_ERROR, "请求方法不存在");
		}
		if (StringUtils.isBlank(path)) {
			throw new BusinessException(ResponseCode.OPERATION_ERROR, "请求路径不存在");
		}

		path = path.substring(path.indexOf("/api/"));
		log.info("【ApiService】请求方法：{}，请求路径：{}，请求参数：{}", method, path, request.getRequestParams());

		HttpRequest httpRequest;
		switch (method) {
			case "GET": {
				httpRequest = HttpRequest.get(splicingGetRequest(request, path));
				break;
			}
			case "POST": {
				httpRequest = HttpRequest.post(gatewayHost + path);
				break;
			}
			default: {
				throw new BusinessException(ResponseCode.OPERATION_ERROR, "不支持该请求");
			}
		}
		return httpRequest.addHeaders(getHeaders(JSONUtil.toJsonStr(request), apiClient)).body(JSONUtil.toJsonStr(request.getRequestParams()));
	}

	/**
	 * 拼接Get请求
	 */
	private <O, T extends ResultResponse> String splicingGetRequest(BaseRequest<O, T> request, String path) {
		StringBuilder urlBuilder = new StringBuilder(gatewayHost);
		// urlBuilder最后是/结尾且path以/开头的情况下，去掉urlBuilder结尾的/
		if (urlBuilder.toString().endsWith("/")) {
			urlBuilder.setLength(urlBuilder.length() - 1);
		}
		urlBuilder.append(path);
		if (!request.getRequestParams().isEmpty()) {
			urlBuilder.append("?");
			for (Map.Entry<String, Object> entry : request.getRequestParams().entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue().toString();
				urlBuilder.append(key).append("=").append(value).append("&");
			}
			urlBuilder.deleteCharAt(urlBuilder.length() - 1);
		}
		log.info("【ApiService】请求URL：{}", urlBuilder);
		return urlBuilder.toString();
	}

	/**
	 * 获取响应数据
	 */
	public <O, T extends ResultResponse> T res(BaseRequest<O, T> request) throws BusinessException {
		if (apiClient == null || StringUtils.isAnyBlank(apiClient.getAccessKey(), apiClient.getSecretKey())) {
			throw new BusinessException(ResponseCode.NO_AUTH_ERROR, "请先配置密钥AccessKey/SecretKey");
		}
		T rsp;
		try {
			Class<T> clazz = request.getResponseClass();
			rsp = clazz.newInstance();
		} catch (Exception e) {
			throw new BusinessException(ResponseCode.OPERATION_ERROR, e.getMessage());
		}
		Map<String, Object> data;
		try (HttpResponse httpResponse = doRequest(request)) {
			String body = httpResponse.body();
			data = new HashMap<>();
			if (httpResponse.getStatus() != 200) {
				ErrorResponse errorResponse = JSONUtil.toBean(body, ErrorResponse.class);
				data.put("errorMessage", errorResponse.getMessage());
				data.put("code", errorResponse.getCode());
			} else {
				try {
					// 尝试解析为JSON对象
					data = new Gson().fromJson(body, new TypeToken<Map<String, Object>>() {
					}.getType());
				} catch (JsonSyntaxException e) {
					// 解析失败，将body作为普通字符串处理
					data.put("value", body);
				}
			}
		}
		rsp.setData(data);
		return rsp;
	}

	/**
	 * 获取请求头
	 */
	private Map<String, String> getHeaders(String body, ApiClient apiClient) {
		Map<String, String> hashMap = new HashMap<>(4);
		hashMap.put("accessKey", apiClient.getAccessKey());
		String encodedBody = SecureUtil.md5(body);
		hashMap.put("body", encodedBody);
		hashMap.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
		hashMap.put("sign", SignUtil.getSign(encodedBody, apiClient.getSecretKey()));
		return hashMap;
	}

	/**
	 * 随机毒鸡汤
	 */
	@Override
	public PoisonousChickenSoupResponse getPoisonousChickenSoup() throws BusinessException {
		PoisonousChickenSoupRequest request = new PoisonousChickenSoupRequest();
		return request(request);
	}

	/**
	 * 随机毒鸡汤
	 */
	@Override
	public PoisonousChickenSoupResponse getPoisonousChickenSoup(ApiClient apiClient) throws BusinessException {
		PoisonousChickenSoupRequest request = new PoisonousChickenSoupRequest();
		return request(apiClient, request);
	}

	/**
	 * 随机壁纸
	 */
	@Override
	public RandomWallpaperResponse getRandomWallpaper(RandomWallpaperRequest request) throws BusinessException {
		return request(request);
	}

	/**
	 * 随机壁纸
	 */
	@Override
	public RandomWallpaperResponse getRandomWallpaper(ApiClient apiClient, RandomWallpaperRequest request) throws BusinessException {
		return request(apiClient, request);
	}

	/**
	 * 随机情话
	 */
	@Override
	public LoveResponse randomLoveTalk() throws BusinessException {
		LoveRequest request = new LoveRequest();
		return request(request);
	}

	/**
	 * 随机情话
	 */
	@Override
	public LoveResponse randomLoveTalk(ApiClient apiClient) throws BusinessException {
		LoveRequest request = new LoveRequest();
		return request(apiClient, request);
	}

	/**
	 * 星座运势
	 */
	@Override
	public ResultResponse horoscope(HoroscopeRequest request) throws BusinessException {
		return request(request);
	}

	/**
	 * 星座运势
	 */
	@Override
	public ResultResponse horoscope(ApiClient apiClient, HoroscopeRequest request) throws BusinessException {
		return request(apiClient, request);
	}

	/**
	 * 获取ip信息
	 */
	@Override
	public ResultResponse getIpInfo(ApiClient apiClient, IpInfoRequest request) throws BusinessException {
		return request(apiClient, request);
	}

	/**
	 * 获取ip信息
	 */
	@Override
	public ResultResponse getIpInfo(IpInfoRequest request) throws BusinessException {
		return request(request);
	}

	/**
	 * 获取天气信息
	 */
	@Override
	public ResultResponse getWeatherInfo(ApiClient apiClient, WeatherRequest request) throws BusinessException {
		return request(apiClient, request);
	}

	/**
	 * 获取天气信息
	 */
	@Override
	public ResultResponse getWeatherInfo(WeatherRequest request) throws BusinessException {
		return request(request);
	}

}
