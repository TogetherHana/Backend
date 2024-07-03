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
	WRONG_PHONE_NUMBER(HttpStatus.BAD_REQUEST,"올바르지 않은 전화번호입니다."),
	WRONG_CERTIFICATION_NUMBER(HttpStatus.BAD_REQUEST, "일치하지 않는 인증번호입니다."),
	WRONG_ACCOUNT_NUMBER(HttpStatus.BAD_REQUEST,"올바르지 않은 계좌번호입니다."),
	JOINED_MEMBER(HttpStatus.BAD_REQUEST, "이미 가입된 멤버입니다."),
	ALREADY_GAME_CREATED(HttpStatus.BAD_REQUEST, "이미 진행중인 게임이 있습니다."),

	WRONG_PASSWORD(HttpStatus.BAD_REQUEST,"계좌 비밀번호가 일치하지 않습니다."),
	NOT_ENOUGH_BALANCE(HttpStatus.BAD_REQUEST, "계좌 잔액이 부족합니다."),

	IS_DEADLINE_PASSED(HttpStatus.BAD_REQUEST, "투표마감시간이 지났습니다."),
	ALREADY_MYTEAM_PICKED(HttpStatus.BAD_REQUEST, "이미 응원팀을 설정했습니다."),

	NOT_ENOUGH_MILEAGE_AMOUNT(HttpStatus.BAD_REQUEST, "마일리지 잔액이 부족합니다."),
	WRONG_INVITATION_CODE(HttpStatus.BAD_REQUEST, "일치하는 초대코드가 없습니다."),
	NOT_COINCIDE_INVITATION(HttpStatus.BAD_REQUEST, "초대장 코드와 가입하려는 모임통장이 일치하지 않습니다."),
	ALREADY_PICK(HttpStatus.BAD_REQUEST, "이미 참여한 이벤트입니다."),
	ALREADY_START(HttpStatus.BAD_REQUEST, "이미 경기가 시작하였습니다."),

	/**
	 * 401 UNAUTHROZIED
	 */
	UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증되지 않았습니다."),

	EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "만료된 토큰입니다."),
	INVALID_JWT(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
	INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 리프레시 토큰입니다."),
	UNSUPPORTED_TOKEN(HttpStatus.UNAUTHORIZED, "지원하지 않는 토큰입니다."),
	NO_CLAIMS_JWT(HttpStatus.UNAUTHORIZED,"인증된 정보가 없습니다."),
	UNKNOWN_JWT_ERROR(HttpStatus.UNAUTHORIZED, "인증에 실패하였습니다."),
	LEADER_PRIVILEGES_REQUIRED(HttpStatus.UNAUTHORIZED, "총무 권한이 필요합니다."),


	/**
	 * 404 NOT FOUND
	 */
	NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 리소스입니다."),
	NO_SPORTSCLUB_INFO(HttpStatus.NOT_FOUND, "구단 정보가 존재하지 않습니다."),
	INVALID_MEMBER_IDX(HttpStatus.NOT_FOUND, "존재하지 않는 멤버입니다."),
	MEMBER_BY_DEVICE_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "멤버 정보를 찾을 수 없습니다."),
	SHARING_ACCOUNT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 모임통장이 존재하지 않습니다."),
	SHARING_MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 모임원이 존재하지 않습니다."),
	GAME_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 게임이 존재하지 않습니다."),
	GAME_OPTION_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 게임선택지가 존재하지 않습니다."),
	EVENT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 이벤트가 존재하지 않습니다."),

	INVAILD_MILEAGE_IDX(HttpStatus.NOT_FOUND, "마일리지 정보를 찾을 수 없습니다."),
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
