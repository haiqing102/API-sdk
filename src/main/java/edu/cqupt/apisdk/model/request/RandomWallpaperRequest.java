package edu.cqupt.apisdk.model.request;

import edu.cqupt.apisdk.model.enums.RequestMethodEnum;
import edu.cqupt.apisdk.model.params.RandomWallpaperParams;
import edu.cqupt.apisdk.model.response.RandomWallpaperResponse;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class RandomWallpaperRequest extends BaseRequest<RandomWallpaperParams, RandomWallpaperResponse> {

	@Override
	public String getPath() {
		return "/api/interface/randomWallpaper";
	}

	/**
	 * 获取响应类
	 */
	@Override
	public Class<RandomWallpaperResponse> getResponseClass() {
		return RandomWallpaperResponse.class;
	}

	@Override
	public String getMethod() {
		return RequestMethodEnum.GET.getValue();
	}

}
