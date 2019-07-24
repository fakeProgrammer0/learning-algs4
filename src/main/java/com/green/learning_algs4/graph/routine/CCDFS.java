package com.green.learning_algs4.graph.routine;


import com.green.learning_algs4.graph.UndirectedGraph;

public class CCDFS extends ConnectedComponents
{
    public CCDFS(UndirectedGraph graph)
    {
        super(graph);
        
        for(int v = 0; v < graph.V(); v++)
        {
            if(!marked[v]){
                dfs(graph, v); // dfsNonRec(graph, v);
                idCounter++;
            }
        }
    }
    
    private void dfs(UndirectedGraph graph, int v)
    {
        marked[v] = true;
        id[v] = idCounter;
        for (int w : graph.adj(v))
            if (!marked[w])
            {
                dfs(graph, w);
            }
    }
}
