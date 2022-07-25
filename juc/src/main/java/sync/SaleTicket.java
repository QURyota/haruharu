package sync;

/**
 * 多线程编程步骤
 * 1. 创建资源类,在资源类创建属性和操作方法
 * 2. 创建多个线程,调用资源类的操作方法
 * <p>
 * 线程     资源类
 * 需求:3个售票员,卖出30张票
 * <p>
 * 实现:1.创建资源类,定义属性和操作方法
 *     2.创建多个线程,调用资源类中的方法
 *
 * @Author Ryota
 * @create 2022/7/9 0:19
 */
public class SaleTicket {
    public static void main(String[] args) {
        //2
        //创建Ticket
        Ticket ticket = new Ticket();
        //创建三个线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                //调用买票方法
                for (int i = 0; i < 40; i++) {
                    ticket.sale();
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        },"售票口A").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                //调用买票方法
                for (int i = 0; i < 40; i++) {
                    ticket.sale();
                }
            }
        },"售票口B").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                //调用买票方法
                for (int i = 0; i < 40; i++) {
                    ticket.sale();
                }
            }
        },"售票口C").start();
    }
}

class Ticket {
    /**
     * 票数
     */
    private int number = 30;

    /**
     * 操作方法
     */
    public synchronized void sale() {
        //判断:是否邮票
        if (number > 0) {
            System.out.println(Thread.currentThread().getName() + ":卖出" + (number--) + "剩下:" + number);
        }

    }
}