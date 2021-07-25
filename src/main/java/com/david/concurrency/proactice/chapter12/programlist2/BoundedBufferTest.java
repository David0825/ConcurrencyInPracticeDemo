package com.david.concurrency.proactice.chapter12.programlist2;

import com.david.concurrency.proactice.chapter12.programlist1.BoundBuffer;

/**
 *
 *
 * @version 1.0.0
 *
 * @author wangwei910825@icloud.com
 *
 * @since 1.0.0
 *
 * @create 2021-07-21 07:21
 **/
public class BoundedBufferTest {

	public static void main(String[] args)throws InterruptedException {
		//testIsEmptyWhenConstructed();
		testIsFullAfterPuts();
	}

	public static void testIsEmptyWhenConstructed(){
		BoundBuffer boundBuffer = new BoundBuffer(10);
		System.out.println(boundBuffer.isEmpty());
		System.out.println(boundBuffer.isFull());
	}


	public static void testIsFullAfterPuts()throws InterruptedException {
		BoundBuffer<Integer> bb = new BoundBuffer<>(10);
		for (int i = 0; i < 10; i++) {
			bb.put(i);
		}
		System.out.println(bb.isFull());
		System.out.println(bb.isEmpty());
	}
}
