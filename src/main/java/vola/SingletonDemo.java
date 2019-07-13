package vola;

public class SingletonDemo {
    private static volatile SingletonDemo instance = null;

    private SingletonDemo(){//构造器
        System.out.println(Thread.currentThread().getName()+"\t 我是构造方法SingletonDemo");
    }

    //DCL(\Dubbo Check Lock 双端检索机制)指的就是在加锁的前和后都进行判断
    private static SingletonDemo getInstance(){
        //DCL(Dubbo Check Lock 双端检索机制)指的就是在加锁(synchronized)的前和后都进行判断,加在方法端中可以是加的锁更为牢固
        if (instance == null){
            synchronized(SingletonDemo.class){
                if (instance == null){
                    instance = new SingletonDemo();
                }
            }


        }
        return instance;
    }

    public static void main(String[] args) {
        //单线程(main线程的操作动作……)
//        System.out.println(SingletonDemo.getInstance() == SingletonDemo.getInstance());
//        System.out.println(SingletonDemo.getInstance() == SingletonDemo.getInstance());
//        System.out.println(SingletonDemo.getInstance() == SingletonDemo.getInstance());
//
//        System.out.println();
        //并发多线程后，情况发生很大的变化
        for (int i = 1;i <=10;i++){
            new Thread(()->{
                SingletonDemo.getInstance();
            },String.valueOf(i)).start();
        }



    }
}
