package com.find;

import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

public class LocalDataTest {

    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<>();

    @Test
    public void testA() throws ExecutionException, InterruptedException {
        Callable<Date> callable = () -> {
            System.out.println(Thread.currentThread().getName());
            return sdf.parse("20160801");
        };
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        List<Future<Date>> results = new ArrayList<>();
        for (int i = 0 ;i < 100; i++){
            Future<Date> submit = executorService.submit(callable);
            results.add(submit);
        }
        for(int i = 0; i < results.size();i++){
            Date date = results.get(i).get();
            System.out.println(date);
        }
    }

    @Test void testB() throws ExecutionException, InterruptedException {
        Callable<Date> callable = ()-> {
            threadLocal.set(new SimpleDateFormat("yyyyMMdd"));
            return threadLocal.get().parse("20170701");
        };
        ExecutorService service = Executors.newFixedThreadPool(100);
        List<Future<Date>> results = new ArrayList<>();
        for(int i = 0; i < 100 ; i++){
            Future<Date> dataFuture = service.submit(callable);
            results.add(dataFuture);
        }

        for(int i = 0; i < 100 ; i++){
            Date date = results.get(i).get();
            System.out.println(date);
        }
    }

    @Test
    public void testC() throws ExecutionException, InterruptedException {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
        Callable<LocalDate> callable = () -> {
            System.out.println(Thread.currentThread().getName());
            return LocalDate.parse("2019-08-01",formatter);
        };
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        List<Future<LocalDate>> results = new ArrayList<>();
        for (int i = 0 ;i < 100; i++){
            Future<LocalDate> submit = executorService.submit(callable);
            results.add(submit);
        }
        for(int i = 0; i < results.size();i++){
            LocalDate date = results.get(i).get();
            System.out.println(date);
        }
    }

    @Test
    public void testD(){
//        now
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);

//        of
        LocalDateTime createTime = LocalDateTime.of(2020, 4, 11, 20, 35);
        System.out.println(createTime);

//        from
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_DATE;
        LocalDateTime time3 = LocalDateTime.from(dateTimeFormatter.parse("2019-07-01"));
        System.out.println(time3);

//        plus minus
        time3.plusYears(1);
        time3.minusYears(1);
        time3.plusHours(1);
        time3.minusHours(10);

//        get
        time3.getYear();
        time3.getMinute();
        time3.getDayOfMonth();
        time3.getHour();
    }

    @Test
    public void testE(){
//        now默认UTC时间
        Instant now = Instant.now();
        System.out.println(now);

//        偏移时区
        OffsetDateTime offsetDateTime = now.atOffset(ZoneOffset.ofHours(8));
        System.out.println(offsetDateTime);

//        时间戳
        System.out.println(now.toEpochMilli());
    }

    @Test
    public void testF() throws InterruptedException {
        Instant start = Instant.now();
        Thread.sleep(1000);
        Instant end = Instant.now();

//        start < end
        System.out.println(Duration.between(start,end).toMillis());

//        其他事件类型也可
        LocalDate start1 = LocalDate.now();
        Thread.sleep(1000);
        LocalDate end1 = LocalDate.now();
        System.out.println(Duration.between(start,end).toMillis());

//        不能混用
//        System.out.println(Duration.between(start,end1).toMillis());

    }

    @Test
    public void testG(){
        LocalDateTime now1 = LocalDateTime.now();
        System.out.println(now1);

        LocalDateTime localDateTime = now1.withHour(10);
        System.out.println(localDateTime);

        LocalDateTime with = localDateTime.with(TemporalAdjusters.firstDayOfMonth());
        System.out.println(with);

        LocalDateTime with1 = with.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        System.out.println(with1);

    }
}
