package com.green.learning_algs4.graph;

import com.green.learning_algs4.list.XLinkedList;
import com.green.learning_algs4.list.XList;

import java.util.Iterator;

public class EdgesPath <E extends AbstractEdge> implements Iterable<E>
{
    private XList<E> edges;
    
    public EdgesPath()
    {
        edges = new XLinkedList<>();
    }
    
    public EdgesPath(Iterable<E> edges)
    {
        this.edges = new XLinkedList<>(edges);
    }
    
    @Override
    public Iterator<E> iterator()
    {
        return edges.iterator();
    }
    
    public void addEdge(E edge)
    {
        edges.append(edge);
    }
}
