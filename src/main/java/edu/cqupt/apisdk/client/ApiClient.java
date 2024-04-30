package edu.cqupt.apisdk.client;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@NoArgsConstructor
@ConfigurationProperties(prefix = "api.client")
public class ApiClient {

	/**
	 * 访问密码
	 */
	private String accessKey;

	/**
	 * 安全密钥
	 */
	private String secretKey;

	/**
	 * 网关地址
	 */
	private String gatewayHost = "http://43.138.216.214:9000";

	public ApiClient(String accessKey, String secretKey) {
		this.accessKey = accessKey;
		this.secretKey = secretKey;
	}

}
