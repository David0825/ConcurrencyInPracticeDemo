package com.david.concurrency.proactice.chapter14.programlist8;

import com.david.concurrency.proactice.chapter14.programlist2.BaseBoundedBuffer;

/**
 *
 *使用条件通知
 * @version 1.0.0
 *
 * @author wangwei910825@icloud.com
 *
 * @since 1.0.0
 *
 * @create 2021-07-25 11:02
 **/
public class BoundedBuffer<V> extends BaseBoundedBuffer<V> {

	public BoundedBuffer(int capacity) {
		super(capacity);
	}

	public synchronized void put(V v)throws InterruptedException{
		while (isFull()){
			wait();
		}
		boolean wasEmpty = isEmpty();
		doPut(v);
		if (wasEmpty){
			notifyAll();
		}
	}
}
