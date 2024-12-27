package itk.academy.orekhov.springdatajdbc.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

public class BookTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        // Создаем экземпляр валидатора для проверки аннотаций
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testBookConstructor() {
        // Тестируем создание объекта через конструктор с параметрами
        Book book = new Book(1L, "Effective Java", "Joshua Bloch", 2008);

        assertNotNull(book);
        assertEquals(1L, book.getId());
        assertEquals("Effective Java", book.getTitle());
        assertEquals("Joshua Bloch", book.getAuthor());
        assertEquals(2008, book.getPublicationYear());
    }

    @Test
    public void testBookSettersAndGetters() {
        // Тестируем геттеры и сеттеры
        Book book = new Book();
        book.setId(2L);
        book.setTitle("Clean Code");
        book.setAuthor("Robert C. Martin");
        book.setPublicationYear(2008);

        assertEquals(2L, book.getId());
        assertEquals("Clean Code", book.getTitle());
        assertEquals("Robert C. Martin", book.getAuthor());
        assertEquals(2008, book.getPublicationYear());
    }

    @Test
    public void testBookToString() {
        // Тестируем метод toString
        Book book = new Book(1L, "Effective Java", "Joshua Bloch", 2008);
        String expectedString = "Book{id=1, title='Effective Java', author='Joshua Bloch', publicationYear=2008}";

        assertEquals(expectedString, book.toString());
    }

    @Test
    public void testBookEqualsAndHashCode() {
        // Тестируем equals и hashCode
        Book book1 = new Book(1L, "Effective Java", "Joshua Bloch", 2008);
        Book book2 = new Book(1L, "Effective Java", "Joshua Bloch", 2008);
        Book book3 = new Book(2L, "Clean Code", "Robert C. Martin", 2008);

        // Проверка equals
        assertTrue(book1.equals(book2));
        assertFalse(book1.equals(book3));

        // Проверка hashCode
        assertEquals(book1.hashCode(), book2.hashCode());
        assertNotEquals(book1.hashCode(), book3.hashCode());
    }

    @Test
    public void testValidationNotBlank() {
        // Тестируем валидацию поля title
        Book book = new Book(null, "Clean Code", "Robert C. Martin", 2008);
        Set<jakarta.validation.ConstraintViolation<Book>> violations = validator.validate(book);
        assertFalse(violations.isEmpty());

        // Тестируем валидацию поля author
        book = new Book(1L, "Clean Code", null, 2008);
        violations = validator.validate(book);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testValidationMin() {
        // Тестируем валидацию поля publicationYear
        Book book = new Book(1L, "Clean Code", "Robert C. Martin", -2008);
        Set<jakarta.validation.ConstraintViolation<Book>> violations = validator.validate(book);
        assertFalse(violations.isEmpty());
    }
}
