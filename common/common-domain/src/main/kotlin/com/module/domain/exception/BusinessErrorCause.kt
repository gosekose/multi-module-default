package com.module.domain.exception

enum class BusinessErrorCause(
    val message: String,
) {
    UNAUTHORIZED("권한이 없습니다."),

    MEMBER_NOT_FOUND("정보를 찾을 수 없습니다."),
    INVALID_PASSWORD("비밀번호가 일치하지 않습니다."),

    INVALID_PHONE_NUMBER_FORMAT("휴대폰 번호는 숫자와 하이픈으로 구성된 xxx-xxxx-xxxx 형식이어야 합니다."),
    PHONE_NUMBER_ALREADY_EXISTS("휴대폰 번호로 가입한 회원이 이미 존재합니다."),
    PHONE_NUMBER_NOT_FOUND("휴대폰 번호로 가입한 회원이 존재하지 않습니다."),

    DISTRIBUTE_LOCK_TRY_FAILED("이미 다른 요청이 진행 중 입니다. 잠시 후에 다시 시도해주세요."),

    SIZE_NOT_AVAILABLE("사이즈는 small, large 중 하나여야 합니다."),

    PRODUCT_NOT_FOUND("상품 정보를 찾을 수 없습니다."),
    FILE_NOT_FOUND("파일 정보를 찾을 수 없습니다."),

    INTERNAL_SERVER_ERROR("일시적인 문제가 발생하였습니다. 고객센터에 문의해주세요."),
}
