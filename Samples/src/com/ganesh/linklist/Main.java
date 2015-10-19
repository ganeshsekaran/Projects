package com.ganesh.linklist;

import com.ganesh.linklist.LinkList.LinkListRandomOption;

/**
 * User: Ganesh
 */
public class Main
{
    public static void main(String args[]) throws Exception
    {
        LinkList list = new LinkList();
        list.generateRandomLinkList(6, LinkListRandomOption.RANDOM_UNIQUE);
        System.out.println(list.toString());
        list.reverseListIterative();
        System.out.println(list.toString());
       
    }
}
