package com.david.concurrency.proactice.chapter7.programlist12;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.FutureTask;
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
 * @create 2021-07-18 10:03
 **/
public abstract class SocketUsingTask<T> implements CancellableTask<T>{
	private Socket socket;

	protected  synchronized void setSocket(Socket socket) {
		this.socket = socket;
	}

	@Override
	public synchronized void cancel(){
		if (socket != null) {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public RunnableFuture<T> newTask() {
		return new FutureTask<T>(this){
			@Override
			public boolean cancel(boolean mayInterruptIfRunning) {
				SocketUsingTask.this.cancel();
				return super.cancel(mayInterruptIfRunning);
			}
		};
	}
}
