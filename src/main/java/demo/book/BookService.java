package demo.book;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

/**
 * Application-wide book operations. {@code @ApplicationScoped} means one
 * instance for the whole application. The {@code EntityManager} is
 * container-managed: Payara injects it, joins it to the current JTA
 * transaction, and closes it. Application code must not call {@code close()}.
 */
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

    /**
     * {@code persist} needs a transaction. {@code @Transactional} starts a
     * JTA transaction for this method and commits it on a normal return.
     */
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
