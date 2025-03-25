package dev.barcelos.resolvers;



import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import dev.barcelos.models.Author;
import dev.barcelos.models.Book;
import dev.barcelos.repositories.AuthorRepository;
import dev.barcelos.repositories.BookRepository;

@Controller
public class MutationResolver {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public MutationResolver(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @MutationMapping
    public Author createAuthor(@Argument String name, @Argument Integer age) {
        Author author = new Author();
        author.setName(name);
        author.setAge(age);
        return authorRepository.save(author);
    }

    @MutationMapping
    public Book createBook(
            @Argument String title,
            @Argument String isbn,
            @Argument Integer pageCount,
            @Argument Long authorId) {
        Book book = new Book();
        book.setTitle(title);
        book.setIsbn(isbn);
        book.setPageCount(pageCount);
        
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new IllegalArgumentException("Author not found"));
        book.setAuthor(author);
        
        return bookRepository.save(book);
    }
}