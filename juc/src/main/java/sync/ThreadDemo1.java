package sync;

/**
 * 一.创建资源类,在资源类创建属性和操作方法
 * 二. 在资源类操作方法
 * 1):判断
 * 2):干活
 * 3):通知
 * <p>
 * 三.创建多个线程,调用资源类的操作方法
 *
 * @Author  Ryota
 * @create 2022/7/25 20:09
 */
class Share {
    //初始值
    private int number = 0;

    /**
     * +1 方法
     */
    public synchronized void incr() throws InterruptedException {
        //2判断 干活 通知
        //判断number值是否为0,如果不是0,等待
        while (number != 0) {
//        if (number != 0) {
            this.wait();
        }
        //如果number是0 就+1
        number++;
        System.out.println(Thread.currentThread().getName() + " :: " + number);
        //通知其他线程
        this.notifyAll();
    }

    /**
     * -1 方法
     */
    public synchronized void decr() throws InterruptedException {
        //2判断 干活 通知
        //判断number值是否为0,如果不是0,等待
        while (number != 1) {
//        if (number != 1) {
            this.wait();
        }
        //如果number是0 就+1
        number--;
        System.out.println(Thread.currentThread().getName() + " :: " + number);
        //通知其他线程
        this.notifyAll();
    }
}

public class ThreadDemo1 {

    /**
     * 3.
     * 用  if 关键字判断共享资源时  四个线程出现了虚假唤醒的情况
     * 虚假唤醒的解决方法  :放在循环中执行
     * @param args
     */
    public static void main(String[] args) {
        Share share = new Share();
        //创建线程
        new Thread(()->{
            for (int i = 1; i <= 10; i++) {
                try {
                    share.incr();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"线程A").start();
        new Thread(()->{
            for (int i = 1; i <= 10; i++) {
                try {
                    share.decr();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"线程B").start();
        new Thread(()->{
            for (int i = 1; i <= 10; i++) {
                try {
                    share.incr();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"线程C").start();
        new Thread(()->{
            for (int i = 1; i <= 10; i++) {
                try {
                    share.decr();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"线程D").start();

    }
}
