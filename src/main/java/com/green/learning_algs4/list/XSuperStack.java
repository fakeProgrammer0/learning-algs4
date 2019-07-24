package com.green.learning_algs4.list;

import java.util.Objects;

/**
 * 支持removeFirst操作，移除第一个匹配的元素
 */
public class XSuperStack<E> extends XLinkedStack<E>
{
    public XSuperStack()
    {
    
    }
    
    /**
     * remove the first occurrence of {@code e}
     * @param e the element to be removed
     * @return true if the specified element is removed
     *          from the superstack
     */
    public boolean removeFirst(E e)
    {
        // java.util.Objects.equals 方法
        // 连java自己都意识到null引用的麻烦了吧
        if(Objects.equals(e, head.item)) // 等价于下行的语句
//        if(e == head.item || e != null && e.equals(head.item))
        {
            pop();
            return true;
        }
        
        Node<E> pre = head;
        Node<E> x = pre.next;
        while(x != null)
        {
            if(x.item == e || e != null && e.equals(x.item))
            {
                pre.next = x.next;
                x.item = null;
                x.next = null;
                size--;
                return true;
            }
            
            pre = x;
            x = x.next;
        }
        return false;
    }
}
