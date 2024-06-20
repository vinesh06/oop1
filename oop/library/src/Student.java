import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class Student extends User {
    private List<Book> borrowedBooks;
    private String username;
    private String password;

    public Student(String id, String name, String username, String password) {
        super(id, name);
        this.borrowedBooks = new ArrayList<>();
        this.username = username;
        this.password = password;
    }

    public List<Book> viewBorrowedBooks() {
        return borrowedBooks;
    }

    public void borrowBook(Book book) {
        borrowedBooks.add(book);
        book.setBorrowed(true);
    }

    public void returnBook(Book book) {
        borrowedBooks.remove(book);
        book.setBorrowed(false);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean login() {
        String enteredUsername = JOptionPane.showInputDialog("Enter username:");
        String enteredPassword = JOptionPane.showInputDialog("Enter password:");

        return username.equals(enteredUsername) && password.equals(enteredPassword);
    }
}
