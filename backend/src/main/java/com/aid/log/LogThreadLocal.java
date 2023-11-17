package com.aid.log;

public class LogThreadLocal {

	public static final ThreadLocal<LogDto> logThreadLocal = new ThreadLocal<LogDto>();

	public static void set(LogDto context) {
		logThreadLocal.set(context);
	}

	public static void unset() {
		logThreadLocal.set(null);
	}

	public static LogDto get() {
		return logThreadLocal.get();
	}

}
