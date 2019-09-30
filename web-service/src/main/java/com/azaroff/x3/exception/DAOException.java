package com.azaroff.x3.exception;

import java.math.BigDecimal;

public class DAOException extends RuntimeException {

    private BigDecimal errorCode;

    public DAOException(BigDecimal errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public DAOException(Throwable cause) {
        super(cause);
    }

    public BigDecimal getErrorCode() {
        return errorCode;
    }

    public DAOException(String message) {
        super(message);
    }
}