package unsafeCollection;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * list集合线程不安全
 * 解决方法
 * 1.用Vector
 * 2.Collections工具类
 *
 * @Author Ryota
 * @create 2022/7/25 21:11
 */
public class ThreadList {
    public static void main(String[] args) {
        ArrayList<String> strings = new ArrayList<>();
        //解决方法1
//        List<String> strings = new Vector<>();

        //解决方法2
//        List<String> strings = Collections.synchronizedList(new ArrayList<>());

        //解决方法3
//        List<String> strings = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                //向集合种添加内容
                strings.add(UUID.randomUUID().toString().substring(0, 8));
                //从集合中获取内容
                System.out.println(strings);
            }, String.valueOf(i)).start();
        }

    }
}
