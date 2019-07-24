package com.green.learning_algs4.util;

import com.green.learning_algs4.list.XArrayList;
import com.green.learning_algs4.list.XList;

import java.util.Date;

public class XTimer
{
    static class Interval
    {
        private final Date start;
        private Date end;
        private String desc;
    
        public Interval()
        {
            this.start = new Date();
        }
    
        public Interval(String desc)
        {
            this.start = new Date();
            this.desc = desc;
        }
    
        public void end()
        {
            this.end = new Date();
        }
    
        public String getDesc()
        {
            return desc;
        }
    
        public void setDesc(String desc)
        {
            this.desc = desc;
        }
    
        @Override
        public String toString()
        {
            final String lineSep = System.lineSeparator();
            return "<" + desc + ">" + lineSep +
                    "from: " +  start.toString() + lineSep +
                    "  to: " + end.toString() + lineSep +
                    "total ms: " + (end.getTime() - start.getTime()) + lineSep;
        }
    }
    
    private String name;
    private Interval currInterval;
    private XList<Interval> intervals = new XArrayList<>();
    
    public XTimer()
    {
        this("A Timer");
    }
    
    public XTimer(String name)
    {
        this.name = name;
    }
    
    public void start()
    {
        start("");
    }
    
    public void start(String desc)
    {
        if(currInterval != null)
            throw new IllegalStateException();
        currInterval = new Interval(desc);
    }
    
    public void stop()
    {
        currInterval.end();
        intervals.append(currInterval);
        currInterval = null;
    }
    
    public void clear()
    {
        currInterval = null;
        intervals.clear();
    }
    
    /**
     * @return the last interval in milliseconds
     */
    public long lastIntervalMs()
    {
        if(intervals.isEmpty())
            throw new IllegalStateException();
        Interval interval = intervals.get(intervals.size() - 1);
        return interval.end.getTime() - interval.start.getTime();
    }
    
    @Override
    public String toString()
    {
        final String lineSep = System.lineSeparator();
        StringBuilder sb = new StringBuilder(name).append(lineSep);
        for(int i = 0; i < intervals.size(); i++)
            sb.append(i+1).append(".").append(intervals.get(i));
//        sb.append(lineSep);
        return sb.toString();
    }
}
