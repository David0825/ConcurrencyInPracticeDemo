package com.david.concurrency.proactice.chapter7.programlist12;

import java.util.concurrent.*;

/**
 *
 *
 * @version 1.0.0
 *
 * @author wangwei910825@icloud.com
 *
 * @since 1.0.0
 *
 * @create 2021-07-18 09:59
 **/
public class CancellingExecutor extends ThreadPoolExecutor {

	public CancellingExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
	}

	@Override
	protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
		if (callable instanceof CancellableTask) {
			return ((CancellableTask<T>)callable).newTask();
		}else {
			return super.newTaskFor(callable);
		}
	}
}
