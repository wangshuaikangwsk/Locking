package locker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/*
 *
 *
 * 栈先进后出，队列先进先出(FIFO)
 *
 *1.队列
 *
 * 2.阻塞队列
 *   2.1 阻塞队列有没有好的一面(海底捞火锅用餐区经常满客人，但是还有一些人可以放在候客区，而不是将客人给赶出去)
 *
 *   2.2 不得不阻塞你怎么管理
 *
 *
 */
public class BlockingQueueDemo {

    public static void main(String[] args) throws Exception{

        //List list = new ArrayList();
        //队列中只能存取3个位置
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);

        System.out.println(blockingQueue.offer("a", 2L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("a", 2L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("a", 2L, TimeUnit.SECONDS));

        System.out.println(blockingQueue.poll(2L,TimeUnit.SECONDS));
        System.out.println(blockingQueue.poll(2L,TimeUnit.SECONDS));
        System.out.println(blockingQueue.poll(2L,TimeUnit.SECONDS));
        System.out.println(blockingQueue.poll(2L,TimeUnit.SECONDS));



    }

}
