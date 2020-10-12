package com.green.learning.algs4.oj;

public class PermuteMatchBrackets
{
    private int n;
    private char[] bracketStr;
    private static final char LEFT_BRACKET = '(';
    private static final char RIGHT_BRACKET = ')';
    
    public PermuteMatchBrackets(int n)
    {
        this.n = n;
        bracketStr = new char[2 * n];
        System.out.printf("n = %d\n", n);
        permute(0, 0);
        System.out.println();
    }
    
    public void permute(int left, int right)
    {
        if (left == n && right == n)
        {
            System.out.println(bracketStr);
            return;
        }
        
        if (left < n)
        {
            bracketStr[left + right] = LEFT_BRACKET;
            permute(left + 1, right);
        }
        if (left > right)
        {
            bracketStr[left + right] = RIGHT_BRACKET;
            permute(left, right + 1);
        }
    }
    
    public static void main(String[] args)
    {
        new PermuteMatchBrackets(2);
        new PermuteMatchBrackets(3);
        new PermuteMatchBrackets(4);
    }
}
