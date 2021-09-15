package com.green.learning.algs4.oj;

/**
 * 只需要找到 N 皇后问题的一个可行解即可
 */
public class NQueensSolver_v1
{
    // 皇后个数 N
    private final int N;

    // cols[i] 表示 第 i 行的皇后所在的列，即皇后的位置为 (i, cols[i])
    private int[] cols;

    /**
     * @param N 皇后个数
     */
    public NQueensSolver_v1(int N)
    {
        this.N = N;
    }

    /**
     * N 皇后求解算法
     *
     * @param maxSearchSecs 允许搜索的最长时间（秒）
     * @param displayBoard  如果找到一个可行解，是否输出棋盘上 N 个皇后的布局
     * @return {@code this.cols} 如果找到一个可行解
     * {@code null} 如果规定时间内没有找到一个可行解
     */
    public int[] solve(long maxSearchSecs, boolean displayBoard)
    {
        long startTime = System.currentTimeMillis();
        long maxSearchMillis = maxSearchSecs * 1000;

        // step 1. 随机地将 N 个皇后分布在棋盘上，使得棋盘的每行、每列只有一个皇后
        cols = new int[this.N];
        for (int i = 0; i < N; i++)
            cols[i] = i;
        NQueensUtil.shuffle(cols);

        // step 2. 如果 N 个皇后之间不存在冲突，则转 step 6
        while (NQueensUtil.existConflicts(cols))
        {
            long cur_time = System.currentTimeMillis();
            if ((cur_time - startTime) >= maxSearchMillis)
            {
                // 规定时间内仍未找到可行解，退出，算法结束
                System.out.printf("N = %d, after searching for %d seconds, the algorithm still cannot find a solution. Exit...\n", N, maxSearchSecs);
                return null;
            }

            // 邻域内的局部搜索
            neighbourhoodSearch();
        }

        // step 6. 找到一个可行解，输出结果，算法结束
        long endTime = System.currentTimeMillis();
        System.out.printf("N = %d, find a solution within %d seconds\n", N, ((endTime - startTime) / 1000));
        if (displayBoard)
            NQueensUtil.displayQueens(cols);
        return cols;
    }

    /**
     * 邻域内局部搜索
     */
    private void neighbourhoodSearch()
    {
        for (int p = 0; p < N; p++)
        {
            for (int q = p + 1; q < N; q++)
            {
                // step 3. 检查棋盘上的两个皇后，交换他们的位置
                // 由 cols[p] 和 cols[q] 引起的冲突数
                int reduced_conflicts = conflicts(p, q);

                // 交换后新位置上的皇后引起的冲突数
                NQueensUtil.swap(cols, p, q);
                int increased_conflicts = conflicts(p, q);

                // step 4. 如果交换后的冲突数减小，则接受这种交换，转 step 2
                if (reduced_conflicts > increased_conflicts)
                    return;
                else NQueensUtil.swap(cols, p, q); // 否则拒绝交换
            }
        }
        // step 5. 陷入了局部极小，即交换了所有的皇后后，冲突数仍不能下降
        //         转 step 1，重新初始化
        NQueensUtil.shuffle(cols);
    }

    /**
     * 由皇后 (p, cols[p]) 和 (q, cols[q]) 引起的冲突数
     */
    private int conflicts(int p, int q)
    {
        int conflict_caused_by_pq = 0;
        // 由 (p, cols[p]) 引起的冲突数
        for (int i = 0; i < N; i++)
        {
            if (i == p) continue;
            if (cols[i] == cols[p])
                conflict_caused_by_pq++;
            if (Math.abs(cols[i] - cols[p]) == Math.abs(i - p))
                conflict_caused_by_pq++;
        }
        // 由 (q, cols[q]) 引起的冲突数
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
