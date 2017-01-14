package com.thanple.thinking.algorithm;

import java.util.Iterator;

/**
 * 实现链表的反转：基于递归与迭代
 * @author Thanple
 * @date 2016-7-9
 * */

class Node<T> implements Iterable<Node<T>>{
	private T data;
	private Node<T> next = null;
	
	public void setNext(Node<T> next) {
		this.next = next;
	}
	public Node<T> getNext() {
		return next;
	}
	
	public Node(T data){
		this.data = data;
	}
	
	/**
	 * 可用于foreach的迭代
	 * @return Iterator<Node<T>> 返回迭代器
	 * */
	@Override
	public Iterator<Node<T>> iterator() {
		return new Iterator<Node<T>>(){		
			
			private  Node<T> currentNode = Node.this;	//保存当前的节点
		
			@Override
			public boolean hasNext() {
				return currentNode.getNext() != null;
			}

			@Override
			public Node<T> next() {
				currentNode = currentNode.getNext();
				return currentNode;
			}
		};
	}
	
	@Override
	public String toString(){
		return data.toString() + (this.iterator().hasNext() ? "->" : "");
	}
}

public class LinkedList_Reverse {
	
	/**
	 * 反转链表
	 * @param Node<T> node 需要反转的头节点
	 * @return Node<T> 新的头结点
	 * */
	public static <T> Node<T> reverse(Node<T> node){
		
		Node<T> newHead = node;		//保存新的头节点
		
		if(node != null){		
			Node<T> nextNode = null;		
			if( (nextNode = node.getNext() ) != null ){	
				newHead = reverse(nextNode);	//递归下一个节点			
				nextNode.setNext(node);  //将下一节点指向这个节点
				node.setNext(null);		//将本次节点指向null
			}
		}
			
		return newHead;
	}
	
	/**
	 * 迭代打印链表
	 * */
	public static <T> void printList(Node<T> head){
		
		if(head==null)	return;
		
		//先打印头
		System.out.print(head);
		
		//迭代器模式打印各个节点
		head.forEach( (each)->{
			System.out.print(each);
		});

//		也可以用这个形式的for-each
//		for(Node<T> each : head){
//			System.out.print(each);
//		}
		
//		或者返回迭代器自己遍历
//		Iterator<Node<T>> it = head.iterator();
//		while(it.hasNext()){
//			System.out.print( it.next() );			
//		}
		
	}

	public static void main(String[] args) {

		//生成链表
		Node<Integer> head = new Node<>(1);		//头结点
		Node<Integer> p = head;
		for(int element : new int[]{3,5,6,1,2,7,8,9,2} ){
			Node<Integer> node = new Node<>(element);
			p.setNext(node);
			p = node;
		}
		
		//原来的链表
		System.out.println("原来的链表: ");
		printList(head);
		
		//反转链表
		System.out.println("\n反转后的链表: ");
		head = reverse(head);
		printList(head);
		
	}

}
