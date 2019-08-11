# PriorityQueue


```java
class XPriorityQueue
{
    private boolean hasHigherPriority(E o1, E o2)
    {
        return comparator.compare(o1, o2) <= 0;
    }
        
    // 更小的元素，优先级更高
    private class SmallComparator implements Comparator<E>
    {
        @Override
        public int compare(E o1, E o2)
        {
            if(o1 == null || o2 == null)
                throw new NullPointerException("null element cannot be compared");
            return o1.compareTo(o2);
        }
    }
    
    // 更大的元素，优先级更高
    private class LargeComparator implements Comparator<E>
    {
        @Override
        public int compare(E o1, E o2)
        {
            if(o1 == null || o2 == null)
                throw new NullPointerException("null element cannot be compared");
            return o2.compareTo(o1);
        }
    }
}
```


