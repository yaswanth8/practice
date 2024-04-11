package com.careerit.jfs.lld;

import java.util.List;
import java.util.function.Predicate;

public class Refactored {


    public static int sumByTest(List<Integer> nums, Predicate<Integer> selector){

        return nums
                .stream()
                .filter(selector)
                .mapToInt(Integer::intValue)
                .sum();
    }

    public static void main(String[] args) {

        System.out.println(sumByTest(List.of(1,2,3,4,5),(x)->true));
        System.out.println(sumByTest(List.of(1,2,3,4,5),(x)->x%2!=0));
    }
}
