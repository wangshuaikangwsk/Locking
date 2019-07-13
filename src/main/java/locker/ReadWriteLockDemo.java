package locker;

/*
 *多个线程同时读一个资源类没有任何问题，所以为了满足并发量，读取共享资源类应该可以同时进行
 *但是
 *
 * 如果有一个线程想去写共享资源类，就不应该再有其他线程可以对该资源进行读或者写
 *
 * 小总结:
 *   读--读能共存
 *   读--写不能共存
 *   写--写不能共存
 *
 *   写操作:原子+独占，整个过程必须是一个完整的统一体。中间不需被分割，被打断
 */

import com.sun.org.apache.bcel.internal.generic.NEW;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class MyCache{
    private volatile Map<String,Object> map = new HashMap<>();
    //代替了synchronized，但是他只能一个线程释放锁后则是另一个线程使用
    //private Lock lock = new ReentrantLock();
    private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

    public void put(String key,Object value){

        rwLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName()+"\t 正在写入:"+key);
            //暂停一会线程
            try {
                TimeUnit.MILLISECONDS.sleep(300);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            map.put(key,value);
            System.out.println(Thread.currentThread().getName()+"\t 写入完成:");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            rwLock.writeLock().unlock();
        }

    }

    public void get(String key){

        rwLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName()+"\t 正在读取 :");
            //暂停一会线程
            try {
                TimeUnit.MILLISECONDS.sleep(300);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            Object result = map.get(key);
            System.out.println(Thread.currentThread().getName()+"\t d读取完成:"+result);

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            rwLock.readLock().unlock();
        }

    }
}


public class ReadWriteLockDemo {

    public static void main(String[] args) {

//        HashSet<Object> objects = new HashSet<>().add();
//        HashMap<Object, Object> map = new HashMap<>().put();
//          ReentrantLock reentrantLock = new ReentrantLock();
//        List<Object> objects = new CopyOnWriteArrayList<>();
//        List<Object> objects01 = new Vector<>().add(1);
        MyCache myCache = new MyCache();

        for (int i = 1;i <= 5;i++){
            final int tempInt = i;
            new Thread(()->{
                myCache.put(tempInt+"",tempInt+"");
            },String.valueOf(i)).start();
        }

        for (int i = 1; i <=5;i++){
            final int tempInt = i;
            new Thread(()->{
             myCache.get(tempInt+"");
            },String.valueOf(i)).start();
        }




    }
}
