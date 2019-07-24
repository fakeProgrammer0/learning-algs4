package com.green.learning_algs4.graph.routine;

import com.green.learning_algs4.graph.WeightedUndirectedEdge;
import com.green.learning_algs4.graph.WeightedUndirectedGraph;
import com.green.learning_algs4.list.XLinkedQueue;
import com.green.learning_algs4.list.XQueue;
import com.green.learning_algs4.tree.IndexedPriorityQueue;
import edu.princeton.cs.algs4.StdRandom;

/**
 * @see edu.princeton.cs.algs4.PrimMST
 */
public class EagerPrimeMST extends MinimumSpanningTree
{
    private XQueue<WeightedUndirectedEdge> mst = new XLinkedQueue<>();
    private final IndexedPriorityQueue<WeightedUndirectedEdge> ipq;
    private final boolean[] marked;
    
    public EagerPrimeMST(WeightedUndirectedGraph graph)
    {
        super(graph);
        final int src = StdRandom.uniform(graph.V());
        ipq = new IndexedPriorityQueue<>(graph.V());
        marked = new boolean[graph.V()];
        scan(src);
        while (mst.size() < graph.V() - 1)
        {
            WeightedUndirectedEdge edge = ipq.dequeue();
            mst.enqueue(edge);
            totalWeight += edge.getWeight();
            
            int v = edge.either(), w = edge.other(v);
            int x = marked[v] ? w : v;
            scan(x);
        }
    }
    
    private void scan(int v)
    {
        marked[v] = true;
        for (WeightedUndirectedEdge edge : graph.adjEdges(v))
        {
            int w = edge.other(v);
            if (marked[w]) continue;
            if (!ipq.containsIndex(w))
                ipq.set(w, edge);
            else if (!(ipq.getKey(w).compareTo(edge) <= 0))
                ipq.decreasePriority(w, edge);
        }
    }
    
    @Override
    public Iterable<WeightedUndirectedEdge> edges()
    {
        return mst;
    }
}
