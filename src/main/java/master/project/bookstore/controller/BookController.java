package master.project.bookstore.controller;

import master.project.bookstore.entity.Book;
import master.project.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<List<Book>> addBooks(@RequestBody List<Book> books) {
        List<Book> savedBooks = bookService.addBooks(books);
        return ResponseEntity.ok(savedBooks);
    }
    @GetMapping("/{title}")
    public ResponseEntity getBook(@PathVariable String title) {
        return ResponseEntity.ok(bookService.getBook(title));
    }
    @GetMapping
    public ResponseEntity<List<Book>> listBooks() {
        return ResponseEntity.ok(bookService.listBooks());
    }

    @GetMapping("/author/{author}")
    public ResponseEntity<Optional<List<Book>>> getBooksByAuthor(@PathVariable String author) {
        Optional<List<Book>> books = bookService.findBooksByAuthor(author);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/genre/{genre}")
    public ResponseEntity<Optional<List<Book>>> getBooksByGenre(@PathVariable String genre) {
        Optional<List<Book>> books = bookService.findBooksByGenre(genre);
        return ResponseEntity.ok(books);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{title}")
    public ResponseEntity updateBookStock(@PathVariable String title, @RequestBody int quantity) {
        String response = bookService.updateBookStock(title, quantity);
        return ResponseEntity.ok(response);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{title}")
    public ResponseEntity deleteBook(@PathVariable String title) {
        return ResponseEntity.ok(bookService.deleteBook(title));
    }
}
