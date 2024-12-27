package itk.academy.orekhov.springdatajdbc.service;

import itk.academy.orekhov.springdatajdbc.entity.Book;
import itk.academy.orekhov.springdatajdbc.exceptions.ResourceNotFoundException;
import itk.academy.orekhov.springdatajdbc.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class BookServiceTest {

    @Mock
    private BookRepository bookRepository; // Мок репозитория

    @InjectMocks
    private BookService bookService; // Сервис с внедренным зависимым репозиторием

    private Book book;

    @BeforeEach
    void setUp() {
        // Инициализация Mockito
        MockitoAnnotations.openMocks(this);
        book = new Book(1L, "Java Basics", "John Doe", 2020); // Пример книги
    }

    // Тестируем метод findAll()
    @Test
    void testFindAll() {
        // Given
        Iterable<Book> books = List.of(book);
        when(bookRepository.findAll()).thenReturn(books);

        // When
        Iterable<Book> result = bookService.findAll();

        // Then
        assertNotNull(result);
        assertTrue(result.iterator().hasNext());
        verify(bookRepository, times(1)).findAll();
    }

    // Тестируем метод findById()
    @Test
    void testFindByIdWhenBookExists() {
        // Given
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        // When
        Optional<Book> result = bookService.findById(1L);

        // Then
        assertTrue(result.isPresent());
        assertEquals(book.getTitle(), result.get().getTitle());
        verify(bookRepository, times(1)).findById(1L);
    }

    // Тестируем метод findById() когда книга не найдена
    @Test
    void testFindByIdWhenBookNotFound() {
        // Given
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            bookService.findById(1L);
        });
        assertEquals("Book with id 1 not found", exception.getMessage());
        verify(bookRepository, times(1)).findById(1L);
    }

    // Тестируем метод save() с корректными данными
    @Test
    void testSaveValidBook() {
        // Given
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        // When
        Book result = bookService.save(book);

        // Then
        assertNotNull(result);
        assertEquals(book.getTitle(), result.getTitle());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    // Тестируем метод save() с некорректными данными (пустой заголовок)
    @Test
    void testSaveBookWithEmptyTitle() {
        // Given
        Book invalidBook = new Book(2L, "", "John Doe", 2020);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            bookService.save(invalidBook);
        });
        assertEquals("Book title cannot be null or empty.", exception.getMessage());
        verify(bookRepository, times(0)).save(any(Book.class));
    }

    // Тестируем метод deleteById()
    @Test
    void testDeleteByIdWhenBookExists() {
        // Given
        doNothing().when(bookRepository).deleteById(1L);

        // When
        bookService.deleteById(1L);

        // Then
        verify(bookRepository, times(1)).deleteById(1L);
    }

    // Тестируем метод deleteById() когда книга не найдена
    @Test
    void testDeleteByIdWhenBookNotFound() {
        // Given
        doThrow(new ResourceNotFoundException("Book with id 1 does not exist."))
                .when(bookRepository).deleteById(1L);

        // When & Then
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            bookService.deleteById(1L);
        });
        assertEquals("Book with id 1 does not exist.", exception.getMessage());
        verify(bookRepository, times(1)).deleteById(1L);
    }
}
