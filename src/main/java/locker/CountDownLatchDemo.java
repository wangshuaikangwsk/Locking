package locker;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {

    public static void main(String[] args) throws Exception{

closeDoor();

    }

    public static void closeDoor() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(6);

        for (int i = 1;i <= 6;i++){
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"\t  国。灭了");
                countDownLatch.countDown();//一直-1
            },CountryEnum.foreach_CountryEnum(i).getRetMessage()).start();
        }


        countDownLatch.await();//等待
        System.out.println(Thread.currentThread().getName()+"\t ***************** 秦帝国，一统华夏！！！");
        System.out.println(CountryEnum.ONE);
        System.out.println(CountryEnum.ONE.getRetCode());
        System.out.println(CountryEnum.ONE.getRetMessage());
    }
}
