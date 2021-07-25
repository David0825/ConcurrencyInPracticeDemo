package com.david.concurrency.proactice.chapter8.programlist9;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

/**
 *
 * 自定义线程池，通过beforeExecute、afterExecute和terminated方法来添加日志记录
 * 和统计信息，测量任务的允许时间，beforeExecute必须记录开始时间并把它保存到一个afterExecute
 * 可以访问的地方，因为这方法将在执行任务的线程中调用，因此beforeExecute可以把值保存在ThreadLocal变量
 * 然后由afterExecute来读取，在TimingThreadPool中使用两个AtomicLong变量，分别用于记录已处理的任务数
 * 和总的处理时间
 * @version 1.0.0
 *
 * @author wangwei910825@icloud.com
 *
 * @since 1.0.0
 *
 * @create 2021-07-19 08:11
 **/
public class TimingThreadPool extends ThreadPoolExecutor {
	private final ThreadLocal<Long> startTime = new ThreadLocal<>();
	private final Logger logger = Logger.getLogger("TimingThreadPool");
	private final AtomicLong numTasks = new AtomicLong();
	private final AtomicLong totalTime = new AtomicLong();

	public TimingThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
	}

	@Override
	protected void beforeExecute(Thread t, Runnable r) {
		super.beforeExecute(t,r);
		logger.fine(String.format("Thread %s: start %s",t,r));
		startTime.set(System.nanoTime());
	}

	@Override
	protected void afterExecute(Runnable r, Throwable t) {
		try {
			long endTime = System.nanoTime();
			long taskTime = endTime - startTime.get();
			numTasks.incrementAndGet();
			totalTime.addAndGet(taskTime);
			logger.fine(String.format("Thread %s: end %s,time=%dns",t,r,taskTime));
		}finally {
			super.afterExecute(r,t);
		}
	}

	@Override
	protected void terminated() {
		try {
			logger.info(String.format("Terminated:avg time=%dns",totalTime.get()/ numTasks.get()));
		}finally {
			super.terminated();
		}
	}
}
