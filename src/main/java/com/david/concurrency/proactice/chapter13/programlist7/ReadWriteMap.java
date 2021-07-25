package com.david.concurrency.proactice.chapter13.programlist7;

import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *
 * 用读写锁来包装map
 * @version 1.0.0
 *
 * @author wangwei910825@icloud.com
 *
 * @since 1.0.0
 *
 * @create 2021-07-24 22:17
 **/
public class ReadWriteMap<k,v> {

	private final Map<k,v> map;
	private final ReadWriteLock lock = new ReentrantReadWriteLock();
	private final Lock r = lock.readLock();
	private final Lock w = lock.writeLock();

	public ReadWriteMap(Map<k, v> map) {
		this.map = map;
	}

	public v put(k key,v value) {
		w.lock();
		try {
			return map.put(key,value);
		}finally {
			w.unlock();
		}
	}

	public v get(k key) {
		r.lock();
		try {
			return map.get(key);
		}finally {
			r.unlock();
		}
	}
}
