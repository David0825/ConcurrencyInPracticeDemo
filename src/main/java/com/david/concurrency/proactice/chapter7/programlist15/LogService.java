package com.david.concurrency.proactice.chapter7.programlist15;

import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * 向LogWriter添加可靠的取消操作
 * @version 1.0.0
 *
 * @author wangwei910825@icloud.com
 *
 * @since 1.0.0
 *
 * @create 2021-07-18 10:27
 **/
public class LogService {
	private final BlockingQueue<String> queue;
	private final LoggerThread logger;
	private boolean isShutDown;
	private int reservations;

	public LogService(PrintWriter writer) {
		this.queue = new LinkedBlockingQueue<String>(200);
		this.logger = new LoggerThread(writer);
	}

	public void start(){
		logger.start();
	}
	public void stop(){
		synchronized (this){
			isShutDown = true;
		}
		logger.interrupt();
	}

	public void log(String msg) throws InterruptedException{
		synchronized(this){
			if (isShutDown) {
				throw new IllegalStateException("");
			}
			++reservations;
		}
		queue.put(msg);
	}


	private class LoggerThread extends Thread {
		private final PrintWriter writer;

		public LoggerThread(PrintWriter writer) {
			this.writer = writer;
		}

		@Override
		public void run() {
			try {
				while (true) {
					synchronized (LogService.class) {
						if (isShutDown && reservations == 0) {
							break;
						}
					}
					String msg = queue.take();
					synchronized (LogService.class){
						--reservations;
					}
					writer.println(msg);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}finally {
				writer.close();
			}
		}
	}
}
