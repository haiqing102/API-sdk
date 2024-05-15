package vin.suki.apisdk.config;

import vin.suki.apisdk.client.ApiClient;
import vin.suki.apisdk.service.ApiService;
import vin.suki.apisdk.service.impi.ApiServiceImpl;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@EnableConfigurationProperties(ApiClient.class)
public class ApiClientAutoConfig {

	@Bean
	public ApiService apiService() {
		return new ApiServiceImpl();
	}

}
