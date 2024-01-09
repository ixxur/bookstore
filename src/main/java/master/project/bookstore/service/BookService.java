package master.project.bookstore.service;

import master.project.bookstore.entity.Book;
import master.project.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public List<Book> addBooks(List<Book> books) {
        List<Book> savedBooks = new ArrayList<>();
        for (Book book : books) {
            savedBooks.add(bookRepository.save(book));
        }
        return savedBooks;
    }

    public List<Book> listBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBook(String title) {
        Book book = bookRepository.findByTitle(title)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        return Optional.ofNullable(book);
    }

    public Optional<List<Book>> findBooksByAuthor(String author) {
        return bookRepository.findByAuthor(author);
    }

    public Optional<List<Book>> findBooksByGenre(String genre) {
        return bookRepository.findByGenre(genre);
    }

    public Optional<Book> findBooksByTitle(String title) {
        return bookRepository.findByTitle(title);
    }
    public String updateBookStock(String title, int quantity) {
        Book book = bookRepository.findByTitle(title)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        int newStock = book.getStock() - quantity;
        if (newStock < 0) {
            throw new RuntimeException("Insufficient stock for " + title);
        }
        book.setStock(newStock);
        bookRepository.save(book);

        return "The updated stock for " + title + " is: " + book.getStock();
    }

    public String deleteBook(String title) {
        Optional<Book> book = bookRepository.findByTitle(title);
        if (book.isPresent()) {
            bookRepository.delete(book.get());
            return "Successfully delete item " + title;
        } else {
            throw new RuntimeException("Book with title " + title + " was not found");
        }
    }
}
