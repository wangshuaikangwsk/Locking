package vola;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class MyData{//MyData.java----->MyData.class--->Jvm字节码

     int number = 0;//初始值

    //调用这个方法那么初始值直接就变成60
    public void addTo60(){
        this.number = 60;
    }

    //请注意，此时的number是前面加了volatile关键词修饰的。切记;volatile是不保证原子性的
    public void addPlusPlus(){
        number++;
    }

    //AtomicIntger带原子性的number  ，atomicIntger默认是0而number为0；
    AtomicInteger atomicInteger = new AtomicInteger();//原子整数型

    public void addMyAtomic(){
        atomicInteger.getAndIncrement();//默认每次加1
    }

}

/*
 *1.验证volatile的可见性
 * 1.1假如int number = 0，number变量之前没有添加volatile关键词修饰，换句话说 就是没有可见性
 * 1.2添加volatile保证可见性
 *
 * 2.验证volatie不保证原子性
 *  2.1原子性是什么？
 *      不可分割、完整性，也即某个线程正在做某个业务时，中间不可以被加塞。需要整体完整
 *      要么同时成功，要么同时失败
 *  2.2volatile不保证原子性的案例演示
 *  2.3如何解决原子性？
 *     1.直接加上synhronized
 *     2.直接用juc下面的AtomicInteger(凹凸米克Integer) Atomic本身就是带有原子性的  Atomic为什么可以保证原子型因为CAS
 *
 *
 *
 */

public class VolatileDemo {
    public static void main(String[] args) { //main是一切方法的运行入口

        MyData myData = new MyData();

        for (int i = 1;i <=20;i++){
            new Thread(()->{
                for (int j =1;j <=1000;j++){
                    myData.addPlusPlus();//不保证原子性
                    myData.addMyAtomic();//保证原子性
                }
            },String.valueOf(i)).start();
        }

        //需要等待上面20个线程全部计算执行完成后，再用main线程取得最终的结果值是多少？
        while (Thread.activeCount() > 2){
            Thread.yield();
        }
        System.out.println(Thread.currentThread().getName()+"\t type int, finally number value:"+myData.number);
        System.out.println(Thread.currentThread().getName()+"\t atomic Integer,finally number value:"+myData.atomicInteger);

    }

    //volatile可以保证可见性，及时通知其他线程，主物理内存的值已经改变
    private static void seeOkByvolatile() {
        MyData myData = new MyData();//线程操作的资源类

        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"\t come in");//表示AAA的线程
            try{
                //暂停线程休息3秒
                TimeUnit.SECONDS.sleep(3);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            myData.addTo60();//3秒后改为60
            System.out.println(Thread.currentThread().getName()+"\t update number value:"+myData.number);//表示AAA的线程
        },"AAA").start();

        //第二个线程就是我们的main线程
        while (myData.number == 0){
            //mian线程就一直等待下去直到number的值不在等于0
        }
        //当跳出循环后那么执行这句话  此时的number已经从0变成60
        System.out.println(Thread.currentThread().getName()+"\t mission is over，mian get number value:"+myData.number);
    }
}
