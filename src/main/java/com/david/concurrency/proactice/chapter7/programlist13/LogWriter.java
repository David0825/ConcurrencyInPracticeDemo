package com.david.concurrency.proactice.chapter7.programlist13;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * 不支持关闭的生产者-消费者日志服务
 * @version 1.0.0
 *
 * @author wangwei910825@icloud.com
 *
 * @since 1.0.0
 *
 * @create 2021-07-18 10:16
 **/
public class LogWriter {

	private final BlockingQueue<String> queue;
	private final LoggerThread logger;

	public LogWriter(Writer writer) {
		this.queue = new LinkedBlockingQueue<String>(100);
		this.logger = new LoggerThread((PrintWriter) writer);
	}

	public void start(){
		logger.start();
	}

	public void log(String msg)throws InterruptedException {
		queue.put(msg);
	}

	private class LoggerThread extends Thread{
		private final PrintWriter writer;

		public LoggerThread(PrintWriter writer) {
			this.writer = writer;
		}

		@Override
		public void run() {
			while (true) {
				try {
					writer.println(queue.take());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}finally {
					writer.close();
				}
			}
		}
	}
}
