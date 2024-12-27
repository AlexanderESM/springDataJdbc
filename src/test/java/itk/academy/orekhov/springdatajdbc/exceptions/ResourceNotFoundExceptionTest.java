package itk.academy.orekhov.springdatajdbc.exceptions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ResourceNotFoundExceptionTest {

    @Test
    @DisplayName("Проверка сообщения исключения при передаче корректного текста")
    void testExceptionMessage() {
        // Given
        String message = "Resource not found";

        // When
        ResourceNotFoundException exception = new ResourceNotFoundException(message);

        // Then
        assertNotNull(exception, "Исключение не должно быть null");
        assertEquals(message, exception.getMessage(), "Сообщение исключения должно совпадать с переданным");
    }

    @Test
    @DisplayName("Проверка исключения без сообщения")
    void testExceptionWithoutMessage() {
        // When
        ResourceNotFoundException exception = new ResourceNotFoundException(null);

        // Then
        assertNotNull(exception, "Исключение не должно быть null");
        assertNull(exception.getMessage(), "Сообщение исключения должно быть null");
    }

    @Test
    @DisplayName("Проверка выброса исключения с сообщением")
    void testThrowException() {
        // Given
        String message = "Resource not found";

        // When & Then
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> { throw new ResourceNotFoundException(message); },
                "Исключение ResourceNotFoundException должно быть выброшено"
        );

        assertEquals(message, exception.getMessage(), "Сообщение выброшенного исключения должно совпадать");
    }
}
