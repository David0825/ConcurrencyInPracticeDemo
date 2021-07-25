package com.david.concurrency.proactice.chapter12.programlist7;

import com.david.concurrency.proactice.chapter12.programlist1.BoundBuffer;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 *
 * @version 1.0.0
 *
 * @author wangwei910825@icloud.com
 *
 * @since 1.0.0
 *
 * @create 2021-07-21 08:25
 **/
public class Consumer implements Runnable{

	private final CyclicBarrier barrier;
	private final BoundBuffer<Integer> boundBuffer;
	private final AtomicInteger takeSum;

	public Consumer(CyclicBarrier barrier, BoundBuffer<Integer> boundBuffer, AtomicInteger takeSum) {
		this.barrier = barrier;
		this.boundBuffer = boundBuffer;
		this.takeSum = takeSum;
	}

	@Override
	public void run() {

	}
}
