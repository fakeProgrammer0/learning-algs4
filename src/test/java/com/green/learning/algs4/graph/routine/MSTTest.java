package com.green.learning.algs4.graph.routine;

import com.green.learning.algs4.graph.AdjListWeightedUndirectedGraph;
import com.green.learning.algs4.graph.WeightedUndirectedGraph;
import com.green.learning.algs4.util.XTimer;
import edu.princeton.cs.algs4.In;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class MSTTest
{
    private static final double FLOATING_POINT_EPSILON = 1E-12;
    
    @Test
    public void testWeightedUndirectedGraph()
    {
        In in = new In("/data/graph/tinyEWG.txt");
        WeightedUndirectedGraph graph = new AdjListWeightedUndirectedGraph(in);
        System.out.println(graph);
    }
    
    @ParameterizedTest
    @ValueSource(strings = {
            "tinyEWG",
            "mediumEWG",
                        "1000EWG",
                        "10000EWG",
            //            "largeEWG"
    })
    public void testKruskalMST(String graphName)
    {
        XTimer timer = new XTimer("MST test; graph: " + graphName);
        timer.start("init data");
        In in = new In("data/graph/" + graphName + ".txt");
        WeightedUndirectedGraph graph = new AdjListWeightedUndirectedGraph(in);
        timer.stop();
        
        timer.start("kruskal MST");
        MinimumSpanningTree kruskalMST = new KruskalMST(graph);
        timer.stop();
//        kruskalMST.printResult();
//        System.out.println();
        
        timer.start("kruskal MST Opt");
        MinimumSpanningTree kruskalMSTOpt = new KruskalMSTOpt(graph);
        timer.stop();
//        kruskalMSTOpt.printResult();
//        System.out.println();
        
        timer.start("lazy Prime MST");
        MinimumSpanningTree lazyPrimeMST = new LazyPrimeMST(graph);
        timer.stop();
//        lazyPrimeMST.printResult();
//        System.out.println();
        
        timer.start("eager Prime MST");
        MinimumSpanningTree eagerPrimeMST = new LazyPrimeMST(graph);
        timer.stop();
//        eagerPrimeMST.printResult();
//        System.out.println();
        
        Assertions.assertTrue(Math.abs(kruskalMST.totalWeight() - kruskalMSTOpt.totalWeight()) < FLOATING_POINT_EPSILON);
        Assertions.assertTrue(Math.abs(kruskalMSTOpt.totalWeight() - lazyPrimeMST.totalWeight()) < FLOATING_POINT_EPSILON);
        Assertions.assertTrue(Math.abs(eagerPrimeMST.totalWeight() - lazyPrimeMST.totalWeight()) < FLOATING_POINT_EPSILON);
        
        
        System.out.println(timer);
    }
}
