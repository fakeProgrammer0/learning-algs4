package com.green.learning.algs4.graph.routine;


import com.green.learning.algs4.graph.GraphUtils;
import com.green.learning.algs4.graph.UndirectedGraph;

import java.util.*;

public class BipartiteGraphSolverBFS extends BipartiteGraphSolver
{
    private final boolean[] marked;
    private final boolean[] coloredRed;
    private final int[] edgeTo;
    private final static int NULL_VERTEX = -1;
    
    private Queue<Integer> oddLengthCycle;
    private Set<Integer>[] bipartiteVertices;
    
    public BipartiteGraphSolverBFS(UndirectedGraph graph)
    {
        marked = new boolean[graph.V()];
        coloredRed = new boolean[graph.V()];
        edgeTo = new int[graph.V()];
        Arrays.fill(edgeTo, NULL_VERTEX);
        
        for(int v = 0; v < graph.V(); v++)
        {
            if(!marked[v])
                bfs(graph, v);
            if(!isBipartiteGraph())
                break;
        }
        
        if(isBipartiteGraph())
        {
            bipartiteVertices = (Set<Integer>[]) new Set[2];
            bipartiteVertices[0] = new HashSet<>();
            bipartiteVertices[1] = new HashSet<>();
            for(int v = 0; v < graph.V(); v++)
                if(coloredRed[v])
                    bipartiteVertices[0].add(v);
                else
                    bipartiteVertices[1].add(v);
        }
    }
    
    private void bfs(UndirectedGraph graph, int s)
    {
        Queue<Integer> queue = new LinkedList<>();
        marked[s] = true;
        coloredRed[s] = true;
        queue.add(s);
        while(!queue.isEmpty())
        {
            int v = queue.poll();
            for(int w: graph.adj(v))
            {
                if (!marked[w])
                {
                    marked[w] = true;
                    coloredRed[w] = !coloredRed[v];
                    edgeTo[w] = v;
                    queue.add(w);
                } else if (coloredRed[v] == coloredRed[w])
                {
                    solveOddLengthCycle(s, v, w);
                    return;
                }
            }
        }
    }
    
    private void solveOddLengthCycle(int srcVertex, int v, int w)
    {
        oddLengthCycle = new LinkedList<>();
        Stack<Integer> pathToW = new Stack<>();
        int x = v, y = w;
        while(x != y)
        {
            oddLengthCycle.add(x);
            pathToW.push(y);
            x = edgeTo[x];
            y = edgeTo[y];
        }
        oddLengthCycle.add(x);
        while (!pathToW.isEmpty())
            oddLengthCycle.add(pathToW.pop());
        oddLengthCycle.add(v);
    }
    
    @Override
    public boolean isBipartiteGraph()
    {
        return oddLengthCycle == null;
    }
    
    @Override
    public Set<Integer>[] getBipartiteVertices()
    {
        checkBipartite();
        return bipartiteVertices;
    }
    
    @Override
    public Iterable<Integer> getOddLengthCycle()
    {
        return oddLengthCycle;
    }
    
    @Override
    public boolean inSameColor(int v, int w)
    {
        checkBipartite();
        GraphUtils.vaildateVertex(this.marked.length, v);
        GraphUtils.vaildateVertex(this.marked.length, w);
        return coloredRed[v] == coloredRed[w];
    }
}
