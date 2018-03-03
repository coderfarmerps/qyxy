package com.qyxy.record.common;

import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 *
 * @author luxiaoyong
 * @date 2018/2/25
 */
@Data
public class ServiceException extends RuntimeException {
    private static final long serialVersionUID = -3433662187706932470L;

    private int code;
    private String message;

    public ServiceException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ServiceException(String message) {
        this.code = HttpStatus.BAD_REQUEST.value();
        this.message = message;
    }
}