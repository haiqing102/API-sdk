package vin.suki.apisdk.model.request;

import vin.suki.apisdk.model.enums.RequestMethodEnum;
import vin.suki.apisdk.model.params.IpInfoParams;
import vin.suki.apisdk.model.response.ResultResponse;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class IpInfoRequest extends BaseRequest<IpInfoParams, ResultResponse> {

	@Override
	public String getPath() {
		return "/api/interface/ipInfo";
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
