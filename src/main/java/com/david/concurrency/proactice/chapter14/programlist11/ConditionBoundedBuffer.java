package com.david.concurrency.proactice.chapter14.programlist11;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 *使用显式条件变量的有界缓存
 * @version 1.0.0
 *
 * @author wangwei910825@icloud.com
 *
 * @since 1.0.0
 *
 * @create 2021-07-25 11:37
 **/
public class ConditionBoundedBuffer<T> {
	protected final Lock lock = new ReentrantLock();
	//条件谓词：notFull(count < items.length)
	private final Condition notFull = lock.newCondition();
	//条件谓词：notEmpty(count > 0)
	private final Condition notEmpty = lock.newCondition();
	private final T[] items = (T[])new Object[50];
	private int tail,head,count;

	//阻塞并直到：notFull
	public void put(T x)throws InterruptedException{
		lock.lock();
		try {
			while (count == items.length){
				notFull.await();
			}
			items[tail] = x;
			if (++tail == items.length){
				tail = 0;
			}
			++count;
			notEmpty.signal();
		}finally {
			lock.unlock();
		}
	}

	//阻塞并直到：notEmpty
	public T take()throws InterruptedException{
		lock.lock();
		try {
			while (count == 0){
				notEmpty.await();
			}
			T x = items[head];
			items[head] = null;
			if (++head == items.length){
				head = 0;
			}
			--count;
			notFull.signal();
			return x;
		}finally {
			lock.unlock();
		}
	}
}
