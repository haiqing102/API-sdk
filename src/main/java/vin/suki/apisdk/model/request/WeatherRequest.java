package vin.suki.apisdk.model.request;

import vin.suki.apisdk.model.enums.RequestMethodEnum;
import vin.suki.apisdk.model.params.WeatherParams;
import vin.suki.apisdk.model.response.ResultResponse;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class WeatherRequest extends BaseRequest<WeatherParams, ResultResponse> {

	@Override
	public String getPath() {
		return "/api/interface/weather";
	}

	/**
	 * 获取响应类
	 */
	@Override
	public Class<ResultResponse> getResponseClass() {
		return ResultResponse.class;
	}

	@Override
	public String getMethod() {
		return RequestMethodEnum.GET.getValue();
	}

}
