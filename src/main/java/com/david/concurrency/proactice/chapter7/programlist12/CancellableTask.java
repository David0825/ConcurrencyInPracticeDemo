package com.david.concurrency.proactice.chapter7.programlist12;

import java.util.concurrent.Callable;
import java.util.concurrent.RunnableFuture;

/**
 *
 *
 * @version 1.0.0
 *
 * @author wangwei910825@icloud.com
 *
 * @since 1.0.0
 *
 * @create 2021-07-18 09:58
 **/
public interface CancellableTask<T> extends Callable<T> {
	void cancel();
	RunnableFuture<T> newTask();
}
