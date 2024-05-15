package vin.suki.apisdk.service;

import vin.suki.apisdk.client.ApiClient;
import vin.suki.apisdk.exception.BusinessException;
import vin.suki.apisdk.model.request.*;
import vin.suki.apisdk.model.response.LoveResponse;
import vin.suki.apisdk.model.response.PoisonousChickenSoupResponse;
import vin.suki.apisdk.model.response.RandomWallpaperResponse;
import vin.suki.apisdk.model.response.ResultResponse;

public interface ApiService {

	/**
	 * 通用请求
	 */
	<O, T extends ResultResponse> T request(BaseRequest<O, T> request) throws BusinessException;

	/**
	 * 通用请求
	 */
	<O, T extends ResultResponse> T request(ApiClient apiClient, BaseRequest<O, T> request) throws BusinessException;

	/**
	 * 随机毒鸡汤
	 */
	PoisonousChickenSoupResponse getPoisonousChickenSoup() throws BusinessException;

	/**
	 * 随机毒鸡汤
	 */
	PoisonousChickenSoupResponse getPoisonousChickenSoup(ApiClient apiClient) throws BusinessException;

	/**
	 * 随机壁纸
	 */
	RandomWallpaperResponse getRandomWallpaper(RandomWallpaperRequest request) throws BusinessException;

	/**
	 * 随机壁纸
	 */
	RandomWallpaperResponse getRandomWallpaper(ApiClient apiClient, RandomWallpaperRequest request) throws BusinessException;

	/**
	 * 随机情话
	 */
	LoveResponse randomLoveTalk() throws BusinessException;

	/**
	 * 随机情话
	 */
	LoveResponse randomLoveTalk(ApiClient apiClient) throws BusinessException;

	/**
	 * 星座运势
	 */
	ResultResponse horoscope(HoroscopeRequest request) throws BusinessException;

	/**
	 * 星座运势
	 */
	ResultResponse horoscope(ApiClient apiClient, HoroscopeRequest request) throws BusinessException;

	/**
	 * 获取ip信息
	 */
	ResultResponse getIpInfo(ApiClient apiClient, IpInfoRequest request) throws BusinessException;

	/**
	 * 获取ip信息
	 */
	ResultResponse getIpInfo(IpInfoRequest request) throws BusinessException;

	/**
	 * 获取天气信息
	 */
	ResultResponse getWeatherInfo(ApiClient apiClient, WeatherRequest request) throws BusinessException;

	/**
	 * 获取天气信息
	 */
	ResultResponse getWeatherInfo(WeatherRequest request) throws BusinessException;

}
