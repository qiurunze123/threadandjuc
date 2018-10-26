package com.geek.threadandjuc.juc.queue;

/**
 * 数据实体
 * 模拟生产者产生的数据实体
 */
public final class Data {

	private String id;
	private String name;
	
	public Data(String id, String name){
		this.id = id;
		this.name = name;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString(){
		return "{id: " + id + ", name: " + name + "}";
	}
	
}
