package com.green.learning_algs4.graph;

import com.green.learning_algs4.list.XArrayList;
import com.green.learning_algs4.list.XList;
import com.green.learning_algs4.set.XLinkedHashSet;
import com.green.learning_algs4.set.XSet;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 图的一条路径，对节点序列的组织
 */
public class VerticesPath implements Iterable<Integer>
{
    private XList<Integer> vertices;
    
    public VerticesPath()
    {
        vertices = new XArrayList<>();
    }
    
    public VerticesPath(Iterable<Integer> vertices)
    {
//        if(vertices == null)
//            throw new IllegalArgumentException("The path must have one vertex or more.");
        this.vertices = new XArrayList<>(vertices);
    }
    
    @Override
    public Iterator<Integer> iterator()
    {
        return vertices.iterator();
    }
    
    private void checkEmpty()
    {
        if(vertices.isEmpty())
            throw new NoSuchElementException("The path is empty");
    }
    
    public int first()
    {
        checkEmpty();
        return vertices.get(0);
    }
    
    public int last()
    {
        checkEmpty();
        return vertices.get(vertices.size() - 1);
    }
    
    public  boolean isCircuit()
    {
        checkEmpty();
        if(first() == last()) return true;
        XSet<Integer> vertexSet = new XLinkedHashSet<>();
        for(int v : vertices)
            if(vertexSet.contains(v))
                return false;
            else vertexSet.add(v);
        return false;
    }
    
    public void addVertex(int v)
    {
        vertices.append(v);
    }
}
