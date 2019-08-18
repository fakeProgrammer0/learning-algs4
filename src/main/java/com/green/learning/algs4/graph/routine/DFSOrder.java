package com.green.learning.algs4.graph.routine;


import com.green.learning.algs4.graph.Graph;
import com.green.learning.algs4.list.XSuperStack;

import java.util.LinkedList;
import java.util.Queue;

/**
 * DFS
 * 入栈顺序
 * 出栈顺序的逆序，可用于拓扑排序
 */
public class DFSOrder
{
    private final boolean[] marked;
    private final Queue<Integer> firstOrder = new LinkedList<>();
    private final edu.princeton.cs.algs4.Stack<Integer> reversePostOrder = new edu.princeton.cs.algs4.Stack<>();;
    
    public DFSOrder(Graph graph)
    {
        marked = new boolean[graph.V()];
        for(int v = 0; v < graph.V(); v++)
        {
            if(!marked[v])
            {
//                dfs(graph, v);
                dfsNonRec(graph, v);
            }
        }
    }
    
    private void dfs(Graph graph, int v)
    {
        firstOrder.add(v);
        marked[v] = true;
        for(int w: graph.adj(v))
        {
            if(!marked[w])
            {
                dfs(graph, w);
            }
        }
        reversePostOrder.push(v);
    }
    
    // 非递归实现
    private void dfsNonRec(Graph graph, int s)
    {
        XSuperStack<Integer> stack = new XSuperStack<>();
        stack.push(s);
        while (!stack.isEmpty())
        {
            int v = stack.peek();
            if(!marked[v])
            {
                marked[v] = true;
                firstOrder.add(v);
                for(int w: graph.adj(v))
                {
                    if(!marked[w])
                    {
                        stack.removeFirst(w);
                        stack.push(w);
                    }
                }
            }else
            {
                stack.pop();
                reversePostOrder.push(v);
            }
        }
        
    }
    
    public Iterable<Integer> reversePostOrder()
    {
        return reversePostOrder;
    }
    
    public Iterable<Integer> firstOrder() { return  firstOrder; }
}
