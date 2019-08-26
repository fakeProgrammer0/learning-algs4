package com.green.learning.algs4.util;

public class MathUtils
{
    public static int expandToPowerOf2(int N)
    {
        if (N <= 0) throw new IllegalArgumentException("N should be positive.");
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
    
    private static final double DEFAULT_EPSILON = 1e-10;
    
    public static boolean floatEqual(double a, double b)
    {
        return floatEqual(a, b, DEFAULT_EPSILON);
    }
    
    /**
     * @param a a float value
     * @param b another float value
     * @param epsilon the error bound
     * @return true if the two floats' difference fall within the error bound
     */
    public static boolean floatEqual(double a, double b, double epsilon)
    {
        return a - b <= epsilon;
    }
}
