package vola;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

public class ABADemo {  //ABA问题解决  AtomicStampedReference 带时间戳的原子引用

    static AtomicReference<Integer> atomicReference = new AtomicReference<>(100);//普通原子引用
    static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(100,1);//原子引用+时间戳
    public static void main(String[] args) {  //一切程序的入口
        System.out.println(">>>>>>>>>>>>>>>>>>>>以下是ABA问题的产生>>>>>>>>>>>>>>>>>>>>>>>>>>");

        new Thread(()->{
          atomicReference.compareAndSet(100,101);
          atomicReference.compareAndSet(101,100);
        },"T1").start();


        new Thread(()->{
            try {
                //暂停1s中T2线程，保证上面的T1线程完成了一次ABA操作
                TimeUnit.SECONDS.sleep(1);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            System.out.println(atomicReference.compareAndSet(100, 2019)+"\t "+atomicReference.get());
        },"T2").start();


        System.out.println(">>>>>>>>>>>>>>>>>>>>以下是ABA问题的解决>>>>>>>>>>>>>>>>>>>>>>>>>>");

        new Thread(()->{
            int stamp = atomicStampedReference.getStamp();//初始版本号
            System.out.println(Thread.currentThread().getName()+"\t 第一次的版本号:"+stamp);

            try{
                Thread.currentThread().sleep(1);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            //暂停1秒钟T3线程
            atomicStampedReference.compareAndSet(100,101,atomicStampedReference.getStamp(),atomicStampedReference.getStamp()+1);
            System.out.println(Thread.currentThread().getName()+"\t 第二次的版本号:"+atomicStampedReference.getStamp());
            atomicStampedReference.compareAndSet(101,100,atomicStampedReference.getStamp(),atomicStampedReference.getStamp()+1);
            System.out.println(Thread.currentThread().getName()+"\t 第三次的版本号:"+atomicStampedReference.getStamp());

        },"T3").start();

        new Thread(()->{
            int stamp = atomicStampedReference.getStamp();//初始版本号
            System.out.println(Thread.currentThread().getName()+"\t 第一次的版本号:"+stamp);
            //暂停3秒钟T4线程
            try{
                Thread.currentThread().sleep(3);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            boolean result = atomicStampedReference.compareAndSet(100, 2019, stamp, stamp + 1);
            atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName()+"\t修改成功与否:"+result+"当前最新版本号:"+atomicStampedReference.getStamp());
            System.out.println(Thread.currentThread().getName()+"\t修改成功与否:"+result+"当前最新值:"+atomicStampedReference.getReference());


        },"T4").start();

    }
}
