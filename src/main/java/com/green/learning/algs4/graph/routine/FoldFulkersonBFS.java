package com.green.learning.algs4.graph.routine;

import com.green.learning.algs4.graph.FlowEdge;
import com.green.learning.algs4.graph.FlowGraph;
import com.green.learning.algs4.graph.GraphUtils;
import com.green.learning.algs4.list.XLinkedQueue;
import com.green.learning.algs4.list.XQueue;

import java.util.Arrays;

/**
 * @see edu.princeton.cs.algs4.FordFulkerson
 */
public class FoldFulkersonBFS extends MaxflowMincut
{
    private double totalFlow;
    private boolean[] marked;
    private FlowEdge[] edgeTo;
    
    public FoldFulkersonBFS(FlowGraph flowGraph, int src, int sink)
    {
        super(flowGraph, src, sink);
        marked = new boolean[flowGraph.V()];
        edgeTo = new FlowEdge[flowGraph.V()];
        
        while (hasAugmentingPath())
        {
            double bottleneck = Double.POSITIVE_INFINITY;
            for(int v = sink; v != src; v = edgeTo[v].other(v))
                bottleneck = Math.min(bottleneck, edgeTo[v].residualCapacityTo(v));
            
            for(int v = sink; v != src; v = edgeTo[v].other(v))
                edgeTo[v].addResidualFlowTo(v, bottleneck);
            
            totalFlow += bottleneck;
        }
    }
    
    private boolean hasAugmentingPath()
    {
        Arrays.fill(marked, false);
        Arrays.fill(edgeTo, null);
        
        XQueue<Integer> queue = new XLinkedQueue<>();
        queue.enqueue(src);
        marked[src] = true;
        while (!queue.isEmpty())
        {
            int v = queue.dequeue();
            for(FlowEdge edge: flowGraph.adjEdges(v))
            {
                int w = edge.other(v);
                if(!marked[w] && edge.residualCapacityTo(w) > 0)
                {
                    marked[w] = true;
                    edgeTo[w] = edge;
                    queue.enqueue(w);
                }
            }
        }
        return marked[sink];
    }
    
    @Override
    public double maxFlow()
    {
        return totalFlow;
    }
    
    @Override
    public boolean inSrcCut(int v)
    {
        GraphUtils.vaildateVertex(flowGraph, v);
        return marked[v];
    }
}
