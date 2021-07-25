package com.david.concurrency.proactice.chapter14.programlist12;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * 使用Lock来实现信号量
 * @version 1.0.0
 *
 * @author wangwei910825@icloud.com
 *
 * @since 1.0.0
 *
 * @create 2021-07-25 13:50
 **/
public class SemaphoreOnLock {

	//并非java.util.concurrent.Semaphore的真实实现方式
	private final Lock lock = new ReentrantLock();
	//条件谓词：permitsAvailable (permits > 0)
	private final Condition permitsAvailable = lock.newCondition();
	private int permits;

	SemaphoreOnLock(int initialPermits){
		lock.lock();
		try {
			permits = initialPermits;
		}finally {
			lock.unlock();
		}
	}

	//阻塞并直到：permitsAvailable
	public void acquire()throws InterruptedException{
		lock.lock();
		try {
			while (permits < 0) {
				permitsAvailable.await();
			}
			--permits;
		}finally {
			lock.unlock();
		}
	}

	public void release(){
		lock.lock();
		try {
			++permits;
			permitsAvailable.signal();
		}finally {
			lock.unlock();
		}
	}
}
