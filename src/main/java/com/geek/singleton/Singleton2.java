package com.geek.singleton;

public class Singleton2 {
	public static int STATUS=1;
	private Singleton2() {
		System.out.println("Singleton is create");			//���������Ĺ��̿��ܻ�Ƚ���
	}
	private static Singleton2 instance = new Singleton2();
	public static Singleton2 getInstance() {
		return instance;
	}
}
