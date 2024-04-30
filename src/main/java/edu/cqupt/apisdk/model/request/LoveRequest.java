package edu.cqupt.apisdk.model.request;

import edu.cqupt.apisdk.model.enums.RequestMethodEnum;
import edu.cqupt.apisdk.model.response.LoveResponse;
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
