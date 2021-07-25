package com.david.concurrency.proactice.chapter14.programlist3;

/**
 *
 *
 * @version 1.0.0
 *
 * @author wangwei910825@icloud.com
 *
 * @since 1.0.0
 *
 * @create 2021-07-25 08:52
 **/
public class BufferFullException extends Exception{

	public BufferFullException() {
	}

	public BufferFullException(String message) {
		super(message);
	}

	public BufferFullException(String message, Throwable cause) {
		super(message, cause);
	}

	public BufferFullException(Throwable cause) {
		super(cause);
	}

	public BufferFullException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
