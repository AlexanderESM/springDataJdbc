package itk.academy.orekhov.springdatajdbc.exceptions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.request.WebRequest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class GlobalExceptionHandlerTest {

    @Autowired
    private GlobalExceptionHandler globalExceptionHandler;

    @Test
    public void testHandleIllegalArgumentException() {
        // Создаем исключение IllegalArgumentException с сообщением
        IllegalArgumentException ex = new IllegalArgumentException("Invalid argument");

        // Вызываем метод обработки исключения
        ResponseEntity<GlobalExceptionHandler.ErrorResponse> responseEntity = globalExceptionHandler.handleIllegalArgumentException(ex, null);

        // Проверяем статус ответа и сообщение
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Invalid argument", responseEntity.getBody().getMessage());
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getBody().getStatusCode());
    }

    @Test
    public void testHandleGeneralException() {
        // Создаем общее исключение
        Exception ex = new Exception("Unexpected error");

        // Вызываем метод обработки исключения
        ResponseEntity<GlobalExceptionHandler.ErrorResponse> responseEntity = globalExceptionHandler.handleGeneralException(ex, null);

        // Проверяем статус ответа и сообщение
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("An unexpected error occurred", responseEntity.getBody().getMessage());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getBody().getStatusCode());
    }
}
