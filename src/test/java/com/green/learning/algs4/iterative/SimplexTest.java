package com.green.learning.algs4.iterative;

import com.green.learning.algs4.util.MathUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimplexTest
{
    @Test
    void test1()
    {
        /*
        x +  y ≤ 4
        x + 3y ≤ 6
        x ≥ 0, y ≥ 0
         */
        double[][] A = {{1, 1}, {1, 3}};
        double[] b = {4, 6};
        double[] c = {3, 5};
        double maxObjVal = 14;
        double[] optSolution = {3, 1};
        
        Simplex lpSolver = new Simplex(A, b, c);
//        assertEquals(maxObjVal, lpSolver.maxObjectiveValue());
        assertTrue(MathUtils.floatEqual(maxObjVal,lpSolver.maxObjectiveValue()));
        assertArrayEquals(optSolution, lpSolver.optimalSolution());
    }
    
    @DisplayName("Brewer's Problem")
    @Test
    void test2()
    {
        /*
         5x + 15y ≤ 480
         4x +  4y ≤ 160
        35x + 20y ≤ 1190
        x ≥ 0, y ≥ 0
        
        solve max {13x + 23y}
         */
        double[][] A = {{5, 15}, {4, 4}, {35, 20}};
        double[] b = {480, 160, 1190};
        double[] c = {13, 23};
        double maxObjVal = 800;
        double[] optSolution = {12, 28};
        
        Simplex simplex = new Simplex(A,b,c);
        assertTrue(MathUtils.floatEqual(maxObjVal, simplex.maxObjectiveValue()));
        System.out.println("max objective value: " + simplex.maxObjectiveValue());
        
        System.out.println("optimal solution: ");
        double[] optimalSolution = simplex.optimalSolution();
        for(int i = 0; i < optSolution.length; i++)
        {
            assertTrue(MathUtils.floatEqual(optSolution[i], optimalSolution[i]));
            System.out.printf("%d : %.6f%n", i, optimalSolution[i]);
//            System.out.println(i + " : " + optimalSolution[i]);
        }
    }
    
    @Test
    void test3()
    {
        /*
        original form
         x +  y ≥ 4
         x + 3y ≥ 6
        x ≥ 0, y ≥ 0
        
        standard form
        x +  y - u = 4
        x + 3y - v = 6
        x ≥ 0, y ≥ 0, u ≥ 0, v ≥ 0
         */
        double[][] A = {{1, 1, -1, 0}, {1, 3, 0, -1}};
        double[] b = {4, 6};
        
        // min 2x + y
        double[] c1 = {-2, -1};
        double[] x1 = {3, 1};
        double minVal = 7;
        
        Simplex simplex1 = new Simplex(A,b, c1);
        assertTrue(MathUtils.floatEqual(minVal, simplex1.maxObjectiveValue()));
        System.out.println("max objective value: " + simplex1.maxObjectiveValue());
    
        System.out.println("optimal solution: ");
        double[] optimalSolution = simplex1.optimalSolution();
        for(int i = 0; i < x1.length; i++)
        {
            assertTrue(MathUtils.floatEqual(x1[i], optimalSolution[i]));
            System.out.printf("%d : %.6f%n", i, optimalSolution[i]);
//            System.out.println(i + " : " + optimalSolution[i]);
        }
        
    }
}