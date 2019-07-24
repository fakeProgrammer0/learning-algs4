package com.green.learning_algs4.graph.routine;

import com.green.learning_algs4.graph.WeightedUndirectedEdge;
import com.green.learning_algs4.graph.WeightedUndirectedGraph;
import com.green.learning_algs4.graph.uf.SuperWeightedUF;
import com.green.learning_algs4.list.XLinkedQueue;
import com.green.learning_algs4.list.XQueue;
import com.green.learning_algs4.tree.XPriorityQueue;

/**
 * @see edu.princeton.cs.algs4.KruskalMST
 */
public class KruskalMST extends MinimumSpanningTree
{
    private XQueue<WeightedUndirectedEdge> mst = new XLinkedQueue<>();
    
    public KruskalMST(WeightedUndirectedGraph graph)
    {
        super(graph);
        
        SuperWeightedUF uf = new SuperWeightedUF(graph.V());
        XPriorityQueue<WeightedUndirectedEdge> edgeQueue = new XPriorityQueue<>();
        for (WeightedUndirectedEdge edge : graph.edges())
            edgeQueue.enqueue(edge);
        while (!edgeQueue.isEmpty() && mst.size() + 1 < graph.V())
        {
            WeightedUndirectedEdge edge = edgeQueue.dequeue();
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
