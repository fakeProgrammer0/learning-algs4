package com.green.learning_algs4.graph.routine;


import com.green.learning_algs4.graph.Digraph;
import com.green.learning_algs4.graph.GraphUtils;

import java.util.LinkedList;
import java.util.Queue;

public class TopologicalSortX extends TopologicalSort
{
    private final int[] ranks;
    private Queue<Integer> cycle;
    private Queue<Integer> order = new LinkedList<>();
    
    public TopologicalSortX(Digraph digraph)
    {
        ranks = new int[digraph.V()];
        Queue<Integer> queue = new LinkedList<>();
        final int[] indegrees = new int[digraph.V()];
        for(int v = 0; v < digraph.V(); v++)
        {
            indegrees[v] = digraph.inDegree(v);
            if (indegrees[v] == 0)
                queue.add(v);
        }
        
        int rank = 0;
        while (!queue.isEmpty())
        {
            int v = queue.poll();
            ranks[v] = rank++;
            order.add(v);
            for(int w: digraph.adj(v))
            {
                indegrees[w]--;
                if(indegrees[w] == 0){
                    queue.add(w);
                }
            }
        }
        
        int root = -1;
        int[] edgeTo = new int[digraph.V()];
        for(int v = 0; v < digraph.V(); v++)
        {
            if(indegrees[v] == 0) continue;
            else root = v;
            for(int w: digraph.adj(v))
            {
                if(indegrees[w] > 0)
                {
                    edgeTo[w] = v;
                }
            }
        }
        
        if(root != -1)
        {
            cycle = new LinkedList<>();
            int x = root;
            do{
                cycle.add(x);
                x = edgeTo[x];
            }while (x != root);
            cycle.add(root);
        }
    }
    
    @Override
    public boolean hasOrder()
    {
        return cycle == null;
    }
    
    @Override
    public Iterable<Integer> order()
    {
        if(!hasOrder()) throw new UnsupportedOperationException("The graph has a cycle and thus is not a DAG");
        return order;
    }
    
    private void validateVertex(int v)
    {
        GraphUtils.vaildateVertex(ranks.length, v);
    }
    
    @Override
    public int rank(int v)
    {
        validateVertex(v);
        if(hasOrder()) return ranks[v];
        return -1;
    }
    
    @Override
    public Iterable<Integer> cycle()
    {
        return cycle;
    }
}
