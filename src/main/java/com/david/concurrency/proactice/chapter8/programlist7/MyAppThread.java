package com.david.concurrency.proactice.chapter8.programlist7;

import java.nio.channels.Pipe;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * 定制Thread基类
 * @version 1.0.0
 *
 * @author wangwei910825@icloud.com
 *
 * @since 1.0.0
 *
 * @create 2021-07-18 22:34
 **/
public class MyAppThread extends Thread{

	public static final String DEFAULT_NAME = "MyAppThread";
	private static volatile  boolean debugLifecycle = false;
	private static final AtomicInteger created = new AtomicInteger();
	protected static final AtomicInteger alive = new AtomicInteger();
	private static final Logger LOGGER = Logger.getAnonymousLogger();
	public MyAppThread(Runnable runnable,String name){
		super(runnable,name + "-" + created.incrementAndGet());
		setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler(){
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				LOGGER.log(Level.SEVERE,"UNCAUGHT in thread " + t.getName(),e);
			}
		});
	}

	@Override
	public void run() {
		//复制debug标志以确保一致的值
		boolean debug = debugLifecycle;
		if (debug) {
			LOGGER.log(Level.FINE,"created " + getName());
			try {
				alive.incrementAndGet();
				super.run();
			}finally {
				alive.incrementAndGet();
				if (debug) {
					LOGGER.log(Level.FINE,"Exiting " + getName());
				}
			}
		}
	}

	public static int getThreadsCreated(){
		return created.get();
	}
	public static int getThreadsAlive(){
		return alive.get();
	}

	public static boolean getDebug(){
		return debugLifecycle;
	}
	public static void setDebug(boolean b) {
		debugLifecycle = b;
	}
}
