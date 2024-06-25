public class Admin extends User {
    private String password;

    public Admin(String id, String password) {
        super(id, "Admin");
        this.password = password;
    }

    public boolean verifyPassword(String password) {
        return this.password.equals(password);
    }
}
