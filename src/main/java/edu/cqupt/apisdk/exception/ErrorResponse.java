package edu.cqupt.apisdk.exception;

import lombok.Data;

@Data
public class ErrorResponse {

	/**
	 * 错误码
	 */
	private int code;

	/**
	 * 错误消息
	 */
	private String message;

}
