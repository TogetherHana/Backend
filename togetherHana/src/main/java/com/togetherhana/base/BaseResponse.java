package com.togetherhana.base;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.togetherhana.exception.ErrorType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class BaseResponse<T> {
	private final Boolean isSuccess;
	private final String message;
	private final int status;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private T data;

	public static <T> BaseResponse<T> success(T result) {
		return new BaseResponse<>(Boolean.TRUE, HttpStatus.OK.toString(), HttpStatus.OK.value(), result);
	}

	public static  <T> BaseResponse<T> fail(ErrorType errorType) {
		return new BaseResponse<>(Boolean.FALSE, errorType.getMessage(), errorType.getHttpStatusCode());
	}

}
