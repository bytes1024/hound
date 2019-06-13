package com.bytes.hound.collect.exception;

import lombok.Data;

/**
 * @author 江浩
 */
@Data
public class BaseException extends RuntimeException {

    private String code;

    private String message;

    public BaseException(String message) {
        this.message = message;
    }

    public BaseException(String code, String message) {
        super(message);
        this.code = code;
    }

}
