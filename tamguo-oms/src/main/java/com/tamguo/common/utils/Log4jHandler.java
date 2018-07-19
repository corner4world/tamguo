package com.tamguo.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

public class Log4jHandler extends AbstractRunningLogHandler {

	private static final Logger logger = LoggerFactory.getLogger(Log4jHandler.class);

	@Override
	public void info(String msg, String fqnOfCallingClass) {
		logger.info(fqnOfCallingClass, Level.INFO, msg, null);
	}

	@Override
	public void info(String msg, Throwable t, String fqnOfCallingClass) {
		logger.info(fqnOfCallingClass, Level.INFO, msg, t);
	}

	@Override
	public void error(String msg, String fqnOfCallingClass) {
		logger.error(fqnOfCallingClass, Level.ERROR, msg, null);
	}

	@Override
	public void error(String msg, Throwable t, String fqnOfCallingClass) {
		logger.error(fqnOfCallingClass, Level.ERROR, msg, t);
	}

}
