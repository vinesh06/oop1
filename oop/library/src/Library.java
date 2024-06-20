import java.util.ArrayList;
import java.util.List;

public class Library {
    private Catalog catalog;
    private List<Student> students;
    private Patron patron;
    private List<Transaction> transactions;

    public Library() {
        this.catalog = new Catalog();
        this.students = new ArrayList<>();
        this.transactions = new ArrayList<>();
        initializeLibraryData();
    }

    public void setPatron(Patron patron) {
        this.patron = patron;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    private void initializeLibraryData() {
        // Initialize with some books and students
        Book book1 = new Book("1", "The Great Gatsby", "F. Scott Fitzgerald");
        Book book2 = new Book("2", "1984", "George Orwell");
        Book book3 = new Book("3", "To Kill a Mockingbird", "Harper Lee");
        catalog.addBook(book1);
        catalog.addBook(book2);
        catalog.addBook(book3);

        Student student1 = new Student("100", "Alice", "alice", "password1");
        Student student2 = new Student("101", "Bob", "bob", "password2");
        Student student3 = new Student("102", "Charlie", "charlie", "password3");
        students.add(student1);
        students.add(student2);
        students.add(student3);

        Patron patron = new Patron("p1", "Librarian", "librarian@example.com");
        setPatron(patron);
    }

    public Book findBookById(String id) {
        return catalog.findBookById(id);
    }

    public Student findStudentById(String id) {
        for (Student student : students) {
            if (student.getId().equals(id)) {
                return student;
            }
        }
        return null;
    }

    public Student findStudentByUsername(String username) {
        for (Student student : students) {
            if (student.getUsername().equals(username)) {
                return student;
            }
        }
        return null;
    }

    public Patron getPatron() {
        return patron;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public List<Student> getStudents() {
        return students;
    }
}
