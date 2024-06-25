import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Library {
    private ArrayList<Book> books;
    private ArrayList<BorrowedBook> borrowedBooks;
    private Admin admin;
    private Map<String, Member> members;

    public Library() {
        books = new ArrayList<>();
        borrowedBooks = new ArrayList<>();
        members = new HashMap<>();
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void updateBook(int index, Book book) {
        books.set(index, book);
    }

    public void deleteBook(int index) {
        books.remove(index);
    }

    public ArrayList<BorrowedBook> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void populateData() {
        // Populate the library with some initial data
        // For demonstration purposes, we'll add some dummy data here

        // Adding books
        addBook(new Book("The Hobbit", "J.R.R. Tolkien", "Fantasy"));
        addBook(new Book("1984", "George Orwell", "Fiction"));
        addBook(new Book("A Brief History of Time", "Stephen Hawking", "Science"));
        addBook(new Book("Sapiens", "Yuval Noah Harari", "History"));
        addBook(new Book("Educated", "Tara Westover", "Non-fiction"));

        // Creating admin
        admin = new Admin("admin", "password123");

        // Adding members
        addMember(new Member("M001", "Alice"));
        addMember(new Member("M002", "Bob"));
    }

    public Admin getAdmin() {
        return admin;
    }

    public void addMember(Member member) {
        members.put(member.getId(), member);
    }

    public Member getMemberById(String memberId) {
        return members.get(memberId);
    }
}
