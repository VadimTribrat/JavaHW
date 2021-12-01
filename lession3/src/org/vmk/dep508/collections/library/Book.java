package org.vmk.dep508.collections.library;

public class Book {
    int id;
    String title;
    public Book(int i, String st)
    {
        id = i; title = st;
    }
    @Override
    public int hashCode()
    {
        return title.hashCode() + id;
    }
    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null)
            return false;
        return id == ((Book) o).id && title == ((Book) o).title;
    }
}
