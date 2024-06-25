import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Library library = new Library();
        library.populateData();

        boolean exit = false;
        while (!exit) {
            String[] options = {"Admin", "Member"};
            int choice = JOptionPane.showOptionDialog(null, "Login as:", "Library Management System",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            if (choice == JOptionPane.CLOSED_OPTION) {
                exit = true;
                break;
            }

            if (choice == 0) {
                Admin admin = library.getAdmin();
                if (admin == null) {
                    JOptionPane.showMessageDialog(null, "Admin not found!");
                    continue;
                }
                String password = JOptionPane.showInputDialog("Enter admin password:");
                if (password == null) {
                    continue;
                }
                if (admin.verifyPassword(password)) {
                    AdminInterface adminInterface = new AdminInterface(library);
                    adminInterface.start();
                } else {
                    JOptionPane.showMessageDialog(null, "Incorrect password!");
                }
            } else if (choice == 1) {
                String memberId = JOptionPane.showInputDialog("Enter member ID:");
                if (memberId == null) {
                    continue;
                }
                Member member = library.getMemberById(memberId);
                if (member != null) {
                    MemberInterface memberInterface = new MemberInterface(library, member);
                    memberInterface.start();
                } else {
                    JOptionPane.showMessageDialog(null, "Member not found!");
                }
            }
        }
    }
}
