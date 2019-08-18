package com.green.learning.algs4.graph.routine;

import java.util.List;
import java.util.Set;

public abstract class StrongConnectedComponent implements GraphProcessRoutine
{
    public abstract boolean connected(int v, int w);
    public abstract int id(int v);
    public abstract int count();
    public abstract List<Set<Integer>> components();
    
    @Override
    public void printResult()
    {
        System.out.println(count() + " strong components");
        for(Set<Integer> component: components())
            System.out.println(component);
    }
}
