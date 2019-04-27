package com.geek.future;

import java.util.concurrent.Callable;

public class RealData1 implements Callable<Integer> {
    private Integer para;
    public RealData1(Integer para){
    	this.para=para;
    }
	@Override
	public Integer call() throws Exception {

    	StringBuffer sb=new StringBuffer();
        for (int i = 0; i < 1000; i++) {
            para ++;
        }
        return para;
	}
}
