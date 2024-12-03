package mycompany.SpringPruebaMVC.model.entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

@Entity
@Table(name = "lendings")
public class Lending {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false) // LAZY para evitar cargas innecesarias
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull(message = "El usuario es obligatorio.")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "book_id", nullable = false)
    @NotNull(message = "El libro es obligatorio.")  
    private Book book;

    @Column(name = "date_out", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "La fecha de salida es obligatoria.")
    @PastOrPresent(message = "La fecha de salida no puede ser en el futuro.")
    private LocalDate dateOut;

    @Column(name = "date_return")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateReturn;

    // Constructores
    public Lending() {
    }

    public Lending(User user, Book book, LocalDate dateOut, LocalDate dateReturn) {
        this.user = user;
        this.book = book;
        this.dateOut = dateOut;
        this.dateReturn = dateReturn;
    }

    // Getters y Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
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
}
