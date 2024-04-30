package edu.cqupt.apisdk.config;

import edu.cqupt.apisdk.client.ApiClient;
import edu.cqupt.apisdk.service.ApiService;
import edu.cqupt.apisdk.service.impi.ApiServiceImpl;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@EnableConfigurationProperties(ApiClient.class)
public class ApiClientAutoConfig {

	@Bean
	public ApiService apiService() {
		return new ApiServiceImpl();
	}

}
