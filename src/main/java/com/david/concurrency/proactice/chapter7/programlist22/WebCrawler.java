package com.david.concurrency.proactice.chapter7.programlist22;

import com.david.concurrency.proactice.chapter7.programlist21.TrackingExecutor;

import java.net.URL;
import java.security.PublicKey;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 *
 * 使用TrackingExecutorService来保存未完成的任务以备后续执行
 * @version 1.0.0
 *
 * @author wangwei910825@icloud.com
 *
 * @since 1.0.0
 *
 * @create 2021-07-18 11:08
 **/
public abstract class WebCrawler {

	private volatile TrackingExecutor exec;
	private final Set<URL> urlsToCrawl = new HashSet<>();

	public synchronized void start(){
		exec = new TrackingExecutor(Executors.newCachedThreadPool());
		urlsToCrawl.forEach(url -> submitCrawlTask(url));
		urlsToCrawl.clear();
	}

	public synchronized void stop() throws InterruptedException{
		try {
			saveUnCrawled(exec.shutdownNow());
			if (exec.awaitTermination(60, TimeUnit.SECONDS)){
				saveUnCrawled(exec.getCancelledTasks());
			}
		}finally {
			exec = null;
		}
	}

	protected abstract List<URL> processPage(URL url);

	public void saveUnCrawled(List<Runnable> list){
		for (Runnable runnable : list) {
			urlsToCrawl.add(((CrawlTask)runnable).getPage());
		}
	}

	public void submitCrawlTask(URL url) {
		exec.execute(new CrawlTask(url));
	}

	private class CrawlTask implements Runnable{
		private final URL url;

		public CrawlTask(URL url) {
			this.url = url;
		}

		@Override
		public void run() {
			for (URL link : processPage(url)){
				if (Thread.currentThread().isInterrupted()){
					return;
				}
				submitCrawlTask(link);
			}
		}

		public URL getPage(){
			return url;
		}
	}
}
