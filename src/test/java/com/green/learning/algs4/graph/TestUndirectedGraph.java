package com.green.learning.algs4.graph;


import com.green.learning.algs4.graph.routine.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TestUndirectedGraph
{
    @Test
    public void testGraph()
    {
        //        UndirectedGraph graph = GraphData.getUndirectedGraph(GraphData.TINYG);
        Digraph graph = GraphData.getDigraph(GraphData.TINYG);
        System.out.println(graph.V() + " vertices");
        System.out.println(graph.E() + " edges");
        
        System.out.print("neighbors of vertex [0]:");
        for (int v : graph.adj(0))
            System.out.print(v + " ");
        System.out.println();
        
        System.out.println(graph.toString());
    }
    
    
    
    @Test
    public void testSCC()
    {
        //        Digraph graph = GraphData.getDigraph(GraphData.TINYDG);
        //        Digraph graph = GraphData.getDigraph(GraphData.MEDIUMDG);
        Digraph graph = GraphData.getDigraph(GraphData.TINYDAG);
        StrongConnectedComponent SCC = new KosarajuSharirSCC(graph);
        ((KosarajuSharirSCC) SCC).printResult();
    }
    
    @Test
    public void testGraphDegree()
    {
        //        UndirectedGraph graph = GraphData.TINYG.getUndirectedGraph();
        UndirectedGraph graph = GraphData.MEDIUMG.getUndirectedGraph();
        //        UndirectedGraph graph = GraphData.LARGEG.getUndirectedGraph();
        System.out.println("total degree: " + graph.totalDegree());
        System.out.println("average degree: " + graph.avgDegree());
        System.out.println("max degree: " + graph.maxDegree());
        System.out.println("number of self loop: " + graph.numOfSelfLoops());
        
        System.out.println("\nvertex\tdegree");
        for (int v = 0; v < graph.V(); v++)
            System.out.println(v + "\t" + graph.degree(v));
    }
    
    @Test
    public void testBipartiteGraph()
    {
        UndirectedGraph graph = GraphData.TINYG.getUndirectedGraph();
        //        UndirectedGraph graph = GraphData.BIPARTITEG_1.getUndirectedGraph();
        //        UndirectedGraph graph = GraphData.BIPARTITEG_2.getUndirectedGraph();
        
        BipartiteGraphSolver bipartiteGraphSolver = new BipartiteGraphSolverDFS(graph);
        //        BipartiteGraphSolver bipartiteGraphSolver = new BipartiteGraphSolverBFS(graph);
        bipartiteGraphSolver.printResult();
        
        if (bipartiteGraphSolver.isBipartiteGraph())
        {
            for (int v = 0; v < graph.V(); v++)
            {
                for (int w : graph.adj(v))
                {
                    Assertions.assertFalse(bipartiteGraphSolver.inSameColor(v, w));
                }
            }
        } else
        { // graph has an odd-length cycle
            // verify cycle
            int first = -1, last = -1;
            for (int v : bipartiteGraphSolver.getOddLengthCycle())
            {
                if (first == -1) first = v;
                else Assertions.assertTrue(graph.edgeBetween(v, last));
                last = v;
            }
            Assertions.assertTrue(first == last, String.format("cycle begins with %d and ends with %d\n", first, last));
        }
    }
    
    @Test
    public void testCircle()
    {
        Graph graph = GraphData.TINYG.getUndirectedGraph();
        //        Graph graph = GraphData.MEDIUMG.getUndirectedGraph();
        //        Graph graph = GraphData.LARGEG.getUndirectedGraph();
        CirclePathsSolver circleSolver = new CirclePathsSolver(graph);
        System.out.println("has a circle: " + circleSolver.hasACircle());
        System.out.println(circleSolver);
        
        for (CirclePathsSolver.CirclePath circlePath : circleSolver.getCirclePaths())
        {
            List<Integer> path = circlePath.getPath();
            for (int i = 0; i < path.size() - 1; i++)
                Assertions.assertTrue(graph.edgeBetween(path.get(i), path.get(i + 1)));
            Assertions.assertTrue(graph.edgeBetween(path.get(0), path.get(path.size() - 1)));
        }
    }
    
    @Test
    public void testDigraphCircuit()
    {
        //        Digraph digraph = GraphData.getDigraph(GraphData.TINYDG);
        //        Digraph digraph = GraphData.getDigraph(GraphData.TINYDAG);
        Digraph digraph = GraphData.getDigraph(GraphData.MEDIUMDG);
        CircuitFinder circuitFinder = new DigraphCircuitFinder(digraph);
        circuitFinder.printResult();
    }
    
    
    @RepeatedTest(10)
    public void testUndirectedEulerianCycle1()
    {
        int V = 5, E = 10;
        UnweightedUndirectedGraph graph = UnweightedUndirectedGraphGenerator.eulerianCycle(V, E);
        System.out.println(graph);
        UndirectedEulerianCycle EulerianCycleFinder1 = new UndirectedEulerianCycle(graph);
        Assertions.assertTrue(EulerianCycleFinder1.isEulerianCycleExist());
        EulerianCycleFinder1.printResult();
        
        int F = 3;
        UnweightedUndirectedGraphGenerator.addRandomEdges(graph, F);
        System.out.println(graph);
        UndirectedEulerianCycle EulerianCycleFinder2 = new UndirectedEulerianCycle(graph);
        //        Assertions.assertTrue(EulerianCycleFinder2.isEulerianCycleExist());
        EulerianCycleFinder2.printResult();
    }
    
    @RepeatedTest(10)
    public void testUndirectedEulerianCycle2()
    {
        int V = 7, E = 10;
        UndirectedGraph graph = UnweightedUndirectedGraphGenerator.simpleGraph(V, E);
        System.out.println(graph);
        UndirectedEulerianCycle undirectedEulerianCycle = new UndirectedEulerianCycle(graph);
        undirectedEulerianCycle.printResult();
    }
    
//    @Test
//    public void testUndirectedSymbolGraph()
//    {
//        final int V = 10;
//        List<String> symbolList = new ArrayList<>();
//        for (int i = 0; i < 26; i++)
//            symbolList.add((char) ('A' + i) + "");
//        Collections.shuffle(symbolList);
//        Set<String> symbols = new HashSet<>();
//        for(int i = 0; i< V; i++)
//            symbols.add(symbolList.get(i));
//
//        UndirectedSymbolGraph symbolGraph = new UndirectedSymbolGraph(symbols);
//        final int N = 15;
//        Random random = new Random();
//        for (int i = 0; i < N; i++)
//        {
//            int v = random.nextInt(V);
//            int w = random.nextInt(V);
//            symbolGraph.addEdge(symbolList.get(v), symbolList.get(w));
//        }
//        System.out.println(symbolGraph);
//
//        System.out.print(symbolList.get(0) + ": ");
//        for(String adjSymbol: symbolGraph.adj(symbolList.get(0))){
//            System.out.print(adjSymbol + " ");
//        }
//        System.out.println();
//
////        List<String> symbolList2 = symbolGraph.symbols();
////        symbolList2.remove("C");
//
//
////        Assertions.assertThrows(UnsupportedOperationException.class, symbolList.add("C"));
//    }
}
