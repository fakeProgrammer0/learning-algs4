package com.green.learning.algs4.graph.routine;


import com.green.learning.algs4.graph.Graph;
import com.green.learning.algs4.list.XSuperStack;

import java.util.*;

public class DFSPathsNonRec extends SearchPaths
{
    // 用[set]来记录访问过的节点
    private final Set<Integer> verticesVisited = new HashSet<>();
    // 用[map]来记录遍历节点之间边的走向
    private final Map<Integer, Integer> predecessorMap = new HashMap<>();
    
    public enum DFSVersion {V1, V2, V3};
    
    public DFSPathsNonRec(Graph graph, int srcVertex, DFSVersion version)
    {
        super(graph.V(), srcVertex);
        switch (version)
        {
            case V1:
                dfs1(graph, srcVertex);
                break;
            case V2:
                dfs2(graph, srcVertex);
                break;
            case V3:
                dfs3(graph, srcVertex);
                break;
            default:
                throw new IllegalArgumentException("DFS version should be V1, V2 or V3");
        }
    }
    
    /*
    记录迭代器的遍历位置
    space: O(V + E)
     */
    private void dfs1(Graph graph, int s)
    {
        Iterator<Integer>[] adjIterators = (Iterator<Integer>[]) new Iterator[graph.V()];
        
        // 方法①：预先申请空间
        //        for(int v = 0; v < graph.V(); v++)
        //            adjIterators[v] = graph.adj(v).iterator();
        
        Stack<Integer> vertexStack = new Stack<>();
        //        preVisit(graph, s);
        vertexStack.push(s);
        verticesVisited.add(s);
        while (!vertexStack.isEmpty())
        {
            int v = vertexStack.peek();
            
            // 方法②：动态申请空间，因为iterator()方法会创建一个迭代器对象
            // 如果是连通图，那么每一个节点都会被遍历访问到，所以时间效率上稍微不如方法①
            // 反之，如果图不连通，只访问部分节点，那么动态申请会更节省空间
            if (adjIterators[v] == null) adjIterators[v] = graph.adj(v).iterator();
            
            if (adjIterators[v].hasNext())
            {
                int w = adjIterators[v].next();
                if (!verticesVisited.contains(w))
                {
                    //                    preVisit(graph, v);
                    vertexStack.push(w);
                    verticesVisited.add(w);
                    predecessorMap.put(w, v);
                }
            } else
            {
                vertexStack.pop();
                //                postVisit(graph, v);
            }
        }
    }
    
    /*
    跟传统dfs不一样，优先遍历最后一个邻居节点
    节点会被重复压栈，消耗多余空间
    worst case space: O(V + E)
     */
    private void dfs2(Graph graph, int s)
    {
        Stack<Integer> vertexStack = new Stack<>();
        //         Pre-visit vertex
//        preVisit(graph, s);
        vertexStack.push(s);
        while (!vertexStack.isEmpty())
        {
            int v = vertexStack.pop();
            // Post-visit vertex
            // 跟递归版本的DFS出栈顺序不一样
//            postVisit(graph, v);
            if (!verticesVisited.contains(v))
            {
                verticesVisited.add(v);
                for (int w : graph.adj(v))
                    if (!verticesVisited.contains(w))
                    {
//                        preVisit(graph, w);
                        vertexStack.push(w);
                        verticesVisited.add(w);
                        predecessorMap.put(w, v);
                    }
            }
        }
    }
    
    // space: O(V) worst case
    // time: O(E + V)
    // 比其它两个实现慢，因为要调用SuperStack的deleteFirst方法
    // 但是比较稳定，牺牲了部分时间，以换取较小空间，在处理规模大的数据时比较稳定
    private void dfs3(Graph graph, int s)
    {
        XSuperStack<Integer> stack = new XSuperStack<>();
        stack.push(s);
        while (!stack.isEmpty())
        {
            int v = stack.peek();
            //            preVisit(graph, v);
            if (!verticesVisited.contains(v))
            {
                verticesVisited.add(v);
                for (int w : graph.adj(v))
                {
                    if (!verticesVisited.contains(w))
                    {
                        stack.removeFirst(w); // 意味着会从另一个节点更先地访问到
                        stack.push(w);
                        predecessorMap.put(w, v); // 会被更新多次
                    }
                }
            } else
            {
                // v's adjacency list is exhausted
                stack.pop();
                //                postVisit(graph, v);
            }
        }
    }
    
    @Override
    public boolean hasPathTo(int v)
    {
        validateVertex(v);
        return verticesVisited.contains(v);
    }
    
    @Override
    public Iterable<Integer> pathTo(int v)
    {
        validateVertex(v);
        if (!hasPathTo(v)) return null;
        
        edu.princeton.cs.algs4.Stack<Integer> pathStack = new edu.princeton.cs.algs4.Stack<>();
        while (v != srcVertex)
        {
            pathStack.push(v);
            v = predecessorMap.get(v);
        }
        pathStack.push(srcVertex);
        return pathStack;
    }
    
    private void preVisit(Graph graph, int v)
    {
        System.out.println(String.format("vertex [%d] is pushed into stack", v));
    }
    
    private void postVisit(Graph graph, int v)
    {
        System.out.println(String.format("vertex [%d] is popped out from stack", v));
    }
    
    
}
