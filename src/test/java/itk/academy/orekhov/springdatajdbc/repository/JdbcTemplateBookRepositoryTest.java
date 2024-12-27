package itk.academy.orekhov.springdatajdbc.repository;

import itk.academy.orekhov.springdatajdbc.entity.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class JdbcTemplateBookRepositoryTest {

    @Autowired
    private JdbcTemplateBookRepository bookRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Создаем тестовые данные
    @BeforeEach
    public void setUp() {
        // Очистка базы данных перед каждым тестом
        jdbcTemplate.update("DELETE FROM books");

        // Сохранение тестовых книг в базе данных
        jdbcTemplate.update("INSERT INTO books (id, title, author, publication_year) VALUES (?, ?, ?, ?)",
                1L, "Java Basics", "John Doe", 2020);
        jdbcTemplate.update("INSERT INTO books (id, title, author, publication_year) VALUES (?, ?, ?, ?)",
                2L, "Advanced Java", "Jane Smith", 2021);
        jdbcTemplate.update("INSERT INTO books (id, title, author, publication_year) VALUES (?, ?, ?, ?)",
                3L, "Spring Framework", "John Doe", 2022);
    }

    // Тестируем метод поиска всех книг
    @Test
    public void testFindAll() {
        Iterable<Book> books = bookRepository.findAll();
        assertThat(books).hasSize(3); // Ожидаем, что будет 3 книги
    }

    // Тестируем метод поиска книги по ID
    @Test
    public void testFindById() {
        Optional<Book> book = bookRepository.findById(1L);
        assertThat(book).isPresent(); // Книга с таким ID должна быть найдена
        assertThat(book.get().getTitle()).isEqualTo("Java Basics");
    }

    // Тестируем метод поиска книг по автору
    @Test
    public void testFindByAuthor() {
        List<Book> booksByJohnDoe = bookRepository.findByAuthor("John Doe");
        assertThat(booksByJohnDoe).hasSize(2); // Должно быть 2 книги от John Doe
        assertThat(booksByJohnDoe.get(0).getAuthor()).isEqualTo("John Doe");
    }

    // Тестируем метод поиска книг по году публикации
    @Test
    public void testFindByPublicationYear() {
        List<Book> books2021 = bookRepository.findByPublicationYear(2021);
        assertThat(books2021).hasSize(1); // Должна быть 1 книга, опубликованная в 2021 году
        assertThat(books2021.get(0).getPublicationYear()).isEqualTo(2021);
    }

    // Тестируем метод сохранения книги
    @Test
    public void testSave() {
        Book newBook = new Book(4L, "Spring Boot", "John Doe", 2023);
        Book savedBook = bookRepository.save(newBook);

        assertThat(savedBook).isNotNull();
        assertThat(savedBook.getTitle()).isEqualTo("Spring Boot");
        assertThat(savedBook.getAuthor()).isEqualTo("John Doe");
    }

    // Тестируем метод удаления книги по ID
    @Test
    public void testDeleteById() {
        bookRepository.deleteById(3L);
        Optional<Book> deletedBook = bookRepository.findById(3L);
        assertThat(deletedBook).isNotPresent(); // Книга должна быть удалена и больше не существовать в базе
    }
}
