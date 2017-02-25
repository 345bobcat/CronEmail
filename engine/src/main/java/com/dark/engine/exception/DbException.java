package com.dark.engine.exception;

/**
 * Created by jinwenzhang on 16/12/29.
 */
public class DbException extends RuntimeException {
    /**
     * Instantiates a new ScheduleException
     * @param e
     */
    public DbException(Throwable e) {
        super(e);
    }

    public DbException(String message) {
        super(message);
    }

    public DbException(String message, Throwable cause) {
        super(message, cause);
    }
}
