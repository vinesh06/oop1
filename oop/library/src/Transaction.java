public class Transaction {
    private String id;
    private String bookId;
    private String userId;

    public Transaction(String id, String bookId, String userId) {
        this.id = id;
        this.bookId = bookId;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public String getBookId() {
        return bookId;
    }

    public String getUserId() {
        return userId;
    }
}
