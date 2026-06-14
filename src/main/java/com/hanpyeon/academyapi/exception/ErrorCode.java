package com.hanpyeon.academyapi.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    SERVER_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "-000", "서버 에러"),

    AUTHENTICATION_FAILED_EXCEPTION(HttpStatus.UNAUTHORIZED, "-001", "인증 불가"),
    DENIED_EXCEPTION(HttpStatus.FORBIDDEN, "-002", "허용되지 않음"),
    INVALID_PASSWORD_EXCEPTION(HttpStatus.UNAUTHORIZED, "-003", "비밀번호 오류"),
    JWT_EXPIRED_EXCEPTION(HttpStatus.UNAUTHORIZED, "-004", "만료된 JWT"),
    RE_LOGIN_REQUIRED(HttpStatus.UNAUTHORIZED, "-005", "재 로그인 필요"),
    NOT_REGISTERED_MEMBER(HttpStatus.BAD_REQUEST, "-006", "등록되지 않은 사용자"),
    NOT_OWNED_ACCOUNT(HttpStatus.FORBIDDEN, "-007", "계정 내용 변경은 소유자만 가능"),
    ACCOUNT_POLICY(HttpStatus.BAD_REQUEST, "-008", "계정 정책 위반"),
    ACCOUNT_VERIFICATION_EXCEPTION(HttpStatus.BAD_REQUEST, "-009", "본인 인증 실패"),

    METHOD_ARGUMENT_NOT_VALID_EXCEPTION(HttpStatus.BAD_REQUEST, "-101", "잘못된 JSON 필드"),
    HTTP_MESSAGE_NOT_READABLE(HttpStatus.BAD_REQUEST, "-102", "부적절한 API 요청 형식"),
    INVALID_CONTENT_TYPE(HttpStatus.BAD_REQUEST, "-103", "부적절한 Content-type"),

    ALREADY_REGISTERED(HttpStatus.BAD_REQUEST, "-201", "이미 등록된 사용자"),
    ILLEGAL_MEMBER_REGISTER_FORMAT(HttpStatus.BAD_REQUEST, "-202", "해당 유형 사용자 등록 필수값 부재"),
    NOT_SUPPORTED_MEMBER_TYPE(HttpStatus.BAD_REQUEST, "-203", "등록 할 수 없는 사용자 유형"),


    INVALID_MEMBER_TARGET(HttpStatus.BAD_REQUEST, "-301", "잘못된 대상입니다."),
    NO_SUCH_MEMBER(HttpStatus.BAD_REQUEST, "-302", "존재하지 않는 사용자"),
    NO_SUCH_QUESTION(HttpStatus.BAD_REQUEST, "-303", "존재하지 않는 질문 게시글"),
    NO_SUCH_COMMENT(HttpStatus.BAD_REQUEST, "-304", "존재하지 않는 댓글"),
    ALREADY_SOLVED_QUESTION_EXCEPTION(HttpStatus.BAD_REQUEST, "-305", "이미 해결된 질문"),
    NOT_ADOPTED_COMMENT(HttpStatus.BAD_REQUEST, "-306", "채택되지 않은 댓글"),
    QUESTION_CONTENT_OVERSIZE(HttpStatus.BAD_REQUEST, "-307", "질문 크기 초과"),
    QUESTION_IMAGE_OVER_LENGTH(HttpStatus.BAD_REQUEST, "-308", "질문 게시글 이미지 갯수 초과"),

    ILLEGAL_QUESTION_EXCEPTION(HttpStatus.BAD_REQUEST, "-309", "잘못된 질문입니다."),
    ILLEGAL_COMMENT_EXCEPTION(HttpStatus.BAD_REQUEST, "-310", "잘못된 댓글입니다."),
    COMMENT_EXCEPTION(HttpStatus.BAD_REQUEST, "-311", "잘못된 댓글 접근입니다."),


    ILLEGAL_COURSE_NAME(HttpStatus.BAD_REQUEST, "-401", "잘못된 반 이름"),
    ILLEGAL_COURSE_STUDENT_STATE(HttpStatus.BAD_REQUEST, "-402", "사용할 수 없는 학생"),
    ILLEGAL_COURSE_STUDENT_SIZE(HttpStatus.BAD_REQUEST, "-403", "반 인원 초과"),
    NO_SUCH_COURSE_MEMBER(HttpStatus.BAD_REQUEST, "-404", "대상 찾을 수 없음"),
    NO_SUCH_COURSE(HttpStatus.BAD_REQUEST, "-405", "반을 찾을 수 없음"),
    INVALID_COURSE_ACCESS(HttpStatus.BAD_REQUEST, "-406", "잘못된 반 API 접근"),

    MEMO_DUPLICATED_EXCEPTION(HttpStatus.CONFLICT, "-407", "중복된 메모"),
    MEMO_NOT_EXIST(HttpStatus.BAD_REQUEST, "-408", "찾을 수 없는 메모"),
    MEMO_MEDIA_SEQUENCE(HttpStatus.BAD_REQUEST, "-409", "메모 미디어 순서 에러"),
    MEMO_MEDIA_DUPLICATED(HttpStatus.BAD_REQUEST, "-410", "중복된 영상 파일"),
    MEMO_CANNOT_DELETE(HttpStatus.BAD_REQUEST, "-411", "지울 수 없음"),
    MEMO_CANNOT_MODIFY(HttpStatus.BAD_REQUEST, "-412", "수정 불가"),
    MEMO_MEDIA_UPDATE_EXCEPTION(HttpStatus.BAD_REQUEST, "-413", "메모 미디어 업데이트 불가"),
    MEMO_MEDIA_DELETE_EXCEPTION(HttpStatus.BAD_REQUEST, "-414", "메모 미디어 삭제 불가"),


    CANNOT_FIND_ATTACHMENT(HttpStatus.BAD_REQUEST, "-412", "첨부파일을 찾을 수 없음"),
    CANNOT_DELETE_ATTACHMENT(HttpStatus.BAD_REQUEST, "-412", "첨부파일 지울 수 없음"),

    NOT_EXIST_DIRECTORY(HttpStatus.BAD_REQUEST, "-501", "존재하지 않는 디렉토리"),
    CANNOT_CREATE_DIRECTORY_WITH_THIS_USER(HttpStatus.BAD_REQUEST, "-502", "이 유저는 디렉터리 생성 불가"),
    ITS_NOT_YOUR_DIRECTORY(HttpStatus.BAD_REQUEST, "-503", "당신의 디렉토리가 아닙니다"),
    CANNOT_FIND_USER(HttpStatus.BAD_REQUEST, "-504", "찾을 수 없는 유저"),
    ALREADY_EXISTS_DIRECTORY_PATH(HttpStatus.BAD_REQUEST, "-505", "이미 존재하는 절대 경로"),
    ILLEGAL_PATH(HttpStatus.BAD_REQUEST, "-506", "잘못된 디렉토리 경로 입니다."),
    CANNOT_ACCESS_TO_THIS_DIRECTORY(HttpStatus.BAD_REQUEST, "-507", "개인 소유의 디렉토리 입니다"),
    DIRECTORY_CANNOT_MODIFY(HttpStatus.BAD_REQUEST, "-508", "디렉토리 수정 불가"),
    DIRECTORY_CANNOT_DELETE(HttpStatus.BAD_REQUEST, "-509", "디렉토리 삭제 불가"),

    CHUNK_GROUP_EXCEPTION(HttpStatus.BAD_REQUEST, "-601", "해당 청크 수신 불가"),
    CHUNK_SIZE_EXCEPTION(HttpStatus.BAD_REQUEST, "-602", "청크 사이즈 문제"),
    CHUNK_ACCESS_EXCEPTION(HttpStatus.BAD_REQUEST, "-603", "청크파일에 접근 불가"),
    CHUNK_HANDLE_EXCEPTION(HttpStatus.BAD_REQUEST, "-604", "청크 파일 처리 불가"),
    CHUNK_ILLEGAL_REQUEST_EXCEPTION(HttpStatus.BAD_REQUEST, "-605", "잘못된 청크 전송"),


    INVALID_UPLOAD_FILE(HttpStatus.BAD_REQUEST, "-801", "파일 검증 실패"),
    NOT_SUPPORTED_MEDIA(HttpStatus.BAD_REQUEST, "-802", "지원하지 않는 타입의 파일"),
    NO_SUCH_MEDIA(HttpStatus.BAD_REQUEST, "-803", "파일 찾기 실패"),
    MEDIA_STORE_EXCEPTION(HttpStatus.BAD_REQUEST, "-804", "파일을 저장 불가"),
    MEDIA_ACCESS_EXCEPTION(HttpStatus.BAD_REQUEST, "-805", "파일 스트리밍 불가"),
    MEDIA_DELETE_EXCEPTION(HttpStatus.BAD_REQUEST, "-806", "미디어 삭제 불가"),

    BANNER_EXCEPTION(HttpStatus.BAD_REQUEST, "-900", "배너 오류"),

    ONLINE_COURSE_EXCEPTION(HttpStatus.BAD_REQUEST, "-701", "온라인 수업 접근 오류");

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String errorDescription;

    ErrorCode(HttpStatus httpStatus, String errorCodeResponse, String errorDescription) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCodeResponse;
        this.errorDescription = errorDescription;
    }
}
