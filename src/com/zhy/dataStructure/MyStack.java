package com.zhy.dataStructure;

public class MyStack {
	private String ind_Content;
	
	private String[]	arr;
	private int top;
	public MyStack(){
		arr = new String[200];
		top = -1;
	}
	
	public MyStack(int maxSize){
		arr = new String[maxSize];
		top = -1;
	}
	
	public void push(String value){
		arr[++top] = value;
	}
	
	public String pop(){
		return arr[top--];
	}
	
	public String peek(){
		return arr[top];
	}
	
	public boolean isEmpty(){
		return top == -1;
	}
	
	public boolean isFull(){
		return top == arr.length - 1;
	}
}
