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
 * @create 2021-07-25 08:54
 **/
public class BufferEmptyException extends Exception{

	public BufferEmptyException() {
		super();
	}

	public BufferEmptyException(String message) {
		super(message);
	}

	public BufferEmptyException(String message, Throwable cause) {
		super(message, cause);
	}

	public BufferEmptyException(Throwable cause) {
		super(cause);
	}

	protected BufferEmptyException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
