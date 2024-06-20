package com.togetherhana.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.togetherhana.base.BaseResponse;

@RestControllerAdvice
public class ExceptionControllerAdvice {

	/**
	 * BaseException
	 */
	@ExceptionHandler(BaseException.class)
	public BaseResponse handleBaseException(BaseException e) {
		return BaseResponse.fail(e.getErrorType());
	}
}
