package com.david.concurrency.proactice.chapter8.programlist4;

import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.Semaphore;

/**
 *
 * 使用Semaphore来控制任务的提交速率
 * @version 1.0.0
 *
 * @author wangwei910825@icloud.com
 *
 * @since 1.0.0
 *
 * @create 2021-07-18 22:24
 **/
public class BoundedExecutor {

	private final Executor exec;
	protected final Semaphore semaphore;

	public BoundedExecutor(Executor exec, Semaphore semaphore,int bound) {
		//bound为线程池的大小+队列的大小，创建信号量的上界
		this.exec = exec;
		this.semaphore = new Semaphore(bound);
	}

	public void submitTask(final Runnable command)throws InterruptedException {
		semaphore.acquire();
		try {
			exec.execute(new Runnable() {
				@Override
				public void run() {
					try {
						command.run();
					}finally {
						semaphore.release();
					}
				}
			});
		}catch (RejectedExecutionException e) {
			semaphore.release();
		}
	}
}
