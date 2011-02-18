package org.rascalmpl.parser.gtd.util;

public class DoubleStack<E, F>{
	private final static int DEFAULT_SIZE = 8;
	
	private E[] firstData;
	private F[] secondData;
	private int size;
	
	public DoubleStack(){
		super();
		
		firstData = (E[]) new Object[DEFAULT_SIZE];
		secondData = (F[]) new Object[DEFAULT_SIZE];
		size = 0;
	}
	
	public DoubleStack(int initialSize){
		super();
		
		firstData = (E[]) new Object[initialSize];
		secondData = (F[]) new Object[initialSize];
		size = 0;
	}
	
	public void enlarge(){
		E[] oldFirstData = firstData;
		firstData = (E[]) new Object[size << 1];
		System.arraycopy(oldFirstData, 0, firstData, 0, size);
		
		F[] oldSecondData = secondData;
		secondData = (F[]) new Object[size << 1];
		System.arraycopy(oldSecondData, 0, secondData, 0, size);
	}
	
	public void push(E first, F second){
		while(size >= firstData.length){
			enlarge();
		}
		
		firstData[size] = first;
		secondData[size++] = second;
	}
	
	public E peekFirst(){
		return firstData[size - 1];
	}
	
	public F peekSecond(){
		return secondData[size - 1];
	}
	
	public E pop(){
		E first = firstData[--size];
		firstData[size] = null;
		secondData[size] = null;
		return first;
	}
	
	public F popSecond(){
		F second = secondData[--size];
		firstData[size] = null;
		secondData[size] = null;
		return second;
	}
	
	public void purge(){
		firstData[--size] = null;
	}
	
	public E dirtyPop(){
		return firstData[--size];
	}
	
	public void dirtyPurge(){
		--size;
	}
	
	public boolean isEmpty(){
		return (size == 0);
	}
	
	public void clear(){
		firstData = (E[]) new Object[firstData.length];
		secondData = (F[]) new Object[secondData.length];
		size = 0;
	}
}
