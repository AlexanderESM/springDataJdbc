package itk.academy.orekhov.springdatajdbc.repository;

import itk.academy.orekhov.springdatajdbc.entity.Book;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcTemplateBookRepository implements BookRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateBookRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Реализация метода для поиска всех книг
    @Override
    public Iterable<Book> findAll() {
        String sql = "SELECT * FROM books";
        return jdbcTemplate.query(sql, rowMapper());
    }

    // Реализация метода для поиска книги по ID
    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM books WHERE id = ?";
        try {
            Book book = jdbcTemplate.queryForObject(sql, new Object[]{id}, rowMapper());
            return Optional.ofNullable(book);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty(); // Если книга не найдена, возвращаем пустой Optional
        }
    }

    // Реализация метода для поиска книг по автору
    @Override
    public List<Book> findByAuthor(String author) {
        String sql = "SELECT * FROM books WHERE author = ?";
        return jdbcTemplate.query(sql, new Object[]{author}, rowMapper());
    }

    // Реализация метода для поиска книг по году публикации
    @Override
    public List<Book> findByPublicationYear(int year) {
        String sql = "SELECT * FROM books WHERE publication_year = ?";
        return jdbcTemplate.query(sql, new Object[]{year}, rowMapper());
    }

    // Реализация метода для сохранения книги
    @Override
    public Book save(Book book) {
        // Не передаем ID, если он должен быть сгенерирован автоматически
        String sql = "INSERT INTO books (title, author, publication_year) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, book.getTitle(), book.getAuthor(), book.getPublicationYear());

        // После сохранения возвращаем книгу, предполагаем, что ID будет автоматически назначен
        return book;  // В реальной ситуации, можно выполнить SELECT для получения ID, если необходимо
    }
    // Реализация метода для удаления книги по ID
    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM books WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    // Общий RowMapper для преобразования результата SQL-запроса в объекты Book
    private RowMapper<Book> rowMapper() {
        return (rs, rowNum) -> new Book(
                rs.getLong("id"),
                rs.getString("title"),
                rs.getString("author"),
                rs.getInt("publication_year")
        );
    }
}
