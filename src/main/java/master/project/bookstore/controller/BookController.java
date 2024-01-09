package master.project.bookstore.controller;

import master.project.bookstore.entity.Book;
import master.project.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        return ResponseEntity.ok(bookService.addBook(book));
    }

    @GetMapping
    public ResponseEntity<List<Book>> listBooks() {
        return ResponseEntity.ok(bookService.listBooks());
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{title}")
    public ResponseEntity updateBookStock(@PathVariable String title, @RequestBody int quantity) {
        return (ResponseEntity) ResponseEntity.ok();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{title}")
    public void deleteBook(@PathVariable String title) {
        bookService.deleteBook(title);
    }
}
