package com.geekq.learnguava.guava.utilities;

/**
 * @author 邱润泽 bullock
 */
public class SplitterG {

    private final strategyG strategyG ;


    public SplitterG(com.geekq.learnguava.guava.utilities.strategyG strategyG) {
        this.strategyG = strategyG;
    }


    public Integer get(int a , int b){
        return strategyG.calu(a,b) ;
    }
    public static void main(String[] args) {
        SplitterG splitterG = new SplitterG(new A());

        Integer aaddb = splitterG.get(1,3);

        System.out.println(aaddb);
    }
}
