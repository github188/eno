package com.energicube.eno.common.model;

import java.util.ArrayList;

/**
 * 消息结果
 * <br />
 * 返回操作结果消息和操作对象结果列表
 * */
public class MessageResult<T> {

	private Message message;
	private ArrayList<T> objects;
	
	public MessageResult() {
		
	}
	
	public MessageResult(Message message, T t) {
		this.message = message;
		if(this.objects==null)
			this.objects = new ArrayList<T>();
		this.objects.add(t);
	}

	public MessageResult(Message message, ArrayList<T> objects) {
		this.message = message;
		this.objects = objects;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public ArrayList<T> getObjects() {
		return objects;
	}

	public void setObjects(ArrayList<T> objects) {
		this.objects = objects;
	}

}
