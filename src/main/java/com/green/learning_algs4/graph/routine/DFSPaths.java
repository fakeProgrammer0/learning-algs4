package com.green.learning_algs4.graph.routine;


import com.green.learning_algs4.graph.Graph;
import com.green.learning_algs4.list.XLinkedStack;
import com.green.learning_algs4.list.XStack;

import java.util.Arrays;

/**
 * DFS遍历，支持无向图、有向图
 */
public class DFSPaths extends SearchPaths
{
    private final int NULL_VERTEX = -1;
    // 用[数组]记录访问过的节点和节点之间的走向
    private final boolean[] marked;
    private final int[] edgeTo;
    
    public DFSPaths(Graph graph, int srcVertex)
    {
        super(graph.V(), srcVertex);
        marked = new boolean[graph.V()];
        edgeTo = new int[graph.V()];
        Arrays.fill(edgeTo, NULL_VERTEX);
        
        dfs(graph, srcVertex);
    }
    
    private void dfs(Graph graph, int v)
    {
        marked[v] = true;
        for(int w: graph.adj(v))
            if (!marked[w])
            {
                edgeTo[w] = v;
                dfs(graph, w);
            }
    }
    
    @Override
    public boolean hasPathTo(int v)
    {
        validateVertex(v);
//        return v == srcVertex || edgeTo[v] != NULL_VERTEX;
        return marked[v];
    }
    
    @Override
    public Iterable<Integer> pathTo(int v)
    {
        validateVertex(v);
        if(!hasPathTo(v)) return null;
        XStack<Integer> pathStack = new XLinkedStack<>();
        do{
            pathStack.push(v);
            v = edgeTo[v];
        }
        while(v != NULL_VERTEX);
        return pathStack;
    }
    
    
}
