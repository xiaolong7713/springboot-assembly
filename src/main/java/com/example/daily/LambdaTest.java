package com.example.daily;

import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LambdaTest {
    public static void main(String[] args) {
        List<String> list = Stream.of("a","b","c").filter((v) -> v.equals("a")).collect(Collectors.toList());
    }
}
