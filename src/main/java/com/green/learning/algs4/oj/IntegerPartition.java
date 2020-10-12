package com.green.learning.algs4.oj;

public class IntegerPartition
{
    // 正整数 n 的一种划分
    private int[] partition;
    
    // 缓存待输出的划分
    private StringBuilder partitionStrs;
    
    public IntegerPartition(int n)
    {
        partition = new int[n];
        partitionStrs = new StringBuilder();
        int partitionCount = q(n, n, 0);
        System.out.printf("正整数%d有如下%d种不同的划分：\n", n, partitionCount);
        System.out.println(partitionStrs);
    }
    
    /**
     * 输出已计算完成的一个划分 {@code partition[0: len]}
     *
     * @param len 该划分中包含的加数个数
     */
    private void printPartition(int len)
    {
        partitionStrs.append(partition[0]);
        for (int i = 1; i < len; i++)
        {
            partitionStrs.append('+').append(partition[i]);
        }
        partitionStrs.append('\n');
    }
    
    /**
     * 算法核心代码：输出所有划分，并计算总的划分个数
     *
     * @param n   整数划分的输入正整数
     * @param m   划分中最大加数 n1 的上限，即 n1 <= m
     * @param len 当前计算的一个划分中包含的加数个数
     * @return 总的划分个数
     */
    private int q(int n, int m, int len)
    {
        if (m == 1)
        {
            // [case 1] 最大加数的上限为 1，即只有唯一的加数 1，
            // 因此只有 1 种划分 n = 1 + 1 + ... + 1
            for (; n > 0; n--)
            {
                partition[len++] = 1;
            }
            printPartition(len);
            return 1;
        }
        
        if (m > n)
        {
            // [case 2] 最大加数的上限超过 n，简化为 m = n 的情况
            return q(n, n, len);
        }
        
        if (m == n)
        {
            // [case 3]
            // [case 3.1] 唯一的加数正好为 n，有唯一的划分 n = n
            partition[len] = n;
            printPartition(len + 1);
            
            // [case 3.2] 递归计算最大加数 n1 <= n - 1 的情况
            return 1 + q(n, n - 1, len);
        }
        
        // [case 4] 1 < m < n
        // [case 4.1] 递归计算最大加数 n1 <= m - 1 的情况
        int count1 = q(n, m - 1, len);
        
        // [case 4.2] 令最大加数 n1 = m
        partition[len++] = m;
        // 在后续递归中计算正整数 n - m 的划分情况
        int count2 = q(n - m, m, len);
        
        // 累加 [case 4.1] 和 [case 4.2] 的划分数
        return count1 + count2;
    }
    
    public static void main(String[] args)
    {
        // 多组测试样例
        new IntegerPartition(1); // 边界测试
        new IntegerPartition(4);
        new IntegerPartition(6);
        new IntegerPartition(10);
    }
}
