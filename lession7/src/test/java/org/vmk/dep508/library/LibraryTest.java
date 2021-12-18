package org.vmk.dep508.library;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.BeforeClass;
import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class LibraryTest {
    @Before
    public void setUp() throws Exception {
//        library = new LibraryImpl("jdbc:h2:mem:library", "", "");
//        try(
//                Connection connection = DriverManager.getConnection("jdbc:h2:mem:library");
//                Statement stmt = connection.createStatement();)
//        {
//            stmt.execute("create table abonents(student_id int, student_name varchar(255))");
//            stmt.execute("create table books(book_id int, book_title varchar(255), student_id int)");
//        }
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void addNewBook() throws Exception {
        LibraryImpl library = new LibraryImpl("jdbc:h2:mem:library", "", "");
        try(
                Connection connection = DriverManager.getConnection("jdbc:h2:mem:library");
                Statement stmt = connection.createStatement();)
        {
            stmt.execute("create table abonents(student_id int, student_name varchar(255))");
            stmt.execute("create table books(book_id int, book_title varchar(255), student_id int)");
            library.addNewBook(new Book(0, "A"));
            assertTrue(library.findAvailableBooks().size() == 1);
            library.addNewBook(new Book(1, "B"));
            assertTrue(library.findAvailableBooks().size() == 2);
        }
    }

    @Test
    public void addAbonent() throws Exception {
        LibraryImpl library = new LibraryImpl("jdbc:h2:mem:library", "", "");
        try(
                Connection connection = DriverManager.getConnection("jdbc:h2:mem:library");
                Statement stmt = connection.createStatement();)
        {
            stmt.execute("create table abonents(student_id int, student_name varchar(255))");
            stmt.execute("create table books(book_id int, book_title varchar(255), student_id int)");
            library.addAbonent(new Student(0, "Ivan"));
            assertTrue(library.getAllStudents().size() == 1);
            assertTrue(library.findAvailableBooks().size() == 0);
            library.addAbonent(new Student(1, "Petr"));
            assertTrue(library.getAllStudents().size() == 2);
            assertTrue(library.findAvailableBooks().size() == 0);
        }
    }

    @Test
    public void borrowBook() throws Exception {
        LibraryImpl library = new LibraryImpl("jdbc:h2:mem:library", "", "");
        try(
                Connection connection = DriverManager.getConnection("jdbc:h2:mem:library");
                Statement stmt = connection.createStatement();)
        {
            stmt.execute("create table abonents(student_id int, student_name varchar(255))");
            stmt.execute("create table books(book_id int, book_title varchar(255), student_id int)");
            library.addAbonent(new Student(0, "Ivan"));
            library.addAbonent(new Student(1, "Petr"));
            library.addAbonent(new Student(2, "Ser"));
            library.addNewBook(new Book(0, "A"));
            library.addNewBook(new Book(1, "B"));
            library.addNewBook(new Book(2, "C"));
            assertTrue(library.findAvailableBooks().size() == 3);
            library.borrowBook(new Book(0, "A"), new Student(1, "Petr"));
            assertTrue(library.findAvailableBooks().size() == 2);
            library.borrowBook(new Book(1, "B"), new Student(1, "Petr"));
            assertTrue(library.findAvailableBooks().size() == 1);
            library.borrowBook(new Book(2, "C"), new Student(2, "Ser"));
            assertTrue(library.findAvailableBooks().size() == 0);
        }
    }

    @Test
    public void returnBook() throws Exception {
        LibraryImpl library = new LibraryImpl("jdbc:h2:mem:library", "", "");
        try(
                Connection connection = DriverManager.getConnection("jdbc:h2:mem:library");
                Statement stmt = connection.createStatement();)
        {
            stmt.execute("create table abonents(student_id int, student_name varchar(255))");
            stmt.execute("create table books(book_id int, book_title varchar(255), student_id int)");
            library.addAbonent(new Student(0, "Ivan"));
            library.addAbonent(new Student(1, "Petr"));
            library.addAbonent(new Student(2, "Ser"));
            library.addNewBook(new Book(0, "A"));
            library.addNewBook(new Book(1, "B"));
            library.addNewBook(new Book(2, "C"));
            library.borrowBook(new Book(0, "A"), new Student(1, "Petr"));
            library.borrowBook(new Book(1, "B"), new Student(1, "Petr"));
            assertTrue(library.findAvailableBooks().size() == 1);
            library.borrowBook(new Book(1, "B"), new Student(0, "Ivan"));
            assertTrue(library.findAvailableBooks().size() == 1);
            library.returnBook(new Book(1, "B"), new Student(1, "Petr"));
            assertTrue(library.findAvailableBooks().size() == 2);
            library.returnBook(new Book(0, "A"), new Student(1, "Petr"));
            assertTrue(library.findAvailableBooks().size() == 3);
        }
    }

}