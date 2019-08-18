package com.green.learning.algs4.graph.routine;


import com.green.learning.algs4.graph.UndirectedGraph;
import com.green.learning.algs4.list.XArrayList;
import com.green.learning.algs4.list.XList;
import com.green.learning.algs4.set.XLinkedHashSet;
import com.green.learning.algs4.set.XSet;

public abstract class ConnectedComponents implements GraphProcessRoutine
{
    protected boolean[] marked;
    protected int[] id;
    protected int idCounter = 0;
    
    public ConnectedComponents(UndirectedGraph graph)
    {
        final int V = graph.V();
        marked = new boolean[V];
        id = new int[V];
    }
    
    public boolean connected(int v, int w)
    {
        return id[v] == id[w];
    }
    
    public int id(int v)
    {
        return id[v];
    }
    
    public int count()
    {
        return idCounter;
    }
    
    public Iterable<XSet<Integer>> components()
    {
        XList<XSet<Integer>> components = new XArrayList<>();
        for (int i = 0; i < count(); i++)
            components.append(new XLinkedHashSet<>());
        
        for (int v = 0; v < id.length; v++)
            components.get(id[v]).add(v);
        return components;
    }
    
    @Override
    public void printResult()
    {
        final String lineSep = System.lineSeparator();
        StringBuilder sb = new StringBuilder();
        sb.append(count()).append(" connected components").append(lineSep);
        for(XSet<Integer> connectedComponents: components())
        {
            sb.append("[");
            for(int v: connectedComponents)
                sb.append(v).append(", ");
            sb.delete(sb.lastIndexOf(", "), sb.length());
            sb.append("]").append(lineSep);
        }
        System.out.print(sb.toString());
    }
}
