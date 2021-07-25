package com.david.concurrency.proactice.chapter15.programlist3;

import java.util.concurrent.atomic.AtomicReference;

/**
 *
 * 通过CAS来维持包含多个变量的不变性条件
 * @version 1.0.0
 *
 * @author wangwei910825@icloud.com
 *
 * @since 1.0.0
 *
 * @create 2021-07-25 15:13
 **/
public class CasNumberRange {

	private static class IntPair{
		final int lower;
		final int upper;
		//不变性条件：lower <= upper

		public IntPair(int lower, int upper) {
			this.lower = lower;
			this.upper = upper;
		}
	}

	private final AtomicReference<IntPair> values = new AtomicReference<>(new IntPair(0,0));
	public int getLower(){
		return values.get().lower;
	}

	public int getUpper(){
		return values.get().upper;
	}

	public void setLower(int i) {
		while (true){
			IntPair oldv = values.get();
			if (i > oldv.upper){
				throw new IllegalArgumentException("Can't set lower to " + i + " > upper");
			}
			IntPair newv = new IntPair(i,oldv.upper);
			if (values.compareAndSet(oldv,newv)){
				return;
			}
		}
	}
}
