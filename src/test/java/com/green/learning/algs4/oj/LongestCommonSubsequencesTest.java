package com.green.learning.algs4.oj;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LongestCommonSubsequencesTest
{
    private static class LCS_TestCase
    {
        private String x;
        private String y;
        private String lcs;
        
        public LCS_TestCase(String x, String y, String lcs)
        {
            this.x = x;
            this.y = y;
            this.lcs = lcs;
        }
        
        public String getX()
        {
            return x;
        }
        
        public String getY()
        {
            return y;
        }
        
        public String getLcs()
        {
            return lcs;
        }
    }
    
    
    @Test
    void test_lcs_dp1()
    {
        LCS_TestCase[] testCases = {
                new LCS_TestCase("BCDAACD", "ACDBAC", "CDAC"),
                new LCS_TestCase("ABCDGH", "AEDFHR", "ADH"),
                new LCS_TestCase("ABC","AC","AC")
        };
        
        for (LCS_TestCase testCase : testCases)
        {
            assertEquals(testCase.getLcs(),
                    LongestCommonSubsequences.lcs_dp1(testCase.getX(), testCase.getY()));
        }
    }
    
    @Test
    void test_lcs_rec()
    {
        LCS_TestCase[] testCases = {
                new LCS_TestCase("BCDAACD", "ACDBAC", "CDAC"),
                new LCS_TestCase("ABCDGH", "AEDFHR", "ADH"),
                new LCS_TestCase("ABC","AC","AC")
        };
    
        for (int t = 0; t < testCases.length; t++)
        {
            System.out.printf("test case [%d]\n", t);
            LCS_TestCase testCase = testCases[t];
            System.out.printf("x = %s ; y = %s ; \ntarget lcs = %s\n",
                    testCase.getX(), testCase.getY(), testCase.getLcs());
            System.out.print("output lcs = ");
            new LongestCommonSubsequencesRecursive(testCase.getX(), testCase.getY());
            System.out.println("\n");
        }
    }
}