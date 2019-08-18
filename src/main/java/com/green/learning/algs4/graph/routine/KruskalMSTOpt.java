package com.green.learning.algs4.graph.routine;

import com.green.learning.algs4.graph.WeightedUndirectedEdge;
import com.green.learning.algs4.graph.WeightedUndirectedGraph;
import com.green.learning.algs4.graph.uf.SuperWeightedUF;
import com.green.learning.algs4.list.XLinkedQueue;
import com.green.learning.algs4.list.XQueue;
import com.green.learning.algs4.sort.MergeSortOpt;

public class KruskalMSTOpt extends MinimumSpanningTree
{
    private XQueue<WeightedUndirectedEdge> mst = new XLinkedQueue<>();
    
    public KruskalMSTOpt(WeightedUndirectedGraph graph)
    {
        super(graph);
        
        SuperWeightedUF uf = new SuperWeightedUF(graph.V());
        WeightedUndirectedEdge[] edges = new WeightedUndirectedEdge[graph.E()];
        int e = 0;
        for (WeightedUndirectedEdge edge : graph.edges())
            edges[e++] = edge;
        MergeSortOpt.sort(edges);
        for (int i = 0; i < edges.length && mst.size() + 1 < graph.V(); i++)
        {
            WeightedUndirectedEdge edge = edges[i];
            int v = edge.either();
            int w = edge.other(v);
            if (!uf.isConnected(v, w))
            {
                uf.union(v, w);
                mst.enqueue(edge);
                totalWeight += edge.getWeight();
            }
        }
    }
    
    @Override
    public Iterable<WeightedUndirectedEdge> edges()
    {
        return mst;
    }
    
}
