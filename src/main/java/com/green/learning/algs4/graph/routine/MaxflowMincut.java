package com.green.learning.algs4.graph.routine;

import com.green.learning.algs4.graph.AdjListFlowGraph;
import com.green.learning.algs4.graph.FlowEdge;
import com.green.learning.algs4.graph.FlowGraph;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class MaxflowMincut implements GraphProcessRoutine
{
    protected FlowGraph flowGraph;
    protected int src;
    protected int sink;
    
    public MaxflowMincut(FlowGraph flowGraph, int src, int sink)
    {
        if (flowGraph.inDegree(src) > 0 || flowGraph.outDegree(sink) > 0)
            throw new IllegalArgumentException("Invalid source vertex or invalid sink vertex");
        
        this.flowGraph = new AdjListFlowGraph(flowGraph);
        this.src = src;
        this.sink = sink;
    }
    
    public abstract double maxFlow();
    
    public abstract boolean inSrcCut(int v);
    
    public Cut minCut()
    {
        Set<Integer> srcCut = new HashSet<>();
        Set<Integer> sinkCut = new HashSet<>();
        
        for (int v = 0; v < flowGraph.V(); v++)
            if (inSrcCut(v))
                srcCut.add(v);
            else sinkCut.add(v);
        return new Cut(srcCut, sinkCut);
    }
    
    public static class Cut
    {
        private final Set<Integer> srcCut;
        private final Set<Integer> sinkCut;
        
        public Cut(Set<Integer> srcCut, Set<Integer> sinkCut)
        {
            this.srcCut = Collections.unmodifiableSet(srcCut);
            this.sinkCut = Collections.unmodifiableSet(sinkCut);
        }
        
        public Set<Integer> getSrcCut()
        {
            return srcCut;
        }
        
        public Set<Integer> getSinkCut()
        {
            return sinkCut;
        }
        
        @Override
        public String toString()
        {
            String sep = System.lineSeparator();
            return "srcCut : " + srcCut + sep +
                    "sinkCut: " + sinkCut + sep;
        }
    }
    
    @Override
    public void printResult()
    {
        final String lineSep = System.lineSeparator();
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("# MaxFlow: %.6f", maxFlow())).append(lineSep);
        sb.append("# MinCut: ").append(lineSep).append(minCut().toString()).append(lineSep);
        sb.append("# Flow: ").append(lineSep);
        for(FlowEdge edge: flowGraph.edges())
            sb.append(edge.toString()).append(lineSep);
        System.out.println(sb.toString());
    }
}
