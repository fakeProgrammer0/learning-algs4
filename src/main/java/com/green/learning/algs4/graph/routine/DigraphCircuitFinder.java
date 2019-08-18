package com.green.learning.algs4.graph.routine;


import com.green.learning.algs4.graph.Digraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 求有向图中是否存在一条回路
 */
public class DigraphCircuitFinder extends CircuitFinder
{
    private final boolean[] marked;
    private final boolean[] inStack;
    private final int[] edgeTo;
    private final static int NULL_VERTEX = -1;
    
    private List<Integer> circuit;
    
    public DigraphCircuitFinder(Digraph graph)
    {
        marked = new boolean[graph.V()];
        inStack = new boolean[graph.V()];
        edgeTo = new int[graph.V()];
        Arrays.fill(edgeTo, NULL_VERTEX);
        
        for (int v = 0; v < graph.V(); v++)
        {
            if (!marked[v])
                dfs(graph, v);
            if (hasACircuit()) break;
        }
    }
    
    private void dfs(Digraph graph, int v)
    {
        if (hasACircuit()) return;
        
        marked[v] = true;
        inStack[v] = true;
        
        for (int w : graph.adj(v))
        {
            if (!marked[w])
            {
                edgeTo[w] = v;
                dfs(graph, w);
            } else if (inStack[w])
            {
                circuit = new ArrayList<>();
                circuit.add(w);
                for (int x = v; x != w; x = edgeTo[x])
                    circuit.add(x);
                circuit.add(w);
            }
            
            if (hasACircuit()) break;
        }
        
        inStack[v] = false;
    }
    
    @Override
    public boolean hasACircuit()
    {
        return this.circuit != null && circuit.size() > 0;
    }
    
    @Override
    public Iterable<Integer> getCircuit()
    {
        return circuit;
    }
    
}
