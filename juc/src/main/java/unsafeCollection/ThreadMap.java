package unsafeCollection;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author Ryota
 * @create 2022/7/25 21:47
 */
public class ThreadMap {
    public static void main(String[] args) {
        //ConcurrentModificationException
        Map<String, String> map = new ConcurrentHashMap<>();
        for (int i = 0; i < 10; i++) {
            String key = String.valueOf(i);
            new Thread(() -> {
                map.put(key, UUID.randomUUID().toString().substring(0, 3));
                System.out.println(map);
            }, String.valueOf(i)).start();
        }
    }
}
