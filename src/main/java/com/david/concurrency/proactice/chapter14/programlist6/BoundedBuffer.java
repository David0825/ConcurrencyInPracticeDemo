package com.david.concurrency.proactice.chapter14.programlist6;

import com.david.concurrency.proactice.chapter14.programlist2.BaseBoundedBuffer;

/**
 *
 *使用条件队列实现的有界缓存
 * @version 1.0.0
 *
 * @author wangwei910825@icloud.com
 *
 * @since 1.0.0
 *
 * @create 2021-07-25 10:20
 **/
public class BoundedBuffer<V> extends BaseBoundedBuffer<V> {

	public BoundedBuffer(int capacity) {
		super(capacity);
	}
	//条件谓词：not-full(!isFull())
	//条件谓词：not-empty(!isEmpty())
	public synchronized void put(V v) throws InterruptedException{
		while (isFull()){
			wait();
		}
		doPut(v);
		notifyAll();
	}

	public synchronized  V take() throws InterruptedException{
		while (isEmpty()){
			wait();
		}
		V v = doTake();
		notifyAll();
		return v;
	}
}
