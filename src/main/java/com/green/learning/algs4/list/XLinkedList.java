package com.green.learning.algs4.list;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @see java.util.LinkedList
 */
public class XLinkedList<E> extends XAbstractList<E>
        implements XQueue<E>, Iterable<E>
{
    private static class Node<T>
    {
        private T item;
        private Node<T> next;
        
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
    
    public XLinkedList()
    {
    }
    
    public XLinkedList(Iterable<E> elements)
    {
        if(elements == null)
            throw new IllegalArgumentException("The parameter 'elements' should not be null");
        Iterator<E> iterator = elements.iterator();
        if(!iterator.hasNext())
            throw new IllegalArgumentException("No element");
        while (iterator.hasNext())
            append(iterator.next());
    }
    
    public void addFirst(E e)
    {
        head = new Node<>(e, head);
        if (tail == null) tail = head;
        size++;
    }
    
    public void addLast(E e)
    {
        if (isEmpty())
            head = tail = new Node<>(e);
        else
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
        validateInsertIndex(index);
        
        if (index == 0)
            addFirst(e);
        else if (index == size)
            addLast(e);
        else
        {
            Node<E> pre = head;
            for (int i = 0; i < index - 1; i++)
                pre = pre.next;
            
            // 过于简洁，有点影响可读性
            pre.next = new Node<>(e, pre.next);
            size++;
        }
    }
    
    @Override
    public E remove(int index)
    {
        validateElementIndex(index);
        if (index == 0)
            removeFirst();
        Node<E> pre = head;
        for (int i = 0; i < index - 1; i++)
            pre = pre.next;
        Node<E> targetNode = pre.next;
        E item = targetNode.item;
        pre.next = targetNode.next;
        if (tail == targetNode)
            tail = pre;
        size--;
        
        targetNode.item = null;
        targetNode.next = null;
        
        return item;
    }
    
    public E removeFirst()
    {
        checkEmpty();
        
        Node<E> temp = head;
        E item = head.item;
        head = head.next;
        size--;
        
        temp.item = null;
        temp.next = null;
        
        if (head == null)
            tail = head;
        
        return item;
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
        if (e == head.item || e != null && e.equals(head.item))
            removeFirst();
        
        Node<E> pre = null, curr = head;
        if (e == null)
            while (curr != null && curr.item != null)
            {
                pre = curr;
                curr = curr.next;
            }
        else
            for (; curr != null && !e.equals(curr.item); pre = curr, curr = curr.next) ;
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
        Node<E> pre = null, curr = head;
        if (e == null)
            while (curr != null)
            {
                if (curr.item == null)
                {
                    if (pre != null)
                        pre.next = curr.next; // pre指针保持位置不变
                    else head = head.next; // 命中head节点
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
                    if (pre != null)
                        pre.next = curr.next; // pre指针保持位置不变
                    else head = head.next;
                    size--;
                } else
                    pre = curr;
                curr = curr.next;
            }
    
        // 如果tail节点被删除，pre指针停留在链式结构的最后一个节点上
        // 而此时tail指针指向一个游离的节点
        if(tail.item == e || e != null && e.equals(tail.item))
            tail = pre;
    }
    
    @Override
    public E get(int index)
    {
        validateElementIndex(index);
        Node<E> curr = head;
        for (int i = 0; i < index; i++)
            curr = curr.next;
        return curr.item;
    }
    
    @Override
    public E peek()
    {
        return getFirst();
    }
    
    public E getFirst()
    {
        checkEmpty();
        return head.item;
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
        Node<E> curr = head;
        for (int i = 0; i < index; i++)
            curr = curr.next;
        E item = curr.item;
        curr.item = e;
        return item;
    }
    
    @Override
    public void clear()
    {
        Node<E> nextNode;
        for (Node<E> curr = head; curr != null; curr = nextNode)
        {
            nextNode = curr.next;
            curr.item = null;
            curr.next = null;
        }
        
        head = tail = null;
        size = 0;
        
        // 手动清理仍然是有必要的，不然如果有迭代器存在，该迭代器仍引用着原来链表中的元素
        // java垃圾回收的机制不会清空这些元素
        // 以下是两行是标准的错误示范，来自[java语言程序设计]ch24
        //        size = 0;
        //        head = tail = null;
    }
    
    @Override
    public int indexOf(E e)
    {
        Node<E> curr = head;
        int index = 0;
        
        if (e != null)
            for (; curr != null && !e.equals(curr.item); index++) // 无法测试null
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
        Node<E> curr = head;
        
        if (e != null)
            for (int index = 0; curr != null; index++)
            {
                if (e.equals(curr.item))
                    temp = index;
                curr = curr.next;
            }
        else
            for (int index = 0; curr != null; index++)
            {
                if (curr.item == null)
                    temp = index;
                curr = curr.next;
            }
        
        return temp;
    }
    
    @Override
    public String toString()
    {
        if(isEmpty()) return "[]";
        final String elementSep = ", ";
        StringBuilder sb = new StringBuilder("[");
        for (Node<E> curr = head; curr != tail; curr = curr.next)
            sb.append(curr.item).append(elementSep);
        sb.append(tail.item);
        sb.append("]");
        return sb.toString();
    }
    
    @Override
    public Object[] toArray()
    {
        Object[] temp = new Object[size];
        Node<E> x = head;
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
        Node<E> x = head;
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
        
        private XLinkedListIterator()
        {
            current = head;
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
