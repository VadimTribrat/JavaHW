package org.vmk.dep508.collections.library;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

public class LibTest {

    Lib mylib;
    @Before
    public void setUp() throws Exception {
        mylib = new Lib();
    }

    @After
    public void tearDown() throws Exception {
        mylib = null;
    }

    @Test
    public void addNewBook() {
        assertEquals(0, mylib.books.size());
        mylib.addNewBook(new Book(1, "Mary"));
        assertTrue(mylib.books.get(new Book(1, "Mary")) == 1);
        mylib.addNewBook(new Book(1, "Mary"));
        mylib.addNewBook(new Book(2, "Harry Potter"));
        assertTrue(mylib.books.get(new Book(1, "Mary")) == 2);
        assertTrue(mylib.books.get(new Book(2, "Harry Potter")) == 1);
        assertTrue(mylib.books.size() == 2);
    }

    @Test
    public void borrowBook() {
        mylib.addNewBook(new Book(1, "Mary"));
        mylib.addNewBook(new Book(1, "Mary"));
        mylib.addNewBook(new Book(2, "Harry Potter"));
        mylib.borrowBook(new Book(1, "Mari"), "Petrov");
        assertTrue(mylib.books.get(new Book(1, "Mary")) == 2);
        assertTrue(mylib.books.get(new Book(2, "Harry Potter")) == 1);
        assertTrue(mylib.books.size() == 2);
        assertTrue(mylib.map.get(new Book(1, "Mary")) == null);
        mylib.borrowBook(new Book(1, "Mary"), "Petrov");
        assertTrue(mylib.map.get(new Book(1, "Mary")).size() == 1);
        assertTrue(mylib.books.get(new Book(1, "Mary")) == 1);
        mylib.borrowBook(new Book(1, "Mary"), "Ivanov");
        assertTrue(mylib.map.get(new Book(1, "Mary")).size() == 2);
        assertTrue(mylib.books.get(new Book(1, "Mary")) == 0);
        mylib.borrowBook(new Book(1, "Mary"), "Ivanov");
        assertTrue(mylib.books.get(new Book(1, "Mary")) == 0);
        assertTrue(mylib.map.get(new Book(1, "Mary")).size() == 2);
    }

    @Test
    public void returnBook() {
        mylib.addNewBook(new Book(1, "Mary"));
        mylib.addNewBook(new Book(1, "Mary"));
        mylib.addNewBook(new Book(2, "Harry Potter"));
        mylib.borrowBook(new Book(1, "Mary"), "Petrov");
        mylib.borrowBook(new Book(1, "Mary"), "Ivanov");
        assertTrue(mylib.map.get(new Book(1, "Mary")).size() == 2);
        mylib.returnBook(new Book(1, "Mary"), "Petrov");
        assertTrue(mylib.map.get(new Book(1, "Mary")).size() == 1);
        assertTrue(mylib.books.get(new Book(1, "Mary")) == 1);
        mylib.returnBook(new Book(1, "Mary"), "Ivanov");
        assertTrue(mylib.map.get(new Book(1, "Mary")).size() == 0);
        assertTrue(mylib.books.get(new Book(1, "Mary")) == 2);
    }

    @Test
    public void findAvailableBooks() {
        mylib.addNewBook(new Book(1, "Mary"));
        mylib.addNewBook(new Book(1, "Mary"));
        mylib.addNewBook(new Book(2, "Harry Potter"));
        mylib.addNewBook(new Book(2, "Harry Potter"));
        mylib.addNewBook(new Book(2, "Harry Potter"));
        mylib.addNewBook(new Book(3, "Harry Potter 2"));
        mylib.borrowBook(new Book(1, "Mary"), "Petrov");
        mylib.borrowBook(new Book(2, "Harry Potter"), "Ivanov");
        mylib.borrowBook(new Book(2, "Harry Potter"), "Sidorov");
        mylib.borrowBook(new Book(2, "Harry Potter"), "Zagoruyko");
        assertTrue(mylib.findAvailableBooks().size() == 2);
        mylib.borrowBook(new Book(1, "Mary"), "Ivanov");
        assertTrue(mylib.findAvailableBooks().size() == 1);
        mylib.returnBook(new Book(2, "Harry Potter"), "Sidorov");
        mylib.borrowBook(new Book(3, "Harry Potter 2"), "Zagoruyko");
        assertTrue(mylib.findAvailableBooks().size() == 1);
        mylib.returnBook(new Book(2, "Harry Potter"), "Sidorov");
        mylib.returnBook(new Book(3, "Harry Potter 2"), "Zagoruyko");
        assertThat(mylib.findAvailableBooks(), hasItem(new Book(2, "Harry Potter")));
    }
}