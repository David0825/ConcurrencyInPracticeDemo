package com.david.concurrency.proactice.chapter7.programlist21;

import java.util.*;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 * 在ExecutorService中跟踪在关闭之后被取消的任务
 * @version 1.0.0
 *
 * @author wangwei910825@icloud.com
 *
 * @since 1.0.0
 *
 * @create 2021-07-18 10:58
 **/
public class TrackingExecutor extends AbstractExecutorService {

	private final ExecutorService executorService;
	private final Set<Runnable> tasksCancelledAtShutdown = Collections.synchronizedSet(new HashSet<Runnable>());

	public TrackingExecutor(ExecutorService executorService) {
		this.executorService = executorService;
	}

	public List<Runnable> getCancelledTasks(){
		/**
		 * isTerminated:当调用shutdown()方法后，并且所有提交的任务完成后返回true;
		 * isTerminated:当调用shutdownNow()方法后，成功停止后返回true;
		 * */
		if (!executorService.isTerminated()) {
			throw new IllegalStateException("");
		}
		return new ArrayList<Runnable>(tasksCancelledAtShutdown);
	}
	@Override
	public void execute(Runnable command) {
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				try {
					command.run();
				}finally {
					if (isShutdown() && Thread.currentThread().isInterrupted()) {
						tasksCancelledAtShutdown.add(command);
					}
				}
			}
		});
	}

	@Override
	public void shutdown() {
		executorService.shutdown();
	}

	@Override
	public List<Runnable> shutdownNow() {
		return executorService.shutdownNow();
	}

	@Override
	public boolean isShutdown() {
		return executorService.isShutdown();
	}

	@Override
	public boolean isTerminated() {
		return executorService.isTerminated();
	}

	@Override
	public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
		return executorService.awaitTermination(timeout,unit);
	}
}
