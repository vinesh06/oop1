import javax.swing.*;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class MemberInterface {
    private Library library;
    private Member member;
    private String[] genres = {"Fiction", "Non-fiction", "Science", "Fantasy", "History"};

    public MemberInterface(Library library, Member member) {
        this.library = library;
        this.member = member;
    }

    public void start() {
        while (true) {
            String[] options = {"Borrow Book", "View Borrowed Books", "Return Book","Review Books" ,"Logout"};
            int choice = JOptionPane.showOptionDialog(null, "Member Panel", "Member Interface",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            if (choice == JOptionPane.CLOSED_OPTION || choice == 4) {
                break;  // Exit the loop when "Logout" or close button is clicked
            }
            switch (choice) {
                case 0:
                    borrowBook();
                    break;
                case 1:
                    viewBorrowedBooks();
                    break;
                case 2:
                    returnBook();
                    break;
                case 3:
                    giveReview();
                    break;
            }
        }
    }

    private void borrowBook() {
        String genre = (String) JOptionPane.showInputDialog(null, "Choose genre:", "Select Genre",
                JOptionPane.QUESTION_MESSAGE, null, genres, genres[0]);
        if (genre == null) return; // Handle cancel

        List<Book> availableBooks = new ArrayList<>();
        for (Book book : library.getBooks()) {
            if (book.getGenre().equals(genre)) {
                availableBooks.add(book);
            }
        }

        if (availableBooks.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No books available to borrow in this genre at the moment");
            return;
        }

        StringBuilder availableBooksInfo = new StringBuilder();
        for (int i = 0; i < availableBooks.size(); i++) {
            availableBooksInfo.append(i).append(": ").append(availableBooks.get(i)).append("\n");
        }
        try {
            int index = Integer.parseInt(JOptionPane.showInputDialog("Enter book index to borrow:\n" + availableBooksInfo));
            Book book = availableBooks.get(index);
            BorrowedBook borrowedBook = new BorrowedBook(book, member, new Date());
            member.borrowBook(borrowedBook);
            library.getBorrowedBooks().add(borrowedBook);
            library.getBooks().remove(book);  // Remove the borrowed book from the library's available books list
            JOptionPane.showMessageDialog(null, "Book borrowed successfully!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid input or book already borrowed!");
        }
    }

    private void viewBorrowedBooks() {
        StringBuilder borrowedBooksInfo = new StringBuilder();
        for (BorrowedBook borrowedBook : member.getBorrowedBooks()) {
            borrowedBooksInfo.append(borrowedBook).append("\n");
        }
        JOptionPane.showMessageDialog(null, borrowedBooksInfo.length() > 0 ? borrowedBooksInfo.toString() : "No borrowed books!");
    }

    private void returnBook() {
        if (member.getBorrowedBooks().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Books are all returned");
            return;
        }

        StringBuilder borrowedBooksInfo = new StringBuilder();
        for (int i = 0; i < member.getBorrowedBooks().size(); i++) {
            borrowedBooksInfo.append(i).append(": ").append(member.getBorrowedBooks().get(i)).append("\n");
        }
        try {
            int index = Integer.parseInt(JOptionPane.showInputDialog("Enter book index to return:\n" + borrowedBooksInfo));
            BorrowedBook borrowedBook = member.getBorrowedBooks().get(index);
            borrowedBook.setReturnDate(new Date());
            member.returnBook(borrowedBook);
            // Do not remove from library.getBorrowedBooks(), just update the return date
            library.addBook(borrowedBook.getBook());  // Add the returned book back to the library's available books list
            JOptionPane.showMessageDialog(null, "Book returned successfully!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid input!");
        }
    }

    private void giveReview() {
        String genre = (String) JOptionPane.showInputDialog(null, "Choose genre:", "Select Genre",
                JOptionPane.QUESTION_MESSAGE, null, genres, genres[0]);
        if (genre == null) return; // Handle cancel
    
        List<Book> availableBooks = new ArrayList<>();
        for (Book book : library.getBooks()) {
            if (book.getGenre().equals(genre)) {
                availableBooks.add(book);
            }
        }
    
        if (availableBooks.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No books available for the selected genre.");
            return;
        }
    
        Book selectedBook = (Book) JOptionPane.showInputDialog(null, "Choose book:", "Select Book",
                JOptionPane.QUESTION_MESSAGE, null, availableBooks.toArray(), availableBooks.get(0));
        if (selectedBook == null) return; // Handle cancel
    
        String reviewer = JOptionPane.showInputDialog(null, "Enter your ID:");
        if (reviewer == null || reviewer.trim().isEmpty()) return; // Handle cancel or empty input
    
        String comment = JOptionPane.showInputDialog(null, "Enter your review:");
        if (comment == null || comment.trim().isEmpty()) return; // Handle cancel or empty input
    
        String ratingStr;
        int rating = 0;
        while (true) {
            ratingStr = JOptionPane.showInputDialog(null, "Enter rating (1-5):");
            if (ratingStr == null) return; // Handle cancel
            try {
                rating = Integer.parseInt(ratingStr);
                if (rating < 1 || rating > 5) {
                    JOptionPane.showMessageDialog(null, "Rating must be between 1 and 5.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number for rating.");
            }
        }
    
        Review review = new Review(reviewer, comment, rating);
        selectedBook.addReview(review);
        JOptionPane.showMessageDialog(null, "Thank you for your review!");
    }
}
