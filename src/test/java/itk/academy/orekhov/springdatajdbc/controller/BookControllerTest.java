package itk.academy.orekhov.springdatajdbc.controller;

import itk.academy.orekhov.springdatajdbc.entity.Book;
import itk.academy.orekhov.springdatajdbc.service.BookService;
import itk.academy.orekhov.springdatajdbc.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class BookControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    void testGetAllBooks() throws Exception {
        Iterable<Book> books = List.of(
                new Book(1L, "Book 1", "Author 1", 2021),
                new Book(2L, "Book 2", "Author 2", 2020)
        );

        when(bookService.findAll()).thenReturn(books);

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].title").value("Book 1"))
                .andExpect(jsonPath("$[1].title").value("Book 2"));

        verify(bookService, times(1)).findAll();
    }

    @Test
    void testGetBookById_Success() throws Exception {
        Book book = new Book(1L, "Book 1", "Author 1", 2021);

        when(bookService.findById(1L)).thenReturn(Optional.of(book));

        mockMvc.perform(get("/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Book 1"))
                .andExpect(jsonPath("$.author").value("Author 1"));

        verify(bookService, times(1)).findById(1L);
    }

    @Test
    void testGetBookById_NotFound() throws Exception {
        when(bookService.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/books/1"))
                .andExpect(status().isNotFound());

        verify(bookService, times(1)).findById(1L);
    }

    @Test
    void testCreateBook_Success() throws Exception {
        Book book = new Book(1L, "New Book", "New Author", 2022);
        String bookJson = "{ \"title\": \"New Book\", \"author\": \"New Author\", \"publicationYear\": 2022 }";

        when(bookService.save(any(Book.class))).thenReturn(book);

        mockMvc.perform(post("/books")
                        .contentType("application/json")
                        .content(bookJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("New Book"))
                .andExpect(jsonPath("$.author").value("New Author"));

        verify(bookService, times(1)).save(any(Book.class));
    }

    @Test
    void testUpdateBook_Success() throws Exception {
        Book existingBook = new Book(1L, "Old Book", "Old Author", 2020);
        Book updatedBook = new Book(1L, "Updated Book", "Updated Author", 2021);
        String bookJson = "{ \"title\": \"Updated Book\", \"author\": \"Updated Author\", \"publicationYear\": 2021 }";

        when(bookService.findById(1L)).thenReturn(Optional.of(existingBook));
        when(bookService.save(any(Book.class))).thenReturn(updatedBook);

        mockMvc.perform(put("/books/1")
                        .contentType("application/json")
                        .content(bookJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Book"))
                .andExpect(jsonPath("$.author").value("Updated Author"));

        verify(bookService, times(1)).save(any(Book.class));
    }

    @Test
    void testUpdateBook_NotFound() throws Exception {
        Book book = new Book(1L, "Updated Book", "Updated Author", 2021);
        String bookJson = "{ \"title\": \"Updated Book\", \"author\": \"Updated Author\", \"publicationYear\": 2021 }";

        when(bookService.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/books/1")
                        .contentType("application/json")
                        .content(bookJson))
                .andExpect(status().isNotFound());

        verify(bookService, times(1)).findById(1L);
    }

    @Test
    void testDeleteBook_Success() throws Exception {
        when(bookService.findById(1L)).thenReturn(Optional.of(new Book(1L, "Book 1", "Author 1", 2021)));

        mockMvc.perform(delete("/books/1"))
                .andExpect(status().isNoContent());

        verify(bookService, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteBook_NotFound() throws Exception {
        when(bookService.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/books/1"))
                .andExpect(status().isNotFound());

        verify(bookService, times(1)).findById(1L);
    }
}
