package com.david.concurrency.proactice.chapter12.programlist6;

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
public class Producer implements Runnable{

	private final CyclicBarrier barrier;
	private final BoundBuffer<Integer> boundBuffer;
	private final AtomicInteger putSum;

	public Producer(CyclicBarrier barrier, BoundBuffer<Integer> boundBuffer, AtomicInteger putSum) {
		this.barrier = barrier;
		this.boundBuffer = boundBuffer;
		this.putSum = putSum;
	}

	@Override
	public void run() {
		try {


		}catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
}
