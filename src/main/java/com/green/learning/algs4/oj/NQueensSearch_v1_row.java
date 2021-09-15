package com.green.learning.algs4.oj;

/**
 * only need to find a solution of the N Queen Problem
 */
public class NQueensSearch_v1_row
{
    private final int N;
    private final int[] cols;

    private void randomInit()
    {
        for (int i = 0; i < N; i++)
            cols[i] = i;
        NQueensUtil.shuffle(cols);
    }

    /**
     * @param n
     * @param allowed_secs
     */
    public NQueensSearch_v1_row(int n, long allowed_secs)
    {
        long start_time = System.currentTimeMillis();
        allowed_secs *= 1000;

        N = n;
        cols = new int[N];
        randomInit();

        while (conflicts() > 0)
        {
            long cur_time = System.currentTimeMillis();
            if ((cur_time - start_time) >= allowed_secs)
            {
                // 仍未找到解，退出
                System.out.printf("After searching for %d seconds, still cannot find a solution. Exit...\n", (allowed_secs / 1000));
                return;
            }

            neighbourhoodSearch();
        }

        // 找到一个可行解，输出
        long cur_time = System.currentTimeMillis();
        System.out.printf("find a solution within %d seconds\n", ((cur_time - start_time) / 1000));
        System.out.println("Queen's positions:");
        for (int i = 0; i < N; i++)
        {
            System.out.printf("(%d , %d)\n", cols[i], i);
        }
        display();
    }

    private void display()
    {
        StringBuilder sb = new StringBuilder();
        int[] rows = new int[N];
        for (int j = 0; j < N; j++)
            rows[cols[j]] = j;

        for (int i = 0; i < N; i++)
        {
            int j = 0;
            for (; j < rows[i]; j++)
                sb.append("* ");
            sb.append("Q ");
            for (; j < N; j++)
                sb.append("* ");
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }

    private void neighbourhoodSearch()
    {
        for (int p = 0; p < N; p++)
        {
            for (int q = p + 1; q < N; q++)
            {
                // 1. 由 cols[p] 和 cols[q] 引起的冲突数
                int reduced_conflicts = conflicts(p, q);
                // 2. 交换后新位置上的皇后引起的冲突数
                NQueensUtil.swap(cols, p, q);
                int increased_conflicts = conflicts(p, q);
                if (reduced_conflicts > increased_conflicts)
                    return; // 找到一个更好的邻居
                else NQueensUtil.swap(cols, p, q);
            }
        }
        // 陷入局部最优解，重新初始化
        randomInit();
    }

    private int conflicts(int p, int q)
    {
        int conflict_caused_by_pq = 0;
        // 1.1 由 cols[p] 引起的冲突数
        for (int i = 0; i < N; i++)
        {
            if (i == p) continue;
            if (cols[i] == cols[p])
                conflict_caused_by_pq++;
            if (Math.abs(cols[i] - cols[p]) == Math.abs(i - p))
                conflict_caused_by_pq++;
        }
        // 1.2. 由 cols[q] 引起的冲突数
        for (int i = 0; i < N; i++)
        {
            if (i == p || i == q) continue;
            if (cols[i] == cols[q])
                conflict_caused_by_pq++;
            if (Math.abs(cols[i] - cols[q]) == Math.abs(i - q))
                conflict_caused_by_pq++;
        }
        return conflict_caused_by_pq;
    }

    /**
     * 计算交换 cols[p] 和 cols[q] 两个位置的皇后之后，冲突数 conflicts 的相对变换
     * 返回正数：交换后，冲突数增加
     * 返回负数：交换后，冲突数减小
     * 返回 0 ：交换后，冲突数不变
     */
    private int relative_conflicts(int p, int q)
    {
        // 1. 由 cols[p] 和 cols[q] 引起的冲突数
        int reduced_conflicts = conflicts(p, q);
        // 2. 交换后新位置上的皇后引起的冲突数
        NQueensUtil.swap(cols, p, q);
        int increased_conflicts = conflicts(p, q);

        return increased_conflicts - reduced_conflicts;
    }

    /**
     * 计算皇后间的冲突数 conflicts
     */
    private int conflicts()
    {
        int count = 0;
        for (int i = 1; i < N; i++)
        {
            for (int j = 0; j < i; j++)
            {
                if (cols[i] == cols[j])
                    count++;
                if (Math.abs(cols[i] - cols[j]) == i - j)
                    count++;
            }
        }
        return count;
    }
}
