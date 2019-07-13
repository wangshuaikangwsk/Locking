package jihe;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/*
 *集合类不安全的问题
 *  ArrayList是不安全的
 *  HashSet是不安全的
 *  HashMap是线程不安全的
 *   不要只会用，会用只是一个API调用工程师
 *  底层原理呢？
 *
 *
 */
public class ContainerNotsafeDemo {
    public static void main(String[] args) {

       Map<String,String> hashMap = new ConcurrentHashMap<>();//Collections.synchronizedMap(new HashMap<>());
       for (int i= 0;i<=30;i++){
           new Thread(()->{
                hashMap.put(Thread.currentThread().getName(),UUID.randomUUID().toString().substring(0,8));
               System.out.println(hashMap);
           },String.valueOf(i)).start();
       }

        //方法二
//        List<String> list2 = new ArrayList<>();
//        list2.add("z");
//        list2.add("x");
//        list2.add("c");
//
//        for (String ele:list2) {
//            System.out.println(ele);
//        }
//        //方法一
//        List<String> list = Arrays.asList("a","b","c");
//        list.forEach(System.out::println);
        listNoSafe();
        //java.util.ConcurrentModificationException

    /*
     *1.故障现象
     *   java.util.ConcurrentModificationException
     *
     * 2.导致原因
     *
     *
     * 3.解决方案
     *
     * 4.优化建议(同样的错误不会再犯2次)
     *
     */

    }

    public static void setNosafe() {
        Set<String> set = new CopyOnWriteArraySet<>(); //Collections.synchronizedSet(new HashSet<>()); //new HashSet<>();

        for (int i = 1;i <=30;i++){
            new Thread(()->{
                set.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(set);
            },String.valueOf(i)).start();
        }
    }


    private static void listNoSafe() {
        List<String> list = new CopyOnWriteArrayList<>();//new Vector<>();             //new ArrayList<>();


        for (int i = 1;i <=30;i++){
            new Thread(()->{
                list.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(list);
            },String.valueOf(i)).start();
        }
    }
}
