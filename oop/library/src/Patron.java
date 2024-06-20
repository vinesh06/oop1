import javax.swing.*;

public class Patron extends User {
    private String contactInfo;

    public Patron(String id, String name, String contactInfo) {
        super(id, name);
        this.contactInfo = contactInfo;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void viewStudentDetails(Library library) {
        StringBuilder studentDetails = new StringBuilder("Student Details:\n");
        for (Student student : library.getStudents()) {
            studentDetails.append("ID: ").append(student.getId()).append(", Name: ").append(student.getName()).append("\n");
        }
        JOptionPane.showMessageDialog(null, studentDetails.toString());
    }

    public void viewBorrowedBooks(Library library) {
        StringBuilder borrowedBooks = new StringBuilder("Borrowed Books:\n");
        for (Student student : library.getStudents()) {
            for (Book book : student.viewBorrowedBooks()) {
                borrowedBooks.append(book.getDetails()).append(" borrowed by ").append(student.getName()).append("\n");
            }
        }
        JOptionPane.showMessageDialog(null, borrowedBooks.toString());
    }

    public void viewAvailableBooks(Library library) {
        StringBuilder availableBooks = new StringBuilder("Available Books:\n");
        for (Book book : library.getCatalog().listBooks()) {
            if (!book.isBorrowed()) {
                availableBooks.append(book.getDetails()).append("\n");
            }
        }
        JOptionPane.showMessageDialog(null, availableBooks.toString());
    }

    public boolean login(String username, String password) {
        return "librarian".equals(username) && "password".equals(password);
    }

    @Override
    public boolean login() {
        return false; // Not used, replaced by login with username and password
    }
}
