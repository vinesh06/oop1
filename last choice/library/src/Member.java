import java.util.ArrayList;

public class Member extends User {
    private ArrayList<BorrowedBook> borrowedBooks;

    public Member(String id, String name) {
        super(id, name);
        this.borrowedBooks = new ArrayList<>();
    }

    public ArrayList<BorrowedBook> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void borrowBook(BorrowedBook book) {
        borrowedBooks.add(book);
    }

    public void returnBook(BorrowedBook book) {
        borrowedBooks.remove(book);
    }
}
