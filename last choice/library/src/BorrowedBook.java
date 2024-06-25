import java.text.SimpleDateFormat;
import java.util.Date;

public class BorrowedBook {
    private Book book;
    private Member member;
    private Date borrowDate;
    private Date returnDate;

    public BorrowedBook(Book book, Member member, Date borrowDate) {
        this.book = book;
        this.member = member;
        this.borrowDate = borrowDate;
    }

    public Book getBook() {
        return book;
    }

    public Member getMember() {
        return member;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        StringBuilder builder = new StringBuilder();
        builder.append(book.getTitle()).append(" by ").append(book.getAuthor()).append(" (").append(book.getGenre()).append(") ");
        builder.append("borrowed by ").append(member.getName()).append(" on ").append(dateFormat.format(borrowDate));
        if (returnDate != null) {
            builder.append(" and returned on ").append(dateFormat.format(returnDate));
        }
        return builder.toString();
    }
}
