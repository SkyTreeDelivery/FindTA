package com.find.callable;

import com.find.Util.Utils.MD5Util;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.*;

public class testUtil {

    private final static Integer SIZE = 20;

    private static ExecutorService executorService = Executors.newCachedThreadPool();

    /**
     * 并发 + 阻塞
     * @param runnable 并发完成后执行的任务
     * @throws Exception
     */
    public static void testA(Runnable runnable) throws Exception {
        ArrayList<Future<Double>> futures = new ArrayList<>();
        CountDownLatch countDownLatch = new CountDownLatch(SIZE);
        for (int i = 0; i < SIZE; i++) {
            double finalI = i;
            Callable<Double> task = ()->{
                Thread.sleep(new Random(0).nextInt(1000));
                countDownLatch.countDown();
                return finalI;
            };
            Future<Double> future = executorService.submit(task);
            futures.add(future);
        }
        countDownLatch.await();
        for (Future<Double> future : futures) {
            Double result = future.get();
            System.out.println(result);
        }
        if(runnable != null){
            runnable.run();
        }
    }

    /**
     * 并发 + 非阻塞
     * @param runnable 并发完成后执行的任务
     * @throws Exception
     */
    public static void testB(Runnable runnable) throws Exception {
        ArrayList<Future<Double>> futures = new ArrayList<>();
        CountDownLatch countDownLatch = new CountDownLatch(SIZE);
        for (int i = 0; i < SIZE; i++) {
            double finalI = i;
            Callable<Double> task = ()->{
                Thread.sleep(new Random(0).nextInt(1000));
                countDownLatch.countDown();
                return finalI;
            };
            Future<Double> future = executorService.submit(task);
            futures.add(future);
        }

        Callable newTask = ()->{
            for (Future<Double> future : futures) {
                Double result = future.get();
                System.out.println(result);
            }
            if(runnable != null){
                runnable.run();
            }
            return null;
        };
        executorService.submit(newTask);
    }

    @Test
    public void testG(){
        String zhang002508 = MD5Util.getMD5Str("zhang002508");
        System.out.println(zhang002508);
    }

    @Test
    public void testH(){
        System.out.println(UUID.randomUUID().toString());

    }


}
