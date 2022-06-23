package com.hhu.other.cat.exception;

/**
 * @author jacks
 * @date 2022/4/6
 */
public class CatCommonException extends RuntimeException {
    public static final String DEFAULT_CODE = "undefined exception";
    private static final long serialVersionUID = 7965627573956949291L;

    private String code;

    public CatCommonException() {
        this.code = DEFAULT_CODE;
    }

    public CatCommonException(String code) {
        this.code = code;
    }

    public CatCommonException(String message, String code) {
        super(message);
        this.code = code;
    }

    public CatCommonException(String message, Throwable cause, String code) {
        super(message, cause);
        this.code = code;
    }

    public CatCommonException(Throwable cause, String code) {
        super(cause);
        this.code = code;
    }

    public CatCommonException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace,
        String code) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
    }
}
