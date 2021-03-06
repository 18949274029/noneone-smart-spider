/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.github.quartzweb.log;

/**
 * log4j2 日志记录
 * @author leisure
 */
public class Log4J2Logger implements QuartzWebLogger {

    private static final org.apache.logging.log4j.Logger LOGGER = org.apache.logging.log4j.LogManager
            .getLogger(INTERNAL_LOGGER_NAME);

    /** {@inheritDoc} */
    @Override
    public void info(String msg) {
        LOGGER.info(msg);
    }

    /** {@inheritDoc} */
    @Override
    public void info(String msg, Throwable throwable) {
        LOGGER.info(msg, throwable);
    }

    /** {@inheritDoc} */
    @Override
    public void debug(String msg) {
        LOGGER.debug(msg);
    }

    /** {@inheritDoc} */
    @Override
    public void debug(String msg, Throwable throwable) {
        LOGGER.debug(msg, throwable);
    }

    /** {@inheritDoc} */
    @Override
    public void warn(String msg, Throwable throwable) {
        LOGGER.warn(msg, throwable);
    }

    /** {@inheritDoc} */
    @Override
    public void error(String msg, Throwable throwable) {
        LOGGER.error(msg, throwable);
    }

}
