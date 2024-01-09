package master.project.bookstore.service;

import master.project.bookstore.entity.Book;
import master.project.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public List<Book> listBooks() {
        return bookRepository.findAll();
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

    public void deleteBook(String title) {
        Optional<Book> book = bookRepository.findByTitle(title);
        if (book.isPresent()) {
            bookRepository.delete(book.get());
        } else {
            throw new RuntimeException("Book with title " + title + " was not found");
        }
    }
}
