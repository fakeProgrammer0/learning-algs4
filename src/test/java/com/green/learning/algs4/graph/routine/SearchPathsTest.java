package com.green.learning.algs4.graph.routine;

import com.green.learning.algs4.graph.Graph;
import com.green.learning.algs4.graph.GraphData;
import com.green.learning.algs4.graph.UndirectedGraph;
import com.green.learning.algs4.graph.UnweightedUndirectedGraphGenerator;
import com.green.learning.algs4.util.XTimer;
import edu.princeton.cs.algs4.StdRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class SearchPathsTest
{
    @Test
    public void testSearchPaths()
    {
                UndirectedGraph graph = GraphData.getUndirectedGraph(GraphData.TINYG);
//        UndirectedGraph graph = GraphData.getUndirectedGraph(GraphData.MEDIUMG);
        //        UndirectedGraph graph = GraphData.getUndirectedGraph(GraphData.LARGEG);
        //        Digraph graph = GraphData.getDigraph(GraphData.TINYDG);
        //                Digraph graph = GraphData.getDigraph(GraphData.MEDIUMDG);
        //                Digraph graph = GraphData.getDigraph(GraphData.LARGEDG);
        
        final int srcVertex = 0;
                SearchPaths searchPaths = new BFSPaths(graph, srcVertex);
        //        SearchPaths searchPaths = new DFSPaths(graph, srcVertex);
//        SearchPaths searchPaths = new DFSPathsNonRec(graph, srcVertex);
        
        searchPaths.printResult();
        for (int v = 0; v < graph.V(); v++)
        {
            Iterable<Integer> path = searchPaths.pathTo(v);
            if (path == null) continue;
            testPathExist(graph, path);
        }
    }
    
    private void testPathExist(Graph graph, Iterable<Integer> path)
    {
        Assertions.assertNotNull(path);
        int predecessor = -1;
        for (Integer v : path)
        {
            Assertions.assertNotNull(v);
            if (predecessor != -1)
                Assertions.assertTrue(graph.edgeBetween(predecessor, v));
            predecessor = v;
        }
    }
    
    @Test
    public void testDFSEquality()
    {
//        Graph graph = GraphData.getUndirectedGraph(GraphData.TINYG);
                Graph graph = GraphData.getUndirectedGraph(GraphData.MEDIUMG);
        //        Graph graph = GraphData.getUndirectedGraph(GraphData.LARGEG);
        int srcVertex = 5;
        DFSPaths dfsPaths1 = new DFSPaths(graph, srcVertex);
        DFSPathsNonRec dfsPaths2 = new DFSPathsNonRec(graph, srcVertex, DFSPathsNonRec.DFSVersion.V3);
        for (int v = 0; v < graph.V(); v++)
        {
            Iterable<Integer> path1 = dfsPaths1.pathTo(v);
            Iterable<Integer> path2 = dfsPaths2.pathTo(v);
            if (path1 == path2) continue;
            if (!path1.equals(path2))
            {
                testPathExist(graph, path1);
                testPathExist(graph, path2);
            }
        }
    }
    
    @Test
    void testDFSPathsEfficiency()
    {
//        Graph graph = GraphData.MEDIUMG.getGraph();
//        Graph graph = GraphData.LARGEG.getGraph(); // 得跑好久
        
        final int V = 10_000;
        final int E = 100_000;
        Graph graph = UnweightedUndirectedGraphGenerator.simpleGraph(V, E);
        
        final int srcVertex = StdRandom.uniform(V);
    
        XTimer timer = new XTimer("test Non-Recursive DFS Search Paths");
        for(DFSPathsNonRec.DFSVersion version: DFSPathsNonRec.DFSVersion.values())
        {
            timer.start("dfs " + version.name());
            DFSPathsNonRec dfs = new DFSPathsNonRec(graph, srcVertex, version);
            timer.stop();
    
            for (int v = 0; v < graph.V(); v++)
            {
                Iterable<Integer> path = dfs.pathTo(v);
                if (path != null)
                    testPathExist(graph, path);
            }
        }
        
        System.out.println();
        System.out.println(timer);
    }
    
    @Test
    public void testMultiSourcesBFSPaths()
    {
        UndirectedGraph graph = GraphData.getUndirectedGraph(GraphData.TINYG);
        List<Integer> srcVertices = Arrays.asList(0, 6, 7, 9);
        MultiSourcesBFSPaths bfsPaths = new MultiSourcesBFSPaths(graph, srcVertices);
        bfsPaths.printResult();
        int v = 3;
        System.out.printf("edge dist to vertex [%d] = %d\n", v, bfsPaths.minEdgeDistTo(v));
    }
}
