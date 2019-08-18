package com.green.learning.algs4.graph;

import com.green.learning.algs4.graph.uf.*;
import com.green.learning.algs4.util.XTimer;
import edu.princeton.cs.algs4.In;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.util.Random;

public class UnionFindTest
{
    private final String dataDir = "data" + File.separator + "graph" + File.separator;
    private final String testData1 = dataDir + "tinyUF.txt";
    private final String testData2 = dataDir + "mediumUF.txt";
    private final String testData3 = dataDir + "largeUF.txt";
    
    @Test
    public void simpleTest()
    {
        In in = new In(testData2);
        final int N = in.readInt();
//        uf = new QuickFind(N);
//        uf = new QuickUnion(N);
        UnionFind unionFind = new WeightedUF(N);
//        uf = new SuperWeightedUionFind(N);
        while (!in.isEmpty())
        {
            int v = in.readInt(), w = in.readInt();
            if(unionFind.isConnected(v, w))
                System.out.printf("%d %d\n", v, w);
            else unionFind.union(v, w);
        }
        System.out.printf("%d count(s)\n", unionFind.count());
    }
    
    @ParameterizedTest
    @ValueSource(classes = {
            SuperWeightedUF.class,
            WeightedUF.class,
            QuickUnion.class,
            QuickFind.class
            })
    void testTimeEfficiency(Class<? extends UnionFind> ufClass)
    {
        XTimer timer = new XTimer("test " + ufClass.getSimpleName());
        In in = new In(testData3);
        final int N = in.readInt();
        UnionFind unionFind;
        try
        {
            unionFind = ufClass.getConstructor(int.class).newInstance(N);
        }catch (Exception ex)
        {
            ex.printStackTrace();
            return;
        }
        
        timer.start("union & connected");
        while (!in.isEmpty())
        {
            int v = in.readInt(), w = in.readInt();
            if(!unionFind.isConnected(v, w))
                unionFind.union(v, w);
        }
        timer.stop();
        System.out.println(timer);
    }
    
    @ParameterizedTest
    @ValueSource(ints = {1000, 1000_000, 10_000_000})
    void randomTest(int N)
    {
        XTimer timer = new XTimer("union find random test: N = " + N);
        final int T = 2 * N;
        
        Random random = new Random();
        timer.start("init random ints");
        int[] rndInts = random.ints(2 * T, 0, N).toArray();
        timer.stop();
        
        Class[] ufClasses = {SuperUF.class, SuperWeightedUF.class, WeightedUF.class};
        
        for(Class<? extends UnionFind> ufClass: ufClasses)
        {
            try
            {
                UnionFind uf = ufClass.getConstructor(int.class).newInstance(N);
                timer.start(uf.getClass().getSimpleName());
                testWithRndInts(uf, rndInts);
                timer.stop();
            }catch (Exception ex)
            {
                ex.printStackTrace();
                return;
            }
        }
        
        System.out.println(timer);
    }
    
    private void testWithRndInts(UnionFind unionFind, int[] rndInts)
    {
        for(int i = 0; i < rndInts.length;i += 2)
        {
            int v = rndInts[i], w = rndInts[i + 1];
            if(!unionFind.isConnected(v, w))
                unionFind.union(v, w);
        }
    }
    
}
