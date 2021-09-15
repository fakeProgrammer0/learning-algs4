package com.green.learning.algs4.oj;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArithmeticReductionTest
{

    @Test
    void test_findMaxMin_1()
    {
        double[] nums = {2, 6, 9, 3};
        char[] operators = {'-', '*', '+', '*'};

        ArithmeticReduction.Result result  = ArithmeticReduction.findMaxMin(nums, operators);
        System.out.printf("min_val = %.0f\nmax_val = %.0f", result.getMin(), result.getMax());
    }

    @Test
    void test_findMaxMin_2()
    {
        double[] nums = {2, -9, -7, 1};
        char[] operators = {'+', '*', '-', '*'};

        ArithmeticReduction.Result result  = ArithmeticReduction.findMaxMin(nums, operators);
        System.out.printf("min_val = %.0f\nmax_val = %.0f", result.getMin(), result.getMax());
    }
}