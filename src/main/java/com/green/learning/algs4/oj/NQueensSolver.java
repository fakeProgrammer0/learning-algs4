package com.green.learning.algs4.oj;

import java.util.Arrays;

/**
 * 只需要找到 N 皇后问题的一个可行解即可。
 * 在本算法中，采用了一种空间换时间的策略高效地
 * (1) 检测 N 个皇后之间是否存在冲突 -- {@code boolean existConflicts()} 函数，其时间复杂度为 O(N)
 * (2) 计算由两个皇后引起和其它皇后之间的冲突数 -- {@code int conflicts(int p, int q)} 函数，其时间复杂度为 O(1)
 * 相关原理具体可参考 "10394 用位运算速解 n 皇后问题 - 王赟 Maigo的文章 - 知乎 https://zhuanlan.zhihu.com/p/22846106"
 */
public class NQueensSolver
{
    // 皇后个数 N
    private final int N;

    // cols[i] 表示 第 i 行的皇后所在的列，即皇后的位置为 (i, cols[i])
    private int[] cols;

    private final int DIAGONAL_COUNT;

    /**
     * 每条对角线上的皇后数量
     * (i, cols[i]) 的皇后所在的对角线的编号为 N - 1 - i + cols[i]
     */
    private int[] diagonalQueens;

    /**
     * 每条反对角线上的皇后数量
     * (i, cols[i]) 的皇后所在的对角线的编号为 i + cols[i]
     */
    private int[] antidiagonalQueens;

    private void shuffleQueens()
    {
        NQueensUtil.shuffle(cols);

        Arrays.fill(diagonalQueens, 0);
        Arrays.fill(antidiagonalQueens, 0);
        for (int i = 0; i < N; i++)
        {
            diagonalQueens[N - 1 - i + cols[i]]++;
            antidiagonalQueens[i + cols[i]]++;
        }
    }

    private void swapQueens(int p, int q)
    {
        // 更新对角线和反对角线上的皇后数量
        diagonalQueens[N - 1 - p + cols[p]]--;
        antidiagonalQueens[p + cols[p]]--;
        diagonalQueens[N - 1 - q + cols[q]]--;
        antidiagonalQueens[q + cols[q]]--;

        NQueensUtil.swap(cols, p, q);

        diagonalQueens[N - 1 - p + cols[p]]++;
        antidiagonalQueens[p + cols[p]]++;
        diagonalQueens[N - 1 - q + cols[q]]++;
        antidiagonalQueens[q + cols[q]]++;
    }

    /**
     * 检测 N 个皇后之间是否存在冲突
     */
    private boolean existConflicts()
    {
        for (int id = 1; id < DIAGONAL_COUNT; id++)
        {
            if (diagonalQueens[id] > 1 || antidiagonalQueens[id] > 1)
                return true;
        }
        return false;
    }

    /**
     * @param N 皇后个数
     */
    public NQueensSolver(int N)
    {
        this.N = N;
        this.DIAGONAL_COUNT = 2 * N - 1;
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
        diagonalQueens = new int[DIAGONAL_COUNT];
        antidiagonalQueens = new int[DIAGONAL_COUNT];
        shuffleQueens();

        // step 2. 如果 N 个皇后之间不存在冲突，则转 step 6
        while (existConflicts())
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
        System.out.printf("N = %d, find a solution within %d seconds\n", N, (long)Math.ceil((endTime - startTime) * 1.0 / 1000));
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
                swapQueens(p, q);
                int increased_conflicts = conflicts(p, q);

                // step 4. 如果交换后的冲突数减小，则接受这种交换，转 step 2
                if (reduced_conflicts > increased_conflicts)
                    return;
                else swapQueens(p, q);; // 否则拒绝交换
            }
        }
        // step 5. 陷入了局部极小，即交换了所有的皇后后，冲突数仍不能下降
        //         转 step 1，重新初始化
        shuffleQueens();
    }

    /**
     * 由皇后 (p, cols[p]) 和 (q, cols[q]) 引起的冲突数
     */
    private int conflicts(int p, int q)
    {
        int conflict_caused_by_pq = 0;

        // 由 (p, cols[p]) 引起的冲突数
        conflict_caused_by_pq += diagonalQueens[N - 1 - p + cols[p]] - 1;
        conflict_caused_by_pq += antidiagonalQueens[p + cols[p]] - 1;

        // 由 (q, cols[q]) 引起的冲突数
        conflict_caused_by_pq += diagonalQueens[N - 1 - q + cols[q]] - 1;
        conflict_caused_by_pq += antidiagonalQueens[q + cols[q]] - 1;
        // 如果 (p, cols[p]) 和 (q, cols[q]) 同时位于同一对角线或反对角线上
        if(Math.abs(p - q) == Math.abs(cols[p] - cols[q]))
            conflict_caused_by_pq--;

        return conflict_caused_by_pq;
    }
}
