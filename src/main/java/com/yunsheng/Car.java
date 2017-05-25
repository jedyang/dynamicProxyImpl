package com.yunsheng;

/**
 * Created by shengyun on 17/5/25.
 */
public class Car implements Moveable {

    @Override
    public void move() {
        System.out.println("moveing....");
    }

    @Override
    public void stop() {
        System.out.println("stop....");
    }
}
