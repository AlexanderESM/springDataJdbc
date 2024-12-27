package itk.academy.orekhov.springdatajdbc.entity;

import org.springframework.data.annotation.Id;  // Используем аннотацию для Spring Data JDBC
import org.springframework.data.relational.core.mapping.Table; // Для указания таблицы в Spring Data JDBC
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;

import java.util.Objects;

// Класс, представляющий сущность книги
@Table("books")  // Указываем название таблицы для Spring Data JDBC
public class Book {

    @Id // Указывает, что это поле является идентификатором (Primary Key) в базе данных
    private Long id; // Идентификатор книги

    @NotBlank(message = "Title must not be empty") // Обязательное поле
    private String title; // Название книги

    @NotBlank(message = "Author must not be empty") // Обязательное поле
    private String author; // Автор книги

    @Min(value = 0, message = "Publication year must be a positive number") // Проверка на положительное число
    private int publicationYear; // Год публикации книги

    // Конструктор без параметров (необходим для работы с Spring Data JDBC)
    public Book() {
    }

    // Конструктор с параметрами
    public Book(Long id, String title, String author, int publicationYear) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
    }

    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publicationYear=" + publicationYear +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return publicationYear == book.publicationYear &&
                id.equals(book.id) &&
                title.equals(book.title) &&
                author.equals(book.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, publicationYear);
    }
}
