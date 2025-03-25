package dev.barcelos.seeders;


import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import dev.barcelos.models.Author;
import dev.barcelos.models.Book;
import dev.barcelos.repositories.AuthorRepository;
import dev.barcelos.repositories.BookRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final Random random = new Random();

    public DatabaseSeeder(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (authorRepository.count() == 0 && bookRepository.count() == 0) {
            seedDatabase();
        }
    }

    private void seedDatabase() {
        // Criar autores famosos
        List<Author> authors = new ArrayList<>();
        authors.add(new Author(null, "J.K. Rowling", 57, null));
        authors.add(new Author(null, "George R.R. Martin", 74, null));
        authors.add(new Author(null, "Stephen King", 75, null));
        authors.add(new Author(null, "Agatha Christie", 85, null));
        authors.add(new Author(null, "J.R.R. Tolkien", 81, null));
        authors.add(new Author(null, "Dan Brown", 58, null));
        authors.add(new Author(null, "Haruki Murakami", 73, null));
        authors.add(new Author(null, "Paulo Coelho", 75, null));
        authors.add(new Author(null, "Machado de Assis", 69, null));
        authors.add(new Author(null, "Clarice Lispector", 56, null));
        
        authorRepository.saveAll(authors);

        // Gerar 100 livros
        List<Book> books = new ArrayList<>();
        String[] genres = {"Fantasia", "Ficção Científica", "Romance", "Mistério", "Terror", "Aventura", "Biografia", "História", "Poesia"};
        
        for (int i = 1; i <= 100; i++) {
            Book book = new Book();
            book.setTitle(gerarTituloLivro(i));
            book.setIsbn(gerarISBN());
            book.setPageCount(150 + random.nextInt(400)); // Entre 150 e 550 páginas
            book.setAuthor(authors.get(random.nextInt(authors.size())));
            
            books.add(book);
        }
        
        bookRepository.saveAll(books);
    }

    private String gerarTituloLivro(int numero) {
        String[] prefixos = {"O", "A", "Os", "As", "O Grande", "A Incrível", "O Último", "O Primeiro", "A História de", "As Crônicas de"};
        String[] temas = {"Segredo", "Mistério", "Herói", "Tempo", "Rei", "Rainha", "Dragão", "Feiticeiro", "Assassino", "Amor", "Ódio", "Guerra", "Paz", "Viagem", "Destino"};
        String[] sufixos = {"Perdido", "Esquecido", "Proibido", "Sagrado", "Maldito", "Final", "Inicial", "Oculto", "Vermelho", "Negro", "Branco", "Dourado"};
        
        return prefixos[random.nextInt(prefixos.length)] + " " + 
               temas[random.nextInt(temas.length)] + " " + 
               sufixos[random.nextInt(sufixos.length)] + " " + numero;
    }

    private String gerarISBN() {
        // Gerar um ISBN fictício no formato 978-XXXX-XXXX-X
        return "978-" + 
               (1000 + random.nextInt(9000)) + "-" + 
               (1000 + random.nextInt(9000)) + "-" + 
               random.nextInt(10);
    }
}