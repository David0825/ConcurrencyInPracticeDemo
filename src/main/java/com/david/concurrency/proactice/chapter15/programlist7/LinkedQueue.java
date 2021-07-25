package com.david.concurrency.proactice.chapter15.programlist7;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 *
 *非阻塞算法中的插入算法
 * @version 1.0.0
 *
 * @author wangwei910825@icloud.com
 *
 * @since 1.0.0
 *
 * @create 2021-07-25 16:27
 **/
public class LinkedQueue<E> {
	private static class Node<E>{
		final E item;
		final AtomicReference<Node<E>> next;

		public Node(E item, Node<E> next) {
			this.item = item;
			this.next = new AtomicReference<>(next);
		}
	}
	//哑结点或者哨兵节点
	private final Node<E> dummy = new Node<>(null,null);
	//头节点
	private final AtomicReference<Node<E>> head = new AtomicReference<>(dummy);
	//尾节点
	private final AtomicReference<Node<E>> tail = new AtomicReference<>(dummy);


	/***
	 * 当插入一个新元素时，需要更新两个指针，首先更新当前最后一个元素的next指针，将新节点链接到列表队尾，然后更新尾结点，将其指向新元素
	 * 在这两个操作之间，队列处于一种中间状态
	 * 实现这两个技巧的关键点在于：当队列处于稳定状态，尾节点的next域为空，如果队列处于中间状态，那么tail.next为非空
	 * 当队列处于中间状态，可以通过将尾节点向前移动一个节点，从而结束其他线程正在执行的插入元素的操作，使得队列恢复为稳定状态
	 * */
	public boolean put(E item){
		Node<E> newNode = new Node<>(item,null);
		while (true){
			Node<E> curTail = tail.get();
			Node<E> tailNext = curTail.next.get();
			if (curTail == tail.get()){
				if (tailNext != null) {
					//队列处于中间状态，推进尾节点
					tail.compareAndSet(curTail,tailNext);
				}else {
					//处于稳定状态，尝试插入新节点
					if (curTail.next.compareAndSet(null,newNode)){
						//插入操作成功，尝试推进尾节点
						tail.compareAndSet(curTail,newNode);
						return true;
					}
				}
			}
		}
	}
}
