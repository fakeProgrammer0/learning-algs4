package com.green.learning_algs4.graph.routine;

import com.green.learning_algs4.graph.WeightedUndirectedEdge;
import com.green.learning_algs4.graph.WeightedUndirectedGraph;
import com.green.learning_algs4.list.XLinkedQueue;
import com.green.learning_algs4.list.XQueue;
import com.green.learning_algs4.tree.XPriorityQueue;
import edu.princeton.cs.algs4.StdRandom;

/**
 * {@link edu.princeton.cs.algs4.LazyPrimMST}
 */
public class LazyPrimeMST extends MinimumSpanningTree
{
    private XQueue<WeightedUndirectedEdge> mst = new XLinkedQueue<>();
    
    private boolean[] marked;
    private XPriorityQueue<WeightedUndirectedEdge> pq = new XPriorityQueue<>();
    
    public LazyPrimeMST(WeightedUndirectedGraph graph)
    {
        super(graph);
        marked = new boolean[graph.V()];
        
        final int src = StdRandom.uniform(graph.V());
        scan(src);
        
        while (mst.size() + 1 < graph.V())
        {
            WeightedUndirectedEdge minWeightEdge = pq.dequeue();
            int v = minWeightEdge.either(), w = minWeightEdge.other(v);
            if (marked[v] && marked[w])
                continue;
            
            mst.enqueue(minWeightEdge);
            totalWeight += minWeightEdge.getWeight();
            
            int x = marked[v] ? w : v;
            scan(x);
        }
    }
    
    private void scan(int s)
    {
        marked[s] = true;
        for(WeightedUndirectedEdge edge: graph.adjEdges(s))
            if (!marked[edge.other(s)])
                pq.enqueue(edge);
    }
    
    @Override
    public Iterable<WeightedUndirectedEdge> edges()
    {
        return mst;
    }
}
