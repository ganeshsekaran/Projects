package com.ganesh.linklist;

/**
 * User: Ganesh
 */
public class Main
{
    public static void main(String args[]) throws Exception
    {
        LinkList list = new LinkList();
        list.generateRandomLinkList(1000000);

        list.reverseList();

    }
}
