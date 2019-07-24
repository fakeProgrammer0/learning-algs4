package com.green.learning_algs4.graph.routine;

//  无向图和有向图还是别耦合在一起，代码看着很乱，可读性低。。
/**
 * 判断给定图是否存在环
 * 如果存在，找出一条回路
 * 适用于有向图和无向图
 * 处理 self loop && parallel edges 的情况
 */
public abstract class CircuitFinder implements GraphProcessRoutine
{
    public abstract boolean hasACircuit();
    public abstract Iterable<Integer> getCircuit();
    
    @Override
    public void printResult()
    {
        if(hasACircuit())
        {
            System.out.println("The graph has a circle: ");
            System.out.println(getCircuit());
        }else {
            System.out.println("The graph has no circle.");
        }
    }
}
