package com.find.callable;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class testCallable {

    private final static Integer SIZE = 10;

    private static ExecutorService executorService = Executors.newFixedThreadPool(SIZE);

    /**
     * 使用thread原生实现并发
     * @throws InterruptedException
     */
    @Test
    public void createThread() throws InterruptedException {
        ArrayList<Integer> results = new ArrayList<>();
        CountDownLatch countDownLatch = new CountDownLatch(SIZE);
        for (int i = 0; i < SIZE; i++) {
            int finalI = i;
            Thread thread = new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread());
                    Thread.sleep(new Random(0).nextInt(1000));
                    results.add(finalI);
                    countDownLatch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
////            调用传入的runable对象的run方法，不开辟线程
//            thread.run();
//            开辟线程执行run方法
            thread.start();
        }
        countDownLatch.await();
        results.forEach(System.out::println);
    }

    /**
     * 使用executorService实现并发
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void createTask() throws ExecutionException, InterruptedException {
        ArrayList<Future<Integer>> futures = new ArrayList<>();
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < SIZE; i++) {
            int finalI = i;
            Callable<Integer> callable = ()->{
                Thread.sleep(new Random(0).nextInt(1000));
                System.out.println(Thread.currentThread());
                list.add(finalI);
                return finalI;
            };
            Future<Integer> future = executorService.submit(callable);
            futures.add(future);
        }

        for (Future<Integer> future : futures) {
            Integer result = future.get();
            System.out.println(result);
        }

        System.out.println("==========================");
        list.forEach(System.out::println);
    }

    /**
     * 任务延后触发
     * @throws Exception
     */
    @Test
    public void testC() throws Exception {
        Instant startTime = Instant.now();
        final int[] start = {0};
//        runable是在耗时操作全部完成之后启动的处理任务
        Runnable runnable = () -> {
            start[0] = 1;
            System.out.println("后续处理完成");
            try {
                System.out.println("执行其他任务");
                testUtil.testA(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        testUtil.testA(runnable);
        Instant endTime = Instant.now();
        long l = Duration.between(startTime, endTime).toMillis();
        System.out.println("总耗时：" + l);
    }

    /**
     * 反复横跳
     * @throws Exception
     */
    @Test
    public void testD() throws Exception {
        Instant startTime = Instant.now();
        final int[] start = {0};
//        runable是在耗时操作全部完成之后进行的处理
        Runnable runnable = () -> {
            start[0] = 1;
            System.out.println("后续处理完成");
            try {
                System.out.println("执行其他任务");
                testUtil.testA(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        testUtil.testB(runnable);
        Instant endTime = Instant.now();
        long l = Duration.between(startTime, endTime).toMillis();
        System.out.println("总耗时：" + l);
    }

    /**
     *  结合executorService与countDownLatch 取得第一个完成的任务的结果
     * @throws InterruptedException
     */
    @Test
    public void testE() throws InterruptedException {
        List<String> urls = Arrays.asList(
                "1","2","3","4","5","6",
                "8","9","10","11","12","13"
        );
        CountDownLatch countDownLatch = new CountDownLatch(1);
        AtomicBoolean flag = new AtomicBoolean(false);
        AtomicReference<String> result = new AtomicReference<>(null);
        for (String url : urls) {
            Callable callable = ()->{
//                System.out.println(Thread.currentThread());
                Thread.sleep(new Random(0).nextInt(1000));
                System.out.println("睡眠后：" + Thread.currentThread());
                if(flag.get() == false){
                    result.set(url);
                    flag.set(true);
                    countDownLatch.countDown();
                    System.out.println(result);
                }
                return null;
            };
            executorService.submit(callable);
        }
        countDownLatch.await();
        System.out.println(result);

    }

    /**
     *  stream并行流处理 取得第一个完成的任务的结果
     * @throws InterruptedException
     */
    @Test
    public void testF() throws InterruptedException {
        List<String> urls = Arrays.asList(
                "1","2","3","4","5","6",
                "8","9","10","11","12","13"
        );
        Optional<String> any = urls.parallelStream().map((e) -> {
            try {
                Thread.sleep(new Random(0).nextInt(1000));
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            return e;
        }).findAny();
        System.out.println(any.get());

    }


}
