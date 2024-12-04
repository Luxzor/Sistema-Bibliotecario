// src/main/java/mycompany/SpringPruebaMVC/model/entities/LendingsDetailed.java

package mycompany.SpringPruebaMVC.model.entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Table(name = "lendings_detailed")
public class LendingsDetailed {

    @Id
    @Column(name = "lending_id")
    private Integer lendingId;

    @Column(name = "date_out")
    private LocalDate dateOut;

    @Column(name = "date_return")
    private LocalDate dateReturn;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_last_name_p")
    private String userLastNameP;

    @Column(name = "user_last_name_m")
    private String userLastNameM;

    @Column(name = "book_id")
    private Integer bookId;

    @Column(name = "book_title")
    private String bookTitle;

    // Getters y Setters

    public Integer getLendingId() {
        return lendingId;
    }

    public void setLendingId(Integer lendingId) {
        this.lendingId = lendingId;
    }

    public LocalDate getDateOut() {
        return dateOut;
    }

    public void setDateOut(LocalDate dateOut) {
        this.dateOut = dateOut;
    }

    public LocalDate getDateReturn() {
        return dateReturn;
    }

    public void setDateReturn(LocalDate dateReturn) {
        this.dateReturn = dateReturn;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserLastNameP() {
        return userLastNameP;
    }

    public void setUserLastNameP(String userLastNameP) {
        this.userLastNameP = userLastNameP;
    }

    public String getUserLastNameM() {
        return userLastNameM;
    }

    public void setUserLastNameM(String userLastNameM) {
        this.userLastNameM = userLastNameM;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }
}
