package com.green.learning_algs4.list;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 单链表的第2种实现形式，维持head节点为空，以简化代码逻辑
 */
public class XLinkedListX<E> extends XAbstractList<E>
        implements XQueue<E>, Iterable<E>
{
    private static class Node<T>
    {
        private T item;
        private Node<T> next;
        
        private Node()
        {
        }
        
        private Node(T item)
        {
            this.item = item;
        }
        
        private Node(T item, Node<T> next)
        {
            this.item = item;
            this.next = next;
        }
    }
    
    private Node<E> head;
    private Node<E> tail;
    
    public XLinkedListX()
    {
        tail = head = new Node<>();
    }
    
    private Node<E> node(int index)
    {
        // 检验index范围
        if (index == -1)
            return head;
        validateElementIndex(index);
        
        Node<E> curr = head;
        for (int i = 0; i <= index; i++)
            curr = curr.next;
        
        return curr;
    }
    
    public void addFirst(E e)
    {
        insert(0, e);
    }
    
    public void addLast(E e)
    {
        tail = tail.next = new Node<>(e);
        size++;
    }
    
    @Override
    public void append(E e)
    {
        addLast(e);
    }
    
    @Override
    public void enqueue(E e)
    {
        addLast(e);
    }
    
    private void checkEmpty()
    {
        if (isEmpty())
            throw new NoSuchElementException("The list is empty");
    }
    
    @Override
    public void insert(int index, E e)
    {
        if (index == size)
            addLast(e);
        else
        {
            validateElementIndex(index);
            Node<E> pre = node(index - 1);
            pre.next = new Node<>(e, pre.next);
            size++;
        }
    }
    
    @Override
    public E remove(int index)
    {
        validateElementIndex(index);
        Node<E> pre = node(index - 1);
        Node<E> targetNode = pre.next;
        E item = targetNode.item;
        pre.next = targetNode.next;
        if (tail == targetNode)
            tail = pre;
        size--;
        
        return item;
    }
    
    public E removeFirst()
    {
        return remove(0);
    }
    
    @Override
    public E dequeue()
    {
        return removeFirst();
    }
    
    public E removeLast()
    {
        return remove(size - 1);
    }
    
    @Override
    public boolean removeFirst(E e)
    {
        if (isEmpty()) return false;
        
        Node<E> pre = head, curr = head.next;
        if (e == null)
            while (curr != null && curr.item != null)
            {
                pre = curr;
                curr = curr.next;
            }
        else
            for (; curr != null && !curr.item.equals(e); pre = curr, curr = curr.next) ;
        if (curr == null) return false;
        pre.next = curr.next;
        if (curr == tail)
            tail = pre;
        size--;
        
        curr.item = null;
        curr.next = null;
        
        return true;
    }
    
    @Override
    public void removeAll(E e)
    {
        Node<E> pre = head, curr = head.next;
        if (e == null)
            while (curr != null)
            {
                if (curr.item == null)
                {
                    pre.next = curr.next; // pre指针保持位置不变
                    size--;
                } else
                    pre = curr;
                curr = curr.next;
            }
        else
            while (curr != null)
            {
                if (e.equals(curr.item))
                {
                    pre.next = curr.next;
                    size--;
                } else
                    pre = curr;
                curr = curr.next;
            }
        
        // 如果tail节点被删除，pre指针停留在链式结构的最后一个节点上
        // 而此时tail指针指向一个游离的节点
        if (tail.item == e || tail.item != null && tail.item.equals(e))
            tail = pre;
    }
    
    @Override
    public E get(int index)
    {
        validateElementIndex(index);
        return node(index).item;
    }
    
    @Override
    public E peek()
    {
        return getFirst();
    }
    
    public E getFirst()
    {
        checkEmpty();
        return head.next.item;
    }
    
    public E getLast()
    {
        checkEmpty();
        return tail.item;
    }
    
    @Override
    public E set(int index, E e)
    {
        validateElementIndex(index);
        Node<E> curr = node(index);
        E item = curr.item;
        curr.item = e;
        return item;
    }
    
    @Override
    public void clear()
    {
        for (Node<E> curr = head.next; curr != null; curr = head.next)
        {
            head.next = curr.next;
            curr.item = null;
            curr.next = null;
        }
        
        tail = head;
        size = 0;
    }
    
    @Override
    public int indexOf(E e)
    {
        Node<E> curr = head.next;
        int index = 0;
        
        if (e != null)
            for (; curr != null && !e.equals(curr.item); index++)
                curr = curr.next;
        else
            for (; curr != null && curr.item != null; index++)
                curr = curr.next;
        
        if (curr == null)
            return -1;
        return index;
    }
    
    @Override
    public int lastIndexOf(E e)
    {
        int temp = -1;
        Node<E> curr = head.next;
        
        if (e != null)
            for (int i = 0; curr != null; i++)
            {
                if (e.equals(curr.item))
                    temp = i;
                curr = curr.next;
            }
        else
            for (int i = 0; curr != null; i++)
            {
                if (curr.item == null)
                    temp = i;
                curr = curr.next;
            }
        
        return temp;
    }
    
    @Override
    public Object[] toArray()
    {
        Object[] temp = new Object[size];
        Node<E> x = head.next;
        for(int i = 0; x != null; i++, x = x.next)
            temp[i] = x.item;
        return temp;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public E[] toArray(E[] a)
    {
        E[] result;
        if(a.length < size)
            result = (E[]) Array.newInstance(a.getClass().getComponentType(), size);
        else result = a;
        Node<E> x = head.next;
        for(int i = 0; x != null; i++, x = x.next)
            result[i] = x.item;
        return a;
    }
    
    @Override
    public Iterator<E> iterator()
    {
        return new XLinkedListIterator();
    }
    
    private class XLinkedListIterator implements Iterator<E>
    {
        private Node<E> current;
        
        public XLinkedListIterator()
        {
            current = head.next;
        }
        
        public XLinkedListIterator(Node<E> current)
        {
            this.current = current;
        }
        
        @Override
        public boolean hasNext()
        {
            return current != null;
        }
        
        @Override
        public E next()
        {
            if (!hasNext())
                throw new NoSuchElementException();
            E item = current.item;
            current = current.next;
            return item;
        }
        
        @Override
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
    }
}
