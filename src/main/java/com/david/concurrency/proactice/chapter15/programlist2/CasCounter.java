package com.david.concurrency.proactice.chapter15.programlist2;

import com.david.concurrency.proactice.chapter15.programlist1.SimulatedCAS;

/**
 *
 * 基于CAS实现的非阻塞计数器
 * @version 1.0.0
 *
 * @author wangwei910825@icloud.com
 *
 * @since 1.0.0
 *
 * @create 2021-07-25 14:55
 **/
public class CasCounter {

	private SimulatedCAS value;

	public int getValue(){
		return value.get();
	}
	public int increment(){
		int v;
		do {
			v = value.get();
		}while (v != value.compareAndSwap(v,v+1));
		return v;
	}
}
