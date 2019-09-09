package com.geek.design.builder.commonbuilder;

/**
 * @author 邱润泽 bullock
 */
public class Bike {

    private String frame;
    private String seat;
    private String tire;

    public String getFrame() {
        return frame;
    }

    public Bike setFrame(String frame) {
        this.frame = frame;
        return this;
    }

    public String getSeat() {
        return seat;
    }

    public Bike setSeat(String seat) {
        this.seat = seat;
        return this;
    }

    public String getTire() {
        return tire;
    }

    public Bike setTire(String tire) {
        this.tire = tire;
        return this;
    }
}
