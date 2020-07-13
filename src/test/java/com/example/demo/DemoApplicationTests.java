package com.example.demo;

import junit.framework.JUnit4TestCaseFacade;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
class DemoApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testLamdbaFilter() {
        List<String> list = Stream.of("a", "b", "c").filter((v) -> v.equals("a")).collect(Collectors.toList());
        TestCase.assertEquals(Arrays.asList("a"), list);
    }

    @Test
    void testLamdbaMap() {
        List<String> list = Stream.of("a", "b").map(str -> str.toUpperCase()).collect(Collectors.toList());
        TestCase.assertEquals(Arrays.asList("A", "B"), list);
    }

    @Test
    void testLamdbaFlatMap() {
        List<String> list = Stream.of(Arrays.asList("a", "b"), Arrays.asList("c", "d")).
                flatMap(List::stream).collect(Collectors.toList());
        TestCase.assertEquals(Arrays.asList("a", "b", "c", "d"), list);
    }

    @Test
    void testLamdbaSum() {
        Comparator<Integer> comparator = (x, y) -> x > y ? x : y;
        BinaryOperator.maxBy(comparator);
        Optional<Integer> count = Stream.of(1, 2, 3).reduce(BinaryOperator.maxBy(comparator));
        System.out.println(count.get());
    }

    @Test
    void testLamdbaAverag() {
        Double avag = Stream.of(1, 2, 3).collect(Collectors.averagingDouble((t) -> t));
        System.out.println(avag);
    }
}
