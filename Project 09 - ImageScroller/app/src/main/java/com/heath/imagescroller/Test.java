package com.heath.imagescroller;

public class Test {

    private  static  Integer count = 0;
    private  static  final  Integer FULL = 10;
    private  static  String LOCK = "lock";

    // 生产者
    class Productor implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(30);
                } catch (Exception e) {
                    // 打印捕获的异常
                    e.printStackTrace();
                }
                // 添加同步锁
                synchronized (LOCK) {
                    while (count == FULL) {
                        try {
                            LOCK.wait();
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    count++;
                    System.out.print(Thread.currentThread().getName() + "生产者生产， 目前总共有" + count);
                    LOCK.notifyAll();
                }
            }
        }
    }

    // 消费者
    class Consumer implements  Runnable {
        @Override
        public void run() {
            for (int i = 0;i < 10; i++) {
                try {
                    Thread.sleep(30);
                }catch (Exception e) {
                    e.printStackTrace();
                }
                synchronized (LOCK) {
                    while (count == 0) {
                        try {
                            LOCK.wait();
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    count--;
                    System.out.print(Thread.currentThread().getName() + "消费者消费， 目前总共有" + count);
                    LOCK.notifyAll();
                }
            }
        }
    }
}
