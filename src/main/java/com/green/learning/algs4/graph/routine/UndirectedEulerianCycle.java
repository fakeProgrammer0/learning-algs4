package com.green.learning.algs4.graph.routine;


import com.green.learning.algs4.graph.UndirectedEdge;
import com.green.learning.algs4.graph.UndirectedGraph;

import java.util.*;

public class UndirectedEulerianCycle implements GraphProcessRoutine
{
    private boolean EulerianCycleExist;
    private String EulerianCycleNotExistReason;
    
    private int startVertex = -1;
    private List<UndirectedEdge>[] edgesLinkingVertics;
    
    private LinkedHashSet<UndirectedEdge> edgeQueue = new LinkedHashSet<>();
    private LinkedList<Integer> verticesCycle = new LinkedList<>();
    
    public UndirectedEulerianCycle(UndirectedGraph graph)
    {
        EulerianCycleExist = hasEulerianCycle(graph);
        if (!EulerianCycleExist) return;
        
        edgesLinkingVertics = (List<UndirectedEdge>[]) new List[graph.V()];
        for (int v = 0; v < graph.V(); v++)
            edgesLinkingVertics[v] = new ArrayList<>();
        
        for (int v = 0; v < graph.V(); v++)
            for (int w : graph.adj(v))
                if (v <= w)
                {
                    UndirectedEdge edge = new UndirectedEdge(v, w);
                    edgesLinkingVertics[v].add(edge);
                    if (v != w)
                        edgesLinkingVertics[w].add(edge);
                }
        
        dfsEdge(graph, startVertex);
    }
    
    // dfs遍历边
    private void dfsEdge(UndirectedGraph graph, int v)
    {
        verticesCycle.add(v);
        for (UndirectedEdge edge : edgesLinkingVertics[v])
        {
            if (!edgeQueue.contains(edge))
            {
                edgeQueue.add(edge);
                int w = edge.other(v);
                dfsEdge(graph, w);
                if (edgeQueue.size() == graph.E()) return;
                // 还没遍历完所有的边，必须要回溯
                edgeQueue.remove(edge);
                verticesCycle.pollLast();
                // 继续在for循环遍历edge
            }
        }
    }
    
    // 以下代码存在问题
    @Deprecated
    private void dfsNonRec(UndirectedGraph graph, int v)
    {
        UndirectedEdge lastAddedEdge = null;
        boolean traversalByEdge = false;
        while (true)
        {
            verticesCycle.add(v);
            for (UndirectedEdge edge : edgesLinkingVertics[v])
            {
                if (!edgeQueue.contains(edge))
                {
                    edgeQueue.add(edge);
                    lastAddedEdge = edge;
                    v = edge.other(v);
                    traversalByEdge = true;
                    break;
                }
            }

            if(traversalByEdge)
            {
                traversalByEdge = false;
                continue;
            }

            if (edgeQueue.size() == graph.E()) return;
            // 回溯，但是又要从第一条边开始遍历，所以是个bug
            // 需要记住遍历到哪条边了。。
            edgeQueue.remove(lastAddedEdge);
            verticesCycle.pollLast();
            v = verticesCycle.peekLast();
        }
    }
    
    // 图存在边 + 所有节点的度为偶数 + 度不为零的节点都连通
    private boolean hasEulerianCycle(UndirectedGraph graph)
    {
        if (graph.E() == 0)
        {
            EulerianCycleNotExistReason = "The graph has no edge";
            return false;
        }
        
        int[] degrees = new int[graph.V()];
        for (int v = 0; v < graph.V(); v++)
        {
            degrees[v] = graph.degree(v);
            if (degrees[v] % 2 != 0)
            {
                EulerianCycleNotExistReason = "The degree of vertex [" + v + "] is odd";
                return false;
            }
        }
        
        int s = 0;
        while (degrees[s] == 0) s++;
        startVertex = s;
        
        boolean[] marked = new boolean[graph.V()];
        Queue<Integer> queue = new LinkedList<>();
        marked[s] = true;
        queue.add(s);
        while (!queue.isEmpty())
        {
            int v = queue.poll();
            for (int w : graph.adj(v))
            {
                if (!marked[w])
                {
                    marked[w] = true;
                    queue.add(w);
                }
            }
        }
        
        for (int x = s + 1; x < graph.V(); x++)
        {
            if (!marked[x] && degrees[x] != 0)
            {
                EulerianCycleNotExistReason = "The graph has more than one connected components that have edges";
                return false;
            }
        }
        return true;
    }
    
    public boolean isEulerianCycleExist()
    {
        return EulerianCycleExist;
    }
    
    @Override
    public void printResult()
    {
        if (!EulerianCycleExist)
        {
            System.out.print(EulerianCycleNotExistReason);
            System.out.println(", so the graph has no Eulerian Cycle.");
        } else
        {
            System.out.println("One Eulerian Cycle of the graph is: ");
            System.out.println(verticesCycle);
        }
        System.out.println();
    }
}
