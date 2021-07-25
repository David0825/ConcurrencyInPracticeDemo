package com.david.concurrency.proactice.chapter12.programlist3;

import com.david.concurrency.proactice.chapter12.programlist1.BoundBuffer;

/**
 *
 *测试阻塞行为以及对中断的响应性
 * @version 1.0.0
 *
 * @author wangwei910825@icloud.com
 *
 * @since 1.0.0
 *
 * @create 2021-07-21 08:01
 **/
public class TestTakeBlock {

	void testTakeBlocksWhenEmpty(){
		final BoundBuffer<Integer> bb = new BoundBuffer<>(10);
		Thread taker = new Thread(){
			@Override
			public void run() {
				try {
					int unused = bb.take();
					fail();
				}catch (InterruptedException success) {

				}
			}
		};
		try {
			taker.start();
			Thread.sleep(1000);
			taker.interrupt();
			taker.join(1000);
			System.out.println(taker.isAlive());
		}catch (Exception unexpected){
			fail();
		}
	}

	public void fail(){

	}
}
