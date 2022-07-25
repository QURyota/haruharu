package day01;

/**
 * @Author Ryota
 * @create 2022/7/7 21:19
 */
public class Test01 {
    public static void main(String[] args) {
        //运行结果 main:over AA::false
        //主线程已经停止,用户线程还在执行,jvm存活
        Thread thread = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "::" + Thread.currentThread().isDaemon());
            while (true) {

            }
        }, "AA");
        //设置守护线程
        //当设置自定义线程为守护线程的时候,在主线程执行结束的时候,jvm也结束
        thread.setDaemon(true);
        thread.start();
        System.out.println(Thread.currentThread().getName() + ":over");
    }
}
