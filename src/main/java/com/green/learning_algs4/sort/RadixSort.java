package com.green.learning_algs4.sort;


import com.green.learning_algs4.list.XArrayList;
import com.green.learning_algs4.list.XList;

import java.util.ArrayList;
import java.util.Arrays;

@Deprecated
public class RadixSort
{
    
    private static void checkUnsignedInts(Integer[] A)
    {
        for(int a : A)
            if(a < 0)
                throw new IllegalArgumentException(a + " is negative");
    }
    
    private static void validateBase(int base)
    {
        if(base <= 0)
            throw new IllegalArgumentException("base should be positive");
    }
    
    private static int bitCountOfBinary(int N)
    {
        return Integer.SIZE - Integer.numberOfLeadingZeros(N);
    }
    
    private static int bitCount(int base, int N)
    {
        validateBase(base);
        if(base == 2) return bitCountOfBinary(N);
        
        int bitCount = 0;
        do
        {
            N /= base;
            bitCount++;
        } while (N != 0);
        return bitCount;
    }
    
    private static int bitCountOfDecimal(int N)
    {
        return bitCount(10, N);
    }
    
    private static int numAtBit(int base, int N, int bitNo)
    {
        //从个位开始，记为第0位
        return N / (int) Math.pow(base, bitNo) % base;
    }
    
    private static int numAtBitOfDecimal(int N, int bitNo)
    {
        return numAtBit(10, N, bitNo);
    }
    
    public static void sortUnsignedInt(Integer[] A)
    {
        checkUnsignedInts(A);
        @SuppressWarnings({"rawtypes", "unchecked"})
        XList<Integer>[] buckets = (XList<Integer>[]) new XList[10];
        for (int i = 0; i < buckets.length; i++)
            buckets[i] = new XArrayList<>();
        
        int maxVal = A[0];
        for (int i = 1; i < A.length; i++)
            if (A[i].compareTo(maxVal) > 0)
                maxVal = A[i];
        
        int bitCounts = bitCountOfDecimal(maxVal);
        
        for (int bitNo = 0; bitNo < bitCounts; bitNo++)
        {
            for (int i = 0; i < A.length; i++)
                buckets[numAtBitOfDecimal(A[i], bitNo)].append(A[i]);
            
            int k = 0;
            // 用迭代器模式，可读性更高
            for (XList<Integer> bucket: buckets)
            {
                for (int N: bucket)
                    A[k++] = N;
                bucket.clear();
            }
            
            // 循环看起来没那么清晰
//            for (int i = 0; i < buckets.length; i++)
//            {
//                for (int j = 0; j < buckets[i].size(); j++)
//                    A[k++] = buckets[i].get(j);
//                buckets[i].clear();
//            }
        }
    }
    
    //对正负样本分开处理
    public static void sort(Integer[] A)
    {
        ArrayList<Integer> unsignedNumList = new ArrayList<>();
        ArrayList<Integer> negativeNumAbsList = new ArrayList<>();
        for (int N : A)
        {
            if (N >= 0) unsignedNumList.add(N);
            else negativeNumAbsList.add(-N);
        }
        
        Integer[] unsignedNums = new Integer[unsignedNumList.size()];
        unsignedNumList.toArray(unsignedNums);
        radixSortUnsignedInt(unsignedNums);
        
        Integer[] negativeNumsAbs = new Integer[negativeNumAbsList.size()];
        negativeNumAbsList.toArray(negativeNumsAbs);
        radixSortUnsignedInt(negativeNumsAbs);
        
        int cnt = 0;
        for (int i = negativeNumsAbs.length - 1; i >= 0; i--)
            A[cnt++] = -negativeNumsAbs[i];
        
        System.arraycopy(unsignedNums, 0, A, cnt, unsignedNums.length);
        //        for (int i = 0; i < unsignedNums.length; i++)
        //            A[cnt++] = unsignedNums[i];
        
    }
    
    //桶排序 + 分布计数排序
    public static void radixSortUnsignedInt(Integer[] A)
    {
        int maxVal = A[0];
        for (int i = 1; i < A.length; i++)
            if (A[i].compareTo(maxVal) > 0)
                maxVal = A[i];
        
        int bitCounts = bitCountOfDecimal(maxVal);
        
        //        final int base = 10;
        Integer[] counts = new Integer[10];
        Integer[] S = new Integer[A.length];
        
        //        for (int bitNo = 0, currBitWeight = 1; bitNo < bitCounts; bitNo++, currBitWeight *= base)
        for (int bitNo = 0; bitNo < bitCounts; bitNo++)
        {
            Arrays.fill(counts, 0);
            
            for (int i = 0; i < A.length; i++)
                counts[numAtBitOfDecimal(A[i], bitNo)]++;
            
            for (int i = 1; i < counts.length; i++)
                counts[i] += counts[i - 1];
            
            for (int i = A.length - 1; i >= 0; i--)
                S[--counts[numAtBitOfDecimal(A[i], bitNo)]] = A[i];
            
            System.arraycopy(S, 0, A, 0, S.length);
        }
        
    }
}
