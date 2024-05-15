package vin.suki.apisdk.model.request;

import vin.suki.apisdk.model.enums.RequestMethodEnum;
import vin.suki.apisdk.model.params.RandomWallpaperParams;
import vin.suki.apisdk.model.response.RandomWallpaperResponse;
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
