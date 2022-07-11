package lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author quyifan
 * @create 2022/7/9 10:10
 * * 1. 创建资源类,在资源类创建属性和操作方法
 * * 2. 创建多个线程,调用资源类的操作方法
 */
public class LockSaleTicket {
    public static void main(String[] args) {
        //1.创建多个线程,调用资源类的操作方法
        LockTicket lockTicket = new LockTicket();

        //创建3个线程
        new Thread(()->{
            for (int i = 0; i < 40; i++) {
                lockTicket.sale();
            }
        },"售票员A").start();

        new Thread(()->{
            for (int i = 0; i < 40; i++) {
                lockTicket.sale();
            }
        },"售票员B").start();

        new Thread(()->{
            for (int i = 0; i < 40; i++) {
                lockTicket.sale();
            }
        },"售票员C").start();

    }
}

class LockTicket {
    /**
     * 票数
     */
    private int number = 30;

    /**
     * 创建可重入锁
     */
    private final ReentrantLock lock = new ReentrantLock();

    /**
     * 操作方法
     */
    public void sale() {
        //上锁
        lock.lock();
        try {
            //判断是否有票
            if (number > 0) {
                System.out.println(Thread.currentThread().getName() + ":卖出" + number-- + "剩余:" + number);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //释放锁
            lock.unlock();
        }
    }
}