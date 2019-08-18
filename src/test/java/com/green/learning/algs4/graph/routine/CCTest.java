package com.green.learning.algs4.graph.routine;

import com.green.learning.algs4.graph.AdjListUnweightedUndirectedGraph;
import com.green.learning.algs4.graph.GraphData;
import com.green.learning.algs4.graph.UndirectedGraph;
import com.green.learning.algs4.util.XTimer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

public class CCTest
{
    @Test
    void testTinyG()
    {
        UndirectedGraph graph = GraphData.TINYG.getGraph(AdjListUnweightedUndirectedGraph.class);
        //        ConnectedComponents connectedComponents = new CCBFS(graph);
        ConnectedComponents connectedComponents = new CCDFS(graph);
        connectedComponents.printResult();
        
        Assertions.assertTrue(connectedComponents.connected(0, 5));
        Assertions.assertTrue(connectedComponents.connected(1, 6));
        
        Assertions.assertFalse(connectedComponents.connected(1, 8));
        Assertions.assertFalse(connectedComponents.connected(9, 4));
        
        Assertions.assertTrue(connectedComponents.id(8) == 1);
        Assertions.assertTrue(connectedComponents.id(9) == 2);
    }
    
    @ParameterizedTest
    @EnumSource(value = GraphData.class, names = {"TINYG", "MEDIUMG", "LARGEG"})
    void testGraphData(GraphData graphData)
    {
        UndirectedGraph graph = graphData.getGraph(AdjListUnweightedUndirectedGraph.class);
        ConnectedComponents connectedComponents = new CCBFS(graph);
        //        ConnectedComponents connectedComponents = new CCDFS(graph);
        connectedComponents.printResult();
        System.out.println();
    }
    
    @ParameterizedTest
    @EnumSource(value = GraphData.class, names = {"TINYG", "MEDIUMG"})
    void testCCCounts(GraphData graphData)
    {
        XTimer timer = new XTimer("CC test Timer");
        
        timer.start("init graph data: " + graphData.name());
        UndirectedGraph graph = graphData.getGraph(AdjListUnweightedUndirectedGraph.class);
        timer.stop();
        
        timer.start("CC BFS");
        ConnectedComponents cc1 = new CCBFS(graph);
        timer.stop();
        
        timer.start("CC DFS");
        ConnectedComponents cc2 = new CCDFS(graph);
        timer.stop();
        
        System.out.println(timer);
        
        Assertions.assertEquals(cc1.count(), cc2.count());
        for (int v = 0; v < graph.V(); v++)
            for (int w = v + 1; w < graph.V(); w++)
            {
                Assertions.assertEquals(cc1.connected(v, w), cc2.connected(v, w));
            }
    }
}
