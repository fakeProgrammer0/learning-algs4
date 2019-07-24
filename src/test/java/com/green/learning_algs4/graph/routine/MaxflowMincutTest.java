package com.green.learning_algs4.graph.routine;

import com.green.learning_algs4.graph.AdjListFlowGraph;
import com.green.learning_algs4.graph.FlowGraph;
import edu.princeton.cs.algs4.In;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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