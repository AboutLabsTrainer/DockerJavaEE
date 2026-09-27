package demo.web;

import demo.book.Book;
import demo.book.BookService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;

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
