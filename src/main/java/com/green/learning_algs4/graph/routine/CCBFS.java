package com.green.learning_algs4.graph.routine;

import com.green.learning_algs4.graph.UndirectedGraph;
import com.green.learning_algs4.list.XLinkedQueue;
import com.green.learning_algs4.list.XQueue;

public class CCBFS extends ConnectedComponents
{
    public CCBFS(UndirectedGraph graph)
    {
        super(graph);
        for(int v = 0; v < graph.V();v++)
        {
            if(!marked[v])
            {
                bfs(graph, v);
                idCounter++;
            }
        }
    }
    
    private void bfs(UndirectedGraph graph, int s)
    {
        XQueue<Integer> queue = new XLinkedQueue<>();
        marked[s] = true;
        queue.enqueue(s);
        while (!queue.isEmpty())
        {
            int v = queue.dequeue();
            id[v] = idCounter;
            for(int w : graph.adj(v))
            {
                if(!marked[w])
                {
                    queue.enqueue(w);
                    marked[w] = true;
                }
            }
        }
    }
}
