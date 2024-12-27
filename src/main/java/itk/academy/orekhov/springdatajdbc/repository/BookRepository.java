package itk.academy.orekhov.springdatajdbc.repository;

import itk.academy.orekhov.springdatajdbc.entity.Book;
import java.util.List;
import java.util.Optional;

public interface BookRepository {

    // Метод для поиска всех книг
    Iterable<Book> findAll();

    // Метод для поиска книги по ID
    Optional<Book> findById(Long id);

    // Метод для поиска книг по автору
    List<Book> findByAuthor(String author);

    // Метод для поиска книг по году публикации
    List<Book> findByPublicationYear(int year);

    // Метод для сохранения книги
    Book save(Book book);

    // Метод для удаления книги по ID
    void deleteById(Long id);
}
