package lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author Ryota
 * @create 2022/7/25 20:56
 */
class ShareResource {
    /**
     * 定义标志位 1.A 2.B 3.C
     */
    private int flag = 1;
    /**
     * 创建Lock锁
     */
    private final Lock lock = new ReentrantLock();

    /**
     * 创建3个condition
     */
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();

    public void print5(int loop) throws InterruptedException {
        //上锁
        lock.lock();
        try {
            //判断
            while (flag != 1) {
                //等待
                c1.await();
            }
            //干活
            for (int i = 1; i <= 5; i++) {
                System.out.println(Thread.currentThread().getName() + "::" + i + "轮数" + loop);
            }
            //通知
            //先修改标志位为2
            flag = 2;
            //再通知B线程
            c2.signal();
        } finally {
            lock.unlock();
        }

    }

    public void print10(int loop) throws InterruptedException {
        //上锁
        lock.lock();
        try {
            //判断
            while (flag != 2) {
                //等待
                c2.await();
            }
            //干活
            for (int i = 1; i <= 10; i++) {
                System.out.println(Thread.currentThread().getName() + "::" + i + "轮数" + loop);
            }
            //通知
            //先修改标志位为2
            flag = 3;
            //再通知B线程
            c3.signal();
        } finally {
            lock.unlock();
        }

    }

    public void print15(int loop) throws InterruptedException {
        //上锁
        lock.lock();
        try {
            //判断
            while (flag != 3) {
                //等待
                c3.await();
            }
            //干活
            for (int i = 1; i <= 15; i++) {
                System.out.println(Thread.currentThread().getName() + "::" + i + "轮数" + loop);
            }
            //通知
            //先修改标志位为2
            flag = 3;
            //再通知B线程
            c1.signal();
        } finally {
            lock.unlock();
        }

    }


}

public class ThreadDemo3 {
    public static void main(String[] args) {
        ShareResource shareResource = new ShareResource();
        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    shareResource.print5(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"线程A").start();
        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    shareResource.print10(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"线程B").start();
        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    shareResource.print15(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"线程C").start();
    }
}
