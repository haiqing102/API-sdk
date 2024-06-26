package vin.suki.apisdk.model.request;

import vin.suki.apisdk.model.enums.RequestMethodEnum;
import vin.suki.apisdk.model.params.PoisonousChickenSoupParams;
import vin.suki.apisdk.model.response.PoisonousChickenSoupResponse;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class PoisonousChickenSoupRequest extends BaseRequest<PoisonousChickenSoupParams, PoisonousChickenSoupResponse> {

	@Override
	public String getPath() {
		return "/api/interface/poisonousChickenSoup";
	}

	/**
	 * 获取响应类
	 */
	@Override
	public Class<PoisonousChickenSoupResponse> getResponseClass() {
		return PoisonousChickenSoupResponse.class;
	}

	@Override
	public String getMethod() {
		return RequestMethodEnum.GET.getValue();
	}

}
