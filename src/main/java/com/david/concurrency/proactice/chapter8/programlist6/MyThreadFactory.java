package com.david.concurrency.proactice.chapter8.programlist6;

import com.david.concurrency.proactice.chapter8.programlist7.MyAppThread;

import java.util.concurrent.ThreadFactory;

/**
 *
 * 自定义的线程工程
 * @version 1.0.0
 *
 * @author wangwei910825@icloud.com
 *
 * @since 1.0.0
 *
 * @create 2021-07-18 22:33
 **/
public class MyThreadFactory implements ThreadFactory {

	private final String poolName;

	public MyThreadFactory(String poolName) {
		this.poolName = poolName;
	}

	@Override
	public Thread newThread(Runnable r) {
		return new MyAppThread(r,poolName);
	}
}
