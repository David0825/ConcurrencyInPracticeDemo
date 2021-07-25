package com.david.concurrency.proactice.chapter15.programlist1;

/**
 *
 * 模拟CAS操作
 * @version 1.0.0
 *
 * @author wangwei910825@icloud.com
 *
 * @since 1.0.0
 *
 * @create 2021-07-25 14:49
 **/
public class SimulatedCAS {

	private int value;
	public synchronized int get(){
		return value;
	}

	public synchronized int compareAndSwap(int expectedValue,int newValue){
		int oldValue = value;
		if (oldValue == expectedValue) {
			value = newValue;
		}
		return oldValue;
	}

	public synchronized  boolean compareAndSet(int expectedValue,int newValue) {
		return (expectedValue == compareAndSwap(expectedValue,newValue));
	}
}
