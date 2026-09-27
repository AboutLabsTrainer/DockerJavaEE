package demo.book;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.event.Startup;
import jakarta.inject.Inject;

/**
 * Inserts the sample catalog once. CDI fires {@link Startup} after the
 * application is ready and this bean can be injected. The empty-table
 * check keeps a redeploy from inserting the same three books again.
 */
@ApplicationScoped
public class BookSeeder {

    @Inject
    private BookService books;

    public void onStart(@Observes Startup event) {
        if (!books.findAll().isEmpty()) {
            return;
        }
        books.add("The Jakarta EE Tutorial", "Eclipse Foundation");
        books.add("Effective Java", "Joshua Bloch");
        books.add("Domain-Driven Design", "Eric Evans");
    }
}
