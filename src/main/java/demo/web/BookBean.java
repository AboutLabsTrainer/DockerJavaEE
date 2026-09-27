package demo.web;

import demo.book.Book;
import demo.book.BookService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;

/**
 * Backing bean for {@code index.xhtml}. {@code @Named} publishes this class
 * to Facelets as {@code bookBean}. {@code @RequestScoped} creates a new
 * instance for each HTTP request, so form fields stay on that request.
 */
@Named
@RequestScoped
public class BookBean {

    @Inject
    private BookService books;

    private String title;
    private String author;

    public List<Book> getBooks() {
        return books.findAll();
    }

    /**
     * Saves the form, then redirects. {@code faces-redirect=true} makes the
     * browser GET the list page, so a refresh does not submit the form again.
     */
    public String add() {
        books.add(title, author);
        title = null;
        author = null;
        return "index?faces-redirect=true";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
