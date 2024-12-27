package itk.academy.orekhov.springdatajdbc.service;

import itk.academy.orekhov.springdatajdbc.entity.Book;
import itk.academy.orekhov.springdatajdbc.repository.BookRepository;
import itk.academy.orekhov.springdatajdbc.exceptions.ResourceNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service // Аннотация, которая помечает данный класс как сервисный компонент Spring
public class BookService {

    private final BookRepository bookRepository; // Репозиторий для работы с книгами

    // Конструктор для внедрения зависимости BookRepository
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository; // Инициализируем репозиторий
    }

    // Метод для получения всех книг
    public Iterable<Book> findAll() {
        return bookRepository.findAll(); // Возвращаем все книги через репозиторий
    }

    // Метод для поиска книги по ID
    public Optional<Book> findById(Long id) {
        // Если книга не найдена, выбрасываем исключение
        return Optional.ofNullable(bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book with id " + id + " not found")));
    }

    // Метод для сохранения новой книги
    public Book save(Book book) {
        // Проверки на корректность данных перед сохранением
        if (book.getTitle() == null || book.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Book title cannot be null or empty."); // Заголовок книги не может быть пустым
        }
        if (book.getAuthor() == null || book.getAuthor().isEmpty()) {
            throw new IllegalArgumentException("Author name cannot be null or empty."); // Имя автора не может быть пустым
        }
        if (book.getPublicationYear() <= 0) {
            throw new IllegalArgumentException("Publication year must be a positive number."); // Год публикации должен быть положительным числом
        }
        return bookRepository.save(book); // Сохраняем книгу через репозиторий
    }

    // Метод для удаления книги по ID
    public void deleteById(Long id) {
        try {
            bookRepository.deleteById(id); // Пытаемся удалить книгу по ID
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Book with id " + id + " does not exist."); // Если книга не найдена, выбрасываем исключение
        }
    }
}
