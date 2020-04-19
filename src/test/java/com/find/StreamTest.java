package com.find;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StreamTest {

    private final static Logger logger = LoggerFactory.getLogger(StreamTest.class);

    @Test
    public void testA(){
        List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> results = nums.stream()
                .map((x) -> x * x)
                .collect(Collectors.toList());
        System.out.println("result ==>" + results.toString());
    }

    @Test
    public void testB(){
        List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5);
        Optional<Integer> reduce = nums.stream()
                .map((x) -> 1)
                .reduce((x, y) -> x + y);
        Integer integer = reduce.get();

        System.out.println("result ==>" + integer.toString());
    }

    @Test
    public void testC(){
        List<Trader> traders = Arrays.asList(
                new Trader("Rauul", "Cambridge"),
                new Trader("Mario", "Milan"),
                new Trader("Alan", "Cambridge"),
                new Trader("Brian", "Cambridge")
        );

        List<Transcation> transcations = Arrays.asList(
                new Transcation(traders.get(3), 2011, 300),
                new Transcation(traders.get(0), 2012, 1000),
                new Transcation(traders.get(0), 2011, 400),
                new Transcation(traders.get(1), 2012, 710),
                new Transcation(traders.get(1), 2012, 700),
                new Transcation(traders.get(2), 2012, 950)
        );

        transcations.stream()
                .filter((transcation -> transcation.getYear().equals(2011)))
                .sorted(Comparator.comparing(Transcation::getYear))
                .forEach(System.out::println);
    }

    @Test
    public void testD(){
        List<Trader> traders = Arrays.asList(
                new Trader("Rauul", "Cambridge"),
                new Trader("Mario", "Milan"),
                new Trader("Alan", "Cambridge"),
                new Trader("Brian", "Cambridge")
        );

        List<Transcation> transcations = Arrays.asList(
                new Transcation(traders.get(3), 2011, 300),
                new Transcation(traders.get(0), 2012, 1000),
                new Transcation(traders.get(0), 2011, 400),
                new Transcation(traders.get(1), 2012, 710),
                new Transcation(traders.get(1), 2012, 700),
                new Transcation(traders.get(2), 2012, 950)
        );

        traders.stream()
                .map(Trader::getCity)
                .distinct()
                .forEach(System.out::println);
    }

    @Test
    public void testE(){
        List<Trader> traders = Arrays.asList(
                new Trader("Rauul", "Cambridge"),
                new Trader("Mario", "Milan"),
                new Trader("Alan", "Cambridge"),
                new Trader("Brian", "Cambridge")
        );

        List<Transcation> transcations = Arrays.asList(
                new Transcation(traders.get(3), 2011, 300),
                new Transcation(traders.get(0), 2012, 1000),
                new Transcation(traders.get(0), 2011, 400),
                new Transcation(traders.get(1), 2012, 710),
                new Transcation(traders.get(1), 2012, 700),
                new Transcation(traders.get(2), 2012, 950)
        );

        traders.stream()
                .filter((trader -> trader.getCity().equals("Cambridge")))
                .sorted(Comparator.comparing(Trader::getName))
                .forEach(System.out::println);
    }

    @Test
    public void testF(){
        List<Trader> traders = Arrays.asList(
                new Trader("Rauul", "Cambridge"),
                new Trader("Mario", "Milan"),
                new Trader("Alan", "Cambridge"),
                new Trader("Brian", "Cambridge")
        );

        List<Transcation> transcations = Arrays.asList(
                new Transcation(traders.get(3), 2011, 300),
                new Transcation(traders.get(0), 2012, 1000),
                new Transcation(traders.get(0), 2011, 400),
                new Transcation(traders.get(1), 2012, 710),
                new Transcation(traders.get(1), 2012, 700),
                new Transcation(traders.get(2), 2012, 950)
        );

        traders.stream()
                .map(Trader::getName)
                .sorted()
                .forEach(System.out::println);

        String s = traders.stream()
                .map(Trader::getName)
                .flatMap(str -> {
                    ArrayList<String> strings = new ArrayList<>();
                    char[] chars = str.toCharArray();
                    for (Character aChar : chars) {
                        strings.add(aChar.toString());
                    }
                    return strings.stream();
                })
                .sorted(String::compareToIgnoreCase)
                .reduce(String::concat)
                .get();
        System.out.println(s);
    }

    @Test
    public void testG(){
        List<Trader> traders = Arrays.asList(
                new Trader("Rauul", "Cambridge"),
                new Trader("Mario", "Milan"),
                new Trader("Alan", "Cambridge"),
                new Trader("Brian", "Cambridge")
        );

        List<Transcation> transcations = Arrays.asList(
                new Transcation(traders.get(3), 2011, 300),
                new Transcation(traders.get(0), 2012, 1000),
                new Transcation(traders.get(0), 2011, 400),
                new Transcation(traders.get(1), 2012, 710),
                new Transcation(traders.get(1), 2012, 700),
                new Transcation(traders.get(2), 2012, 950)
        );

        boolean milan = traders.stream()
                .anyMatch(trader -> trader.getCity().equals("Milan"));
        System.out.println(milan);
    }

    @Test
    public void testH(){
        List<Trader> traders = Arrays.asList(
                new Trader("Rauul", "Cambridge"),
                new Trader("Mario", "Milan"),
                new Trader("Alan", "Cambridge"),
                new Trader("Brian", "Cambridge")
        );

        List<Transcation> transcations = Arrays.asList(
                new Transcation(traders.get(3), 2011, 300),
                new Transcation(traders.get(0), 2012, 1000),
                new Transcation(traders.get(0), 2011, 400),
                new Transcation(traders.get(1), 2012, 710),
                new Transcation(traders.get(1), 2012, 700),
                new Transcation(traders.get(2), 2012, 950)
        );

        Optional<Integer> max = transcations.stream()
                .map(Transcation::getValue)
                .max(Integer::compareTo);
        System.out.println(max.get());
    }

    @Test
    public void testI(){
        List<Trader> traders = Arrays.asList(
                new Trader("Rauul", "Cambridge"),
                new Trader("Mario", "Milan"),
                new Trader("Alan", "Cambridge"),
                new Trader("Brian", "Cambridge")
        );

        List<Transcation> transcations = Arrays.asList(
                new Transcation(traders.get(3), 2011, 300),
                new Transcation(traders.get(0), 2012, 1000),
                new Transcation(traders.get(0), 2011, 400),
                new Transcation(traders.get(1), 2012, 710),
                new Transcation(traders.get(1), 2012, 700),
                new Transcation(traders.get(2), 2012, 950)
        );

        Optional<Integer> cambridge = transcations.stream()
                .filter(transcation -> transcation.getTrader().getCity().equals("Cambridge"))
                .map(Transcation::getValue)
                .reduce(Integer::sum);
        System.out.println(cambridge.orElse(-1));

        IntSummaryStatistics cambridge1 = transcations.stream()
                .filter(transcation -> transcation.getTrader().getCity().equals("Cambridge"))
                .collect(Collectors.summarizingInt(Transcation::getValue));
        System.out.println(cambridge1.getSum());
    }

    @Test
    public void testJ(){
        List<Trader> traders = Arrays.asList(
                new Trader("Rauul", "Cambridge"),
                new Trader("Mario", "Milan"),
                new Trader("Alan", "Cambridge"),
                new Trader("Brian", "Cambridge")
        );

        List<Transcation> transcations = Arrays.asList(
                new Transcation(traders.get(3), 2011, 300),
                new Transcation(traders.get(0), 2012, 1000),
                new Transcation(traders.get(0), 2011, 400),
                new Transcation(traders.get(1), 2012, 710),
                new Transcation(traders.get(1), 2012, 700),
                new Transcation(traders.get(2), 2012, 950)
        );

        Optional<Integer> max = transcations.stream()
                .map(Transcation::getValue)
                .max(Integer::compareTo);
        System.out.println(max.get());

        Integer value = transcations.stream()
                .max(Comparator.comparing(Transcation::getValue))
                .get().getValue();
        System.out.println(value);

        int max1 = transcations.stream()
                .map(Transcation::getValue)
                .collect(Collectors.summarizingInt(e -> e))
                .getMax();
        System.out.println(max1);
    }

    @Test
    public void testK(){
        fun("你好",(e)-> e);
    }

    public String fun(String string,Function<String, String> function){
       return function.apply(string);
    }

}
