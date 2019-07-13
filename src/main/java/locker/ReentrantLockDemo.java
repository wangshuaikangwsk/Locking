package locker;
/*
*
* 可重入锁(也叫递归锁)
*
* 指的是同一个线程外层函数获得锁之后，内层递归函数仍然能获取该锁的代码
* 在同一个线程在外层方法获取锁的时候，在进入内层方法会自动获取锁
*   synchronized是典型的可重入锁
* 也就是说，线程可以进入任何一个他已经拥有的锁同步着的代码块
*  t1	 invoked sendSMS()
   t1	 ============invoked sendEmail()
   t2	 invoked sendSMS()
   t2	 ============invoked sendEmail()
 */

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Phone implements Runnable{   //线程操作资源类

    public synchronized void sendSMS()throws Exception{
        System.out.println(Thread.currentThread().getName()+"\t invoked sendSMS()");
        sendEmail();
    }

    public synchronized void sendEmail()throws Exception{
        System.out.println(Thread.currentThread().getName()+"\t ============invoked sendEmail()");
    }
//==========================================================================================

    Lock lock = new ReentrantLock();
    @Override
    public void run() {
        get();
    }

    public void get(){
        lock.lock();
        //lock.lock();
        try{
            System.out.println(Thread.currentThread().getName()+"\t invoked get()");
            set();
        }finally {
            lock.unlock();
            //lock.unlock();
        }

    }

    public void set(){
        lock.lock();
        try{
            System.out.println(Thread.currentThread().getName()+"\t invoked set()");
        }finally {
            lock.unlock();
        }

    }

}

public class ReentrantLockDemo {

    public static void main(String[] args) throws Exception{

        Phone phone = new Phone();

//        List<String,String> list01 = new LinkedList<>().add("zz","01");
//        List<String> list02 = new Vector<>().add("1",);
        new Thread(()->{
            try {
                phone.sendSMS();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"t1").start();

        new Thread(()->{
            try {
                phone.sendSMS();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"t2").start();



        try{
            TimeUnit.SECONDS.sleep(2);
        }catch (InterruptedException e){
            e.printStackTrace();
        }


        Thread t3 = new Thread(phone,"t3");
        Thread t4 = new Thread(phone,"t4");

        t3.start();
        t4.start();

    }
}
