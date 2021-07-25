package com.david.concurrency.proactice.chapter12.programlist5;

import com.david.concurrency.proactice.chapter12.programlist1.BoundBuffer;
import com.david.concurrency.proactice.chapter12.programlist6.Producer;
import com.david.concurrency.proactice.chapter12.programlist7.Consumer;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * 测试BoundedBuffer的生产者-消费者程序
 * @version 1.0.0
 *
 * @author wangwei910825@icloud.com
 *
 * @since 1.0.0
 *
 * @create 2021-07-21 08:19
 **/
public class PutTakeTest {
	private static final ExecutorService pool = Executors.newCachedThreadPool();
	private final AtomicInteger putSum = new AtomicInteger(0);
	private final AtomicInteger takeSum = new AtomicInteger(0);
	private final CyclicBarrier barrier;
	private final BoundBuffer<Integer> boundBuffer;
	private final int nTrials,npairs;

	PutTakeTest(int capacity,int npairs,int ntrials){
		this.boundBuffer = new BoundBuffer<>(capacity);
		this.nTrials = ntrials;
		this.npairs = npairs;
		this.barrier = new CyclicBarrier(npairs * 2 + 1);
	}

	void test(){
		try {
			for (int i = 0; i < npairs; i++) {
				pool.execute(new Producer(barrier,boundBuffer,putSum));
				pool.execute(new Consumer(barrier,boundBuffer,takeSum));
			}
			//等待所有的线程就绪
			barrier.await();
			//等待所有的线程完成
			barrier.await();
			System.out.println(putSum.get() == takeSum.get());
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
