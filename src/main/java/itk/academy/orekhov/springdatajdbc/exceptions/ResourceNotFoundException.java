package itk.academy.orekhov.springdatajdbc.exceptions;

// Класс исключения, которое будет выбрасываться, когда ресурс не найден
// Наследуется от RuntimeException, что означает, что это unchecked исключение
public class ResourceNotFoundException extends RuntimeException {

    // Конструктор, принимающий сообщение исключения
    // Это сообщение будет передано в конструктор родительского класса RuntimeException
    public ResourceNotFoundException(String message) {
        super(message); // Передаем сообщение в родительский класс для дальнейшего использования
    }
}
