package com.green.learning.algs4.graph.routine;

import com.green.learning.algs4.graph.AdjListFlowGraph;
import com.green.learning.algs4.graph.FlowGraph;
import edu.princeton.cs.algs4.In;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MaxflowMincutTest
{
    private FlowGraph flowGraph;
    private int src, sink;
    
    
    @BeforeEach
    void readFlowGraph()
    {
        In in = new In("data/graph/tinyFlowG.txt");
        flowGraph = new AdjListFlowGraph(in);
        src = 0;
        sink = 7;
    }
    
    @Test
    void testFoldFulkersonBFS()
    {
        MaxflowMincut maxflowMincut = new FoldFulkersonBFS(flowGraph, src, sink);
        maxflowMincut.printResult();
    }
    
}