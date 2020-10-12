package com.green.learning.algs4.oj;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * java 1.8 ; junit 5.4
 */
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
                new LCS_TestCase("ABC", "AC", "AC"),
                new LCS_TestCase("1A2C3D4B56", "B1D23CA45B6A", "123456")
        };

        for (LCS_TestCase testCase : testCases)
        {
            String actual_lcs = LongestCommonSubsequences.lcs_dp1(testCase.getX(), testCase.getY());
            System.out.printf("x = %s ; y = %s ; \ntarget lcs = %s , length = %d\noutput lcs = %s , length = %d\n\n",
                    testCase.getX(), testCase.getY(),
                    testCase.getLcs(), testCase.getLcs().length(),
                    actual_lcs, actual_lcs.length());
            assertEquals(testCase.getLcs().length(), actual_lcs.length());

            assertEquals(testCase.getLcs().length(),
                    LongestCommonSubsequences.lcs_dp2(testCase.getX(), testCase.getY()).length());
        }
    }

    @Test
    void test_lcs_len()
    {
        LCS_TestCase[] testCases = {
                new LCS_TestCase("BCDAACD", "ACDBAC", "CDAC"),
                new LCS_TestCase("ABCDGH", "AEDFHR", "ADH"),
                new LCS_TestCase("ABC", "AC", "AC"),
                new LCS_TestCase("1A2C3D4B56", "B1D23CA45B6A", "123456")
        };

        for (LCS_TestCase testCase : testCases)
        {
            int actual_lcs_len = LongestCommonSubsequences.lcs_len(testCase.getX(), testCase.getY());
            System.out.printf("x = %s ; y = %s ; \ntarget lcs length = %d\noutput lcs length = %d\n\n",
                    testCase.getX(), testCase.getY(), testCase.getLcs().length(), actual_lcs_len);
            assertEquals(testCase.getLcs().length(), actual_lcs_len);

//            assertEquals(testCase.getLcs(),
//                    LongestCommonSubsequences.lcs_dp2(testCase.getX(), testCase.getY()));
//            assertEquals(testCase.getLcs().length(),
//                    LongestCommonSubsequences.lcs_len(testCase.getX(), testCase.getY()));
        }
    }

    @Test
    void test_lcs_rec()
    {
        LCS_TestCase[] testCases = {
                new LCS_TestCase("BCDAACD", "ACDBAC", "CDAC"),
                new LCS_TestCase("ABCDGH", "AEDFHR", "ADH"),
                new LCS_TestCase("ABC", "AC", "AC")
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