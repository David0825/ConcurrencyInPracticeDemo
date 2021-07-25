package com.david.concurrency.proactice.chapter14.programlist5;

import com.david.concurrency.proactice.chapter14.programlist2.BaseBoundedBuffer;

/**
 *
 *通过轮询与休眠来实现简单的阻塞
 * @version 1.0.0
 *
 * @author wangwei910825@icloud.com
 *
 * @since 1.0.0
 *
 * @create 2021-07-25 09:02
 **/
public class SleepyBoundedBuffer<V> extends BaseBoundedBuffer<V> {

	protected SleepyBoundedBuffer(int capacity) {
		super(capacity);
	}
	public void put(V v) throws InterruptedException{
		while (true){
			synchronized (this) {
				if (!isFull()){
					doPut(v);
					return;
				}
				Thread.sleep(1000);
			}
		}
	}

	public V take()throws InterruptedException{
		while (true){
			synchronized (this){
				if (!isEmpty()){
					return doTake();
				}
			}
			Thread.sleep(1000);
		}
	}
}
