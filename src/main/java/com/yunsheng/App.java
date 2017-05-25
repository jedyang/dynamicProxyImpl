package com.yunsheng;

/**
 * 模拟实现java动态代理
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        // 1，生成一段源码
        // 2, 将源码存到.java文件里
        // 3, 使用compiler编译java文件
        // 4, 使用classLoader加载编译生成的class
        // 5, 使用动态代理类

        //Moveable car = new Car();
        //Moveable proxyInstance = (Moveable)Proxy.newProxyInstance(car.getClass().getClassLoader(), car.getClass().getInterfaces(),
        //    new MyHandler(car));
        //
        //proxyInstance.move();

        try {
            Moveable proxyInstance = (Moveable)MyProxy.newProxyInstance(Car.class.getInterfaces()[0], new Car());
            proxyInstance.move();
            proxyInstance.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
