package com.green.learning.algs4.graph.routine;

import com.green.learning.algs4.graph.WeightedUndirectedEdge;
import com.green.learning.algs4.graph.WeightedUndirectedGraph;

public abstract class MinimumSpanningTree implements GraphProcessRoutine
{
    protected double totalWeight = 0;
    protected WeightedUndirectedGraph graph;
    
    // 检查图是否连通
    // 同时限制子类API
    public MinimumSpanningTree(WeightedUndirectedGraph graph)
    {
        ConnectedComponents cc = new CCBFS(graph);
        if (cc.count() > 1)
            throw new IllegalArgumentException("The graph has more than 1 connected components");
        this.graph = graph;
    }
    
    public abstract Iterable<WeightedUndirectedEdge> edges();
    
    public double totalWeight()
    {
        return totalWeight;
    }
    
    @Override
    public void printResult()
    {
        System.out.printf("total weight of MST: %.6f\n", totalWeight);
        for (WeightedUndirectedEdge edge : edges())
            System.out.println(edge);
    }
}
