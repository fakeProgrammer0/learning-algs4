package com.green.learning.algs4.oj;

/**
 * 只需要找到 N 皇后问题的一个可行解即可
 */
public class NQueensSearch_v1_backup
{
    // 皇后个数 N
    private final int N;

    // cols[i] 表示 第 i 行的皇后所在的列
    private final int[] cols;

    /**
     * @param N 皇后个数
     * @param allowed_secs 允许最长搜索时间（秒）
     */
    public NQueensSearch_v1_backup(int N, long allowed_secs, boolean displayBoard)
    {
        long start_time = System.currentTimeMillis();
        final long allowed_millis = allowed_secs * 1000;

        // step 1. 随机地将N个皇后分布在棋盘上，使得棋盘的每行、每列只有一个皇后
        this.N = N;
        cols = new int[this.N];
        randomInit();

        while (conflicts() > 0) // step 2. 计算皇后间的冲突数 Conflicts
        {
            long cur_time = System.currentTimeMillis();
            if ((cur_time - start_time) >= allowed_millis)
            {
                // 规定时间内仍未找到可行解，退出，算法结束
                System.out.printf("After searching for %d seconds, the algorithm still cannot find a solution. Exit...\n", (allowed_secs / 1000));
                return;
            }

            // 邻域内的局部搜索
            neighbourhoodSearch();
        }

        // step 6 & 7. 冲突数等于 0，找到一个可行解，输出结果，算法结束
        long cur_time = System.currentTimeMillis();
        System.out.printf("find a solution within %d seconds\n", ((cur_time - start_time) / 1000));
        NQueensUtil.displayQueens(cols);
    }

    /**
     * 随机初始 N 个皇后的位置
     */
    private void randomInit()
    {
        for (int i = 0; i < N; i++)
            cols[i] = i;
        NQueensUtil.shuffle(cols);
    }

    private void neighbourhoodSearch()
    {
        // step 4. 对于棋盘上的任意两个皇后，交换他们的位置，如果交换后的冲突数减少，则接受这种交换
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
                    return; // 交换后冲突数更少，允许交换
                else NQueensUtil.swap(cols, p, q);
            }
        }
        // step 5. 陷入了局部极小，即交换了所有的皇后后，冲突数仍不能下降
        // 陷入局部最优解，重新初始化
        randomInit();
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
}
