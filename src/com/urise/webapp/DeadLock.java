package com.urise.webapp;

public class DeadLock {
    private static final String lock1 = "Thread №1";
    private static final String lock2 = "Thread №2";

    public static void main(String[] args) {
        doDeadLock(lock1, lock2);
        doDeadLock(lock2, lock1);
    }

    private static void doDeadLock(String lock1, String lock2) {
        new Thread(() -> {
            System.out.println(lock1 + ": waiting");
            synchronized (lock1) {
                System.out.println(lock1 + ": working");
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(lock2 + ": waiting");
                synchronized (lock2) {
                    System.out.println(lock2 + ": working");
                }
            }
        }).start();
    }
}
