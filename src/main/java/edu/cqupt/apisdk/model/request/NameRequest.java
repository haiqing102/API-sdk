package edu.cqupt.apisdk.model.request;

import edu.cqupt.apisdk.model.enums.RequestMethodEnum;
import edu.cqupt.apisdk.model.params.NameParams;
import edu.cqupt.apisdk.model.response.NameResponse;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class NameRequest extends BaseRequest<NameParams, NameResponse> {

	@Override
	public String getPath() {
		return "/api/interface/name";
	}

	/**
	 * 获取响应类
	 */
	@Override
	public Class<NameResponse> getResponseClass() {
		return NameResponse.class;
	}

	@Override
	public String getMethod() {
		return RequestMethodEnum.GET.getValue();
	}

}
