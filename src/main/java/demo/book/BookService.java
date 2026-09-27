package demo.book;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class BookService {

    @PersistenceContext(unitName = "libraryPU")
    private EntityManager em;

    public List<Book> findAll() {
        return em.createQuery("SELECT b FROM Book b ORDER BY b.title", Book.class)
                .getResultList();
    }

    public Book find(Long id) {
        return em.find(Book.class, id);
    }

    @Transactional
    public Book add(String title, String author) {
        Book book = new Book(title, author);
        em.persist(book);
        return book;
    }

    @Transactional
    public boolean delete(Long id) {
        Book book = em.find(Book.class, id);
        if (book == null) {
            return false;
        }
        em.remove(book);
        return true;
    }
}
