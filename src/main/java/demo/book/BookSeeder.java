package demo.book;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.event.Startup;
import jakarta.inject.Inject;

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
