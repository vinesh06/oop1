import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class LibraryUI {
    private Library library;
    private JFrame frame;

    public LibraryUI(Library library) {
        this.library = library;
        initializeUI();
    }

    private void initializeUI() {
        frame = new JFrame("Library Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize the window
        frame.setLocationRelativeTo(null); // Center the window

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);
        frame.setJMenuBar(menuBar);
    }

    public void showMainMenu() {
        frame.getContentPane().removeAll();
        frame.setLayout(new GridLayout(3, 1));

        JButton studentButton = new JButton("Student Login");
        JButton patronButton = new JButton("Patron Login");
        JButton exitButton = new JButton("Exit");

        studentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showStudentLogin();
            }
        });

        patronButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPatronLogin();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        frame.add(studentButton);
        frame.add(patronButton);
        frame.add(exitButton);

        frame.setVisible(true);
    }

    private void showStudentLogin() {
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        Object[] message = {
            "Username:", usernameField,
            "Password:", passwordField
        };

        int option = JOptionPane.showConfirmDialog(frame, message, "Student Login", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            Student student = library.findStudentByUsername(username);
            if (student != null && student.getPassword().equals(password)) {
                showStudentMenu(student);
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showPatronLogin() {
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        Object[] message = {
            "Username:", usernameField,
            "Password:", passwordField
        };

        int option = JOptionPane.showConfirmDialog(frame, message, "Patron Login", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            Patron patron = library.getPatron();
            if (patron.login(username, password)) {
                showPatronMenu(patron);
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showStudentMenu(Student student) {
        frame.getContentPane().removeAll();
        frame.setLayout(new GridLayout(4, 1));

        JButton borrowBookButton = new JButton("Borrow Book");
        JButton returnBookButton = new JButton("Return Book");
        JButton viewBorrowedBooksButton = new JButton("View Borrowed Books");
        JButton logoutButton = new JButton("Logout");

        borrowBookButton.addActionListener(e -> borrowBook(student));
        returnBookButton.addActionListener(e -> returnBook(student));
        viewBorrowedBooksButton.addActionListener(e -> viewBorrowedBooks(student));
        logoutButton.addActionListener(e -> showMainMenu());

        frame.add(borrowBookButton);
        frame.add(returnBookButton);
        frame.add(viewBorrowedBooksButton);
        frame.add(logoutButton);

        frame.revalidate();
        frame.repaint();
    }

    private void showPatronMenu(Patron patron) {
        frame.getContentPane().removeAll();
        frame.setLayout(new GridLayout(5, 1));

        JButton viewStudentDetailsButton = new JButton("View Student Details");
        JButton viewBorrowedBooksButton = new JButton("View Borrowed Books");
        JButton viewAvailableBooksButton = new JButton("View Available Books");
        JButton addBookButton = new JButton("Add Book");
        JButton updateBookButton = new JButton("Update Book");
        JButton logoutButton = new JButton("Logout");

        viewStudentDetailsButton.addActionListener(e -> patron.viewStudentDetails(library));
        viewBorrowedBooksButton.addActionListener(e -> patron.viewBorrowedBooks(library));
        viewAvailableBooksButton.addActionListener(e -> patron.viewAvailableBooks(library));
        addBookButton.addActionListener(e -> addBook());
        updateBookButton.addActionListener(e -> updateBook());
        logoutButton.addActionListener(e -> showMainMenu());

        frame.add(viewStudentDetailsButton);
        frame.add(viewBorrowedBooksButton);
        frame.add(viewAvailableBooksButton);
        frame.add(addBookButton);
        frame.add(updateBookButton);
        frame.add(logoutButton);

        frame.revalidate();
        frame.repaint();
    }

    private void borrowBook(Student student) {
        List<Book> availableBooks = library.getCatalog().listBooks();
        String[] bookOptions = availableBooks.stream().filter(book -> !book.isBorrowed()).map(Book::getDetails).toArray(String[]::new);

        if (bookOptions.length == 0) {
            JOptionPane.showMessageDialog(frame, "No available books to borrow.");
            return;
        }

        String bookChoice = (String) JOptionPane.showInputDialog(frame, "Choose a book to borrow:", "Borrow Book",
                JOptionPane.QUESTION_MESSAGE, null, bookOptions, bookOptions[0]);

        if (bookChoice != null) {
            Book selectedBook = availableBooks.stream().filter(book -> book.getDetails().equals(bookChoice)).findFirst().orElse(null);
            if (selectedBook != null) {
                student.borrowBook(selectedBook);
                library.addTransaction(new Transaction(String.valueOf(System.currentTimeMillis()), selectedBook.getId(), student.getId()));
                JOptionPane.showMessageDialog(frame, "Book borrowed successfully!");
            }
        }
    }

    private void returnBook(Student student) {
        List<Book> borrowedBooks = student.viewBorrowedBooks();
        String[] bookOptions = borrowedBooks.stream().map(Book::getDetails).toArray(String[]::new);

        if (bookOptions.length == 0) {
            JOptionPane.showMessageDialog(frame, "You have not borrowed any books.");
            return;
        }

        String bookChoice = (String) JOptionPane.showInputDialog(frame, "Choose a book to return:", "Return Book",
                JOptionPane.QUESTION_MESSAGE, null, bookOptions, bookOptions[0]);

        if (bookChoice != null) {
            Book selectedBook = borrowedBooks.stream().filter(book -> book.getDetails().equals(bookChoice)).findFirst().orElse(null);
            if (selectedBook != null) {
                student.returnBook(selectedBook);
                JOptionPane.showMessageDialog(frame, "Book returned successfully!");
            }
        }
    }

    private void viewBorrowedBooks(Student student) {
        List<Book> borrowedBooks = student.viewBorrowedBooks();
        if (borrowedBooks.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No books borrowed.");
        } else {
            StringBuilder borrowedBooksList = new StringBuilder("Borrowed Books:\n");
            for (Book book : borrowedBooks) {
                borrowedBooksList.append(book.getDetails()).append(" - ").append("\n");
            }
            JOptionPane.showMessageDialog(frame, borrowedBooksList.toString());
        }
    }

    private void addBook() {
        JTextField idField = new JTextField();
        JTextField titleField = new JTextField();
        JTextField authorField = new JTextField();
        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(new JLabel("Book ID:"));
        panel.add(idField);
        panel.add(new JLabel("Title:"));
        panel.add(titleField);
        panel.add(new JLabel("Author:"));
        panel.add(authorField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Add Book", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String id = idField.getText();
            String title = titleField.getText();
            String author = authorField.getText();
            Book newBook = new Book(id, title, author);
            library.getCatalog().addBook(newBook);
            JOptionPane.showMessageDialog(null, "Book added successfully!");
        }
    }

    private void updateBook() {
        List<Book> availableBooks = library.getCatalog().listBooks();
        String[] bookOptions = availableBooks.stream().map(Book::getDetails).toArray(String[]::new);

        if (bookOptions.length == 0) {
            JOptionPane.showMessageDialog(frame, "No books available to update.");
            return;
        }

        String bookChoice = (String) JOptionPane.showInputDialog(frame, "Choose a book to update:", "Update Book",
                JOptionPane.QUESTION_MESSAGE, null, bookOptions, bookOptions[0]);

        if (bookChoice != null) {
            Book selectedBook = availableBooks.stream().filter(book -> book.getDetails().equals(bookChoice)).findFirst().orElse(null);
            if (selectedBook != null) {
                JTextField titleField = new JTextField(selectedBook.getTitle());
                JTextField authorField = new JTextField(selectedBook.getAuthor());
                JPanel panel = new JPanel(new GridLayout(2, 2));
                panel.add(new JLabel("Title:"));
                panel.add(titleField);
                panel.add(new JLabel("Author:"));
                panel.add(authorField);

                int result = JOptionPane.showConfirmDialog(null, panel, "Update Book", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    selectedBook.setTitle(titleField.getText());
                    selectedBook.setAuthor(authorField.getText());
                    JOptionPane.showMessageDialog(null, "Book updated successfully!");
                }
            }
        }
    }
}
