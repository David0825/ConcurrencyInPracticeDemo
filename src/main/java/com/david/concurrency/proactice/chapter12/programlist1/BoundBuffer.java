package com.david.concurrency.proactice.chapter12.programlist1;

import java.util.concurrent.Semaphore;

/**
 *
 * 基于信号量的有界缓存
 * @version 1.0.0
 *
 * @author wangwei910825@icloud.com
 *
 * @since 1.0.0
 *
 * @create 2021-07-20 22:42
 **/
public class BoundBuffer<E> {

	private final Semaphore availableItems,availableSpaces;
	private final E[] items;
	private int putPosition = 0,takePosition = 0;

	public BoundBuffer(int capacity) {
		/**
		 * new Semaphore(0)的场景下，semaphore.acquire()后，信号量变为-1，此时阻塞
		 * 此时，必须其他线程调用semaphore.release()才能释放
		 * semaphore.release()创建一个许可，semaphore.acquire()消耗一个许可
		 * */
		availableItems = new Semaphore(0);
		availableSpaces = new Semaphore(capacity);
		items = (E[])new Object[capacity];
	}

	public boolean isEmpty(){
		return availableItems.availablePermits() == 0;
	}

	public boolean isFull(){
		return availableSpaces.availablePermits() == 0;
	}

	public void put(E x) throws InterruptedException{
		availableSpaces.acquire();
		doInsert(x);
		availableItems.release();

	}

	public E take()throws InterruptedException{
		availableItems.acquire();
		E item = doExtract();
		availableSpaces.release();
		return item;
	}


	private synchronized  void doInsert(E x) {
		int i = putPosition;
		items[i] = x;
		putPosition = (++i == items.length) ? 0 : i;
	}

	private synchronized E doExtract(){
		int i = takePosition;
		E x = items[i];
		items[i] = null;
		takePosition = (++i == items.length) ? 0 : i;
		return x;
	}
}
