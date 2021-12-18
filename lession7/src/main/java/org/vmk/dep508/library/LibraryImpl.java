package org.vmk.dep508.library;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibraryImpl implements Library{
    private String Url;
    private String user;
    private String password;

    public LibraryImpl(String Url, String user, String password)
    {
        this.Url = Url;
        this.user = user;
        this.password = password;
    }
    /* Получить список свободных книг */
    @Override
    public List<Book> findAvailableBooks()
    {
        List<Book> books = new ArrayList<>();
        try (
                Connection c = DriverManager.getConnection(Url, user, password);
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery("select book_id,book_title from books where student_id = -1");)
        {
            while (rs.next())
            {
                int i = rs.getInt("book_id");
                String str = rs.getString("book_title");
                books.add(new Book(i, str));
            }
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return books;
    }

    /* Список всех записанных в библиотеку студентов*/
    @Override
    public List<Student> getAllStudents()
    {
        List<Student> students = new ArrayList<>();
        try (
                Connection c = DriverManager.getConnection(Url, user, password);
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery("select student_id,student_name from abonents");)
        {
            while (rs.next())
            {
                int i = rs.getInt("student_id");
                String str = rs.getString("student_name");
                students.add(new Student(i, str));
            }
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return students;
    }

    /* Регистрация новой книги */
    @Override
    public void addNewBook(Book book)
    {
        try (
                Connection c = DriverManager.getConnection(Url, user, password);
                PreparedStatement st = c
                .prepareStatement("insert into books values( ?,?,-1)");)
        {
            st.setInt(1, book.getId());
            st.setString(2, book.getTitle());
            st.executeUpdate();
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    /* Добавление нового абонента */
    @Override
    public void addAbonent(Student student)
    {
        try (
                Connection c = DriverManager.getConnection(Url, user, password);
                PreparedStatement st = c
                        .prepareStatement("insert into abonents values(?,?)");)
        {
            st.setInt(1, student.getId());
            st.setString(2, student.getName());
            st.executeUpdate();
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    /* Студент берет книгу */
    @Override
    public void borrowBook(Book book, Student student)
    {
        if (!findAvailableBooks().contains(book))
            return;
        try (
                Connection c = DriverManager.getConnection(Url, user, password);
                PreparedStatement st = c
                        .prepareStatement("update books set student_id = ? where book_id = ?");)
        {
            st.setInt(1, student.getId());
            st.setInt(2, book.getId());
            st.executeUpdate();
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    /* Студент возвращает книгу */
    @Override
    public void returnBook(Book book, Student student)
    {
        if (findAvailableBooks().contains(book))
            return;
        try (
                Connection c = DriverManager.getConnection(Url, user, password);
                PreparedStatement st = c
                        .prepareStatement("update books set student_id = -1 where student_id = ? and book_id = ?");)
        {
            st.setInt(1, student.getId());
            st.setInt(2, book.getId());
            st.executeUpdate();
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }
}
