package com.geek.future;

import java.util.concurrent.Callable;

public class RealData implements Callable<Integer> {
    private Integer para;
    public RealData(Integer para){
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
