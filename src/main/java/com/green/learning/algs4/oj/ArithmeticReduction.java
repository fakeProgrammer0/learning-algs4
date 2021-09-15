package com.green.learning.algs4.oj;

public class ArithmeticReduction
{
    public static class Result
    {
        private final double min;
        private final double max;

        public Result(double min, double max)
        {
            this.min = min;
            this.max = max;
        }

        public double getMin()
        {
            return min;
        }

        public double getMax()
        {
            return max;
        }
    }

    public static Result findMaxMin(double[] nums, char[] operators)
    {
        final int N = nums.length;
        assert operators.length == N;
        final int N2 = 2 * N;
        double[] vals = new double[N2];
        char[] ops = new char[N2];
        for (int i = 0; i < N; i++)
        {
            vals[i + N] = vals[i] = nums[i];
            ops[i + N] = ops[i] = operators[i];
        }

        double[][] minVals = new double[N2][N2];
        double[][] maxVals = new double[N2][N2];

        for (int i = 0; i < N2; i++)
        {
            maxVals[i][i] = minVals[i][i] = vals[i];
        }
        // r = chain_length - 1
        for (int r = 1; r < N; r++)
        {
            for (int i = 0, j = i + r; j < N2; i++, j++)
            {
                double temp_max = calculate(maxVals[i][i], ops[i], maxVals[i + 1][j]);
                double temp_min = calculate(minVals[i][i], ops[i], minVals[i + 1][j]);
                maxVals[i][j] = Math.max(temp_max, temp_min);
                minVals[i][j] = Math.min(temp_max, temp_min);

                for (int k = i + 1; k < j; k++)
                {
                    temp_max = calculate(maxVals[i][k], ops[k], maxVals[k + 1][j]);
                    temp_min = calculate(minVals[i][k], ops[k], minVals[k + 1][j]);
                    maxVals[i][j] = Math.max(maxVals[i][j], Math.max(temp_max, temp_min));
                    minVals[i][j] = Math.min(minVals[i][j], Math.min(temp_max, temp_min));
                }
            }
        }

        double maxVal = maxVals[0][N - 1], minVal = minVals[0][N - 1];
        for (int i = 1; i < N; i++)
        {
            maxVal = Math.max(maxVal, maxVals[i][i + N - 1]);
            minVal = Math.min(minVal, minVals[i][i + N - 1]);
        }
        return new Result(minVal, maxVal);
    }

    private static double calculate(double x, char op, double y)
    {
        switch (op)
        {
            case '+':
                return x + y;
            case '-':
                return x - y;
            case '*':
                return x * y;
            default:
                throw new IllegalArgumentException("unsupported operator [" + op + "]");
        }
    }
}
