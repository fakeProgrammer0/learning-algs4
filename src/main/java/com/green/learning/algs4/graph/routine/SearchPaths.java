package com.green.learning.algs4.graph.routine;


import com.green.learning.algs4.graph.GraphUtils;

/**
 * 从特定节点s遍历图，找到从节点s到其它节点的路径
 */
public abstract class SearchPaths implements GraphProcessRoutine
{
    protected final int srcVertex;
    protected final int V;
    
    protected SearchPaths(int V, int srcVertex)
    {
        GraphUtils.validateVerticesCnt(V);
        this.V = V;
        validateVertex(srcVertex);
        this.srcVertex = srcVertex;
    }
    
    public abstract boolean hasPathTo(int v);
    public abstract Iterable<Integer> pathTo(int v);
    
    protected void validateVertex(int v)
    {
        GraphUtils.vaildateVertex(this.V, v);
    }
    
    @Override
    public void printResult()
    {
        System.out.println("Path to other vertex from vertex [" + srcVertex + "]");
        for (int v = 0; v < V; v++)
        {
            if(v == srcVertex) continue;
            System.out.print(String.format("to [%d]:", v));
            Iterable<Integer> path = pathTo(v);
            if (path == null)
            {
                System.out.println("[]");
            } else
            {
                System.out.print("[");
                for (Integer x : path)
                {
                    System.out.print(x);
                    if (x != v) System.out.print(" -> ");
                }
                System.out.println("]");
            }
        }
    }
}
