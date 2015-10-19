package com.ganesh.linklist;

/**
 * User: Ganesh
 */
public class Thread2 implements Runnable
{
    private LinkList list;

    public Thread2(LinkList list)
    {
        super();
        this.list = list;
    }

    public void run()
    {
        list.displayList();
    }
}
