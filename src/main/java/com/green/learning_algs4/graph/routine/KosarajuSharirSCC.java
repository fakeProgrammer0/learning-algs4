package com.green.learning_algs4.graph.routine;


import com.green.learning_algs4.graph.Digraph;
import com.green.learning_algs4.graph.GraphUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class KosarajuSharirSCC extends StrongConnectedComponent
{
    private final boolean[] marked;
    private final int[] ids;
    private int idCounter;
    
    public KosarajuSharirSCC(Digraph graph)
    {
        marked = new boolean[graph.V()];
        ids = new int[graph.V()];
        
        Digraph reverseGraph = graph.reverse();
        DFSOrder dfsOrder = new DFSOrder(reverseGraph);
        for(int v: dfsOrder.reversePostOrder())
        {
            if(!marked[v])
            {
                dfs(graph, v);
                idCounter++;
            }
        }
    }
    
    private void dfs(Digraph graph, int v)
    {
        marked[v] = true;
        ids[v] = idCounter;
        for(int w: graph.adj(v))
            if(!marked[w])
                dfs(graph, w);
    }
    
    @Override
    public boolean connected(int v, int w)
    {
        GraphUtils.vaildateVertex(marked.length, v);
        GraphUtils.vaildateVertex(marked.length, w);
        return ids[v] == ids[w];
    }
    
    @Override
    public int id(int v)
    {
        GraphUtils.vaildateVertex(ids.length, v);
        return ids[v];
    }
    
    @Override
    public int count()
    {
        return idCounter;
    }
    
    @Override
    public List<Set<Integer>> components()
    {
        List<Set<Integer>> components = new ArrayList<>();
        for(int i = 0; i < idCounter; i++)
            components.add(new HashSet<>());
        for(int v = 0; v < ids.length; v++)
        {
            components.get(ids[v]).add(v);
        }
        return components;
    }
}
