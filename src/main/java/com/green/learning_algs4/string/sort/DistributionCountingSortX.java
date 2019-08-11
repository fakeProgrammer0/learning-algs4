package com.green.learning_algs4.string.sort;

import com.green.learning_algs4.list.XArrayList;

/**
 * a variant of DistributionCountingSort which use O(N) space,
 * where <b>N</b> is the length of the array to be sorted.
 * @see DistributionCountingSort
 */
@Deprecated
public class DistributionCountingSortX
{
    public static void sort(Integer[] A)
    {
        XArrayList<Integer> unsignedNumList = new XArrayList<>();
        XArrayList<Integer> negativeNumAbsList = new XArrayList<>();
        for (int N : A)
            if (N >= 0) unsignedNumList.append(N);
            else negativeNumAbsList.append(-N);
    
        Integer[] unsignedNums = new Integer[unsignedNumList.size()];
        unsignedNumList.toArray(unsignedNums);
        sortUnsignedInts(unsignedNums);
    
        Integer[] negativeNumsAbs = new Integer[negativeNumAbsList.size()];
        negativeNumAbsList.toArray(negativeNumsAbs);
        sortUnsignedInts(negativeNumsAbs);
    
        int j = 0;
        for (int i = negativeNumsAbs.length - 1; i >= 0; i--)
            A[j++] = -negativeNumsAbs[i];
    
        System.arraycopy(unsignedNums, 0, A, j, unsignedNums.length);
    }
    
    private static void sortUnsignedInts(Integer[] A)
    {
        int maxVal = A[0], minVal = A[0];
        for (int i = 1; i < A.length; i++)
        {
            if (A[i].compareTo(maxVal) > 0)
                maxVal = A[i];
            if (A[i].compareTo(minVal) < 0)
                minVal = A[i];
        }
        
        final int len = maxVal - minVal + 1;
        if(len <= 0)
            throw new IllegalArgumentException("The range of integers is too large! Don't use this algorithm!");
        
        int[] D = new int[len];
        for (int i = 0; i < A.length; i++)
            D[A[i] - minVal]++;
    
        // 重新计算分布值
        // 结果：D[i]表示 (i+minVal+1) 在排序好的数组中的起始位置
        for (int i = 1; i < D.length; i++)
            D[i] += D[i - 1];
    
        Integer[] aux = new Integer[A.length];
        for (int i = A.length - 1; i >= 0; i--)
        {
            //            int j = A[i] - minVal; // calculate the index
            //            aux[D[j] - 1] = A[i];
            //            D[j]--;
        
            aux[--D[A[i] - minVal]] = A[i];
        }
    
        for (int i = 0; i < A.length; i++)
            A[i] = aux[i];
    }
}
