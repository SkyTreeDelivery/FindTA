package com.find;

import java.util.concurrent.RecursiveTask;

public class ForkJoinCalculate extends RecursiveTask<Long> {

    private long start;
    private long end;
    final long THROSHED = 10000000L;   //拆成100 ~ 1000份比较合适
    public ForkJoinCalculate(long start, long end){
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        if(end - start <THROSHED){
            long sum = 0;
            for(long i = start; i < end; i++){
                sum += i;
            }
            return sum;
        }else{
            long mid = (start + end) / 2;
            ForkJoinCalculate forkJoinTest = new ForkJoinCalculate(start, mid);
            forkJoinTest.fork();

            ForkJoinCalculate forkJoinTest1 = new ForkJoinCalculate(mid + 1, end);
            forkJoinTest1.fork();

            return forkJoinTest.join() + forkJoinTest1.join();
        }
    }
}
