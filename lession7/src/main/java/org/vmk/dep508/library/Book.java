package org.vmk.dep508.library;

/**
 * Created by Asus on 10/21/2018.
 */
public class Book {
    private int id;
    private String title;

    public Book(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }

    @Override
    public int hashCode()
    {
        return title.hashCode() + id;
    }

    @Override
    public boolean equals(Object obj)
     {
         if (obj == this)
             return true;
         if (obj == null || obj.getClass()!=this.getClass())
             return false;
         Book book = (Book) obj;
         if (book.id == this.id && book.title == this.title)
             return true;
         return false;
     }
}
