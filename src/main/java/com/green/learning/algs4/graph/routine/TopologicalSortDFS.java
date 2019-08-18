package com.green.learning.algs4.graph.routine;


import com.green.learning.algs4.graph.Digraph;
import com.green.learning.algs4.graph.GraphUtils;

public class TopologicalSortDFS extends TopologicalSort
{
    private Iterable<Integer> cycle;
    private Iterable<Integer> order;
    private int[] ranks;
    
    public TopologicalSortDFS(Digraph digraph)
    {
        DigraphCircuitFinderX circuitSolver = new DigraphCircuitFinderX(digraph);
        if(circuitSolver.hasACircuit())
        {
            cycle = circuitSolver.getCircuit();
            return;
        }
        
        ranks = new int [digraph.V()];
        DFSOrder dfsOrder = new DFSOrder(digraph);
        order = dfsOrder.reversePostOrder();
        int rank = 0;
        for(int v: order)
            ranks[v] = rank++;
    }
    
    public boolean hasOrder()
    {
        return cycle == null;
    }
    
    public Iterable<Integer> cycle()
    {
        return cycle;
    }
    
    public Iterable<Integer> order()
    {
        if(!hasOrder()) throw new UnsupportedOperationException("The graph has a cycle and thus is not a DAG");
        return order;
    }
    
    private void validateVertex(int v)
    {
        GraphUtils.vaildateVertex(ranks.length, v);
    }
    
    public int rank(int v)
    {
        validateVertex(v);
        if(hasOrder()) return ranks[v];
        return -1;
    }
}
