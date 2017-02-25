package com.dark.service.cron.exception;

/**
 * Created by jinwenzhang on 16/12/29.
 */
public class ScheduleException extends RuntimeException {
    /**
     * Instantiates a new ScheduleException
     * @param e
     */
    public ScheduleException(Throwable e) {
        super(e);
    }

    public ScheduleException(String message) {
        super(message);
    }

    public ScheduleException(String message, Throwable cause) {
        super(message, cause);
    }
}
