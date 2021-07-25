package com.david.concurrency.proactice.chapter14.programlist3;

import com.david.concurrency.proactice.chapter14.programlist2.BaseBoundedBuffer;

/**
 *
 *当不满足前提条件时，有界缓存不会执行相应的操作
 * @version 1.0.0
 *
 * @author wangwei910825@icloud.com
 *
 * @since 1.0.0
 *
 * @create 2021-07-25 08:49
 **/
public class GrumpyBoundedBuffer<V> extends BaseBoundedBuffer<V> {

	public GrumpyBoundedBuffer(int capacity) {
		super(capacity);
	}

	public synchronized void put(V v) throws BufferFullException{
		if (isFull()){
			throw new BufferFullException();
		}
		doPut(v);
	}

	public synchronized V take() throws BufferEmptyException{
		if (isEmpty()){
			throw new BufferEmptyException();
		}
		return doTake();
	}
}
