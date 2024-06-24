package com.togetherhana.exception;

import org.springframework.http.HttpStatus;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorType {

	/**
	 * 400 BAD REQUEST
	 */
	BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
	JOINED_MEMBER(HttpStatus.BAD_REQUEST, "이미 가입된 멤버입니다."),

	/**
	 * 401 UNAUTHROZIED
	 */
	UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증되지 않았습니다."),

	/**
	 * 404 NOT FOUND
	 */
	NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 리소스입니다."),
	NO_SPORTSCLUB_INFO(HttpStatus.NOT_FOUND, "구단 정보가 존재하지 않습니다."),
	INVAILD_MEMBER_IDX(HttpStatus.NOT_FOUND, "존재하지 않는 멤버입니다."),

	/**
	 * 500 INTERNAL SERVER
	 */
	INTERNAL_SERVER(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러가 발생했습니다");

	private final HttpStatus httpStatus;
	private final String message;

	public int getHttpStatusCode() {
		return httpStatus.value();
	}
}
