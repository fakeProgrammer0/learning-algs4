package com.green.learning.algs4.graph;

import com.green.learning.algs4.graph.routine.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class UnweightedUndirectedGraphGeneratorTest
{
    public void testParrallelEdges(UndirectedGraph graph)
    {
        boolean[] marked = new boolean[graph.V()];
        for (int v = 0; v < graph.V(); v++)
        {
            //            marked[v] = true; // 便于检测 self loops
            for (int w : graph.adj(v))
            {
                if (marked[w])
                    System.out.printf("parallel edge: %d - %d\n", v, w);
                Assertions.assertFalse(marked[w]);
                marked[w] = true;
            }
            
            for (int w : graph.adj(v))
            {
                marked[w] = false;
            }
            //            marked[v] = false;
        }
    }
    
    @RepeatedTest(10)
    public void testSimpleGraph()
    {
        final int V = 10;
        final int E = 40;
        UndirectedGraph simpleGraph = UnweightedUndirectedGraphGenerator.simpleGraph(V, E);
        System.out.println(simpleGraph);
        Assertions.assertEquals(simpleGraph.V(), V);
        Assertions.assertEquals(simpleGraph.E(), E);
        Assertions.assertEquals(simpleGraph.numOfSelfLoops(), 0);
        testParrallelEdges(simpleGraph);
    }
    
    @Test
    public void testRandomSimpleGraph()
    {
        final int V = 1000;
        final double p = 0.005;
        int E = V * (V - 1) / 2;
        UndirectedGraph simpleGraph = UnweightedUndirectedGraphGenerator.randomSimpleGraph(V, p);
        System.out.println(simpleGraph);
        System.out.printf("maxE = %d, avgE = %d, E = %d", E, (int) (E * p), simpleGraph.E());
        Assertions.assertEquals(simpleGraph.numOfSelfLoops(), 0);
        testParrallelEdges(simpleGraph);
    }
    
    @Test
    public void testCompleteGraph()
    {
        final int V = 10;
        UndirectedGraph completeGraph = UnweightedUndirectedGraphGenerator.completeGraph(V);
        System.out.println(completeGraph);
        Assertions.assertEquals((V * (V - 1) / 2), completeGraph.E());
        for (int v = 0; v < completeGraph.V(); v++)
            for (int w = 0; w < completeGraph.V(); w++)
                if (v != w)
                    Assertions.assertTrue(completeGraph.edgeBetween(v, w));
    }
    
    @RepeatedTest(10)
    public void testBipartiteGraph()
    {
        final int V1 = 30, V2 = 40;
        double p = 0.6;
        int E = 7;
        int F = 2;
        //        UndirectedGraph graph = UndirectedGraphGenerator.bipartiteGraph(V1, V2, E);
        UnweightedUndirectedGraph graph = UnweightedUndirectedGraphGenerator.bipartiteGraph(V1, V2, p);
        System.out.println(graph);
        BipartiteGraphSolver solver1 = new BipartiteGraphSolverDFS(graph);
        Assertions.assertTrue(solver1.isBipartiteGraph());
        solver1.printResult();
        
        UnweightedUndirectedGraphGenerator.addRandomEdges(graph, F);
        System.out.println();
        System.out.println(graph);
        BipartiteGraphSolver solver2 = new BipartiteGraphSolverBFS(graph);
        solver2.printResult();
        System.out.println();
    }
    
    @Test
    public void testCompleteBipartiteGraph()
    {
        final int V1 = 5, V2 = 7;
        UndirectedGraph graph = UnweightedUndirectedGraphGenerator.completeBipartiteGraph(V1, V2);
        System.out.println(graph);
        BipartiteGraphSolver bipartiteGraphSolver = new BipartiteGraphSolverDFS(graph);
        bipartiteGraphSolver.printResult();
        Set<Integer>[] vertices = bipartiteGraphSolver.getBipartiteVertices();
        for (int i = 0; i < vertices.length; i++)
        {
            for (Integer v : vertices[i])
                for (Integer w : vertices[1 - i])
                    Assertions.assertTrue(graph.edgeBetween(v, w));
        }
    }
    
    @Test
    public void testPath()
    {
        final int V = 10;
        UndirectedGraph graph = UnweightedUndirectedGraphGenerator.path(V);
        System.out.println(graph);
        int x = -1, y = -1;
        for (int v = 0; v < graph.V(); v++)
        {
            if (graph.degree(v) == 1)
            {
                if (x == -1) x = v;
                else if (y == -1) y = v;
                else
                    break;
            }
        }
        
        Assertions.assertNotEquals(-1, x);
        Assertions.assertNotEquals(-1, y);
        SearchPaths searchPathsFromX = new DFSPaths(graph, x);
        searchPathsFromX.printResult();
        
        SearchPaths searchPathsFromY = new DFSPaths(graph, y);
        searchPathsFromY.printResult();
    }
    
    @RepeatedTest(10)
    public void testCycle()
    {
        final int V = 10;
        UndirectedGraph graph = UnweightedUndirectedGraphGenerator.cycle(V);
        System.out.println(graph);
        CircuitFinder circuitFinder = new UndirectedGraphCircuitFinder(graph);
        Assertions.assertTrue(circuitFinder.hasACircuit());
        circuitFinder.printResult();
        System.out.println();
    }
    
    @RepeatedTest(10)
    public void testWheel()
    {
        int V = 8;
        UndirectedGraph graph = UnweightedUndirectedGraphGenerator.wheelGraph(V);
        System.out.println(graph);
        Assertions.assertEquals(V + 1, graph.V());
        Assertions.assertEquals(2 * V, graph.E());
        int centeralVertex = -1;
        for (int v = 0; v < graph.V(); v++)
            if (graph.degree(v) == V)
                centeralVertex = v;
            else Assertions.assertEquals(3, graph.degree(v));
        
        Assertions.assertNotEquals(-1, centeralVertex);
        for (int v = 0; v < graph.V(); v++)
            if (v != centeralVertex)
                Assertions.assertTrue(graph.edgeBetween(v, centeralVertex));
    }
    
    @RepeatedTest(10)
    public void testStarGraph()
    {
        int V = 8;
        UndirectedGraph graph = UnweightedUndirectedGraphGenerator.starGraph(V);
        System.out.println(graph);
        Assertions.assertEquals(V, graph.V());
        Assertions.assertEquals(V - 1, graph.E());
        int centeralVertex = -1;
        for (int v = 0; v < graph.V(); v++)
            if (graph.degree(v) == V - 1)
                centeralVertex = v;
            else Assertions.assertEquals(1, graph.degree(v));
        
        Assertions.assertNotEquals(-1, centeralVertex);
        for (int v = 0; v < graph.V(); v++)
            if (v != centeralVertex)
                Assertions.assertTrue(graph.edgeBetween(v, centeralVertex));
    }
    
    @RepeatedTest(20)
    public void testCompleteBinaryTree()
    {
        final int V = 41; // odd 奇数
//        final int V = 40; // even 偶数 会有一半测试不通过
        UndirectedGraph graph = UnweightedUndirectedGraphGenerator.completeBinaryTree(V);
        int root = -1;
        for(int v = 0; v < graph.V(); v++)
        {
            int degree = graph.degree(v);
            Assertions.assertTrue(degree >= 1 && degree <= 3);
            if(graph.degree(v) == 2)
            {
                if (root == -1)
                {
                    root = v;
                }
//                else Assertions.fail();
            }
        }
        
        Assertions.assertNotEquals(-1, root);
        BFSPaths searchPaths = new BFSPaths(graph, root);
        searchPaths.printResult();
        Map<Integer, Integer> hop2VerticesCntMap = new HashMap<>();
        for(int v = 0; v < graph.V(); v ++)
        {
            int edgeDist = searchPaths.edgeDistTo(v);
            if(!hop2VerticesCntMap.containsKey(edgeDist))
                hop2VerticesCntMap.put(edgeDist, 1);
            else
                hop2VerticesCntMap.put(edgeDist, hop2VerticesCntMap.get(edgeDist) + 1);
        }
        
        for(int hop: hop2VerticesCntMap.keySet())
        {
            int verticesCnt = hop2VerticesCntMap.get(hop);
            System.out.printf("hop = %d, verticesCnt = %d\n", hop, verticesCnt);
            Assertions.assertTrue(verticesCnt == Math.pow(2, hop) || verticesCnt + Math.pow(2, hop) - 1 == V);
        }
    }
    
    
}
