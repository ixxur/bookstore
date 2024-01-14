package master.project.bookstore.service;

import master.project.bookstore.entity.Book;
import master.project.bookstore.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

//import static jdk.internal.org.objectweb.asm.util.CheckClassAdapter.verify;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    public void whenBookFound_thenDeleteBook() {
        String title = "Existing Book";
        Book mockBook = new Book();
        mockBook.setTitle(title);

        when(bookRepository.findByTitle(title)).thenReturn(Optional.of(mockBook));

        String expectedMessage = "Successfully delete item " + title;
        String actualMessage = bookService.deleteBook(title);

        verify(bookRepository, times(1)).delete(mockBook);
        assertEquals(expectedMessage, actualMessage, "The delete message should match the expected output");
    }

    @Test
    public void whenBookNotFound_thenThrowException() {
        String title = "NonExisting Book";
        when(bookRepository.findByTitle(title)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            bookService.deleteBook(title);
        }, "Book with title " + title + " was not found");
    }
}
