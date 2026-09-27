package demo.book;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

/**
 * Turns on Jakarta REST for this WAR. An empty {@code Application} subclass
 * tells the server to scan for {@code @Path} resources, so {@code web.xml}
 * needs no REST servlet mapping.
 */
@ApplicationPath("/api")
public class LibraryApplication extends Application {
}
