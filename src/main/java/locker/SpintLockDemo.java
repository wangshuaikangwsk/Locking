package locker;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/*
 *题目:实现一个自旋锁
 * 自旋锁的好处:循环比较获取直到成功为止，没有类似的wait阻塞
 *
 * 通过CAS操作完成自旋锁，A线程现金爱调用mylock方法自己持有锁5s，B随后进来发现
 * 当线程持有锁，不是null，所以只能通过自旋等待，直到A释放锁后B锁后抢到
 *
 */
public class SpintLockDemo {

    //原子引用线程
    //AtomicReference是原子引用的意思，因为需要自旋锁，所以泛型里面写的Thread
    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public void myLock(){
        Thread thread = Thread.currentThread();//当前进来的线程
        System.out.println(Thread.currentThread().getName()+"\t come in 0000");
        while (!atomicReference.compareAndSet(null,thread)){

        }

    }

    public void myUnlock(){
        Thread thread = Thread.currentThread();//当前进来的线程
        atomicReference.compareAndSet(thread,null);
        System.out.println(Thread.currentThread().getName()+"\t invoked myUnLock()");
    }




    public static void main(String[] args) {//一切程序的入口

        SpintLockDemo spintLockDemo = new SpintLockDemo();//线程操控资源类
        new Thread(()->{
            spintLockDemo.myLock();
            try{
                //暂停一会线程
                TimeUnit.SECONDS.sleep(5);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            spintLockDemo.myUnlock();
        },"AA").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        new Thread(()->{
            spintLockDemo.myLock();
            try {
                TimeUnit.SECONDS.sleep(1);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            spintLockDemo.myUnlock();
        },"BB").start();

    }

}
