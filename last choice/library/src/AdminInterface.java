import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class AdminInterface {
    private Library library;
    private String[] genres = {"Fiction", "Non-fiction", "Science", "Fantasy", "History"};

    public AdminInterface(Library library) {
        this.library = library;
    }

    public void start() {
        while (true) {
            String[] options = {"Add Book", "Update Book", "Delete Book", "View Borrowed Books","View Reviews", "Logout"};
            int choice = JOptionPane.showOptionDialog(null, "Admin Panel", "Admin Interface",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            if (choice == JOptionPane.CLOSED_OPTION || choice == 5) {
                break;
            }
            switch (choice) {
                case 0:
                    addBook();
                    break;
                case 1:
                    updateBook();
                    break;
                case 2:
                    deleteBook();
                    break;
                case 3:
                    viewBorrowedBooks();
                    break;
                case 4:
                    viewBookReviews();
                    break;
            }
        }
    }

    private void addBook() {
        String genre = (String) JOptionPane.showInputDialog(null, "Choose genre:", "Select Genre",
                JOptionPane.QUESTION_MESSAGE, null, genres, genres[0]);
        if (genre == null) return; // Handle cancel

        String title = JOptionPane.showInputDialog("Enter book title:");
        if (title == null) return; // Handle cancel

        String author = JOptionPane.showInputDialog("Enter book author:");
        if (author == null) return; // Handle cancel

        Book book = new Book(title, author, genre);
        library.addBook(book);
        JOptionPane.showMessageDialog(null, "Book added successfully!");
    }

    private void updateBook() {
        if (library.getBooks().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No books available to update.");
            return;
        }

        StringBuilder booksInfo = new StringBuilder();
        for (int i = 0; i < library.getBooks().size(); i++) {
            booksInfo.append(i).append(": ").append(library.getBooks().get(i)).append("\n");
        }
        try {
            int index = Integer.parseInt(JOptionPane.showInputDialog("Enter book index to update:\n" + booksInfo));
            String genre = (String) JOptionPane.showInputDialog(null, "Choose new genre:", "Select Genre",
                    JOptionPane.QUESTION_MESSAGE, null, genres, genres[0]);
            if (genre == null) return; // Handle cancel

            String title = JOptionPane.showInputDialog("Enter new book title:");
            if (title == null) return; // Handle cancel

            String author = JOptionPane.showInputDialog("Enter new book author:");
            if (author == null) return; // Handle cancel

            Book book = new Book(title, author, genre);
            library.updateBook(index, book);
            JOptionPane.showMessageDialog(null, "Book updated successfully!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid input!");
        }
    }

    private void deleteBook() {
        if (library.getBooks().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No books available to delete.");
            return;
        }

        StringBuilder booksInfo = new StringBuilder();
        for (int i = 0; i < library.getBooks().size(); i++) {
            booksInfo.append(i).append(": ").append(library.getBooks().get(i)).append("\n");
        }
        try {
            int index = Integer.parseInt(JOptionPane.showInputDialog("Enter book index to delete:\n" + booksInfo));
            library.deleteBook(index);
            JOptionPane.showMessageDialog(null, "Book deleted successfully!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid input!");
        }
    }

    private void viewBorrowedBooks() {
        StringBuilder borrowedBooksInfo = new StringBuilder();
        boolean hasBorrowedBooks = false;

        for (BorrowedBook borrowedBook : library.getBorrowedBooks()) {
            borrowedBooksInfo.append(borrowedBook).append("\n");
            hasBorrowedBooks = true;
        }

        if (!hasBorrowedBooks) {
            JOptionPane.showMessageDialog(null, "No books borrowed.");
        } else {
            JOptionPane.showMessageDialog(null, borrowedBooksInfo.toString());
        }
    }
    
    public void viewBookReviews() {
        StringBuilder reviewsInfo = new StringBuilder();
        boolean hasReviews = false;

        for (Book book : library.getBooks()) {
            List<Review> reviews = book.getReviews();
            if (!reviews.isEmpty()) {
                reviewsInfo.append("Category: ").append(book.getGenre()).append("\n")
                           .append("Book: ").append(book.getTitle()).append("\n")
                           .append("Reviews:\n");
                for (Review review : reviews) {
                    reviewsInfo.append("Reviewer: ").append(review.getReviewer()).append("\n")
                               .append("Rating: ").append(review.getRating()).append("\n")
                               .append("Comment: ").append(review.getComment()).append("\n\n");
                }
                hasReviews = true;
            }
        }

        if (!hasReviews) {
            JOptionPane.showMessageDialog(null, "No reviews found.");
        } else {
            JOptionPane.showMessageDialog(null, reviewsInfo.toString());
        }
    }
}

