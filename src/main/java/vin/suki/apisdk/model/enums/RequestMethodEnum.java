package vin.suki.apisdk.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RequestMethodEnum {

	GET("GET", "GET"),

	POST("POST", "POST");

	private final String text;

	private final String value;

}
