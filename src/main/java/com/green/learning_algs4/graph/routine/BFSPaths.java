package com.green.learning_algs4.graph.routine;


import com.green.learning_algs4.graph.Graph;
import com.green.learning_algs4.list.XLinkedQueue;
import com.green.learning_algs4.list.XLinkedStack;
import com.green.learning_algs4.list.XQueue;
import com.green.learning_algs4.list.XStack;

import java.util.Arrays;

public class BFSPaths extends SearchPaths
{
    private final int NULL_VERTEX = -1;
    private final boolean[] marked;
    private final int[] edgeTo;
    private final int[] edgeDistTo;
    
    public BFSPaths(Graph graph, int srcVertex)
    {
        super(graph.V(), srcVertex);
        marked = new boolean[graph.V()];
        edgeTo = new int[graph.V()];
        edgeDistTo = new int[graph.V()];
        Arrays.fill(edgeTo, NULL_VERTEX);
        Arrays.fill(edgeDistTo, -1);
        bfs(graph, srcVertex);
    }
    
    private void bfs(Graph graph, int s)
    {
        XQueue<Integer> queue = new XLinkedQueue<>();
        queue.enqueue(s);
        marked[s] = true;
        edgeDistTo[s] = 0;
        while(!queue.isEmpty())
        {
            int v = queue.dequeue();
            for(int w: graph.adj(v))
            {
                if(!marked[w])
                {
                    marked[w] = true;
                    edgeTo[w] = v;
                    edgeDistTo[w] = edgeDistTo[v] + 1;
                    queue.enqueue(w);
                }
            }
        }
    }
    
    @Override
    public boolean hasPathTo(int v)
    {
        return marked[v];
    }
    
    @Override
    public Iterable<Integer> pathTo(int v)
    {
        if(!hasPathTo(v)) return null;
//          Stack<Integer> stack = new Stack<>();
        XStack<Integer> stack = new XLinkedStack<>();
        for(int x = v; x != srcVertex; x = edgeTo[x])
            stack.push(x);
        stack.push(srcVertex);
        return stack;
    }
    
    public int edgeDistTo(int v)
    {
        validateVertex(v);
        if(!hasPathTo(v)) return -1;
        return edgeDistTo[v];
    }
}
