package com.ganesh.linklist;

/**
 * User: Ganesh
 */
public class Thread1 implements Runnable
{
    private LinkList list;

    public Thread1(LinkList list)
    {
        super();
        this.list = list;
    }

    public void run()
    {
        list.reverseList();
    }
}
