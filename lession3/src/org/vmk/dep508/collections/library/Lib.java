package org.vmk.dep508.collections.library;

import java.util.*;

public class Lib implements Library{

    HashMap<Book, Integer> books;
    HashMap<Book, LinkedList<String>> map;
    public Lib()
    {
        books = new HashMap<Book, Integer>();
        map = new HashMap<Book, LinkedList<String>>();
    }
    @Override
    public void addNewBook(Book book)
    {
        if (books.containsKey(book))
            books.put(book, books.get(book) + 1);
        else
            books.put(book, 1);
    }
    @Override
    public void borrowBook(Book book, String student)
    {
        if (books.containsKey(book) && books.get(book)>0)
        {
            books.put(book, books.get(book) - 1);
            if (map.containsKey(book))
            {
                LinkedList<String> temp = map.get(book);
                temp.add(student);
                map.put(book, temp);
            }
            else
            {
                LinkedList<String> temp = new LinkedList<>();
                temp.add(student);
                map.put(book, temp);
            }
        }
    }
    @Override
    public void returnBook(Book book, String student)
    {
        books.put(book, books.get(book) + 1);
        LinkedList<String> temp = map.get(book);
        temp.remove(student);
        map.put(book, temp);
    }
    @Override
    public List<Book> findAvailableBooks()
    {
        Set<Book> tmp = new HashSet<Book>();
        for (Book b: books.keySet())
        {
            if (books.get(b)!= 0)
                tmp.add(b);
        }
        return new LinkedList<Book>(tmp);
    }
}
