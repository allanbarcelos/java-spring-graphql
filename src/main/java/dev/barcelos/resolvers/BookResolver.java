package dev.barcelos.resolvers;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import dev.barcelos.models.Author;
import dev.barcelos.models.Book;
import dev.barcelos.repositories.AuthorRepository;
import dev.barcelos.repositories.BookRepository;

import java.util.List;

@Controller
public class BookResolver {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookResolver(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @QueryMapping
    public Book bookById(@Argument Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    @QueryMapping
    public List<Book> allBooks() {
        return bookRepository.findAll();
    }

    @QueryMapping
    public Author authorById(@Argument Long id) {
        return authorRepository.findById(id).orElse(null);
    }

    @QueryMapping
    public List<Author> allAuthors() {
        return authorRepository.findAll();
    }

    @SchemaMapping
    public Author author(Book book) {
        return authorRepository.findById(book.getAuthor().getId()).orElse(null);
    }

    @SchemaMapping(typeName = "Author")
    public List<Book> books(Author author) {
        return bookRepository.findByAuthorId(author.getId());
    }

    @QueryMapping
    public List<Book> booksByTitle(@Argument String title) {
        return bookRepository.findByTitleContaining(title);
    }
}