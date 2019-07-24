package com.green.learning_algs4.graph.routine;


import com.green.learning_algs4.graph.Graph;
import com.green.learning_algs4.graph.GraphUtils;
import com.green.learning_algs4.list.XLinkedQueue;
import com.green.learning_algs4.list.XQueue;
import com.green.learning_algs4.set.XLinkedHashSet;
import com.green.learning_algs4.set.XSet;
import com.green.learning_algs4.st.LinkedHashST;
import com.green.learning_algs4.st.ST;

public class MultiSourcesBFSPaths implements GraphProcessRoutine
{
    private final int V;
    private final XSet<Integer> srcVertices = new XLinkedHashSet<>();
    private final XSet<Integer> verticesVisited = new XLinkedHashSet<>();
    private final ST<Integer, Integer> predecessorMap = new LinkedHashST<>();
    private final ST<Integer, Integer> minEdgeDistMap = new LinkedHashST<>();
    
    public MultiSourcesBFSPaths(Graph graph, Iterable<Integer> srcVertices)
    {
        this.V = graph.V();
        checkVertices(srcVertices);
        for (Integer srcVertex : srcVertices)
            this.srcVertices.add(srcVertex);
        bfs(graph, srcVertices);
    }
    
    private void checkVertices(Iterable<Integer> vertices)
    {
        for (Integer v : vertices)
            if (v == null || v < 0 || v >= this.V)
                throw new IllegalArgumentException("v should be between 0 and " + (srcVertices.size() - 1));
    }
    
    private void checkVertex(int v)
    {
        GraphUtils.vaildateVertex(this.V, v);
    }
    
    private void bfs(Graph graph, Iterable<Integer> srcVertices)
    {
        XQueue<Integer> queue = new XLinkedQueue<>();
        for (Integer srcVertex : srcVertices)
        {
            queue.enqueue(srcVertex);
            verticesVisited.add(srcVertex);
            minEdgeDistMap.put(srcVertex, 0);
        }
        
        while (!queue.isEmpty())
        {
            int v = queue.dequeue();
            for (int w : graph.adj(v))
            {
                if (!verticesVisited.contains(w))
                {
                    queue.enqueue(w);
                    verticesVisited.add(w);
                    predecessorMap.put(w, v);
                    minEdgeDistMap.put(w, minEdgeDistMap.get(v) + 1);
                }
            }
        }
    }
    
    public boolean hasPathTo(int v)
    {
        checkVertex(v);
        return verticesVisited.contains(v);
    }
    
    public Iterable<Integer> pathTo(int v)
    {
        checkVertex(v);
        if (!hasPathTo(v)) return null;
        edu.princeton.cs.algs4.Stack<Integer> pathStack = new edu.princeton.cs.algs4.Stack<>();
        while (predecessorMap.containsKey(v))
        {
            pathStack.push(v);
            v = predecessorMap.get(v);
        }
        pathStack.push(v); // one srcVertex
        return pathStack;
    }
    
    public int minEdgeDistTo(int v)
    {
        checkVertex(v);
        if (hasPathTo(v))
            return minEdgeDistMap.get(v);
        return -1;
    }
    
    @Override
    public void printResult()
    {
        final String lineSep = System.lineSeparator();
        StringBuilder sb = new StringBuilder("Path to other vertex from any source vertex in [");
        for (Integer srcVertex : srcVertices)
            sb.append(srcVertex).append(", ");
        sb.delete(sb.lastIndexOf(", "), sb.length());
        sb.append("]").append(lineSep);
        for (int v = 0; v < V; v++)
        {
            if (srcVertices.contains(v)) continue;
            sb.append(String.format("to [%d]:", v));
            Iterable<Integer> path = pathTo(v);
            if (path == null)
                sb.append("[ ]");
            else
            {
                sb.append("[");
                for (Integer x : path)
                {
                    sb.append(x);
                    if (x != v)
                        sb.append(" -> ");
                }
                sb.append("]");
            }
            sb.append(lineSep);
        }
        System.out.print(sb.toString());
    }
}
