package com.david.concurrency.proactice.chapter14.programlist9;

/**
 *
 *使用wait和notifyAll来实现可重新关闭的阀门
 * @version 1.0.0
 *
 * @author wangwei910825@icloud.com
 *
 * @since 1.0.0
 *
 * @create 2021-07-25 11:07
 **/
public class ThreadGate {
	//条件谓词 open-since(n) (isOpen || generation > n)
	private boolean isOpen;
	private int generation;

	public synchronized  void close(){
		isOpen = false;
	}
	public synchronized void open(){
		++generation;
		isOpen = true;
		notifyAll();
	}

	//阻塞并直到：opened-since(generation on entry)
	public synchronized void await()throws InterruptedException{
		int arrivalGeneration = generation;
		while (!isOpen && arrivalGeneration == generation){
			wait();
		}
	}
}
