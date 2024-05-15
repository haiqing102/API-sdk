package vin.suki.apisdk.model.request;

import vin.suki.apisdk.model.enums.RequestMethodEnum;
import vin.suki.apisdk.model.response.LoveResponse;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class LoveRequest extends BaseRequest<String, LoveResponse> {

	@Override
	public String getPath() {
		return "/api/interface/loveTalk";
	}

	/**
	 * 获取响应类
	 */
	@Override
	public Class<LoveResponse> getResponseClass() {
		return LoveResponse.class;
	}

	@Override
	public String getMethod() {
		return RequestMethodEnum.GET.getValue();
	}

}
