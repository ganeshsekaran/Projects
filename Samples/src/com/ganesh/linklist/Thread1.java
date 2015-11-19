package com.ganesh.linklist;

import java.util.HashMap;
import java.util.Set;

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
    	HashMap map = new HashMap();
    	
        list.reverseList();
    }
}
