package com.green.learning.algs4.graph.routine;


import com.green.learning.algs4.graph.Digraph;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 使用indegree来删去对circuit没有贡献的源点
 * 类似求拓扑排序的一个算法
 * 非递归，可以求解大问题
 */
public class DigraphCircuitFinderX extends CircuitFinder
{
    private Stack<Integer> circuit;
    
    public DigraphCircuitFinderX(Digraph graph)
    {
        final int[] indegrees = new int[graph.V()];
        for (int v = 0; v < graph.V(); v++)
            indegrees[v] = graph.inDegree(v);
        Queue<Integer> queue = new LinkedList<>();
        for (int v = 0; v < graph.V(); v++)
            if (indegrees[v] == 0) queue.add(v);
        
        while (!queue.isEmpty())
        {
            int v = queue.poll();
            for (int w : graph.adj(v))
            {
                indegrees[w]--;
                if (indegrees[w] == 0) queue.add(w);
            }
        }
        
        final int[] edgeTo = new int[graph.V()];
        int root = -1;
        for (int v = 0; v < graph.V(); v++)
        {
            if (indegrees[v] == 0) continue;
            else root = v;
            for (int w : graph.adj(v))
            {
                // indegrees[w] == 0，由v -> w可知，v是circuit上的点，w是circuit外部的点
                if(indegrees[w] > 0) // 忽略对circuit没有贡献的顶点
                    edgeTo[w] = v;
            }
        }
    
        if (root != -1)
        {
            final boolean[] visited = new boolean[graph.V()];
            while (!visited[root])
            {
                visited[root] = true;
                root = edgeTo[root];
            }
            
            circuit = new Stack<>();
            int x = root;
            do{
                circuit.push(x);
                x = edgeTo[x];
            }while (x != root);
            circuit.add(root);
        }
    }
    
    @Override
    public boolean hasACircuit()
    {
        return circuit != null;
    }
    
    @Override
    public Iterable<Integer> getCircuit()
    {
        return circuit;
    }
    
}
