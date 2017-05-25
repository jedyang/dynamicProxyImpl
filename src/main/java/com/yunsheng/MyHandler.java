package com.yunsheng;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by shengyun on 17/5/25.
 */
public class MyHandler implements InvocationHandler {

    private Object target;

    public MyHandler(Object target) {
        this.target = target;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("before...");
        Object result = method.invoke(target);
        System.out.println("after...");
        return result;
    }
}
