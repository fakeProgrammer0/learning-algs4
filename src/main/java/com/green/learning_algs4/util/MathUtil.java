package com.green.learning_algs4.util;

public class MathUtil
{
    public static int expandToPowerOf2(int N)
    {
        if(N <= 0) throw new IllegalArgumentException("N should be positive.");
        int leadingZeroCounts = Integer.numberOfLeadingZeros(N);
        if (leadingZeroCounts + Integer.numberOfTrailingZeros(N) + 1 == Integer.SIZE)
            return N;
        if (leadingZeroCounts == 1) // signed number
            throw new IllegalArgumentException("N is too large");
        return 1 << (Integer.SIZE - leadingZeroCounts);
    }
    
    public static int expandToPowerOf2_buggy(int N)
    {
        return (N << 1) & (1 << (Integer.SIZE - Integer.numberOfLeadingZeros(N)));
    }
}
