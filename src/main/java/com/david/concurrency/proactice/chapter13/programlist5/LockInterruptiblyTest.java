package com.david.concurrency.proactice.chapter13.programlist5;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 *可中断的锁的获取操作
 * @version 1.0.0
 *
 * @author wangwei910825@icloud.com
 *
 * @since 1.0.0
 *
 * @create 2021-07-24 10:24
 **/
public class LockInterruptiblyTest {

	Lock lock = new ReentrantLock();

	public boolean sendOnSharedLine(String message) throws InterruptedException{
		lock.lockInterruptibly();
		try {
			return cancellableSendOnSharedLine(message);
		}finally {
			lock.unlock();
		}
	}

	private boolean cancellableSendOnSharedLine(String message) throws InterruptedException{
		return false;
	}
}
