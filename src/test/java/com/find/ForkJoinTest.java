package com.find;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.LongStream;

public class ForkJoinTest {

    @Test
    public void testA(){

        Instant start = Instant.now();

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinCalculate forkJoinCalculate = new ForkJoinCalculate(0L, 1000000000L);
        Long result = forkJoinPool.invoke(forkJoinCalculate);

        Instant end = Instant.now();
        long l = Duration.between(start, end).toMillis();
        System.out.println("花费时间为 ==>" + l);
        System.out.println("总和为 ==>" + result);
    }

    @Test
    public void testB(){

        Instant start = Instant.now();

        long sum = 0;
       for(long i = 0; i < 1000000000L; i++){
           sum += i;
       }

       Instant end = Instant.now();
        long l = Duration.between(start, end).toMillis();
        System.out.println("花费时间为 ==>" + l);
        System.out.println("总和为 ==>" + sum);
    }

    @Test
    public void testC(){

        Instant start = Instant.now();

        long reduce = LongStream.rangeClosed(0, 1000000000L)
                .parallel()
                .reduce(0, Long::sum);

        Instant end = Instant.now();
        long l = Duration.between(start, end).toMillis();
        System.out.println("花费时间为 ==>" + l);
        System.out.println("总和为 ==>" + reduce);
    }

}
