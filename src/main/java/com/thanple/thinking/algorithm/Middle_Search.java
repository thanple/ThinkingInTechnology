package com.thanple.thinking.algorithm;

import java.util.Iterator;

/**
 * 二分查找法算法：基于二分迭代
 * */

//扩展迭代器
interface IndexIterator<T extends Comparable<T>> extends Iterator<T>{
	public int getIndex();			//获取下标
	public boolean compareAndMove(T find);	//比较然后移动下标
}

//覆盖基类的iterator接口
interface IndexIteratable<T extends Comparable<T>> extends Iterable<T>{
	public IndexIterator<T> iterator();
}

class MiddleSearchList<T extends Comparable<T>> implements IndexIteratable<T>{
	private T[] data;
	
	public MiddleSearchList(T[] data){
		this.data = data;
	}
	
	/**
	 * 二分查找
	 * @param T find需要查找的节点
	 * @return int : 当找到结果返回下标，否则-1
	 * */
	public int search(T find){
		int resultIndex = -1;
		
		IndexIterator<T> it = this.iterator();
		while(it.hasNext()){
			if(it.compareAndMove(find)){	//比较是否相等
				resultIndex = it.getIndex();
				break;
			}
		}
		
		return resultIndex;
	}
	
	public T getElement(int index) {
		return data[index];
	}
	
	public T[] getData() {
		return data;
	}
	
	public int getSize(){
		return data.length;
	}

	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		for(T each : data){
			builder.append(each + " ") ;
		}
		return builder.toString();
	}

	@Override
	public IndexIterator<T> iterator() {
		return new IndexIterator<T>() {
			
			private int start = 0 , end = getSize()-1;
			private int index = 0;

			@Override
			public boolean hasNext() {
				return start <= end;
			}

			@Override
			public T next() {
				index = (start + end) / 2; 
				
				return getElement(index);
			}
			
			@Override
			public int getIndex() {
				return index;
			}

			@Override
			public boolean compareAndMove(T find) {
		
				T nextElement = this.next();
				boolean result = false;

				//下一节点与需要查找的节点进行比较
				int compare = nextElement.compareTo(find);
				if( compare > 0 ){
					end = index - 1;
				}else if(compare < 0){
					start = index + 1;
				}else{
					result = true;
				}
				
				return result;
			}
		};
	}
	
}


public class Middle_Search {
	public static void main(String[] args) {

		MiddleSearchList<Integer> test = new MiddleSearchList<>(
				new Integer[]{1,2,3,4,5,6,7,8,9,10});
		
		int index = test.search(6);
		
		System.out.println("查询结果: " + (index==-1 ? "无":index) );
		
	}
}
