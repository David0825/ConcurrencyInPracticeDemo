package com.david.concurrency.proactice.chapter15.programlist6;

import java.util.concurrent.atomic.AtomicReference;

/**
 *
 * 使用Treiber算法构造的非阻塞栈
 * @version 1.0.0
 *
 * @author wangwei910825@icloud.com
 *
 * @since 1.0.0
 *
 * @create 2021-07-25 15:31
 **/
public class ConcurrentStack<E> {

	AtomicReference<Node<E>> top = new AtomicReference<Node<E>>();

	public void push(E item) {
		Node<E> newHead = new Node<>(item);
		Node<E> oldHead;
		do {
			oldHead = top.get();
			newHead.next = oldHead;
		}while (!top.compareAndSet(oldHead,newHead));
	}

	public E pop(){
		Node<E> oldHead;
		Node<E> newHead;
		do {
			oldHead = top.get();
			if (oldHead == null){
				return null;
			}
			newHead = oldHead.next;
		}while (!top.compareAndSet(oldHead,newHead));
		return oldHead.item;
	}

	private static class Node<E> {
		public final E item;
		public   Node<E> next;

		public Node(E item) {
			this.item = item;
		}
	}
}
